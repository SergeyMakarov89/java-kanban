import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, ManagerSaveException {

        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager();

        fileBackedTaskManager.loadFromFile();

        System.out.println(fileBackedTaskManager.getAllTasks());
        System.out.println(fileBackedTaskManager.getAllEpics());
        System.out.println(fileBackedTaskManager.getAllSubTasks());

    }
}
