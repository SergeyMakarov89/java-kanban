public class Main {

    public static void main(String[] args)  {

        String path = "C:\\Users\\1\\Videos\\java-kanban\\test.csv";
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(path);




        /*Task task = new Task("Погулять", "Выйти на улицу и прогуляться", "18:30", "PT30M");
        fileBackedTaskManager.makeNewTask(task);

        Task task2 = new Task("Поиграть в компик", "Включить компьютер и поиграть", 1, "13:30", "PT40M");
        fileBackedTaskManager.changeTask(task2);*/

        /*Task task3 = new Task("Покушать", "Достать питсу и покушать", "14:30", "PT40M");
        fileBackedTaskManager.makeNewTask(task3);*/


        /*fileBackedTaskManager.getTaskById(2);
        fileBackedTaskManager.getTaskById(1);*/

        /*System.out.println(fileBackedTaskManager.getPrioritizedTasks());*/


        /*fileBackedTaskManager.loadFromFile(path);
        System.out.println(fileBackedTaskManager.getHistory());*/





        Epic epic = new Epic("Купить продукты", "Сходить в магазин и прибарахлиться", "19:50");
        fileBackedTaskManager.makeNewEpic(epic);

        SubTask subTask = new SubTask("Купить молоко", "Купить молоко Простоквашино", 1, "19:51", "PT10M");
        fileBackedTaskManager.makeNewSubTask(subTask);

        SubTask subTask2 = new SubTask("Купить мясо", "Купить мясо говядины", 1, "20:00", "PT10M");
        fileBackedTaskManager.makeNewSubTask(subTask2);

        SubTask subTask3 = new SubTask("Купить яйца", "Купить яйцо куриное", 1, "20:10", "PT10M");
        fileBackedTaskManager.makeNewSubTask(subTask3);

        fileBackedTaskManager.deleteSubTaskById(3);



        /*fileBackedTaskManager.getEpicById(1);
        fileBackedTaskManager.getEpicById(2);*/

        //fileBackedTaskManager.deleteEpicById(1);

        System.out.println(fileBackedTaskManager.getPrioritizedTasks());

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


        /*fileBackedTaskManager.loadFromFile(path);
        System.out.println(fileBackedTaskManager.getHistory());

        System.out.println(fileBackedTaskManager.getEpicById(1));*/
        //System.out.println(fileBackedTaskManager.getSubTaskById(2).getEndTime());

    }
}
