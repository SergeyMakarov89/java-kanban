import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

class TasksHandler implements HttpHandler {

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    public FileBackedTaskManager fileBackedTaskManager;
    TaskAdapter taskAdapter;

    public TasksHandler(FileBackedTaskManager fileBackedTaskManager) {
        this.fileBackedTaskManager = fileBackedTaskManager;
        this.taskAdapter = new TaskAdapter();
    }

    public static final Gson gsonTask = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .registerTypeAdapter(Task.class, new TaskAdapter())
            .create();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        System.out.println("Началась обработка /tasks запроса от клиента.");

        Endpoint endpoint = getEndpoint(httpExchange.getRequestURI().getPath(), httpExchange.getRequestMethod());
        switch (endpoint) {
            case GET_ALL_TASKS: {
                handleGetTasks(httpExchange);
                break;
            }
            case GET_BY_ID_TASK: {
                handleGetTasksById(httpExchange);
                break;
            }
            case POST_NEW_TASK: {
                handlePostTask(httpExchange);
                break;
            }
            case POST_UPDATE_TASK: {
                handleChangeTaskById(httpExchange);
                break;
            }
            case DELETE_TASK: {
                handleDeleteTaskById(httpExchange);
                break;
            }
            default:
                writeResponse(httpExchange, "Такого эндпоинта не существует", 404);
        }
    }

    public void handleGetTasks(HttpExchange httpExchange) throws IOException {
        if (fileBackedTaskManager.getAllTasks() != null) {
            String jsonTasks = gsonTask.toJson(fileBackedTaskManager.getAllTasks());
            writeResponse(httpExchange, jsonTasks, 200);
        } else {
            writeResponse(httpExchange, "Задач в текущий момент нет", 404);
        }
    }

    public void handleGetTasksById(HttpExchange httpExchange) throws IOException {
        Optional<Integer> taskId = getId(httpExchange);

        if (fileBackedTaskManager.getTaskById(taskId.get()) != null) {
            String jsonTask = gsonTask.toJson(fileBackedTaskManager.getTaskById(taskId.get()));
            writeResponse(httpExchange, jsonTask, 200);
        } else {
            httpExchange.sendResponseHeaders(404, 0);
            try (OutputStream outputStream = httpExchange.getResponseBody()) {
                outputStream.write(("Задача с id: " + taskId.get() + " не найдена").getBytes(DEFAULT_CHARSET));

            }
        }
    }

    public void handlePostTask(HttpExchange httpExchange) throws IOException {

        Task task = getGsonTask().fromJson(new String(httpExchange.getRequestBody().readAllBytes(), DEFAULT_CHARSET), Task.class);
        fileBackedTaskManager.makeNewTask(task);
        writeResponse(httpExchange, "Задача успешно создана на сервере", 201);
    }

    public void handleChangeTaskById(HttpExchange httpExchange) throws IOException {

        Task task = getGsonTask().fromJson(new String(httpExchange.getRequestBody().readAllBytes(), DEFAULT_CHARSET), Task.class);
        fileBackedTaskManager.changeTask(task);
        writeResponse(httpExchange, "Задача успешно изменена на сервере", 201);
    }

    public void handleDeleteTaskById(HttpExchange httpExchange) throws IOException {
        Optional<Integer> taskId = getId(httpExchange);

        if (fileBackedTaskManager.getTaskById(taskId.get()) != null) {
            fileBackedTaskManager.deleteTaskById(taskId.get());
            writeResponse(httpExchange, "Задача успешно удалена на сервере", 201);
        } else {
            httpExchange.sendResponseHeaders(404, 0);
            try (OutputStream outputStream = httpExchange.getResponseBody()) {
                outputStream.write(("Задача с id: " + taskId.get() + " не найдена").getBytes(DEFAULT_CHARSET));

            }
        }
    }

    public static Endpoint getEndpoint(String requestPath, String requestMethod) {
        String[] pathParts = requestPath.split("/");
        if (pathParts[1].equals("tasks")) {
            if (pathParts.length == 2) {
                if (requestMethod.equals("GET")) {
                    return Endpoint.GET_ALL_TASKS;
                }
                if (requestMethod.equals("POST")) {
                    return Endpoint.POST_NEW_TASK;
                }
            }
            if (pathParts.length == 3) {
                if (requestMethod.equals("GET")) {
                    return Endpoint.GET_BY_ID_TASK;
                }
                if (requestMethod.equals("POST")) {
                    return Endpoint.POST_UPDATE_TASK;
                }
                if (requestMethod.equals("DELETE")) {
                    return Endpoint.DELETE_TASK;
                }
            }
        }
        return Endpoint.UNKNOWN;
    }

    public static void writeResponse(HttpExchange httpExchange, String responseString, int responseCode) throws IOException {
        try (OutputStream os = httpExchange.getResponseBody()) {
            httpExchange.sendResponseHeaders(responseCode, 0);
            os.write(responseString.getBytes(DEFAULT_CHARSET));
        }
        httpExchange.close();
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

    public Gson getGsonTask() {
        return gsonTask;
    }
}