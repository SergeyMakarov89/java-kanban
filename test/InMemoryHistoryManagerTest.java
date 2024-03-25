import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    @Test
    void historyManagerSaveTaskEpicOrSubTask() {

        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        Task task = new Task("Погулять", "Выйти на улицу и прогуляться");
        inMemoryTaskManager.makeNewTask(task);
        assertEquals(1, inMemoryTaskManager.taskMap.get(1).getId());

        Task task2 = new Task("Поиграть в компик", "Включить компьютер и поиграть");
        inMemoryTaskManager.makeNewTask(task2);
        assertEquals(2, inMemoryTaskManager.taskMap.get(2).getId());

        Epic epic = new Epic("Купить продукты", "Сходить в магазин и прибарахлиться");
        inMemoryTaskManager.makeNewEpic(epic);
        assertEquals(3, inMemoryTaskManager.epicMap.get(3).getId());

        SubTask subTask = new SubTask("Купить молоко", "Купить молоко Простоквашино", 3);
        inMemoryTaskManager.makeNewSubTask(subTask);
        assertEquals(4, inMemoryTaskManager.subTaskMap.get(4).getId());

        SubTask subTask2 = new SubTask("Купить мясо", "Купить мясо говядины, вырезку", 3);
        inMemoryTaskManager.makeNewSubTask(subTask2);
        assertEquals(5, inMemoryTaskManager.subTaskMap.get(5).getId());

        inMemoryTaskManager.getTaskById(1);
        inMemoryTaskManager.getTaskById(2);
        inMemoryTaskManager.getEpicById(3);
        inMemoryTaskManager.getSubTaskById(4);
        inMemoryTaskManager.getSubTaskById(5);

        List<Task> history = inMemoryTaskManager.getHistory();
        System.out.println(history);

        assertTrue(history.contains(task));
        assertTrue(history.contains(task2));
        assertTrue(history.contains(epic));
        assertTrue(history.contains(subTask));
        assertTrue(history.contains(subTask2));
    }

    @Test
    void historyManagerCanDeleteElementInHistory() {

        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        Task task = new Task("Погулять", "Выйти на улицу и прогуляться");
        inMemoryTaskManager.makeNewTask(task);
        assertEquals(1, inMemoryTaskManager.taskMap.get(1).getId());


        inMemoryTaskManager.getTaskById(1);
        inMemoryTaskManager.getTaskById(1);

        List<Task> history = inMemoryTaskManager.getHistory();

        assertEquals(1, history.size());
    }

    @Test
    void historyManagerDeleteTaskEpicOrSubTask() {

        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        Task task = new Task("Погулять", "Выйти на улицу и прогуляться");
        inMemoryTaskManager.makeNewTask(task);
        assertEquals(1, inMemoryTaskManager.taskMap.get(1).getId());

        Task task2 = new Task("Поиграть в компик", "Включить компьютер и поиграть");
        inMemoryTaskManager.makeNewTask(task2);
        assertEquals(2, inMemoryTaskManager.taskMap.get(2).getId());

        Epic epic = new Epic("Купить продукты", "Сходить в магазин и прибарахлиться");
        inMemoryTaskManager.makeNewEpic(epic);
        assertEquals(3, inMemoryTaskManager.epicMap.get(3).getId());

        SubTask subTask = new SubTask("Купить молоко", "Купить молоко Простоквашино", 3);
        inMemoryTaskManager.makeNewSubTask(subTask);
        assertEquals(4, inMemoryTaskManager.subTaskMap.get(4).getId());

        SubTask subTask2 = new SubTask("Купить мясо", "Купить мясо говядины, вырезку", 3);
        inMemoryTaskManager.makeNewSubTask(subTask2);
        assertEquals(5, inMemoryTaskManager.subTaskMap.get(5).getId());

        inMemoryTaskManager.getTaskById(1);
        inMemoryTaskManager.getTaskById(2);
        inMemoryTaskManager.getEpicById(3);
        inMemoryTaskManager.getSubTaskById(4);
        inMemoryTaskManager.getSubTaskById(5);
        inMemoryTaskManager.getSubTaskById(5);
        inMemoryTaskManager.getSubTaskById(4);
        inMemoryTaskManager.getEpicById(3);
        inMemoryTaskManager.getTaskById(2);
        inMemoryTaskManager.getTaskById(1);

        List<Task> history = inMemoryTaskManager.getHistory();
        System.out.println(history);

        assertEquals(5, history.size());
    }
}