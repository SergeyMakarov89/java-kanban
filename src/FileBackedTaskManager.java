import javax.imageio.IIOException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileBackedTaskManager extends InMemoryTaskManager  {
    @Override
    public void makeNewTask(Task task) throws IOException, ManagerSaveException {
        super.makeNewTask(task);
        save();
    }
    @Override
    public void makeNewEpic(Epic epic) throws IOException, ManagerSaveException {
        super.makeNewEpic(epic);
        save();
    }
    @Override
    public void makeNewSubTask(SubTask subTask) throws IOException, ManagerSaveException {
        super.makeNewSubTask(subTask);
        save();
    }

    @Override
    public void changeTask(Task task) throws IOException, ManagerSaveException {
        super.changeTask(task);
        save();
    }
    @Override
    public void changeEpic(Epic epic) throws IOException, ManagerSaveException {
        super.changeEpic(epic);
        save();
    }

    @Override
    public void changeSubTask(SubTask subTask) throws IOException, ManagerSaveException {
        super.changeSubTask(subTask);
        save();
    }
    @Override
    public void deleteTaskById(int removingId) throws IOException, ManagerSaveException {
        super.deleteTaskById(removingId);
        save();
    }

    @Override
    public void deleteEpicById(int removingId) throws IOException, ManagerSaveException {
        super.deleteEpicById(removingId);
        save();
    }

    @Override
    public void deleteSubTaskById(int removingId) throws IOException, ManagerSaveException {
        super.deleteSubTaskById(removingId);
        save();
    }

    public void save() throws IOException, ManagerSaveException {
        try (Writer fileWriter = new FileWriter("C:\\Users\\1\\Videos\\java-kanban\\test.csv")) {
            fileWriter.write("id,type,name,status,description,epic");
            if (!taskMap.isEmpty()) {
                for (Task task : taskMap.values()) {
                    fileWriter.write("\n" + task.toStringToFile());
                }
            }
            if (!epicMap.isEmpty()) {
                for (Epic epic : epicMap.values()) {
                    fileWriter.write("\n" + epic.toStringToFile());
                }
            }
            if (!subTaskMap.isEmpty()) {
                for (SubTask subTask : subTaskMap.values()) {
                    fileWriter.write("\n" + subTask.toStringToFile());
                }
            }

        }
        catch (IOException e) {
            throw new ManagerSaveException("Ошибка сохранения");
        }
    }

    public void loadFromFile() throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("test.csv"))) {
            while (bufferedReader.ready()) {
                String line = bufferedReader.readLine();
                String[] subStrings = line.split(",");
                if (subStrings[1].equals("TASK")) {
                    Task task = new Task(subStrings[2], subStrings[4], Integer.parseInt(subStrings[0]), StatusTypes.valueOf(subStrings[3]));
                    taskMap.put(Integer.parseInt(subStrings[0]), task);
                } else if (subStrings[1].equals("EPIC")) {
                    Epic epic = new Epic(subStrings[2], subStrings[4], Integer.parseInt(subStrings[0]), StatusTypes.valueOf(subStrings[3]));
                    epicMap.put(Integer.parseInt(subStrings[0]), epic);
                } else if (subStrings[1].equals("SUBTASK")) {
                    SubTask subTask = new SubTask(subStrings[2], subStrings[4], Integer.parseInt(subStrings[0]), StatusTypes.valueOf(subStrings[3]), Integer.parseInt(subStrings[5]));
                    subTaskMap.put(Integer.parseInt(subStrings[0]), subTask);
                    ArrayList<Integer> newSubTaskList = epicMap.get(Integer.parseInt(subStrings[5])).getSubTaskList();
                    newSubTaskList.add(Integer.parseInt(subStrings[0]));
                    epicMap.get(Integer.parseInt(subStrings[5])).setSubTaskList(newSubTaskList);
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка загрузки");
        }
    }
}
