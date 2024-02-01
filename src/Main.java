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
                    taskManager.printAllTasksAndEpicsWithSubtasks();
                    break;
                case 2:
                    taskManager.printById(scanner);
                    break;
                case 3:
                    taskManager.makeNewTask(scanner);
                    break;
                case 4:
                    taskManager.makeNewEpic(scanner);
                    break;
                case 5:
                    taskManager.makeNewSubTask(scanner);
                    break;
                case 6:
                    taskManager.changeTask(scanner);
                    break;
                case 7:
                    taskManager.changeEpic(scanner);
                    break;
                case 8:
                    taskManager.changeSubTask(scanner);
                    break;
                case 9:
                    taskManager.deleteById(scanner);
                    break;
                case 10:
                    taskManager.deleteAllTasksEpicsAndSubtasks();
                    break;
                case 11:
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
        System.out.println("2 - Вывести Задачу, Эпик или Подзадачу по id");
        System.out.println("3 - Создать новую Задачу");
        System.out.println("4 - Создать новый Эпик");
        System.out.println("5 - Создать новую Подзадачу у Эпика");
        System.out.println("6 - Изменить Задачу");
        System.out.println("7 - Изменить Эпик");
        System.out.println("8 - Изменить Подзадачу у Эпика");
        System.out.println("9 - Удалить Задачу, Эпик или Подзадачу по id");
        System.out.println("10 - Удалить все Задачи и Эпики с Подзадачами");
        System.out.println("11 - ВЫХОД");
    }
}
