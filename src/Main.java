import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        String path = "test.csv";
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(path);
        HttpTaskServer httpTaskServer = new HttpTaskServer(fileBackedTaskManager);
        httpTaskServer.startServer();
    }
}
