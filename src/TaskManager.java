import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class TaskManager {

    int numberOfTaskIds = 0;
    int numberOfEpicIds = 0;
    int numberOfSubTaskIds = 0;
    HashMap<Integer, Task> taskMap = new HashMap();
    HashMap<Integer, Epic> epicMap = new HashMap();

    public void printAllTasksAndEpicWithSubtasks() {
        if (taskMap.isEmpty() && epicMap.isEmpty()) {
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
                for (SubTask subTask : epic.subTasks) {
                    System.out.println(subTask);
                }
                System.out.println();
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

        Task task = new Task(taskName, taskDiscription, numberOfTaskIds);
        taskMap.put(numberOfTaskIds, task);
        System.out.println("Задача с названием: '" + task.name + "' успешно создана.");
    }

    public void makeNewEpic(Scanner scanner) {
        String epicName = "";
        String subTaskName = "";

        System.out.println("\nВведите название Эпика:");
        epicName = scanner.next();

        numberOfEpicIds++;

        Epic epic = new Epic(epicName, numberOfEpicIds);

        System.out.println("\nВведите название первой Подзадачи Эпика:");
        subTaskName = scanner.next();

        numberOfSubTaskIds++;

        SubTask subTask = new SubTask(epic, subTaskName, numberOfSubTaskIds);
        epic.subTasks.add(subTask);

        epicMap.put(numberOfEpicIds, epic);
        System.out.println("Эпик: '" + epic.name + "' с первой Подзадачей успешно создан.");
    }

    public void makeNewSubTask(Scanner scanner) {
        String subTaskName = "";
        int parentEpicId = 0;
        Epic parentEpic = null;

        System.out.println("\nСписок текущих Эпиков:");
        for (Epic epic1 : epicMap.values()) {
            System.out.println("Эпик с id:'" + epic1.id + "' и именем '" + epic1.name + "'");
        }
        System.out.println("\nВведите id Эпика, которому хотите добавить Подзадачу:");
        parentEpicId = scanner.nextInt();
        parentEpic = epicMap.get(parentEpicId);
        System.out.println("\nВведите название Подзадачи:");
        subTaskName = scanner.next();

        numberOfSubTaskIds++;

        SubTask subTask = new SubTask(parentEpic, subTaskName, numberOfSubTaskIds);
        parentEpic.subTasks.add(subTask);
        System.out.println("Подзадача успешно добавлена к Эпику: '" + parentEpic.name + "'");
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
            task = taskMap.get(id);
            System.out.println("Введите что хотите изменить:\n1 - Имя Задачи \n2 - Описание Задачи\n3 - Статус Задачи");
            command = scanner.nextInt();
            switch (command) {
                case 1:
                    System.out.println("Введите новое имя Задачи:");
                    task.name = scanner.next();
                    taskMap.put(id, task);
                    System.out.println("Имя Задачи с id:'" + id + "' успешно изменено");
                    break;
                case 2:
                    System.out.println("Введите новое описание Задачи:");
                    task.discription = scanner.next();
                    taskMap.put(id, task);
                    System.out.println("Описание Задачи с id:'" + id + "' успешно изменено");
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
        }
    }

    public void changeEpic(Scanner scanner) {
        int id = 0;
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
            epic = epicMap.get(id);
            System.out.println("\nВведите новое название Эпика:");
            epic.name = scanner.next();
            epicMap.put(id, epic);
            System.out.println("Название Эпика с id:'" + id + "' успешно изменено.");
        }
    }

    public void changeSubTask(Scanner scanner) {
        int epicId = 0;
        int subTaskArrayId = 0;
        Epic epic = null;
        SubTask subTask = null;
        int command = 0;
        int statusCommand = 0;
        int i = 1;
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
            epic = epicMap.get(epicId);
            System.out.println("Список подзадач Эпика: '" + epic.name + "'");
            for (SubTask subTask1 : epic.subTasks) {
                System.out.println("Подзадача № " + i + " c названием: '" + subTask1.name + "'");
                i++;
            }
            System.out.println("\nВведите № Подзадачи, которую нужно изменить:");
            subTaskArrayId = scanner.nextInt() - 1;
            subTask = epic.subTasks.get(subTaskArrayId);
            System.out.println("\nВведите что хотите изменить:\n1 - Имя Подзадачи\n2 - Статус Подзадачи");
            command = scanner.nextInt();
            switch (command) {
                case 1:
                    System.out.println("\nВведите новое имя Подзадачи:");
                    subTask.name = scanner.next();
                    epic.subTasks.remove(subTaskArrayId);
                    epic.subTasks.add(subTaskArrayId, subTask);
                    epicMap.put(epicId, epic);
                    System.out.println("Имя Подзадачи с №: " + (subTaskArrayId + 1) + " успешно изменено.");
                    break;
                case 2:
                    System.out.println("\nВведите новый статус Подзадачи:\n1 - NEW\n2 - IN_PROGRESS\n3 - DONE");
                    statusCommand = scanner.nextInt();
                    if (statusCommand == 1) {
                        epic.subTasks.remove(subTaskArrayId);
                        subTask.status = StatusTypes.NEW;
                        epic.subTasks.add(subTaskArrayId, subTask);
                        epicMap.put(epicId, epic);
                        System.out.println("Статус Подзадачи успешно изменен.");
                    } else if (statusCommand == 2) {
                        epic.subTasks.remove(subTaskArrayId);
                        subTask.status = StatusTypes.IN_PROGRESS;
                        epic.status = StatusTypes.IN_PROGRESS;
                        for (SubTask subTask2 : epic.subTasks) {
                            if (subTask2.status == StatusTypes.NEW) {
                                subTask2.status = StatusTypes.IN_PROGRESS;
                            }
                        }
                        epic.subTasks.add(subTaskArrayId, subTask);
                        epicMap.put(epicId, epic);
                        System.out.println("Статус Подзадачи успешно изменен.");
                    } else if (statusCommand == 3) {
                        epic.status = StatusTypes.IN_PROGRESS;
                        epic.subTasks.remove(subTaskArrayId);
                        subTask.status = StatusTypes.DONE;
                        for (SubTask subTask2 : epic.subTasks) {
                            if (subTask2.status == StatusTypes.NEW) {
                                subTask2.status = StatusTypes.IN_PROGRESS;
                            }
                        }
                        epic.subTasks.add(subTaskArrayId, subTask);
                        epicMap.put(epicId, epic);
                        System.out.println("Статус Подзадачи успешно изменен.");
                        System.out.println("Поздравляем с выполненной Подзадачей!");
                        for (SubTask subTask3 : epic.subTasks) {
                            if (subTask3.status == StatusTypes.DONE) {
                                subtaskStatusCount++;
                                if (subtaskStatusCount == epic.subTasks.size()) {
                                    epic.status = StatusTypes.DONE;
                                    epicMap.put(epicId, epic);
                                    System.out.println("Все подзадачи в Эпике с названием: '" + epic.name + "' выполнены.");
                                    System.out.println("Поздравляем с выполненным Эпиком!");
                                    subtaskStatusCount = 0;
                                }
                            }
                        }
                    } else {
                        System.out.println("Ошибка - Введите число от 1 до 2");
                        break;
                    }
                    break;
            }
        }
    }

    public void deleteTask(Scanner scanner) {
        int id = 0;

        if (taskMap.isEmpty()) {
            System.out.println("\nУдаление невозможно, список Задач пуст.\nСоздайте Задачу.");
        } else {
            System.out.println("\nСписок текущих Задач:");
            for (Task task1 : taskMap.values()) {
                System.out.println("Задача с id:'" + task1.id + "' и названием: '" + task1.name + "'");
            }
            System.out.println("\nВведите id Задачи, которую нужно удалить:");
            id = scanner.nextInt();
            taskMap.remove(id);
            System.out.println("Задача успешно удалена.");
        }
    }

    public void deleteEpic(Scanner scanner) {
        int id = 0;

        if (epicMap.isEmpty()) {
            System.out.println("\nУдаление невозможно, список Эпиков пуст.\nСоздайте Эпик.");
        } else {
            System.out.println("\nСписок текущих Эпиков:");
            for (Epic epic1 : epicMap.values()) {
                System.out.println("Эпик с id:'" + epic1.id + "' и названием: '" + epic1.name + "'");
            }
            System.out.println("\nВведите id Эпика, который нужно удалить:");
            id = scanner.nextInt();
            epicMap.remove(id);
            System.out.println("Эпик успешно удален.");
        }
    }

    public void deleteSubTaskInEpic(Scanner scanner) {
        int epicId = 0;
        int subTaskArrayId = 0;
        Epic epic = null;
        SubTask subTask = null;
        int i = 1;

        if (epicMap.isEmpty()) {
            System.out.println("\nУдаление невозможно, список Эпиков пуст.\nСоздайте Эпик.");
        } else {
            System.out.println("\nСписок текущих Эпиков:");
            for (Epic epic1 : epicMap.values()) {
                System.out.println("Эпик с id:'" + epic1.id + "' и именем '" + epic1.name + "'");
            }
            System.out.println("\nВведите id Эпика, подзадачу которого нужно удалить:");
            epicId = scanner.nextInt();
            epic = epicMap.get(epicId);
            System.out.println("Список подзадач Эпика: '" + epic.name + "'");
            for (SubTask subTask1 : epic.subTasks) {
                System.out.println("Подзадача № " + i + " c именем: '" + subTask1.name + "'");
                i++;
            }
            System.out.println("\nВведите № Подзадачи, которую нужно удалить:");
            subTaskArrayId = scanner.nextInt() - 1;
            epic.subTasks.remove(subTaskArrayId);
            System.out.println("Подзадача у эпика: '" + epic.name + "' удалена.");
        }
    }

    public void deleteAllTasksAndEpicsWithSubtasks() {
        if (taskMap.isEmpty() && epicMap.isEmpty()) {
            System.out.println("\nУдаление невозможно, списки Задач и Эпиков с Подзадачами пусты.");
        } else if (taskMap.isEmpty()) {
            System.out.println("\nУдаление невозможно, список Задач пуст.");
            epicMap.clear();
            System.out.println("Список Эпиков с Подзадачами успешно удален.");
        } else if (epicMap.isEmpty()) {
            System.out.println("\nУдаление невозможно, список Эпиков пуст.");
            epicMap.clear();
            System.out.println("Список Задач успешно удален.");
        } else {
            taskMap.clear();
            epicMap.clear();
            System.out.println("\nВсе Задачи и Эпики с Подзадачами удалены.");
        }
    }
}
