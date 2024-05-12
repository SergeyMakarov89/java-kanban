import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

class EpicsHandler implements HttpHandler {

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    public FileBackedTaskManager fileBackedTaskManager;
    EpicAdapter epicAdapter;

    public EpicsHandler(FileBackedTaskManager fileBackedTaskManager) {
        this.fileBackedTaskManager = fileBackedTaskManager;
        this.epicAdapter = new EpicAdapter();
    }

    public static final Gson gsonEpic = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .registerTypeAdapter(Epic.class, new EpicAdapter())
            .create();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        System.out.println("Началась обработка /epics запроса от клиента.");

        Endpoint endpoint = getEndpoint(httpExchange.getRequestURI().getPath(), httpExchange.getRequestMethod());
        switch (endpoint) {
            case GET_ALL_EPICS: {
                handleGetEpics(httpExchange);
                break;
            }
            case GET_BY_ID_EPIC: {
                handleGetEpicById(httpExchange);
                break;
            }
            case GET_SUBTASKS_BY_EPIC_ID: {
                handleGetSubTusksByEpicId(httpExchange);
                break;
            }
            case POST_NEW_EPIC: {
                handlePostEpic(httpExchange);
                break;
            }
            case POST_UPDATE_EPIC: {
                handleChangeEpicById(httpExchange);
                break;
            }
            case DELETE_EPIC: {
                handleDeleteEpicById(httpExchange);
                break;
            }
            default:
                writeResponse(httpExchange, "Такого эндпоинта не существует", 404);
        }
    }

    public void handleGetEpics(HttpExchange httpExchange) throws IOException {
        if (fileBackedTaskManager.getAllEpics() != null) {
            String jsonEpics = gsonEpic.toJson(fileBackedTaskManager.getAllEpics());
            writeResponse(httpExchange, jsonEpics, 200);
        } else {
            writeResponse(httpExchange, "Эпиков в текущий момент нет", 404);
        }
    }

    public void handleGetEpicById(HttpExchange httpExchange) throws IOException {
        Optional<Integer> epicId = getId(httpExchange);

        if (fileBackedTaskManager.getEpicById(epicId.get()) != null) {
            String jsonTask = gsonEpic.toJson(fileBackedTaskManager.getEpicById(epicId.get()));
            writeResponse(httpExchange, jsonTask, 200);
        } else {
            httpExchange.sendResponseHeaders(404, 0);
            try (OutputStream outputStream = httpExchange.getResponseBody()) {
                outputStream.write(("Эпик с id: " + epicId.get() + " не найден").getBytes(DEFAULT_CHARSET));

            }
        }
    }

    public void handleGetSubTusksByEpicId(HttpExchange httpExchange) throws IOException {
        Optional<Integer> epicId = getId(httpExchange);
        if (fileBackedTaskManager.getEpicById(epicId.get()) != null) {
            String jsonTask = gsonEpic.toJson(fileBackedTaskManager.getAllSubTasksByEpicId(epicId.get()));
            writeResponse(httpExchange, jsonTask, 200);
        } else {
            httpExchange.sendResponseHeaders(404, 0);
            try (OutputStream outputStream = httpExchange.getResponseBody()) {
                outputStream.write(("Эпик с id: " + epicId.get() + " не найден").getBytes(DEFAULT_CHARSET));

            }
        }
    }

    public void handlePostEpic(HttpExchange httpExchange) throws IOException {

        Epic epic = getGsonEpic().fromJson(new String(httpExchange.getRequestBody().readAllBytes(), DEFAULT_CHARSET), Epic.class);
        fileBackedTaskManager.makeNewEpic(epic);
        writeResponse(httpExchange, "Эпик успешно создан на сервере", 201);
    }

    public void handleChangeEpicById(HttpExchange httpExchange) throws IOException {

        Epic epic = getGsonEpic().fromJson(new String(httpExchange.getRequestBody().readAllBytes(), DEFAULT_CHARSET), Epic.class);
        fileBackedTaskManager.changeEpic(epic);
        writeResponse(httpExchange, "Эпик успешно изменен на сервере", 201);
    }

    public void handleDeleteEpicById(HttpExchange httpExchange) throws IOException {
        Optional<Integer> epicId = getId(httpExchange);

        if (fileBackedTaskManager.getEpicById(epicId.get()) != null) {
            fileBackedTaskManager.deleteEpicById(epicId.get());
            writeResponse(httpExchange, "Эпик успешно удален на сервере", 201);
        } else {
            httpExchange.sendResponseHeaders(404, 0);
            try (OutputStream outputStream = httpExchange.getResponseBody()) {
                outputStream.write(("Эпик с id: " + epicId.get() + " не найден").getBytes(DEFAULT_CHARSET));

            }
        }
    }

    public static Endpoint getEndpoint(String requestPath, String requestMethod) {
        String[] pathParts = requestPath.split("/");
        if (pathParts[1].equals("epics")) {
            if (pathParts.length == 2) {
                if (requestMethod.equals("GET")) {
                    return Endpoint.GET_ALL_EPICS;
                }
                if (requestMethod.equals("POST")) {
                    return Endpoint.POST_NEW_EPIC;
                }
            }
            if (pathParts.length == 3) {
                if (requestMethod.equals("GET")) {
                    return Endpoint.GET_BY_ID_EPIC;
                }
                if (requestMethod.equals("POST")) {
                    return Endpoint.POST_UPDATE_EPIC;
                }
                if (requestMethod.equals("DELETE")) {
                    return Endpoint.DELETE_EPIC;
                }
            }
            if (pathParts.length == 4) {
                if (requestMethod.equals("GET")) {
                    return Endpoint.GET_SUBTASKS_BY_EPIC_ID;
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

    public Gson getGsonEpic() {
        return gsonEpic;
    }
}