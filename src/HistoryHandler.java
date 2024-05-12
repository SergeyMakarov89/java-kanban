import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

class HistoryHandler implements HttpHandler {

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    public FileBackedTaskManager fileBackedTaskManager;

    TasksHandler tasksHandler;
    EpicsHandler epicsHandler;
    SubTaskHandler subTaskHandler;

    public HistoryHandler(FileBackedTaskManager fileBackedTaskManager, TasksHandler tasksHandler, EpicsHandler epicsHandler, SubTaskHandler subTaskHandler) {
        this.fileBackedTaskManager = fileBackedTaskManager;
        this.tasksHandler = tasksHandler;
        this.epicsHandler = epicsHandler;
        this.subTaskHandler = subTaskHandler;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        System.out.println("Началась обработка /history запроса от клиента.");

        handleGetHistory(httpExchange);

    }

    public void handleGetHistory(HttpExchange httpExchange) throws IOException {
        String jsonAllTasks = "";

        if (fileBackedTaskManager.getHistory() != null) {
            for (Task task : fileBackedTaskManager.getHistory()) {
                if (task.getType() == Types.TASK) {
                    String jsonTask = tasksHandler.getGsonTask().toJson(task);
                    jsonAllTasks = jsonAllTasks + "\n" + jsonTask;
                }
                if (task.getType() == Types.EPIC) {
                    String jsonEpic = epicsHandler.getGsonEpic().toJson(task);
                    jsonAllTasks = jsonAllTasks + "\n" + jsonEpic;
                }
                if (task.getType() == Types.SUBTASK) {
                    String jsonSubtask = subTaskHandler.getGsonSubTasks().toJson(task);
                    jsonAllTasks = jsonAllTasks + "\n" + jsonSubtask;
                }
            }
            writeResponse(httpExchange, jsonAllTasks, 200);
        } else {
            writeResponse(httpExchange, "Задач в текущий момент нет", 404);
        }
    }

    public static void writeResponse(HttpExchange httpExchange, String responseString, int responseCode) throws IOException {
        try (OutputStream os = httpExchange.getResponseBody()) {
            httpExchange.sendResponseHeaders(responseCode, 0);
            os.write(responseString.getBytes(DEFAULT_CHARSET));
        }
        httpExchange.close();
    }
}