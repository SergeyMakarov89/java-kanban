public class Main {

    public static void main(String[] args)  {

        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager();

        fileBackedTaskManager.loadFromFile();
        System.out.println(fileBackedTaskManager.getHistory());

    }
}
