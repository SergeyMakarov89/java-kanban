import java.util.*;

public class TaskManager {

    int numberOfTaskIds = 0;
    HashMap<Integer, Task> taskMap = new HashMap();
    HashMap<Integer, Epic> epicMap = new HashMap();
    HashMap<Integer, SubTask> subTaskMap = new HashMap();

    public void makeNewTask(Task task) {

        numberOfTaskIds++;

        task.setId(numberOfTaskIds);

        taskMap.put(numberOfTaskIds, task);
        System.out.println("Задача с названием: '" + task.name + "' успешно создана.");
    }

    public void makeNewEpic(Epic epic) {

        numberOfTaskIds++;

        epic.setId(numberOfTaskIds);
        epicMap.put(numberOfTaskIds, epic);
        System.out.println("Эпик с названием: '" + epic.name + "' успешно создан.");
    }

    public void makeNewSubTask(SubTask subTask) {




        if (epicMap.isEmpty()) {
            System.out.println("Создание новой Подазачи невозможно, так как отсутсвтуют Эпики. Создайте Эпик.");
        } else {
            if (epicMap.containsKey(subTask.getParrentId())) {

                numberOfTaskIds++;

                subTask.setId(numberOfTaskIds);
                Epic epic = epicMap.get(subTask.getParrentId());
                ArrayList<Integer> newSubTaskArray = epic.getSubTaskList();
                newSubTaskArray.add(numberOfTaskIds);
                epic.setSubTaskList(newSubTaskArray);
                epicMap.put(epic.getId(), epic);
                if (epic.getStatus() == StatusTypes.IN_PROGRESS) {
                    subTask.setStatus(StatusTypes.IN_PROGRESS);
                }
                subTaskMap.put(numberOfTaskIds, subTask);
                System.out.println("Подзадача с названием: '" + subTask.name + "' успешно создана.");
            } else {
                System.out.println("Ошибка - введите корректный id");
            }
        }
    }

    public void printAllTasksAndEpicsWithSubtasks() {
        if (taskMap.isEmpty() && epicMap.isEmpty() && subTaskMap.isEmpty()) {
            System.out.println("Списки Задач и Эпиков с Подзадачами пусты.\nСоздайте Задачу или Эпик.");
        } else {
            System.out.println("\nСписок текущих Задач:");
            if (taskMap.isEmpty()) {
                System.out.println("Список Задач пуст.");
            }
            for (Task task : taskMap.values()) {
                System.out.println(task);
            }
            System.out.println();

            System.out.println("Список текущих Эпиков:");
            if (epicMap.isEmpty()) {
                System.out.println("Список Эпиков пуст.");
            }
            for (Epic epic : epicMap.values()) {
                System.out.println(epic);
                for (Integer i : epic.getSubTaskList()) {
                    SubTask subTask = subTaskMap.get(i);
                    System.out.println(subTask);
                }
                System.out.println();
            }
        }
    }

    public void changeTask(Task task) {

        if (taskMap.isEmpty()) {
            System.out.println("Изменение невозможно, список Задач пуст.\nСоздайте Задачу.");
        } else {
            if (taskMap.containsKey(task.getId())) {
                taskMap.put(task.getId(), task);
                System.out.println("Задача с id: '" + task.getId() + "' успешно изменена.");
            } else {
                System.out.println("Ошибка - введите корректный id");
            }
        }
    }

    public void changeEpic(Epic epic) {

        if (epicMap.isEmpty()) {
            System.out.println("Изменение невозможно, список Эпиков пуст.\nСоздайте Эпик.");
        } else {
            if (epicMap.containsKey(epic.getId())) {
                ArrayList<Integer> subTaskArray = epicMap.get(epic.getId()).getSubTaskList();
                epic.setSubTaskList(subTaskArray);
                epic.setStatus(epicMap.get(epic.getId()).getStatus());
                epicMap.put(epic.getId(), epic);
                System.out.println("Эпик с id: '" + epic.getId() + "' успешно изменен.");
            } else {
                System.out.println("Ошибка - введите корректный id");
            }
        }
    }

