public class Main {

    public static void main(String[] args)  {

        String path = "C:\\Users\\1\\Videos\\java-kanban\\test.csv";
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(path);

        fileBackedTaskManager.loadFromFile(path);
        System.out.println(fileBackedTaskManager.getHistory());

    }
}
