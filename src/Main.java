
public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        System.out.println("\nПРОГРАММА ТРЕКЕР ЗАДАЧ ЗАПУЩЕНА");

        Task task = new Task("Погулять", "Выйти на улицу и прогуляться");
        taskManager.makeNewTask(task);

        Task task2 = new Task("Поиграть в компик", "Включить компьютер и поиграть");
        taskManager.makeNewTask(task2);

        Epic epic = new Epic("Купить продукты", "Сходить в магазин и прибарахлиться");
        taskManager.makeNewEpic(epic);

        SubTask subTask = new SubTask("Купить молоко", "Купить молоко Простоквашино", 3);
        taskManager.makeNewSubTask(subTask);

        SubTask subTask2 = new SubTask("Купить мясо", "Купить мясо говядины, вырезку", 3);
        taskManager.makeNewSubTask(subTask2);

        SubTask subTask3 = new SubTask("Купить яйца", "Купить яйцо куриное, 10шт, С1", 3);
        taskManager.makeNewSubTask(subTask3);

        Epic epic2 = new Epic("Забрать жену", "Взять машину и довезти жену до дома");
        taskManager.makeNewEpic(epic2);

        SubTask subTask4 = new SubTask("Взять машину", "Дойти до гаража и взять машину", 7);
        taskManager.makeNewSubTask(subTask4);

        SubTask subTask5 = new SubTask("Довезти жену", "Доехать до работы жены и забрать ее", 7);
        taskManager.makeNewSubTask(subTask5);

        SubTask subTask6 = new SubTask("Поставить машину", "Поставить машину в гараж, вернуться", 7);
        taskManager.makeNewSubTask(subTask6);

        taskManager.printAllTasksAndEpicsWithSubtasks();

        Task task3 = new Task("Поплавать", "Сходить в бассейн и поплавать", 1, StatusTypes.IN_PROGRESS);
        taskManager.changeTask(task3);

        System.out.println(taskManager.getTaskById(1));

        Epic epic3 = new Epic("Забрать маму", "Взять машину и довезти маму до дома",
                7);
        taskManager.changeEpic(epic3);

        System.out.println(taskManager.getEpicById(7));

        SubTask subTask7 = new SubTask("Купить молоко сделано", "Купить молоко Простоквашино",
                3, 4, StatusTypes.DONE);
        taskManager.changeSubTask(subTask7);

        System.out.println(taskManager.getSubTaskById(4));

        SubTask subTask8 = new SubTask("Купить мясо сделано", "Купить мясо говядины, вырезку",
                3, 5, StatusTypes.DONE);
        taskManager.changeSubTask(subTask8);

        System.out.println(taskManager.getSubTaskById(5));

        SubTask subTask9 = new SubTask("Купить яйца сделано", "Купить яйцо куриное, 10шт, С1",
                3, 6, StatusTypes.DONE);
        taskManager.changeSubTask(subTask9);

        SubTask subTask10 = new SubTask("Поставить машину", "Поставить машину в гараж, вернуться",
                7, 10, StatusTypes.IN_PROGRESS);
        taskManager.changeSubTask(subTask10);

        System.out.println(taskManager.getSubTaskById(6));

        taskManager.printAllTasksAndEpicsWithSubtasks();

        taskManager.deleteTaskById(2);
        taskManager.deleteEpicById(3);
        taskManager.deleteSubTaskById(10);

        System.out.println("Cписок всех Задач:\n" + taskManager.getAllTasks());
        System.out.println("Cписок всех Эпиков:\n" + taskManager.getAllEpics());
        System.out.println("Cписок всех Подзадач:\n" + taskManager.getAllSubTasks());

        System.out.println("Cписок всех Подзадач эпика с id '7':\n" + taskManager.getAllSubTasksByEpicId(7));
    }
}