    public void changeSubTask(SubTask subTask) {
        int parrentId = 0;
        Epic epic = null;
        int subtaskStatusCount = 0;

        if (epicMap.isEmpty()) {
            System.out.println("Изменение невозможно, список Эпиков пуст.\nСоздайте Эпик.");
        } else {
            if (epicMap.containsKey(subTask.getParrentId())) {
                parrentId = subTaskMap.get(subTask.getId()).getParrentId();
                subTask.setParrentId(parrentId);
                subTaskMap.put(subTask.getId(), subTask);
                System.out.println("Подазадача с id: '" + subTask.getId() + "' успешно изменена.");
                epic = epicMap.get(subTask.getParrentId());
                if (subTask.getStatus() == StatusTypes.IN_PROGRESS) {
                    epic.setStatus(StatusTypes.IN_PROGRESS);
                    epicMap.put(epic.getId(), epic);
                    for (Integer i : epic.getSubTaskList()) {
                        subTaskMap.get(i).setStatus(StatusTypes.IN_PROGRESS);
                    }
                }
                updateStatusEpic(epic.getId());
            } else {
                System.out.println("Ошибка - введите корректный id");
            }
        }

    }

    public void deleteTaskById(int removingId) {

        if (taskMap.isEmpty()) {
            System.out.println("Удаление невозможно, список Задач пуст.");
        } else {
            if (taskMap.containsKey(removingId)) {
                taskMap.remove(removingId);
                System.out.println("Задача с id: '" + removingId + "' успешно удалена.");
            } else {
                System.out.println("Ошибка - введите корректный id");
            }
        }
    }

    public void deleteEpicById(int removingId) {

        if (epicMap.isEmpty()) {
            System.out.println("Удаление невозможно, список Эпиков пуст.");
        } else {
            if (epicMap.containsKey(removingId)) {
                for (Integer i : epicMap.get(removingId).getSubTaskList()) {
                    subTaskMap.remove(i);
                }
                epicMap.remove(removingId);
                System.out.println("Эпик с id: '" + removingId + "' и его подзадачи успешно удалены.");
            } else {
                System.out.println("Ошибка - введите корректный id");
            }
        }
    }

    public void deleteSubTaskById(int removingId) {

        if (subTaskMap.isEmpty()) {
            System.out.println("Удаление невозможно, список Подзадач пуст.");
        } else {
            if (subTaskMap.containsKey(removingId)) {
                (epicMap.get((subTaskMap.get(removingId)).getParrentId())).getSubTaskList().remove(Integer.valueOf(removingId));
                subTaskMap.remove(removingId);
                System.out.println("Подзадача с id: '" + removingId + "' и успешно удалена.");
            } else {
                System.out.println("Ошибка - введите корректный id");
            }
        }
    }

    public void updateStatusEpic(int epicId) {
        int subtaskStatusCount = 0;

        for (Integer i : epicMap.get(epicId).getSubTaskList()) {

            if (subTaskMap.get(i).getStatus() == StatusTypes.IN_PROGRESS) {
               Epic epic = epicMap.get(epicId);
                epic.setStatus(StatusTypes.IN_PROGRESS);
                epicMap.put(epicId, epic);
            }
            if (subTaskMap.get(i).getStatus() == StatusTypes.DONE) {
                subtaskStatusCount++;
                if (subtaskStatusCount == epicMap.get(epicId).getSubTaskList().size()) {
                    Epic epic = epicMap.get(epicId);
                    epic.setStatus(StatusTypes.DONE);
                    epicMap.put(epicId, epic);
                    System.out.println("Все подзадачи в Эпике с названием: '" + epic.name + "' выполнены.");
                    System.out.println("Поздравляем с выполненным Эпиком!");
                }
            }
        }
    }

    public Task getTaskById(int id) {
        return taskMap.get(id);
    }

    public Epic getEpicById(int id) {
        return epicMap.get(id);
    }

    public SubTask getSubTaskById(int id) {
        return subTaskMap.get(id);
    }

    public HashMap<Integer, Task> getAllTasks() {
        return taskMap;
    }

    public HashMap<Integer, Epic> getAllEpics() {
        return epicMap;
    }

    public HashMap<Integer, SubTask> getAllSubTasks() {
        return subTaskMap;
    }

    public ArrayList<Integer> getAllSubTasksByEpicId(int epicId) {
        return epicMap.get(epicId).getSubTaskList();
    }

}