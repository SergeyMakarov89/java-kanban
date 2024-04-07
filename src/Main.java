public class Main {

    public static void main(String[] args)  {

        String path = "C:\\Users\\1\\Videos\\java-kanban\\test.csv";
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(path);

        Task task = new Task("Погулять", "Выйти на улицу и прогуляться", "13:30", "PT50M");
        fileBackedTaskManager.makeNewTask(task);

        Task task2 = new Task("Поиграть в компик", "Включить компьютер и поиграть", 1,"14:00", "PT40M");
        fileBackedTaskManager.changeTask(task2);

        Task task3 = new Task("Покушать", "Достать питсу и покушать", "18:30", "PT30M");
        fileBackedTaskManager.makeNewTask(task3);

        System.out.println(fileBackedTaskManager.getPrioritizedTasks());
    }
}
