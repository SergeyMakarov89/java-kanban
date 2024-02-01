import java.util.HashMap;
import java.util.Scanner;

public class TaskManager {

    int numberOfTaskIds = 0;
    HashMap<Integer, Task> taskMap = new HashMap();
    HashMap<Integer, Epic> epicMap = new HashMap();
    HashMap<Integer, SubTask> subTaskMap = new HashMap();


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

    public void printById(Scanner scanner) {
        int id = 0;

        System.out.println("\nВведите id Задачи, Эпика или Подзадачи, которую нужно вывести:");
        id = scanner.nextInt();

        if (taskMap.isEmpty() && epicMap.isEmpty() && subTaskMap.isEmpty()) {
            System.out.println("Вывод невозможен, списки Задач, Эпиков и Подзадач пусты.");
        } else {
                if (taskMap.containsKey(id)) {
                    System.out.println(taskMap.get(id));
                } else if (epicMap.containsKey(id)) {
                    System.out.println(epicMap.get(id));
                } else if (subTaskMap.containsKey(id)) {
                    System.out.println(subTaskMap.get(id));
                } else {
                    System.out.println("Ошибка - введите корректный id");
                }
        }
    }

    public void makeNewTask(Scanner scanner) {
        String taskName = "";
        String taskDiscription = "";

        System.out.println("\nВведите название Задачи:");
        taskName = scanner.next();
        System.out.println("\nВведите описание Задачи:");
        taskDiscription = scanner.next();

        numberOfTaskIds++;

        Task task = new Task(taskName, numberOfTaskIds, taskDiscription);
        taskMap.put(numberOfTaskIds, task);
        System.out.println("Задача с названием: '" + task.name + "' успешно создана.");
    }

    public void makeNewEpic(Scanner scanner) {
        String taskName = "";
        String taskDiscription = "";

        System.out.println("\nВведите название Эпика:");
        taskName = scanner.next();
        System.out.println("\nВведите описание Эпика:");
        taskDiscription = scanner.next();

        numberOfTaskIds++;

        Task task = new Task(taskName, numberOfTaskIds, taskDiscription);
        Epic epic = new Epic(task);
        epicMap.put(numberOfTaskIds, epic);
        System.out.println("Эпик с названием: '" + epic.name + "' успешно создан.");
    }

    public void makeNewSubTask(Scanner scanner) {
        String taskName = "";
        String taskDiscription = "";
        int parrentId = 0;
        int epicId = 0;

        if (epicMap.isEmpty()) {
            System.out.println("Создание новой Подазачи невозможно, так как отсутсвтуют Эпики. Создайте Эпик.");
        } else {
            for (Epic epic1 : epicMap.values()) {
                System.out.println("Эпик с id:'" + epic1.id + "' и именем '" + epic1.name + "'");
            }
            System.out.println("\nВведите id Эпика, к которому хотите добавить Подзадачу:");
            parrentId = scanner.nextInt();

            if (epicMap.containsKey(parrentId)) {
                System.out.println("\nВведите название Подзадачи:");
                taskName = scanner.next();
                System.out.println("\nВведите описание Подзадачи:");
                taskDiscription = scanner.next();

                numberOfTaskIds++;

                Epic epic = epicMap.get(parrentId);
                epicId = epic.id;
                epic.subTaskList.add(numberOfTaskIds);
                epicMap.put(epicId, epic);

                Task task = new Task(taskName, numberOfTaskIds, taskDiscription);
                SubTask subTask = new SubTask(task, parrentId);
                subTaskMap.put(numberOfTaskIds, subTask);
                System.out.println("Подзадача с названием: '" + subTask.name + "' успешно создана.");
            } else {
                System.out.println("Ошибка - введите корректный id");
            }
        }
    }

