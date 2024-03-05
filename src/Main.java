public class Main {

    public static void main(String[] args) {

        TaskManager inMemoryTaskManager = Managers.makeInMemoryTaskManager();

        System.out.println("\nПРОГРАММА ТРЕКЕР ЗАДАЧ ЗАПУЩЕНА");

        Task task = new Task("Погулять", "Выйти на улицу и прогуляться");
        inMemoryTaskManager.makeNewTask(task);

        Task task2 = new Task("Поиграть в компик", "Включить компьютер и поиграть");
        inMemoryTaskManager.makeNewTask(task2);

        Epic epic = new Epic("Купить продукты", "Сходить в магазин и прибарахлиться");
        inMemoryTaskManager.makeNewEpic(epic);

        SubTask subTask = new SubTask("Купить молоко", "Купить молоко Простоквашино", 3);
        inMemoryTaskManager.makeNewSubTask(subTask);

        SubTask subTask2 = new SubTask("Купить мясо", "Купить мясо говядины, вырезку", 3);
        inMemoryTaskManager.makeNewSubTask(subTask2);

        SubTask subTask3 = new SubTask("Купить яйца", "Купить яйцо куриное, 10шт, С1", 3);
        inMemoryTaskManager.makeNewSubTask(subTask3);

        Epic epic2 = new Epic("Забрать жену с работы", "Взять машину и забрать жену с работы");
        inMemoryTaskManager.makeNewEpic(epic2);

        inMemoryTaskManager.getSubTaskById(6);
        System.out.println(inMemoryTaskManager.getHistory());

        inMemoryTaskManager.getSubTaskById(5);
        System.out.println(inMemoryTaskManager.getHistory());

        inMemoryTaskManager.getEpicById(3);
        System.out.println(inMemoryTaskManager.getHistory());

        inMemoryTaskManager.getTaskById(1);
        System.out.println(inMemoryTaskManager.getHistory());

        inMemoryTaskManager.getSubTaskById(4);
        System.out.println(inMemoryTaskManager.getHistory());

        inMemoryTaskManager.getTaskById(2);
        System.out.println(inMemoryTaskManager.getHistory());

        inMemoryTaskManager.getTaskById(1);
        System.out.println(inMemoryTaskManager.getHistory());

        inMemoryTaskManager.getEpicById(3);
        System.out.println(inMemoryTaskManager.getHistory());

        inMemoryTaskManager.getEpicById(7);
        System.out.println(inMemoryTaskManager.getHistory());

        inMemoryTaskManager.getSubTaskById(5);
        System.out.println(inMemoryTaskManager.getHistory());

        inMemoryTaskManager.getTaskById(2);
        System.out.println(inMemoryTaskManager.getHistory());

        inMemoryTaskManager.getSubTaskById(6);
        System.out.println(inMemoryTaskManager.getHistory());

        inMemoryTaskManager.getSubTaskById(4);
        System.out.println(inMemoryTaskManager.getHistory());

        inMemoryTaskManager.getEpicById(7);
        System.out.println(inMemoryTaskManager.getHistory());

        inMemoryTaskManager.deleteTaskById(1);
        System.out.println(inMemoryTaskManager.getHistory());

        inMemoryTaskManager.deleteEpicById(3);
        System.out.println(inMemoryTaskManager.getHistory());

    }
}
