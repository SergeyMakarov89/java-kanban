import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");
        TaskManager taskManager = new TaskManager();
        int command = 0;

        System.out.println("\nПРОГРАММА ТРЕКЕР ЗАДАЧ ЗАПУЩЕНА");
        while (true) {
            printMainMenu();
            command = scanner.nextInt();

            switch (command) {
                case 1:
                    taskManager.printAllTasksAndEpicWithSubtasks();
                    break;
                case 2:
                    taskManager.makeNewTask(scanner);
                    break;
                case 3:
                    taskManager.makeNewEpic(scanner);
                    break;
                case 4:
                    taskManager.makeNewSubTask(scanner);
                    break;
                case 5:
                    taskManager.changeTask(scanner);
                    break;
                case 6:
                    taskManager.changeEpic(scanner);
                    break;
                case 7:
                    taskManager.changeSubTask(scanner);
                    break;
                case 8:
                    taskManager.deleteTask(scanner);
                    break;
                case 9:
                    taskManager.deleteEpic(scanner);
                    break;
                case 10:
                    taskManager.deleteSubTaskInEpic(scanner);
                    break;
                case 11:
                    taskManager.deleteAllTasksAndEpicsWithSubtasks();
                    break;
                case 12:
                    return;
                default:
                    System.out.println("Ошибка - введите число от 1 до 12");
                    break;
            }
        }
    }

    public static void printMainMenu() {
        System.out.println("\nВведите команду:");
        System.out.println("1 - Вывести все Задачи, Эпики и Подзадачи");
        System.out.println("2 - Создать новую Задачу");
        System.out.println("3 - Создать новый Эпик");
        System.out.println("4 - Создать новую Подзадачу у Эпика");
        System.out.println("5 - Изменить Задачу");
        System.out.println("6 - Изменить Эпик");
        System.out.println("7 - Изменить Подзадачу у Эпика");
        System.out.println("8 - Удалить Задачу");
        System.out.println("9 - Удалить Эпик");
        System.out.println("10 - Удалить Подзадачу у Эпика");
        System.out.println("11 - Удалить все Задачи и Эпики с Подзадачами");
        System.out.println("12 - ВЫХОД");
    }
}
