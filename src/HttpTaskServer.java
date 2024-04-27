import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class HttpTaskServer {
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    private static final int PORT = 8080;

    public FileBackedTaskManager fileBackedTaskManager;
    TasksHandler tasksHandler;
    EpicsHandler epicsHandler;
    SubTaskHandler subTaskHandler;
    HistoryHandler historyHandler;
    PrioritizedHandler prioritizedHandler;


    public HttpTaskServer(FileBackedTaskManager fileBackedTaskManager) {
        this.fileBackedTaskManager = fileBackedTaskManager;
        this.tasksHandler = new TasksHandler(fileBackedTaskManager);
        this.epicsHandler = new EpicsHandler(fileBackedTaskManager);
        this.subTaskHandler = new SubTaskHandler(fileBackedTaskManager);
        this.historyHandler = new HistoryHandler(fileBackedTaskManager, tasksHandler, epicsHandler, subTaskHandler);
        this.prioritizedHandler = new PrioritizedHandler(fileBackedTaskManager, tasksHandler, epicsHandler, subTaskHandler);
    }


    public void main(String[] args) throws IOException {

        HttpServer httpServer = startServer();

        stopServer(httpServer);

    }

    public HttpServer startServer() throws IOException {
        HttpServer httpServer = HttpServer.create();

        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", tasksHandler);
        httpServer.createContext("/epics", epicsHandler);
        httpServer.createContext("/subtasks", subTaskHandler);
        httpServer.createContext("/history", historyHandler);
        httpServer.createContext("/prioritized", prioritizedHandler);
        httpServer.start();
        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");

        return httpServer;
    }

    public void stopServer(HttpServer httpServer) {
        System.out.println("Остановка HTTP-сервера...");
        httpServer.stop(1);
        System.out.println("HTTP-сервер остановлен.");
    }

    public static Optional<Integer> getId(HttpExchange httpExchange) {
        String path = httpExchange.getRequestURI().getPath();
        String[] strings = path.split("/");
        Integer i = null;
        if (strings[2] != null) {
            try {
                i = Integer.parseInt(strings[2]);
            } catch (NumberFormatException e) {
                return Optional.empty();
            }
        }
        return Optional.of(i);
    }

    public void deleteAllTasks() {
        fileBackedTaskManager.taskMap.clear();
        fileBackedTaskManager.epicMap.clear();
        fileBackedTaskManager.subTaskMap.clear();
        fileBackedTaskManager.setNumberOfTaskIdsToZero();
    }
}
