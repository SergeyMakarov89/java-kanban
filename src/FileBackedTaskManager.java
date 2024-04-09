import java.io.*;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;

public class FileBackedTaskManager extends InMemoryTaskManager {
    String path;

    public FileBackedTaskManager() {

    }

    public FileBackedTaskManager(String path) {
        this.path = path;
    }

    @Override
    public void makeNewTask(Task task) {
        super.makeNewTask(task);
        save();
    }

    @Override
    public void makeNewEpic(Epic epic) {
        super.makeNewEpic(epic);
        save();
    }

    @Override
    public void makeNewSubTask(SubTask subTask) {
        super.makeNewSubTask(subTask);
        save();
    }

    @Override
    public void changeTask(Task task) {
        super.changeTask(task);
        save();
    }

    @Override
    public void changeEpic(Epic epic) {
        super.changeEpic(epic);
        save();
    }

    @Override
    public void changeSubTask(SubTask subTask) {
        super.changeSubTask(subTask);
        save();
    }

    @Override
    public void deleteTaskById(int removingId) {
        super.deleteTaskById(removingId);
        save();
    }

    @Override
    public void deleteEpicById(int removingId) {
        super.deleteEpicById(removingId);
        save();
    }

    @Override
    public void deleteSubTaskById(int removingId) {
        super.deleteSubTaskById(removingId);
        save();
    }

    @Override
    public Task getTaskById(int id) {
        inMemoryHistoryManager.add(taskMap.get(id));
        save();
        return taskMap.get(id);
    }

    @Override
    public Epic getEpicById(int id) {
        inMemoryHistoryManager.add(epicMap.get(id));
        save();
        return epicMap.get(id);
    }

    @Override
    public SubTask getSubTaskById(int id) {
        inMemoryHistoryManager.add(subTaskMap.get(id));
        save();
        return subTaskMap.get(id);
    }

    public void save() {
        try (Writer fileWriter = new FileWriter(path)) {
            fileWriter.write("id,type,name,status,description,starttime,duration,epic");
            if (!taskMap.isEmpty()) {
                for (Task task : taskMap.values()) {
                    if (task.getStartTime() != null) {
                        fileWriter.write("\n" + task.toStringToFile());
                    }
                }
            }
            if (!epicMap.isEmpty()) {
                for (Epic epic : epicMap.values()) {
                    if (epic.getStartTime() != null) {
                        fileWriter.write("\n" + epic.toStringToFile());
                    }
                }
            }
            if (!subTaskMap.isEmpty()) {
                for (SubTask subTask : subTaskMap.values()) {
                    if (subTask.getStartTime() != null) {
                        fileWriter.write("\n" + subTask.toStringToFile());
                    }
                }
            }
            if (inMemoryHistoryManager.getHistory() != null) {
                fileWriter.write("\n");
                for (int i = 0; i < inMemoryHistoryManager.getHistory().size(); i++) {
                    if (i == inMemoryHistoryManager.getHistory().size() - 1) {
                        int lastId = inMemoryHistoryManager.getHistory().get(i).getId();
                        fileWriter.write(Integer.toString(lastId));
                    } else {
                        fileWriter.write(inMemoryHistoryManager.getHistory().get(i).getId() + ",");
                    }
                }

            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при сохранении", e);
        }
    }

    public void loadFromFile(String path) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            while (bufferedReader.ready()) {
                String line = bufferedReader.readLine();
                String[] subStrings = line.split(",");
                if (subStrings[1].equals("TASK")) {
                    Task task = new Task(subStrings[2], subStrings[4], subStrings[0], subStrings[3], subStrings[5], subStrings[6]);
                    taskMap.put(Integer.parseInt(subStrings[0]), task);
                    addTaskToTreeSet(task);
                } else if (subStrings[1].equals("EPIC")) {
                    Epic epic = new Epic(subStrings[2], subStrings[4], subStrings[0], subStrings[3], subStrings[5], subStrings[6]);
                    epicMap.put(Integer.parseInt(subStrings[0]), epic);
                } else if (subStrings[1].equals("SUBTASK")) {
                    SubTask subTask = new SubTask(subStrings[2], subStrings[4], subStrings[0], subStrings[3], subStrings[5], subStrings[6], subStrings[7]);
                    subTaskMap.put(Integer.parseInt(subStrings[0]), subTask);
                    epicMap.get(Integer.parseInt(subStrings[7])).getSubTaskList().add(Integer.parseInt(subStrings[0]));
                    addTaskToTreeSet(subTask);
                } else if (!(subStrings[1].equals("type"))) {
                    Collections.reverse(Arrays.asList(subStrings));
                    for (String subString : subStrings) {
                        int i = Integer.parseInt(subString);
                        if (taskMap.containsKey(i)) {
                            inMemoryHistoryManager.add(taskMap.get(i));
                        } else if (epicMap.containsKey(i)) {
                            inMemoryHistoryManager.add(epicMap.get(i));
                        } else if (subTaskMap.containsKey(i)) {
                            inMemoryHistoryManager.add(subTaskMap.get(i));
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при сохранении", e);
        }
    }
}
