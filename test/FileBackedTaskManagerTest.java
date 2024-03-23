import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest {
    @Test
    void loadFromFileTest() throws IOException, ManagerSaveException {
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager();

        fileBackedTaskManager.loadFromFile();

        assertNotNull(fileBackedTaskManager.getAllTasks());
        assertNotNull(fileBackedTaskManager.getAllEpics());
        assertNotNull(fileBackedTaskManager.getAllSubTasks());
    }

    @Test
    void saveTest() throws IOException, ManagerSaveException {
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