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


        int epicId = 0;

        if (epicMap.isEmpty()) {
            System.out.println("Создание новой Подазачи невозможно, так как отсутсвтуют Эпики. Создайте Эпик.");
        } else {
            if (epicMap.containsKey(subTask.parrentId)) {

                numberOfTaskIds++;

                subTask.setId(numberOfTaskIds);
                Epic epic = epicMap.get(subTask.parrentId);
                epicId = epic.id;
                epic.subTaskList.add(numberOfTaskIds);
                epicMap.put(epicId, epic);
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
                for (Integer i : epic.subTaskList) {
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
        ArrayList<Integer> subTaskArray = null;

        if (epicMap.isEmpty()) {
            System.out.println("Изменение невозможно, список Эпиков пуст.\nСоздайте Эпик.");
        } else {
            if (epicMap.containsKey(epic.getId())) {
                subTaskArray = epicMap.get(epic.getId()).subTaskList;
                epic.subTaskList = subTaskArray;
                epicMap.put(epic.getId(), epic);
                System.out.println("Эпик с id: '" + epic.getId() + "' успешно изменен.");
                if (epic.getStatus() == StatusTypes.IN_PROGRESS) {
                    for (Integer i : subTaskArray) {
                        (subTaskMap.get(i)).setStatus(StatusTypes.IN_PROGRESS);
                    }
                }
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
            if (epicMap.containsKey(subTask.parrentId)) {
                parrentId = subTaskMap.get(subTask.getId()).parrentId;
                subTask.setParrentId(parrentId);
                subTaskMap.put(subTask.getId(), subTask);
                System.out.println("Подазадача с id: '" + subTask.getId() + "' успешно изменена.");

                epic = epicMap.get(subTask.parrentId);
                if (subTask.getStatus() == StatusTypes.IN_PROGRESS) {
                    epic.setStatus(StatusTypes.IN_PROGRESS);
                    epicMap.put(epic.getId(), epic);
                }
                if (subTask.getStatus() == StatusTypes.DONE) {
                    for (SubTask subTask1: subTaskMap.values()) {
                        if (subTask1.parrentId == subTask.getParrentId()) {
                            if (subTask1.getStatus() == StatusTypes.DONE) {
                                subtaskStatusCount++;
                                if (subtaskStatusCount == epic.subTaskList.size()) {
                                    epic.setStatus(StatusTypes.DONE);
                                    epicMap.put(epic.getId(), epic);
                                    System.out.println("Все подзадачи в Эпике с названием: '" + epic.name + "' выполнены.");
                                    System.out.println("Поздравляем с выполненным Эпиком!");
                                    subtaskStatusCount = 0;
                                }
                            }
                        }
                    }
                }
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
                for (Integer i : epicMap.get(removingId).subTaskList) {
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
                (epicMap.get((subTaskMap.get(removingId)).parrentId)).subTaskList.remove(Integer.valueOf(removingId));
                subTaskMap.remove(removingId);
                System.out.println("Подзадача с id: '" + removingId + "' и успешно удалена.");
            } else {
                System.out.println("Ошибка - введите корректный id");
            }
        }
    }

    public void printTaskById(int id) {

        if (taskMap.isEmpty()) {
            System.out.println("Вывод невозможен, список Задач пуст.");
        } else {
            if (taskMap.containsKey(id)) {
                System.out.println(taskMap.get(id));
            } else {
                System.out.println("Ошибка - введите корректный id");
            }
        }
    }

    public void printEpicById(int id) {

        if (epicMap.isEmpty()) {
            System.out.println("Вывод невозможен, список Эпиков пуст.");
        } else {
            if (epicMap.containsKey(id)) {
                System.out.println(epicMap.get(id));
                for (Integer i : epicMap.get(id).subTaskList) {
                    System.out.println(subTaskMap.get(i));
                }
            } else {
                System.out.println("Ошибка - введите корректный id");
            }
        }
    }

    public void printSubTaskById(int id) {

        if (subTaskMap.isEmpty()) {
            System.out.println("Вывод невозможен, список Подзадач пуст.");
        } else {
            if (subTaskMap.containsKey(id)) {
                System.out.println(subTaskMap.get(id));
            } else {
                System.out.println("Ошибка - введите корректный id");
            }
        }
    }

    public void printAllTasks() {

        if (taskMap.isEmpty()) {
            System.out.println("Вывод невозможен, список Задач пуст.");
        } else {
            System.out.println("Список всех задач:");
            for (Task task : taskMap.values()) {
                System.out.println(task);
            }
        }
    }

    public void printAllEpics() {

        if (epicMap.isEmpty()) {
            System.out.println("Вывод невозможен, список Эпиков пуст.");
        } else {
            System.out.println("Список всех эпиков:");
            for (Epic epic : epicMap.values()) {
                System.out.println(epic);
            }
        }
    }

    public void printAllSubTasks() {

        if (subTaskMap.isEmpty()) {
            System.out.println("Вывод невозможен, список Подзадач пуст.");
        } else {
            System.out.println("Список всех Подзадач:");
            for (SubTask subTask : subTaskMap.values()) {
                System.out.println(subTask);
            }
        }
    }
}