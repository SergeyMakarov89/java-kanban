import java.time.Duration;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    private int numberOfTaskIds = 0;
    protected Map<Integer, Task> taskMap = new HashMap<>();
    protected Map<Integer, Epic> epicMap = new HashMap<>();
    protected Map<Integer, SubTask> subTaskMap = new HashMap<>();
    protected HistoryManager inMemoryHistoryManager = Managers.makeInMemoryHistoryManager();

    protected TreeSet<Task> sortedTasksByTime = new TreeSet<>(new Comparator<Task>() {
        @Override
        public int compare(Task task1, Task task2) {
            return task1.startTime.compareTo(task2.startTime);
        }
    });

    @Override
    public void makeNewTask(Task task) {
        numberOfTaskIds++;

        task.setId(numberOfTaskIds);

        if (addTaskToTreeSet(task)) {
            taskMap.put(numberOfTaskIds, task);
            System.out.println("Задача с названием: '" + task.name + "' успешно создана.");
        }
    }

    @Override
    public void makeNewEpic(Epic epic) {

        numberOfTaskIds++;

        epic.setId(numberOfTaskIds);

        if (addTaskToTreeSet(epic)) {
            epicMap.put(numberOfTaskIds, epic);
            System.out.println("Эпик с названием: '" + epic.name + "' успешно создан.");
        }
    }

    @Override
    public void makeNewSubTask(SubTask subTask) {
        if (epicMap.isEmpty()) {
            System.out.println("Создание новой Подазачи невозможно, так как отсутсвтуют Эпики. Создайте Эпик.");
        } else {
            if (epicMap.containsKey(subTask.getParrentId())) {

                numberOfTaskIds++;

                subTask.setId(numberOfTaskIds);

                if (addTaskToTreeSet(subTask)) {
                    epicMap.get(subTask.getParrentId()).getSubTaskList().add(numberOfTaskIds);
                    subTaskMap.put(numberOfTaskIds, subTask);
                    updateStatusEpic(subTask.getParrentId());
                    epicMap.get(subTask.getParrentId()).setDurationEpic(subTask);
                    epicMap.get(subTask.getParrentId()).setEndTimeEpic(subTask);
                    System.out.println("Подзадача с названием: '" + subTask.name + "' успешно создана.");
                }
            } else {
                System.out.println("Ошибка - введите корректный id");
            }
        }
    }

    @Override
    public void changeTask(Task task) {

        if (taskMap.isEmpty()) {
            System.out.println("Изменение невозможно, список Задач пуст.\nСоздайте Задачу.");
        } else {
            if (taskMap.containsKey(task.getId())) {
                if (addTaskToTreeSet(task)) {
                    taskMap.put(task.getId(), task);
                    System.out.println("Задача с id: '" + task.getId() + "' успешно изменена.");
                }
            } else {
                System.out.println("Ошибка - введите корректный id");
            }
        }
    }

    @Override
    public void changeEpic(Epic epic) {

        if (epicMap.isEmpty()) {
            System.out.println("Изменение невозможно, список Эпиков пуст.\nСоздайте Эпик.");
        } else {
            if (epicMap.containsKey(epic.getId())) {
                if (addTaskToTreeSet(epic)) {
                    ArrayList<Integer> subTaskArray = epicMap.get(epic.getId()).getSubTaskList();
                    epic.setSubTaskList(subTaskArray);
                    epic.setStatus(epicMap.get(epic.getId()).getStatus());
                    epicMap.put(epic.getId(), epic);
                    System.out.println("Эпик с id: '" + epic.getId() + "' успешно изменен.");
                }
            } else {
                System.out.println("Ошибка - введите корректный id");
            }
        }
    }

    @Override
    public void changeSubTask(SubTask subTask) {
        Epic epic = null;

        if (epicMap.isEmpty()) {
            System.out.println("Изменение невозможно, список Эпиков пуст.\nСоздайте Эпик.");
        } else {
            if (epicMap.containsKey(subTask.getParrentId())) {
                if (subTaskMap.get(subTask.getId()) == null) {
                    return;
                }
                if (addTaskToTreeSet(subTask)) {
                    subTaskMap.put(subTask.getId(), subTask);
                    epic = epicMap.get(subTask.getParrentId());
                    updateStatusEpic(epic.getId());
                    addTaskToTreeSet(subTask);
                    System.out.println("Подазадача с id: '" + subTask.getId() + "' успешно изменена.");
                }
            } else {
                System.out.println("Ошибка - введите корректный id");
            }
        }
    }

    @Override
    public void deleteTaskById(int removingId) {
        Task taskToDel = null;
        if (taskMap.isEmpty()) {
            System.out.println("Удаление невозможно, список Задач пуст.");
        } else {
            if (taskMap.containsKey(removingId)) {
                if (taskMap.get(removingId).getStartTime() != null) {
                    for (Task task2 : sortedTasksByTime) {
                        if (removingId == task2.getId()) {
                            taskToDel = task2;
                        }
                    }
                    if (taskToDel != null) {
                        sortedTasksByTime.remove(taskToDel);
                    }
                }
                inMemoryHistoryManager.remove(removingId);
                taskMap.remove(removingId);
                System.out.println("Задача с id: '" + removingId + "' успешно удалена.");
            } else {
                System.out.println("Ошибка - введите корректный id");
            }
        }
    }

    @Override
    public void deleteEpicById(int removingId) {
        Task taskToDel = null;
        Task subTaskToDel = null;

        if (epicMap.isEmpty()) {
            System.out.println("Удаление невозможно, список Эпиков пуст.");
        } else {
            if (epicMap.containsKey(removingId)) {
                for (Integer j : epicMap.get(removingId).getSubTaskList()) {
                    for (Task task : sortedTasksByTime) {
                        if (task.getId() == subTaskMap.get(j).getId()) {
                            subTaskToDel = subTaskMap.get(j);
                        }
                    }
                    if (subTaskToDel != null) {
                        sortedTasksByTime.remove(subTaskToDel);
                    }
                }
                for (Integer i : epicMap.get(removingId).getSubTaskList()) {
                    inMemoryHistoryManager.remove(i);
                    subTaskMap.remove(i);
                }
                if (epicMap.get(removingId).getStartTime() != null) {
                    for (Task task2 : sortedTasksByTime) {
                        if (removingId == task2.getId()) {
                            taskToDel = task2;
                        }
                    }
                    if (taskToDel != null) {
                        sortedTasksByTime.remove(taskToDel);
                    }
                }
                inMemoryHistoryManager.remove(removingId);
                epicMap.remove(removingId);
                System.out.println("Эпик с id: '" + removingId + "' и его подзадачи успешно удалены.");
            } else {
                System.out.println("Ошибка - введите корректный id");
            }
        }
    }

    @Override
    public void deleteSubTaskById(int removingId) {
        Task taskToDel = null;

        if (subTaskMap.isEmpty()) {
            System.out.println("Удаление невозможно, список Подзадач пуст.");
        } else {
            if (subTaskMap.containsKey(removingId)) {
                int epicId = subTaskMap.get(removingId).getParrentId();
                (epicMap.get((subTaskMap.get(removingId)).getParrentId())).getSubTaskList().remove(Integer.valueOf(removingId));
                if (subTaskMap.get(removingId).getStartTime() != null) {
                    for (Task task2 : sortedTasksByTime) {
                        if (removingId == task2.getId()) {
                            taskToDel = task2;
                            Duration newDuration = epicMap.get(subTaskMap.get(removingId).getParrentId()).getDuration().minus(subTaskMap.get(removingId).getDuration());
                            epicMap.get(subTaskMap.get(removingId).getParrentId()).setDurationEpicAfterRemovingSubTask(newDuration);
                        }
                    }
                    if (taskToDel != null) {
                        sortedTasksByTime.remove(taskToDel);
                    }
                }
                inMemoryHistoryManager.remove(removingId);
                subTaskMap.remove(removingId);
                updateStatusEpic(epicId);
                System.out.println("Подзадача с id: '" + removingId + "' и успешно удалена.");
            } else {
                System.out.println("Ошибка - введите корректный id");
            }
        }
    }

    @Override
    public void updateStatusEpic(int epicId) {
        int subtaskStatusCount = 0;
        int newSubtaskStatusCount = 0;
        if (epicMap.get(epicId).getSubTaskList().isEmpty()) {
            epicMap.get(epicId).setStatus(StatusTypes.NEW);
            return;
        }
        if (subTaskMap.isEmpty()) {
            epicMap.get(epicId).setStatus(StatusTypes.NEW);
            return;
        }
        for (Integer i : epicMap.get(epicId).getSubTaskList()) {
            if (subTaskMap.get(i).getStatus() == StatusTypes.NEW) {
                newSubtaskStatusCount++;
                if (newSubtaskStatusCount == epicMap.get(epicId).getSubTaskList().size()) {
                    Epic epic = epicMap.get(epicId);
                    epic.setStatus(StatusTypes.NEW);
                    return;
                }
            }
            if (subTaskMap.get(i).getStatus() == StatusTypes.IN_PROGRESS) {
                Epic epic = epicMap.get(epicId);
                epic.setStatus(StatusTypes.IN_PROGRESS);
                for (Integer j : epicMap.get(epicId).getSubTaskList()) {
                    subTaskMap.get(j).setStatus(StatusTypes.IN_PROGRESS);
                }
                return;
            }
            if (subTaskMap.get(i).getStatus() == StatusTypes.DONE) {
                subtaskStatusCount++;
                if (subtaskStatusCount == epicMap.get(epicId).getSubTaskList().size()) {
                    Epic epic = epicMap.get(epicId);
                    epic.setStatus(StatusTypes.DONE);
                    System.out.println("Все подзадачи в Эпике с названием: '" + epic.name + "' выполнены.");
                    System.out.println("Поздравляем с выполненным Эпиком!");
                    return;
                }
            }
        }
    }

    @Override
    public Task getTaskById(int id) {
        inMemoryHistoryManager.add(taskMap.get(id));
        return taskMap.get(id);
    }

    @Override
    public Epic getEpicById(int id) {
        inMemoryHistoryManager.add(epicMap.get(id));
        return epicMap.get(id);
    }

    @Override
    public SubTask getSubTaskById(int id) {
        inMemoryHistoryManager.add(subTaskMap.get(id));
        return subTaskMap.get(id);
    }

    @Override
    public List<Task> getAllTasks() {
        List<Task> list = new ArrayList<Task>(taskMap.values());
        return list;
    }

    @Override
    public List<Epic> getAllEpics() {
        List<Epic> list = new ArrayList<Epic>(epicMap.values());
        return list;
    }

    @Override
    public List<SubTask> getAllSubTasks() {
        List<SubTask> list = new ArrayList<SubTask>(subTaskMap.values());
        return list;
    }

    @Override
    public List<Integer> getAllSubTasksByEpicId(int epicId) {
        return epicMap.get(epicId).getSubTaskList();
    }

    @Override
    public List<Task> getHistory() {
        return inMemoryHistoryManager.getHistory();
    }

    public boolean addTaskToTreeSet(Task task1) {
        Task taskToDel = null;

        for (Task prioritizedTask : getPrioritizedTasks()) {
            if (task1.getId() == prioritizedTask.getId()) {
                for (Task task2 : sortedTasksByTime) {
                    if (task1.getId() == task2.getId()) {
                        taskToDel = task2;
                    }
                }
                if (taskToDel != null) {
                    sortedTasksByTime.remove(taskToDel);
                }
                sortedTasksByTime.add(task1);
                return true;
            }
        }
        if (task1.getStartTime() != null) {
            if (isTasksOverlap(task1)) {
                System.out.println("Время задачи пересекается с другой задачей, перепланируйте время.");
                return false;
            } else {
                for (Task task2 : sortedTasksByTime) {
                    if (task1.getId() == task2.getId()) {
                        taskToDel = task2;
                    }
                }
                if (taskToDel != null) {
                    sortedTasksByTime.remove(taskToDel);
                }
                sortedTasksByTime.add(task1);
            }
        } else {
            return true;
        }
        return true;
    }

    public TreeSet<Task> getPrioritizedTasks() {
        return sortedTasksByTime;
    }

    public boolean isTasksOverlap(Task newTask) {
        if (newTask.getLocalClass() == Types.SUBTASK) {
            List<Task> subTaskListOfEpic = new ArrayList<>();
            SubTask subTask1 = (SubTask) newTask;
            if ((epicMap.get(subTask1.getParrentId()).getSubTaskList()) != null) {
                for (Integer i : epicMap.get(subTask1.getParrentId()).getSubTaskList()) {
                    subTaskListOfEpic.add(subTaskMap.get(i));
                }
            }
            if (!(subTaskListOfEpic.isEmpty())) {
                return subTaskListOfEpic.stream()
                        .anyMatch(subTask2 -> newTask.getStartTime().isBefore(subTask2.getEndTime()) &&
                                newTask.getEndTime().isAfter(subTask2.getStartTime()));
            } else {
                List<Task> listOfTasksEpicAndSubTasksWithParrentEpic = new ArrayList<>();
                if (taskMap != null) {
                    for (Task task : taskMap.values()) {
                        listOfTasksEpicAndSubTasksWithParrentEpic.add(task);
                    }
                }
                if (epicMap != null) {
                    for (Task epic : epicMap.values()) {
                        if (!(subTask1.getParrentId() == epic.getId())) {
                            listOfTasksEpicAndSubTasksWithParrentEpic.add(epic);
                        }
                    }
                }
                if (subTaskMap != null) {
                    for (Task subTask : subTaskMap.values()) {
                        listOfTasksEpicAndSubTasksWithParrentEpic.add(subTask);
                    }
                }
                if (listOfTasksEpicAndSubTasksWithParrentEpic != null) {
                    return listOfTasksEpicAndSubTasksWithParrentEpic.stream()
                            .anyMatch(task -> newTask.getStartTime().isBefore(task.getEndTime()) &&
                                    newTask.getEndTime().isAfter(task.getStartTime()));
                } else {
                    return false;
                }

            }
        }
        if (newTask.getLocalClass() == Types.EPIC) {
            List<Task> listOfTasksAndEpicsWithoutSubTasks = new ArrayList<>();
            if (taskMap != null) {
                for (Task task : taskMap.values()) {
                    listOfTasksAndEpicsWithoutSubTasks.add(task);
                }
            }
            if (epicMap != null) {
                for (Epic epic : epicMap.values()) {
                    listOfTasksAndEpicsWithoutSubTasks.add(epic);
                }
            }
            if (listOfTasksAndEpicsWithoutSubTasks != null) {
                return listOfTasksAndEpicsWithoutSubTasks.stream()
                        .anyMatch(task -> newTask.getStartTime().isBefore(task.getEndTime()) &&
                                newTask.getEndTime().isAfter(task.getStartTime()));
            }
        }
        return getPrioritizedTasks().stream()
                .anyMatch(prioritizedTask -> newTask.getStartTime().isBefore(prioritizedTask.getEndTime()) &&
                        newTask.getEndTime().isAfter(prioritizedTask.getStartTime()));
    }
}