    public void changeTask(Scanner scanner) {
        int id = 0;
        Task task = null;
        int command = 0;
        int statusCommand = 0;

        if (taskMap.isEmpty()) {
            System.out.println("Изменение невозможно, список Задач пуст.\nСоздайте Задачу.");
        } else {
            System.out.println("\nСписок текущих Задач:");
            for (Task task1 : taskMap.values()) {
                System.out.println("Задача с id:'" + task1.id + "' и названием: '" + task1.name + "'");
            }
            System.out.println("\nВведите id Задачи, которую нужно изменить:");
            id = scanner.nextInt();
            if (taskMap.containsKey(id)) {
                task = taskMap.get(id);
                System.out.println("Введите что хотите изменить:\n1 - Имя Задачи \n2 - Описание Задачи\n3 - Статус Задачи");
                command = scanner.nextInt();
                switch (command) {
                    case 1:
                        System.out.println("Введите новое имя Задачи:");
                        task.name = scanner.next();
                        taskMap.put(id, task);
                        System.out.println("Имя Задачи с id:'" + id + "' успешно изменено.");
                        break;
                    case 2:
                        System.out.println("Введите новое описание Задачи:");
                        task.discription = scanner.next();
                        taskMap.put(id, task);
                        System.out.println("Описание Задачи с id:'" + id + "' успешно изменено.");
                        break;
                    case 3:
                        System.out.println("Введите новый статус Задачи:\n1 - NEW\n2 - IN_PROGRESS\n3 - DONE");
                        statusCommand = scanner.nextInt();
                        if (statusCommand == 1) {
                            task.status = StatusTypes.NEW;
                            taskMap.put(id, task);
                            System.out.println("Статус задачи успешно изменен.");
                        } else if (statusCommand == 2) {
                            task.status = StatusTypes.IN_PROGRESS;
                            taskMap.put(id, task);
                            System.out.println("Статус задачи успешно изменен.");
                        } else if (statusCommand == 3) {
                            task.status = StatusTypes.DONE;
                            taskMap.put(id, task);
                            System.out.println("Статус задачи успешно изменен.");
                            System.out.println("Поздравляем с выполненной Задачей!");
                        } else {
                            System.out.println("Ошибка - Введите число от 1 до 3");
                            break;
                        }
                        break;
                    default:
                        System.out.println("Ошибка! Введите число от 1 до 3.");
                        break;
                }
            } else {
                System.out.println("Ошибка - введите корректный id");
            }
        }
    }

    public void changeEpic(Scanner scanner) {
        int id = 0;
        int command = 0;
        Epic epic = null;

        if (epicMap.isEmpty()) {
            System.out.println("Изменение невозможно, список Эпиков пуст.\nСоздайте Эпик.");
        } else {
            System.out.println("\nСписок текущих Эпиков:");
            for (Epic epic1 : epicMap.values()) {
                System.out.println("Эпик с id " + epic1.id + " и названием: '" + epic1.name + "'");
            }
            System.out.println("\nВведите id Эпика, который нужно изменить:");
            id = scanner.nextInt();
            if (epicMap.containsKey(id)) {
                epic = epicMap.get(id);
                System.out.println("Введите что хотите изменить:\n1 - Имя Эпика \n2 - Описание Эпика");
                command = scanner.nextInt();
                switch (command) {
                    case 1:
                        System.out.println("Введите новое имя Эпика:");
                        epic.name = scanner.next();
                        epicMap.put(id, epic);
                        System.out.println("Имя Эпика с id:'" + id + "' успешно изменено.");
                        break;
                    case 2:
                        System.out.println("Введите новое описание Эпика:");
                        epic.discription = scanner.next();
                        epicMap.put(id, epic);
                        System.out.println("Описание Эпика с id:'" + id + "' успешно изменено.");
                        break;
                    default:
                        System.out.println("Ошибка! Введите число от 1 до 2.");
                        break;
                }
            } else {
                System.out.println("Ошибка - введите корректный id");
            }
        }
    }

