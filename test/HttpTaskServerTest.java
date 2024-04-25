import com.google.gson.Gson;
import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;

class HttpTaskServerTest {

    HttpTaskServer httpTaskServer = new HttpTaskServer();
    HttpServer httpServer = null;

    @BeforeEach
    public void startUp() throws IOException {
        httpServer = httpTaskServer.startServer();
    }

    @BeforeEach
    public void deleteAllTasks() throws IOException {
        httpTaskServer.deleteAllTasks();
    }

    @AfterEach
    public void shutDown() {
        httpTaskServer.stopServer(httpServer);
    }

    @Test
    void addTaskTest() throws IOException, InterruptedException {
        Task task = new Task("Погулять", "Выйти на улицу и прогуляться", "16:00", "PT40M");
        Gson gsonTask = httpTaskServer.getGsonTask();
        String jsonTask = gsonTask.toJson(task);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonTask))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response.statusCode());
    }

    @Test
    void changeTaskTest() throws IOException, InterruptedException {
        Task task1 = new Task("Погулять", "Выйти на улицу и прогуляться", "16:00", "PT40M");
        Gson gsonTask = httpTaskServer.getGsonTask();
        String jsonTask1 = gsonTask.toJson(task1);

        HttpClient client = HttpClient.newHttpClient();
        URI url1 = URI.create("http://localhost:8080/tasks");
        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(url1)
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonTask1))
                .build();
        HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());

        Task task2 = new Task("Погулять", "Выйти на улицу и прогуляться", 1, "15:00", "PT40M");
        String jsonTask2 = gsonTask.toJson(task2);

        URI url2 = URI.create("http://localhost:8080/tasks/" + task2.getId());
        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(url2)
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonTask2))
                .build();
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response2.statusCode());
    }

    @Test
    void deleteTaskTest() throws IOException, InterruptedException {
        Task task = new Task("Погулять", "Выйти на улицу и прогуляться", "16:00", "PT40M");
        Gson gsonTask = httpTaskServer.getGsonTask();
        String jsonTask = gsonTask.toJson(task);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonTask))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        URI url2 = URI.create("http://localhost:8080/tasks/" + 1);
        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(url2)
                .header("Accept", "application/json")
                .DELETE()
                .build();
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response2.statusCode());
    }

    @Test
    void getAllTasksTest() throws IOException, InterruptedException {
        Task task = new Task("Погулять", "Выйти на улицу и прогуляться", "16:00", "PT40M");
        Gson gsonTask = httpTaskServer.getGsonTask();
        String jsonTask = gsonTask.toJson(task);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(url)
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonTask))
                .build();
        HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());

        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(url)
                .header("Accept", "application/json")
                .GET()
                .build();
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response2.statusCode());
    }

    @Test
    void addEpicTest() throws IOException, InterruptedException {
        Epic epic = new Epic("Погулять", "Выйти на улицу и прогуляться", "00:00", "PT0M");
        Gson gsonEpic = httpTaskServer.getGsonEpic();
        String jsonEpic = gsonEpic.toJson(epic);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epics");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonEpic))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response.statusCode());
    }

    @Test
    void changeEpicTest() throws IOException, InterruptedException {
        Epic epic1 = new Epic("Погулять", "Выйти на улицу и прогуляться", "00:00", "PT0M");
        Gson gsonEpic = httpTaskServer.getGsonEpic();
        String jsonEpic1 = gsonEpic.toJson(epic1);

        HttpClient client = HttpClient.newHttpClient();
        URI url1 = URI.create("http://localhost:8080/epics");
        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(url1)
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonEpic1))
                .build();
        HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());

        Epic epic2 = new Epic("Погулять", "Выйти на улицу и прогуляться", 1, "15:00", "PT40M");
        String jsonEpic2 = gsonEpic.toJson(epic2);

        URI url2 = URI.create("http://localhost:8080/epics/" + epic2.getId());
        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(url2)
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonEpic2))
                .build();
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response2.statusCode());
    }

    @Test
    void deleteEpicTest() throws IOException, InterruptedException {
        Epic epic = new Epic("Погулять", "Выйти на улицу и прогуляться", "00:00", "PT0M");
        Gson gsonEpic = httpTaskServer.getGsonEpic();
        String jsonEpic = gsonEpic.toJson(epic);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epics");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonEpic))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        URI url2 = URI.create("http://localhost:8080/epics/" + 1);
        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(url2)
                .header("Accept", "application/json")
                .DELETE()
                .build();
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response2.statusCode());

    }

    @Test
    void getAllEpicsTest() throws IOException, InterruptedException {
        Epic epic = new Epic("Погулять", "Выйти на улицу и прогуляться", "00:00", "PT0M");
        Gson gsonEpic = httpTaskServer.getGsonEpic();
        String jsonEpic = gsonEpic.toJson(epic);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epics");
        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(url)
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonEpic))
                .build();
        HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());

        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(url)
                .header("Accept", "application/json")
                .GET()
                .build();
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response2.statusCode());
    }

    @Test
    void addSubTaskTest() throws IOException, InterruptedException {
        Epic epic = new Epic("Погулять", "Выйти на улицу и прогуляться", "00:00", "PT0M");
        Gson gsonEpic = httpTaskServer.getGsonEpic();
        String jsonEpic = gsonEpic.toJson(epic);
        HttpClient client = HttpClient.newHttpClient();
        URI url1 = URI.create("http://localhost:8080/epics");
        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(url1)
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonEpic))
                .build();
        HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());

        SubTask subTask = new SubTask("Погулять", "Выйти на улицу и прогуляться", 1, "10:00", "PT50M");
        Gson gsonSubtask = httpTaskServer.getGsonSubTasks();
        String jsonSubtask = gsonSubtask.toJson(subTask);
        URI url2 = URI.create("http://localhost:8080/subtasks");
        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(url2)
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonSubtask))
                .build();
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response2.statusCode());
    }

    @Test
    void changeSubTaskTest() throws IOException, InterruptedException {
        Epic epic = new Epic("Погулять", "Выйти на улицу и прогуляться", "00:00", "PT0M");
        Gson gsonEpic = httpTaskServer.getGsonEpic();
        String jsonEpic = gsonEpic.toJson(epic);
        HttpClient client = HttpClient.newHttpClient();
        URI url1 = URI.create("http://localhost:8080/epics");
        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(url1)
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonEpic))
                .build();
        HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());

        SubTask subTask1 = new SubTask("Погулять", "Выйти на улицу и прогуляться", 1, "10:00", "PT50M");
        Gson gsonSubtask = httpTaskServer.getGsonSubTasks();
        String jsonSubtask1 = gsonSubtask.toJson(subTask1);
        URI url2 = URI.create("http://localhost:8080/subtasks");
        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(url2)
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonSubtask1))
                .build();
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());

        SubTask subTask2 = new SubTask("Погулять", "Выйти на улицу и прогуляться", 2, 1, "10:00", "PT50M", StatusTypes.IN_PROGRESS);
        String jsonSubtask2 = gsonSubtask.toJson(subTask2);
        URI url3 = URI.create("http://localhost:8080/subtasks/" + subTask2.getId());
        HttpRequest request3 = HttpRequest.newBuilder()
                .uri(url3)
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonSubtask2))
                .build();
        HttpResponse<String> response3 = client.send(request3, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response3.statusCode());
    }

    @Test
    void deleteSubTaskTest() throws IOException, InterruptedException {
        Epic epic = new Epic("Погулять", "Выйти на улицу и прогуляться", "00:00", "PT0M");
        Gson gsonEpic = httpTaskServer.getGsonEpic();
        String jsonEpic = gsonEpic.toJson(epic);
        HttpClient client = HttpClient.newHttpClient();
        URI url1 = URI.create("http://localhost:8080/epics");
        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(url1)
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonEpic))
                .build();
        HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());

        SubTask subTask = new SubTask("Погулять", "Выйти на улицу и прогуляться", 1, "10:00", "PT50M");
        Gson gsonSubtask = httpTaskServer.getGsonSubTasks();
        String jsonSubtask = gsonSubtask.toJson(subTask);
        URI url2 = URI.create("http://localhost:8080/subtasks");
        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(url2)
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonSubtask))
                .build();
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());

        URI url3 = URI.create("http://localhost:8080/subtasks/" + 2);
        HttpRequest request3 = HttpRequest.newBuilder()
                .uri(url3)
                .header("Accept", "application/json")
                .DELETE()
                .build();
        HttpResponse<String> response3 = client.send(request3, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response2.statusCode());
    }

    @Test
    void getAllSubTasksTest() throws IOException, InterruptedException {
        Epic epic = new Epic("Погулять", "Выйти на улицу и прогуляться", "00:00", "PT0M");
        Gson gsonEpic = httpTaskServer.getGsonEpic();
        String jsonEpic = gsonEpic.toJson(epic);
        HttpClient client = HttpClient.newHttpClient();
        URI url1 = URI.create("http://localhost:8080/epics");
        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(url1)
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonEpic))
                .build();
        HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());

        SubTask subTask = new SubTask("Погулять", "Выйти на улицу и прогуляться", 1, "10:00", "PT50M");
        Gson gsonSubtask = httpTaskServer.getGsonSubTasks();
        String jsonSubtask = gsonSubtask.toJson(subTask);
        URI url2 = URI.create("http://localhost:8080/subtasks");
        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(url2)
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonSubtask))
                .build();
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());

        HttpRequest request3 = HttpRequest.newBuilder()
                .uri(url2)
                .header("Accept", "application/json")
                .GET()
                .build();
        HttpResponse<String> response3 = client.send(request3, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response3.statusCode());
    }

    @Test
    void canGetHistoryTest() throws IOException, InterruptedException {
        Task task = new Task("Погулять", "Выйти на улицу и прогуляться", "16:00", "PT40M");
        Gson gsonTask = httpTaskServer.getGsonTask();
        String jsonTask = gsonTask.toJson(task);
        HttpClient client = HttpClient.newHttpClient();
        URI url1 = URI.create("http://localhost:8080/tasks");
        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(url1)
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonTask))
                .build();
        HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());

        Epic epic = new Epic("Погулять", "Выйти на улицу и прогуляться", "00:00", "PT0M");
        Gson gsonEpic = httpTaskServer.getGsonEpic();
        String jsonEpic = gsonEpic.toJson(epic);
        URI url2 = URI.create("http://localhost:8080/epics");
        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(url2)
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonEpic))
                .build();
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());

        SubTask subTask = new SubTask("Погулять", "Выйти на улицу и прогуляться", 2, "10:00", "PT50M");
        Gson gsonSubtask = httpTaskServer.getGsonSubTasks();
        String jsonSubtask = gsonSubtask.toJson(subTask);
        URI url3 = URI.create("http://localhost:8080/subtasks");
        HttpRequest request3 = HttpRequest.newBuilder()
                .uri(url3)
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonSubtask))
                .build();
        HttpResponse<String> response3 = client.send(request3, HttpResponse.BodyHandlers.ofString());

        URI url4 = URI.create("http://localhost:8080/subtasks/" + 3);
        HttpRequest request4 = HttpRequest.newBuilder()
                .uri(url4)
                .header("Accept", "application/json")
                .GET()
                .build();
        HttpResponse<String> response4 = client.send(request4, HttpResponse.BodyHandlers.ofString());

        URI url5 = URI.create("http://localhost:8080/tasks/" + 1);
        HttpRequest request5 = HttpRequest.newBuilder()
                .uri(url5)
                .header("Accept", "application/json")
                .GET()
                .build();
        HttpResponse<String> response5 = client.send(request5, HttpResponse.BodyHandlers.ofString());

        URI url6 = URI.create("http://localhost:8080/epics/" + 2);
        HttpRequest request6 = HttpRequest.newBuilder()
                .uri(url6)
                .header("Accept", "application/json")
                .GET()
                .build();
        HttpResponse<String> response6 = client.send(request6, HttpResponse.BodyHandlers.ofString());

        URI url7 = URI.create("http://localhost:8080/history");
        HttpRequest request7 = HttpRequest.newBuilder()
                .uri(url7)
                .header("Accept", "application/json")
                .GET()
                .build();
        HttpResponse<String> response7 = client.send(request7, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response7.statusCode());
    }

    @Test
    void canGetPrioritizedTest() throws IOException, InterruptedException {
        Task task = new Task("Погулять", "Выйти на улицу и прогуляться", "01:00", "PT40M");
        Gson gsonTask = httpTaskServer.getGsonTask();
        String jsonTask = gsonTask.toJson(task);
        HttpClient client = HttpClient.newHttpClient();
        URI url1 = URI.create("http://localhost:8080/tasks");
        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(url1)
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonTask))
                .build();
        HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());

        Epic epic = new Epic("Погулять", "Выйти на улицу и прогуляться", "00:00", "PT0M");
        Gson gsonEpic = httpTaskServer.getGsonEpic();
        String jsonEpic = gsonEpic.toJson(epic);
        URI url2 = URI.create("http://localhost:8080/epics");
        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(url2)
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonEpic))
                .build();
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());

        SubTask subTask = new SubTask("Погулять", "Выйти на улицу и прогуляться", 2, "08:00", "PT50M");
        Gson gsonSubtask = httpTaskServer.getGsonSubTasks();
        String jsonSubtask = gsonSubtask.toJson(subTask);
        URI url3 = URI.create("http://localhost:8080/subtasks");
        HttpRequest request3 = HttpRequest.newBuilder()
                .uri(url3)
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonSubtask))
                .build();
        HttpResponse<String> response3 = client.send(request3, HttpResponse.BodyHandlers.ofString());

        URI url4 = URI.create("http://localhost:8080/subtasks/" + 3);
        HttpRequest request4 = HttpRequest.newBuilder()
                .uri(url4)
                .header("Accept", "application/json")
                .GET()
                .build();
        HttpResponse<String> response4 = client.send(request4, HttpResponse.BodyHandlers.ofString());

        URI url5 = URI.create("http://localhost:8080/tasks/" + 1);
        HttpRequest request5 = HttpRequest.newBuilder()
                .uri(url5)
                .header("Accept", "application/json")
                .GET()
                .build();
        HttpResponse<String> response5 = client.send(request5, HttpResponse.BodyHandlers.ofString());

        URI url6 = URI.create("http://localhost:8080/epics/" + 2);
        HttpRequest request6 = HttpRequest.newBuilder()
                .uri(url6)
                .header("Accept", "application/json")
                .GET()
                .build();
        HttpResponse<String> response6 = client.send(request6, HttpResponse.BodyHandlers.ofString());

        URI url7 = URI.create("http://localhost:8080/prioritized");
        HttpRequest request7 = HttpRequest.newBuilder()
                .uri(url7)
                .header("Accept", "application/json")
                .GET()
                .build();
        HttpResponse<String> response7 = client.send(request7, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response7.statusCode());
    }
}