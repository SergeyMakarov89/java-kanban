import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest {
    @Test
    void loadFromFileTest() {
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager();

        Task task = new Task("Погулять", "Выйти на улицу и прогуляться");
        fileBackedTaskManager.makeNewTask(task);

        Task task2 = new Task("Поиграть в компик", "Включить компьютер и поиграть");
        fileBackedTaskManager.makeNewTask(task2);

        Epic epic = new Epic("Купить продукты", "Сходить в магазин и прибарахлиться");
        fileBackedTaskManager.makeNewEpic(epic);

        SubTask subTask = new SubTask("Купить молоко", "Купить молоко Простоквашино", 3);
        fileBackedTaskManager.makeNewSubTask(subTask);

        SubTask subTask2 = new SubTask("Купить мясо", "Купить мясо говядины", 3);
        fileBackedTaskManager.makeNewSubTask(subTask2);

        SubTask subTask3 = new SubTask("Купить яйца", "Купить яйцо куриное", 3);
        fileBackedTaskManager.makeNewSubTask(subTask3);

        Epic epic2 = new Epic("Забрать жену с работы", "Взять машину и забрать жену с работы");
        fileBackedTaskManager.makeNewEpic(epic2);

        SubTask subTask4 = new SubTask("Забрать жену", "Доехать до работы и забрать жену", 7);
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


        fileBackedTaskManager.loadFromFile();

        assertNotNull(fileBackedTaskManager.getAllTasks());
        assertNotNull(fileBackedTaskManager.getAllEpics());
        assertNotNull(fileBackedTaskManager.getAllSubTasks());
        assertNotNull(fileBackedTaskManager.getHistory());
    }

    @Test
    void saveTest() {
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager();

        Task task = new Task("Погулять", "Выйти на улицу и прогуляться");
        fileBackedTaskManager.makeNewTask(task);

        Task task2 = new Task("Поиграть в компик", "Включить компьютер и поиграть");
        fileBackedTaskManager.makeNewTask(task2);

        Epic epic = new Epic("Купить продукты", "Сходить в магазин и прибарахлиться");
        fileBackedTaskManager.makeNewEpic(epic);

        SubTask subTask = new SubTask("Купить молоко", "Купить молоко Простоквашино", 3);
        fileBackedTaskManager.makeNewSubTask(subTask);

        SubTask subTask2 = new SubTask("Купить мясо", "Купить мясо говядины", 3);
        fileBackedTaskManager.makeNewSubTask(subTask2);

        fileBackedTaskManager.loadFromFile();

        assertNotNull(fileBackedTaskManager.getAllTasks());
        assertNotNull(fileBackedTaskManager.getAllEpics());
        assertNotNull(fileBackedTaskManager.getAllSubTasks());
    }

}