    public void changeSubTask(Scanner scanner) {
        int epicId = 0;
        int subTaskId = 0;
        Epic epic = null;
        SubTask subTask = null;
        int command = 0;
        int statusCommand = 0;
        int subtaskStatusCount = 0;

        if (epicMap.isEmpty()) {
            System.out.println("Изменение невозможно, список Эпиков пуст.\nСоздайте Эпик.");
        } else {
            System.out.println("\nСписок текущих Эпиков:");
            for (Epic epic1 : epicMap.values()) {
                System.out.println("Эпик с id:'" + epic1.id + "' и названием: '" + epic1.name + "'");
            }
            System.out.println("\nВведите id Эпика, подзадачу которого нужно изменить:");
            epicId = scanner.nextInt();
            if (epicMap.containsKey(epicId)) {
                epic = epicMap.get(epicId);
                System.out.println("Список подзадач Эпика: '" + epic.name + "'");
                for (Integer j : epic.subTaskList) {
                    SubTask subTask2 = subTaskMap.get(j);
                    System.out.println(subTask2);
                }
                System.out.println("\nВведите id Подзадачи, которую нужно изменить:");
                subTaskId = scanner.nextInt();
                if (subTaskMap.containsKey(subTaskId)) {
                    subTask = subTaskMap.get(subTaskId);
                    System.out.println("\nВведите что хотите изменить:\n1 - Имя Подзадачи\n2 - Описание Подзадачи" +
                            "\n3 - Статус Подзадачи");
                    command = scanner.nextInt();
                    switch (command) {
                        case 1:
                            System.out.println("\nВведите новое имя Подзадачи:");
                            subTask.name = scanner.next();
                            subTaskMap.put(subTaskId, subTask);
                            System.out.println("Имя Подзадачи с id " + subTaskId + " успешно изменено.");
                            break;
                        case 2:
                            System.out.println("Введите новое описание Подзадачи:");
                            subTask.discription = scanner.next();
                            subTaskMap.put(subTaskId, subTask);
                            System.out.println("Описание Подзадачи с id:'" + subTaskId + "' успешно изменено.");
                            break;
                        case 3:
                            System.out.println("\nВведите новый статус Подзадачи:\n1 - NEW\n2 - IN_PROGRESS\n3 - DONE");
                            statusCommand = scanner.nextInt();
                            if (statusCommand == 1) {
                                subTask.status = StatusTypes.NEW;
                                subTaskMap.put(subTaskId, subTask);
                                System.out.println("Статус Подзадачи успешно изменен.");
                            } else if (statusCommand == 2) {
                                subTask.status = StatusTypes.IN_PROGRESS;
                                epic.status = StatusTypes.IN_PROGRESS;
                                for (SubTask subTask2 : subTaskMap.values()) {
                                    if (subTask2.parrentId == epicId) {
                                        if (subTask2.status == StatusTypes.NEW) {
                                            subTask2.status = StatusTypes.IN_PROGRESS;
                                            subTaskMap.put(subTask2.id, subTask2);
                                        }
                                    }
                                }
                                epicMap.put(epicId, epic);
                                System.out.println("Статус Подзадачи успешно изменен.");
                            } else if (statusCommand == 3) {
                                epic.status = StatusTypes.IN_PROGRESS;
                                subTask.status = StatusTypes.DONE;
                                for (SubTask subTask3 : subTaskMap.values()) {
                                    if (subTask3.parrentId == epicId) {
                                        if (subTask3.status == StatusTypes.NEW) {
                                            subTask3.status = StatusTypes.IN_PROGRESS;
                                            subTaskMap.put(subTask3.id, subTask3);
                                        }
                                    }
                                }
                                epicMap.put(epicId, epic);
                                System.out.println("Статус Подзадачи успешно изменен.");
                                System.out.println("Поздравляем с выполненной Подзадачей!");
                                for (SubTask subTask4 : subTaskMap.values()) {
                                    if (subTask4.parrentId == epicId) {
                                        if (subTask4.status == StatusTypes.DONE) {
                                            subtaskStatusCount++;
                                            if (subtaskStatusCount == epic.subTaskList.size()) {
                                                epic.status = StatusTypes.DONE;
                                                epicMap.put(epicId, epic);
                                                System.out.println("Все подзадачи в Эпике с названием: '" + epic.name + "' выполнены.");
                                                System.out.println("Поздравляем с выполненным Эпиком!");
                                                subtaskStatusCount = 0;
                                            }
                                        }
                                    }
                                }
                            } else {
                                System.out.println("Ошибка - Введите число от 1 до 3");
                                break;
                            }
                            break;
                    }
                } else {
                    System.out.println("Ошибка - введите корректный id");
                }
            } else {
                System.out.println("Ошибка - введите корректный id");
            }
        }

    }

    public void deleteById(Scanner scanner) {
        int id = 0;
        int id2 = 0;
        Epic epic2 = null;

        if (taskMap.isEmpty() && epicMap.isEmpty() && subTaskMap.isEmpty()) {
            System.out.println("Удаление невозможно, списки Задач, Эпиков и Подзадач пусты.");
        } else {
            printAllTasksAndEpicsWithSubtasks();

            System.out.println("\nВведите id Задачи, Эпика или Подзадачи, которую нужно удалить:");
            id = scanner.nextInt();

            if (taskMap.containsKey(id)) {
                taskMap.remove(id);
                System.out.println("Задача успешно удалена.");
            } else if (epicMap.containsKey(id)) {
                epicMap.remove(id);
                for (SubTask subTask : subTaskMap.values()) {
                    if (subTask.parrentId == id) {
                        subTaskMap.remove(id);
                    }
                }
                System.out.println("Эпик и все его Подзадачи успешно удалены.");
            } else if (subTaskMap.containsKey(id)) {
                for (Epic epic : epicMap.values()) {
                    for (Integer b : epic.subTaskList) {
                        if (b == id) {
                            id2 = id;
                            epic2 = epic;
                        }
                    }
                }
                subTaskMap.remove(id2);
                epic2.subTaskList.remove(Integer.valueOf(id));
                epicMap.put(epic2.id, epic2);
                System.out.println("Подзадача успешно удалена.");
            }
        }
    }

    public void deleteAllTasksEpicsAndSubtasks() {
        if (taskMap.isEmpty() && epicMap.isEmpty()) {
            System.out.println("\nУдаление невозможно, списки Задач и Эпиков с Подзадачами пусты.");
        } else if (taskMap.isEmpty()) {
            System.out.println("\nУдаление невозможно, список Задач пуст.");
            epicMap.clear();
            subTaskMap.clear();
            System.out.println("Список Эпиков с Подзадачами успешно удален.");
        } else if (epicMap.isEmpty()) {
            System.out.println("\nУдаление невозможно, список Эпиков пуст.");
            taskMap.clear();
            System.out.println("Список Задач успешно удален.");
        } else {
            taskMap.clear();
            epicMap.clear();
            subTaskMap.clear();
            System.out.println("\nВсе Задачи и Эпики с Подзадачами успешно удалены.");
        }
    }
}