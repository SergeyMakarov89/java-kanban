import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

class SubTaskHandler implements HttpHandler {

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    public FileBackedTaskManager fileBackedTaskManager;
    SubTaskAdapter subTaskAdapter;

    public SubTaskHandler(FileBackedTaskManager fileBackedTaskManager) {
        this.fileBackedTaskManager = fileBackedTaskManager;
        this.subTaskAdapter = new SubTaskAdapter();
    }

    public static final Gson gsonSubTasks = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .registerTypeAdapter(SubTask.class, new SubTaskAdapter())
            .create();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        System.out.println("Началась обработка /subtasks запроса от клиента.");

        Endpoint endpoint = getEndpoint(httpExchange.getRequestURI().getPath(), httpExchange.getRequestMethod());
        switch (endpoint) {
            case GET_ALL_SUBTASKS: {
                handleGetSubTasks(httpExchange);
                break;
            }
            case GET_BY_ID_SUBTASK: {
                handleGetSubTaskById(httpExchange);
                break;
            }
            case POST_NEW_SUBTASK: {
                handlePostSubTask(httpExchange);
                break;
            }
            case POST_UPDATE_SUBTASK: {
                handleChangeSubTaskById(httpExchange);
                break;
            }
            case DELETE_SUBTASK: {
                handleDeleteSubTaskById(httpExchange);
                break;
            }
            default:
                writeResponse(httpExchange, "Такого эндпоинта не существует", 404);
        }
    }

    public void handleGetSubTasks(HttpExchange httpExchange) throws IOException {
        if (fileBackedTaskManager.getAllSubTasks() != null) {
            String jsonSubTasks = gsonSubTasks.toJson(fileBackedTaskManager.getAllSubTasks());
            writeResponse(httpExchange, jsonSubTasks, 200);
        } else {
            writeResponse(httpExchange, "Подзадач в текущий момент нет", 404);
        }
    }

    public void handleGetSubTaskById(HttpExchange httpExchange) throws IOException {
        Optional<Integer> subTaskId = getId(httpExchange);

        if (fileBackedTaskManager.getSubTaskById(subTaskId.get()) != null) {
            String jsonSubTask = gsonSubTasks.toJson(fileBackedTaskManager.getSubTaskById(subTaskId.get()));
            writeResponse(httpExchange, jsonSubTask, 200);
        } else {
            httpExchange.sendResponseHeaders(404, 0);
            try (OutputStream outputStream = httpExchange.getResponseBody()) {
                outputStream.write(("Подзадача с id: " + subTaskId.get() + " не найдена").getBytes(DEFAULT_CHARSET));

            }
        }
    }

    public void handlePostSubTask(HttpExchange httpExchange) throws IOException {

        SubTask subTask = getGsonSubTasks().fromJson(new String(httpExchange.getRequestBody().readAllBytes(), DEFAULT_CHARSET), SubTask.class);
        fileBackedTaskManager.makeNewSubTask(subTask);
        writeResponse(httpExchange, "Подзадача успешно создана на сервере", 201);
    }

    public void handleChangeSubTaskById(HttpExchange httpExchange) throws IOException {

        SubTask subTask = getGsonSubTasks().fromJson(new String(httpExchange.getRequestBody().readAllBytes(), DEFAULT_CHARSET), SubTask.class);
        fileBackedTaskManager.changeSubTask(subTask);
        writeResponse(httpExchange, "Подзадача успешно изменена на сервере", 201);
    }

    public void handleDeleteSubTaskById(HttpExchange httpExchange) throws IOException {
        Optional<Integer> subTaskId = getId(httpExchange);

        if (fileBackedTaskManager.getSubTaskById(subTaskId.get()) != null) {
            fileBackedTaskManager.deleteSubTaskById(subTaskId.get());
            writeResponse(httpExchange, "Подзадача успешно удалена на сервере", 201);
        } else {
            httpExchange.sendResponseHeaders(404, 0);
            try (OutputStream outputStream = httpExchange.getResponseBody()) {
                outputStream.write(("Подзадача с id: " + subTaskId.get() + " не найдена").getBytes(DEFAULT_CHARSET));

            }
        }
    }

    public static Endpoint getEndpoint(String requestPath, String requestMethod) {
        String[] pathParts = requestPath.split("/");
        if (pathParts[1].equals("subtasks")) {
            if (pathParts.length == 2) {
                if (requestMethod.equals("GET")) {
                    return Endpoint.GET_ALL_SUBTASKS;
                }
                if (requestMethod.equals("POST")) {
                    return Endpoint.POST_NEW_SUBTASK;
                }
            }
            if (pathParts.length == 3) {
                if (requestMethod.equals("GET")) {
                    return Endpoint.GET_BY_ID_SUBTASK;
                }
                if (requestMethod.equals("POST")) {
                    return Endpoint.POST_UPDATE_SUBTASK;
                }
                if (requestMethod.equals("DELETE")) {
                    return Endpoint.DELETE_SUBTASK;
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

    public Gson getGsonSubTasks() {
        return gsonSubTasks;
    }
}