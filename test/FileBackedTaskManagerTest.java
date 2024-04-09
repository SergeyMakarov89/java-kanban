import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest {
    @Test
    void loadFromFileTest() {
        String path = "C:\\Users\\1\\Videos\\java-kanban\\test.csv";
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(path);

        Task task = new Task("Погулять", "Выйти на улицу и прогуляться", "13:30", "PT20M");
        fileBackedTaskManager.makeNewTask(task);

        Task task2 = new Task("Поиграть в компик", "Включить компьютер и поиграть", "14:00", "PT40M");
        fileBackedTaskManager.makeNewTask(task2);

        Epic epic = new Epic("Купить продукты", "Сходить в магазин и прибарахлиться");
        fileBackedTaskManager.makeNewEpic(epic);

        SubTask subTask = new SubTask("Купить молоко", "Купить молоко Простоквашино", 3, "12:01", "PT10M");
        fileBackedTaskManager.makeNewSubTask(subTask);

        SubTask subTask2 = new SubTask("Купить мясо", "Купить мясо говядины", 3, "12:12", "PT10M");
        fileBackedTaskManager.makeNewSubTask(subTask2);

        SubTask subTask3 = new SubTask("Купить яйца", "Купить яйцо куриное", 3, "12:23", "PT10M");
        fileBackedTaskManager.makeNewSubTask(subTask3);

        Epic epic2 = new Epic("Забрать жену с работы", "Взять машину и забрать жену с работы");
        fileBackedTaskManager.makeNewEpic(epic2);

        SubTask subTask4 = new SubTask("Забрать жену", "Доехать до работы и забрать жену", 7, "18:01", "PT40M");
        fileBackedTaskManager.makeNewSubTask(subTask4);

        fileBackedTaskManager.getSubTaskById(6);

        fileBackedTaskManager.getSubTaskById(5);

        fileBackedTaskManager.getEpicById(3);

        fileBackedTaskManager.getTaskById(1);

        fileBackedTaskManager.getSubTaskById(4);

        fileBackedTaskManager.getTaskById(2);

        fileBackedTaskManager.getTaskById(1);

        fileBackedTaskManager.getEpicById(3);

        fileBackedTaskManager.getEpicById(7);

        fileBackedTaskManager.getSubTaskById(5);

        fileBackedTaskManager.getTaskById(2);

        fileBackedTaskManager.getSubTaskById(6);

        fileBackedTaskManager.getSubTaskById(4);

        fileBackedTaskManager.getEpicById(7);

        fileBackedTaskManager.getSubTaskById(8);

        fileBackedTaskManager.deleteTaskById(1);

        fileBackedTaskManager.deleteEpicById(3);


        fileBackedTaskManager.loadFromFile(path);

        assertNotNull(fileBackedTaskManager.getAllTasks());
        assertNotNull(fileBackedTaskManager.getAllEpics());
        assertNotNull(fileBackedTaskManager.getAllSubTasks());
        assertNotNull(fileBackedTaskManager.getHistory());
    }

    @Test
    void saveTest() {
        String path = "C:\\Users\\1\\Videos\\java-kanban\\test.csv";
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(path);

        Task task = new Task("Погулять", "Выйти на улицу и прогуляться", "13:30", "PT20M");
        fileBackedTaskManager.makeNewTask(task);

        Task task2 = new Task("Поиграть в компик", "Включить компьютер и поиграть", "14:00", "PT40M");
        fileBackedTaskManager.makeNewTask(task2);

        Epic epic = new Epic("Купить продукты", "Сходить в магазин и прибарахлиться");
        fileBackedTaskManager.makeNewEpic(epic);

        SubTask subTask = new SubTask("Купить молоко", "Купить молоко Простоквашино", 3, "12:01", "PT10M");
        fileBackedTaskManager.makeNewSubTask(subTask);

        SubTask subTask2 = new SubTask("Купить мясо", "Купить мясо говядины", 3, "12:12", "PT10M");
        fileBackedTaskManager.makeNewSubTask(subTask2);

        fileBackedTaskManager.loadFromFile(path);

        assertNotNull(fileBackedTaskManager.getAllTasks());
        assertNotNull(fileBackedTaskManager.getAllEpics());
        assertNotNull(fileBackedTaskManager.getAllSubTasks());
    }

}