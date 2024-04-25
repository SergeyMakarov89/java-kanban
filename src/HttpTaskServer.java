import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class HttpTaskServer {
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    private static final int PORT = 8080;

    public static String path = "test.csv";
    public static FileBackedTaskManager fileBackedTaskManager = Managers.makeFileBackedTaskManager(path);


    public void main(String[] args) throws IOException {

        HttpServer httpServer = startServer();

        stopServer(httpServer);

    }

    public HttpServer startServer() throws IOException {
        HttpServer httpServer = HttpServer.create();

        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", new TasksHandler());
        httpServer.createContext("/epics", new EpicsHandler());
        httpServer.createContext("/subtasks", new SubTaskHandler());
        httpServer.createContext("/history", new HistoryHandler());
        httpServer.createContext("/prioritized", new PrioritizedHandler());
        httpServer.start();
        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");

        return httpServer;
    }

    public void stopServer(HttpServer httpServer) {
        System.out.println("Остановка HTTP-сервера...");
        httpServer.stop(1);
        System.out.println("HTTP-сервер остановлен.");
    }

    static class TasksHandler implements HttpHandler {

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

            Optional taskOpt = parseTaskJson(httpExchange.getRequestBody());
            Task newTask = (Task) taskOpt.get();
            fileBackedTaskManager.makeNewTask(newTask);
            writeResponse(httpExchange, "Задача успешно создана на сервере", 201);
        }

        public void handleChangeTaskById(HttpExchange httpExchange) throws IOException {

            Optional taskOpt = parseTaskJson(httpExchange.getRequestBody());
            Task newTask = (Task) taskOpt.get();
            fileBackedTaskManager.changeTask(newTask);
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

        private Optional<Task> parseTaskJson(InputStream bodyInputStream) throws IOException {
            String body = new String(bodyInputStream.readAllBytes(), DEFAULT_CHARSET);
            String[] strings = body.split(",");
            Integer id = null;
            String name = null;
            String description = null;
            String startTime = null;
            String duration = null;
            for (int i = 0; i < strings.length; i++) {
                if (strings[i].contains("id")) {
                    int index1 = strings[i].indexOf(":");
                    String subString1 = strings[i].substring(index1);
                    String subString2 = subString1.substring(2);
                    id = Integer.parseInt(subString2);
                    if (id != 0) {
                        continue;
                    }
                }
                if (strings[i].contains("name")) {
                    int index1 = strings[i].indexOf(":");
                    String subString1 = strings[i].substring(index1);
                    String subString2 = subString1.substring(3);
                    int index2 = subString2.length();
                    String subString3 = subString2.substring(0, index2 - 1);
                    name = subString3;
                    continue;
                }
                if (strings[i].contains("description")) {
                    int index1 = strings[i].indexOf(":");
                    String subString1 = strings[i].substring(index1);
                    String subString2 = subString1.substring(3);
                    int index2 = subString2.length();
                    String subString3 = subString2.substring(0, index2 - 1);
                    description = subString3;
                    continue;
                }
                if (strings[i].contains("startTime")) {
                    int index1 = strings[i].indexOf(":");
                    String subString1 = strings[i].substring(index1);
                    String subString2 = subString1.substring(3);
                    int index2 = subString2.length();
                    String subString3 = subString2.substring(0, index2 - 1);
                    startTime = subString3;
                    continue;
                }
                if (strings[i].contains("duration")) {
                    int index1 = strings[i].indexOf(":");
                    String subString1 = strings[i].substring(index1);
                    String subString2 = subString1.substring(3);
                    int index2 = subString2.length();
                    String subString3 = subString2.substring(0, index2 - 3);
                    duration = subString3;
                    continue;
                }
            }

            if (id != 0)  {
                Task task = new Task(name, description, id, startTime, duration);
                return Optional.of(task);
            }
            Task task = new Task(name, description, startTime, duration);
            return Optional.of(task);
        }
    }

    static class EpicsHandler implements HttpHandler {

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

            Optional epicOpt = parseEpicJson(httpExchange.getRequestBody());
            Epic newEpic = (Epic) epicOpt.get();
            fileBackedTaskManager.makeNewEpic(newEpic);
            writeResponse(httpExchange, "Эпик успешно создан на сервере", 201);
        }

        public void handleChangeEpicById(HttpExchange httpExchange) throws IOException {

            Optional epicOpt = parseEpicJson(httpExchange.getRequestBody());
            Epic newEpic = (Epic) epicOpt.get();
            fileBackedTaskManager.changeEpic(newEpic);
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

        private Optional<Epic> parseEpicJson(InputStream bodyInputStream) throws IOException {
            String body = new String(bodyInputStream.readAllBytes(), DEFAULT_CHARSET);
            String[] strings = body.split(",");
            Integer id = null;
            String name = null;
            String description = null;
            String startTime = null;
            String duration = null;
            for (int i = 0; i < strings.length; i++) {
                if (strings[i].contains("id")) {
                    int index1 = strings[i].indexOf(":");
                    String subString1 = strings[i].substring(index1);
                    String subString2 = subString1.substring(2);
                    id = Integer.parseInt(subString2);
                    if (id != 0) {
                        continue;
                    }
                }
                if (strings[i].contains("name")) {
                    int index1 = strings[i].indexOf(":");
                    String subString1 = strings[i].substring(index1);
                    String subString2 = subString1.substring(3);
                    int index2 = subString2.length();
                    String subString3 = subString2.substring(0, index2 - 1);
                    name = subString3;
                    continue;
                }
                if (strings[i].contains("description")) {
                    int index1 = strings[i].indexOf(":");
                    String subString1 = strings[i].substring(index1);
                    String subString2 = subString1.substring(3);
                    int index2 = subString2.length();
                    String subString3 = subString2.substring(0, index2 - 1);
                    description = subString3;
                    continue;
                }
                if (strings[i].contains("startTime")) {
                    int index1 = strings[i].indexOf(":");
                    String subString1 = strings[i].substring(index1);
                    String subString2 = subString1.substring(3);
                    int index2 = subString2.length();
                    String subString3 = subString2.substring(0, index2 - 1);
                    startTime = subString3;
                    continue;
                }
                if (strings[i].contains("duration")) {
                    int index1 = strings[i].indexOf(":");
                    String subString1 = strings[i].substring(index1);
                    String subString2 = subString1.substring(3);
                    int index2 = subString2.length();
                    String subString3 = subString2.substring(0, index2 - 3);
                    duration = subString3;
                    continue;
                }
            }

            if (id != 0)  {
                Epic epic = new Epic(name, description, id, startTime, duration);
                return Optional.of(epic);
            }
            Epic epic = new Epic(name, description, startTime, duration);
            return Optional.of(epic);
        }

    }

    static class SubTaskHandler implements HttpHandler {

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

            Optional subTaskOpt = parseSubtaskJson(httpExchange.getRequestBody());
            SubTask newSubTask = (SubTask) subTaskOpt.get();
            fileBackedTaskManager.makeNewSubTask(newSubTask);
            writeResponse(httpExchange, "Подзадача успешно создана на сервере", 201);
        }

        public void handleChangeSubTaskById(HttpExchange httpExchange) throws IOException {

            Optional subTaskOpt = parseSubtaskJson(httpExchange.getRequestBody());
            SubTask newSubTask = (SubTask) subTaskOpt.get();
            fileBackedTaskManager.changeSubTask(newSubTask);
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

        private Optional<SubTask> parseSubtaskJson(InputStream bodyInputStream) throws IOException {
            String body = new String(bodyInputStream.readAllBytes(), DEFAULT_CHARSET);
            String[] strings = body.split(",");
            Integer id = null;
            String name = null;
            String description = null;
            String startTime = null;
            String duration = null;
            Integer parrentId = null;
            StatusTypes status = null;
            for (int i = 0; i < strings.length; i++) {
                if (strings[i].contains("id")) {
                    int index1 = strings[i].indexOf(":");
                    String subString1 = strings[i].substring(index1);
                    String subString2 = subString1.substring(2);
                    id = Integer.parseInt(subString2);
                    if (id != 0) {
                        continue;
                    }

                }
                if (strings[i].contains("name")) {
                    int index1 = strings[i].indexOf(":");
                    String subString1 = strings[i].substring(index1);
                    String subString2 = subString1.substring(3);
                    int index2 = subString2.length();
                    String subString3 = subString2.substring(0, index2 - 1);
                    name = subString3;
                    continue;
                }
                if (strings[i].contains("description")) {
                    int index1 = strings[i].indexOf(":");
                    String subString1 = strings[i].substring(index1);
                    String subString2 = subString1.substring(3);
                    int index2 = subString2.length();
                    String subString3 = subString2.substring(0, index2 - 1);
                    description = subString3;
                    continue;
                }
                if (strings[i].contains("status")) {
                    int index1 = strings[i].indexOf(":");
                    String subString1 = strings[i].substring(index1);
                    String subString2 = subString1.substring(3);
                    int index2 = subString2.length();
                    String subString3 = subString2.substring(0, index2 - 1);
                    status = StatusTypes.valueOf(subString3);
                    continue;
                }
                if (strings[i].contains("parrentId")) {
                    int index1 = strings[i].indexOf(":");
                    String subString1 = strings[i].substring(index1);
                    String subString2 = subString1.substring(2);
                    parrentId = Integer.parseInt(subString2);
                        continue;

                }
                if (strings[i].contains("startTime")) {
                    int index1 = strings[i].indexOf(":");
                    String subString1 = strings[i].substring(index1);
                    String subString2 = subString1.substring(3);
                    int index2 = subString2.length();
                    String subString3 = subString2.substring(0, index2 - 1);
                    startTime = subString3;
                    continue;
                }
                if (strings[i].contains("duration")) {
                    int index1 = strings[i].indexOf(":");
                    String subString1 = strings[i].substring(index1);
                    String subString2 = subString1.substring(3);
                    int index2 = subString2.length();
                    String subString3 = subString2.substring(0, index2 - 3);
                    duration = subString3;
                    continue;
                }
            }

            if (id != 0)  {
                SubTask subTask = new SubTask(name, description, id, parrentId, startTime, duration, status);
                return Optional.of(subTask);
            }
            SubTask subTask = new SubTask(name, description, parrentId, startTime, duration);
            return Optional.of(subTask);
        }
    }

    static class HistoryHandler implements HttpHandler {

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
                        String jsonTask = gsonTask.toJson(task);
                        jsonAllTasks = jsonAllTasks + "\n" + jsonTask;
                    }
                    if (task.getType() == Types.EPIC) {
                        String jsonEpic = gsonEpic.toJson(task);
                        jsonAllTasks = jsonAllTasks + "\n" + jsonEpic;
                    }
                    if (task.getType() == Types.SUBTASK) {
                        String jsonSubtask = gsonSubTasks.toJson(task);
                        jsonAllTasks = jsonAllTasks + "\n" + jsonSubtask;
                    }
                }
                writeResponse(httpExchange, jsonAllTasks, 200);
            } else {
                writeResponse(httpExchange, "Задач в текущий момент нет", 404);
            }
        }
    }

    static class PrioritizedHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            System.out.println("Началась обработка /prioritized запроса от клиента.");

            handleGetPrioritized(httpExchange);

        }

        public void handleGetPrioritized(HttpExchange httpExchange) throws IOException {
            String jsonAllTasks = "";

            if (fileBackedTaskManager.getPrioritizedTasks() != null) {
                for (Task task : fileBackedTaskManager.getPrioritizedTasks()) {
                    if (task.getType() == Types.TASK) {
                        String jsonTask = gsonTask.toJson(task);
                        jsonAllTasks = jsonAllTasks + "\n" + jsonTask;
                    }
                    if (task.getType() == Types.EPIC) {
                        String jsonEpic = gsonEpic.toJson(task);
                        jsonAllTasks = jsonAllTasks + "\n" + jsonEpic;
                    }
                    if (task.getType() == Types.SUBTASK) {
                        String jsonSubtask = gsonSubTasks.toJson(task);
                        jsonAllTasks = jsonAllTasks + "\n" + jsonSubtask;
                    }
                }
                writeResponse(httpExchange, jsonAllTasks, 200);
            } else {
                writeResponse(httpExchange, "Задач в текущий момент нет", 404);
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

    public static class TaskAdapter extends TypeAdapter<Task> {

        @Override
        public void write(JsonWriter jsonWriter, Task task) throws IOException {
            jsonWriter.beginObject();
            jsonWriter.name("id").value(task.getId());
            jsonWriter.name("type").value(task.getType().toString());
            jsonWriter.name("name").value(task.getName());
            jsonWriter.name("status").value(task.getStatus().toString());
            jsonWriter.name("description").value(task.getDescription());
            jsonWriter.name("startTime").value(task.getStartTime().toString());
            jsonWriter.name("duration").value(task.getDuration().toString());
            jsonWriter.endObject();
        }

        @Override
        public Task read(JsonReader jsonReader) throws IOException {
            int id = 0;
            String name = null;
            String description = null;
            String duration = null;
            String startTime = null;

            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String fieldName = jsonReader.nextName();
                switch (fieldName) {
                    case "id":
                        id = jsonReader.nextInt();
                        break;
                    case "name":
                        name = jsonReader.nextString();
                        break;
                    case "description":
                        description = jsonReader.nextString();
                        break;
                    case "duration":
                        duration = jsonReader.nextString();
                        break;
                    case "time":
                        startTime = jsonReader.nextString();
                        break;
                    default:
                        jsonReader.skipValue();
                        break;
                }
            }
            jsonReader.endObject();

            return new Task(name, description, duration, startTime);
        }
    }

    public static Gson gsonTask = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .registerTypeAdapter(Task.class, new TaskAdapter())
            .create();

    public static class EpicAdapter extends TypeAdapter<Epic> {

        @Override
        public void write(JsonWriter jsonWriter, Epic epic) throws IOException {
            jsonWriter.beginObject();
            jsonWriter.name("id").value(epic.getId());
            jsonWriter.name("type").value(epic.getType().toString());
            jsonWriter.name("name").value(epic.getName());
            jsonWriter.name("status").value(epic.getStatus().toString());
            jsonWriter.name("description").value(epic.getDescription());
            jsonWriter.name("startTime").value(epic.getStartTime().toString());
            jsonWriter.name("duration").value(epic.getDuration().toString());
            jsonWriter.endObject();
        }

        @Override
        public Epic read(JsonReader jsonReader) throws IOException {
            int id = 0;
            String name = null;
            String description = null;
            String duration = null;
            String startTime = null;

            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String fieldName = jsonReader.nextName();
                switch (fieldName) {
                    case "id":
                        id = jsonReader.nextInt();
                        break;
                    case "name":
                        name = jsonReader.nextString();
                        break;
                    case "description":
                        description = jsonReader.nextString();
                        break;
                    case "duration":
                        duration = jsonReader.nextString();
                        break;
                    case "time":
                        startTime = jsonReader.nextString();
                        break;
                    default:
                        jsonReader.skipValue();
                        break;
                }
            }
            jsonReader.endObject();

            return new Epic(name, description, duration, startTime);
        }
    }

    public static Gson gsonEpic = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .registerTypeAdapter(Epic.class, new EpicAdapter())
            .create();

    public static class SubTaskAdapter extends TypeAdapter<SubTask> {

        @Override
        public void write(JsonWriter jsonWriter, SubTask subTask) throws IOException {
            jsonWriter.beginObject();
            jsonWriter.name("id").value(subTask.getId());
            jsonWriter.name("type").value(subTask.getType().toString());
            jsonWriter.name("name").value(subTask.getName());
            jsonWriter.name("status").value(subTask.getStatus().toString());
            jsonWriter.name("parrentId").value(subTask.getParrentId());
            jsonWriter.name("description").value(subTask.getDescription());
            jsonWriter.name("startTime").value(subTask.getStartTime().toString());
            jsonWriter.name("duration").value(subTask.getDuration().toString());
            jsonWriter.endObject();
        }

        @Override
        public SubTask read(JsonReader jsonReader) throws IOException {
            int id = 0;
            int parrentid = 0;
            String name = null;
            String description = null;
            String duration = null;
            String startTime = null;

            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String fieldName = jsonReader.nextName();
                switch (fieldName) {
                    case "id":
                        id = jsonReader.nextInt();
                        break;
                    case "name":
                        name = jsonReader.nextString();
                        break;
                    case "description":
                        description = jsonReader.nextString();
                        break;
                    case "parrentid":
                        parrentid = jsonReader.nextInt();
                        break;
                    case "duration":
                        duration = jsonReader.nextString();
                        break;
                    case "time":
                        startTime = jsonReader.nextString();
                        break;
                    default:
                        jsonReader.skipValue();
                        break;
                }
            }
            jsonReader.endObject();

            return new SubTask(name, description, parrentid, duration, startTime);
        }
    }

    public static Gson gsonSubTasks = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .registerTypeAdapter(SubTask.class, new SubTaskAdapter())
            .create();


    public Gson getGsonTask() {
        return gsonTask;
    }

    public Gson getGsonEpic() {
        return gsonEpic;
    }

    public Gson getGsonSubTasks() {
        return gsonSubTasks;
    }

    public void deleteAllTasks() {
        fileBackedTaskManager.taskMap.clear();
        fileBackedTaskManager.epicMap.clear();
        fileBackedTaskManager.subTaskMap.clear();
        fileBackedTaskManager.setNumberOfTaskIdsToZero();
    }
}
