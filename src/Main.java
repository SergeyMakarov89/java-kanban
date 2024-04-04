public class Main {

    public static void main(String[] args)  {

        String path = "C:\\Users\\1\\Videos\\java-kanban\\test.csv";
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(path);




        /*Task task = new Task("Погулять", "Выйти на улицу и прогуляться", "12:30", "PT30M");
        fileBackedTaskManager.makeNewTask(task);

        Task task2 = new Task("Поиграть в компик", "Включить компьютер и поиграть", "13:30", "PT40M");
        fileBackedTaskManager.makeNewTask(task2);

        fileBackedTaskManager.getTaskById(1);
        fileBackedTaskManager.getTaskById(2);*/


        /*fileBackedTaskManager.loadFromFile(path);
        System.out.println(fileBackedTaskManager.getHistory());*/




        /*Epic epic = new Epic("Забрать жену с работы", "Взять машину и забрать жену с работы", "19:00");
        fileBackedTaskManager.makeNewEpic(epic);

        Epic epic2 = new Epic("Купить продукты", "Сходить в магазин и прибарахлиться", "19:50");
        fileBackedTaskManager.makeNewEpic(epic2);

        fileBackedTaskManager.getEpicById(1);
        fileBackedTaskManager.getEpicById(2);*/


        /*fileBackedTaskManager.loadFromFile(path);
        System.out.println(fileBackedTaskManager.getHistory());*/



        /*Epic epic = new Epic("Купить продукты", "Сходить в магазин и прибарахлиться", "19:50");
        fileBackedTaskManager.makeNewEpic(epic);

        SubTask subTask = new SubTask("Купить молоко", "Купить молоко Простоквашино", 1, "19:50", "PT10M");
        fileBackedTaskManager.makeNewSubTask(subTask);

        SubTask subTask2 = new SubTask("Купить мясо", "Купить мясо говядины", 1, "20:00", "PT10M");
        fileBackedTaskManager.makeNewSubTask(subTask2);

        fileBackedTaskManager.getSubTaskById(3);
        fileBackedTaskManager.getEpicById(1);
        fileBackedTaskManager.getSubTaskById(2);*/


        fileBackedTaskManager.loadFromFile(path);
        System.out.println(fileBackedTaskManager.getHistory());

        System.out.println(fileBackedTaskManager.getEpicById(1));
        //System.out.println(fileBackedTaskManager.getSubTaskById(2).getEndTime());

    }
}
