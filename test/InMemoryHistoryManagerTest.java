import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    @Test
    void historyManagerSaveTaskEpicOrSubTask() {

        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        Task task = new Task("Погулять", "Выйти на улицу и прогуляться");
        assertEquals(1, inMemoryTaskManager.makeNewTask(task));

        Task task2 = new Task("Поиграть в компик", "Включить компьютер и поиграть");
        assertEquals(2, inMemoryTaskManager.makeNewTask(task2));

        Epic epic = new Epic("Купить продукты", "Сходить в магазин и прибарахлиться");
        assertEquals(3, inMemoryTaskManager.makeNewEpic(epic));

        SubTask subTask = new SubTask("Купить молоко", "Купить молоко Простоквашино", 3);
        assertEquals(4, inMemoryTaskManager.makeNewSubTask(subTask));

        SubTask subTask2 = new SubTask("Купить мясо", "Купить мясо говядины, вырезку", 3);
        assertEquals(5, inMemoryTaskManager.makeNewSubTask(subTask2));

        inMemoryTaskManager.getTaskById(1);
        inMemoryTaskManager.getTaskById(2);
        inMemoryTaskManager.getEpicById(3);
        inMemoryTaskManager.getSubTaskById(4);
        inMemoryTaskManager.getSubTaskById(5);

        List<Task> history = inMemoryTaskManager.inMemoryHistoryManager.getHistory();

        assertEquals(task, history.get(0));
        assertEquals(task2, history.get(1));
        assertEquals(epic, history.get(2));
        assertEquals(subTask, history.get(3));
        assertEquals(subTask2, history.get(4));
    }

    @Test
    void historyManagerCanDeleteLastElementInHistory() {

        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        Task task = new Task("Погулять", "Выйти на улицу и прогуляться");
        assertEquals(1, inMemoryTaskManager.makeNewTask(task));

        Task task2 = new Task("Поиграть в компик", "Включить компьютер и поиграть");
        assertEquals(2, inMemoryTaskManager.makeNewTask(task2));

        Epic epic = new Epic("Купить продукты", "Сходить в магазин и прибарахлиться");
        assertEquals(3, inMemoryTaskManager.makeNewEpic(epic));

        SubTask subTask = new SubTask("Купить молоко", "Купить молоко Простоквашино", 3);
        assertEquals(4, inMemoryTaskManager.makeNewSubTask(subTask));

        SubTask subTask2 = new SubTask("Купить мясо", "Купить мясо говядины, вырезку", 3);
        assertEquals(5, inMemoryTaskManager.makeNewSubTask(subTask2));

        inMemoryTaskManager.getTaskById(1);
        inMemoryTaskManager.getTaskById(2);
        inMemoryTaskManager.getEpicById(3);
        inMemoryTaskManager.getSubTaskById(4);
        inMemoryTaskManager.getSubTaskById(5);
        inMemoryTaskManager.getTaskById(1);
        inMemoryTaskManager.getTaskById(2);
        inMemoryTaskManager.getEpicById(3);
        inMemoryTaskManager.getSubTaskById(4);
        inMemoryTaskManager.getSubTaskById(5);
        inMemoryTaskManager.getTaskById(1);

        List<Task> history = inMemoryTaskManager.inMemoryHistoryManager.getHistory();

        assertEquals(2, history.get(0).getId());
    }
}