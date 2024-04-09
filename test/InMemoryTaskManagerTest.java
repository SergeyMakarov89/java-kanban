import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    @Test
    void taskEqualsTaskById() {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        Task task1 = new Task("Погулять", "Выйти на улицу и прогуляться");
        inMemoryTaskManager.makeNewTask(task1);

        Task task2 = inMemoryTaskManager.taskMap.get(1);

        assertEquals(task2, task1, "Задачи не совпадают.");
    }

    @Test
    void epicEqualsEpicById() {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        Epic epic1 = new Epic("Купить продукты", "Сходить в магазин и прибарахлиться");
        inMemoryTaskManager.makeNewEpic(epic1);

        Epic epic2 = inMemoryTaskManager.epicMap.get(1);

        assertEquals(epic2, epic1, "Эпики не совпадают.");
    }

    @Test
    void epicCanNotAddItselfIntoEpicAsSubtask() {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        Epic epic = new Epic("Купить продукты", "Сходить в магазин и прибарахлиться");
        inMemoryTaskManager.makeNewEpic(epic);

        SubTask subTask = new SubTask("Купить мясо", "Купить мясо говядины, вырезку", 1, "12:01", "PT10M");
        inMemoryTaskManager.makeNewSubTask(subTask);

        ArrayList<Integer> epicSubTaskList1 = epic.getSubTaskList();
        ArrayList<Integer> epicSubTaskList2 = epic.getSubTaskList();
        int idEpic = epic.getId();

        epicSubTaskList1.add(idEpic);
        epic.setSubTaskList(epicSubTaskList1);

        assertEquals(epicSubTaskList2, inMemoryTaskManager.getEpicById(1).getSubTaskList());
    }

    @Test
    void subTaskCanNotBeAsItselfEpic() {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        Epic epic = new Epic("Купить продукты", "Сходить в магазин и прибарахлиться");
        inMemoryTaskManager.makeNewEpic(epic);

        SubTask subTask = new SubTask("Купить мясо", "Купить мясо говядины, вырезку", 2);

        assertNull(inMemoryTaskManager.subTaskMap.get(2));
    }

    @Test
    void canMakeAndFindTaskEpicAndSubTask() {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        Task task = new Task("Погулять", "Выйти на улицу и прогуляться", "13:30", "PT20M");
        inMemoryTaskManager.makeNewTask(task);
        assertEquals(1, inMemoryTaskManager.taskMap.get(1).getId());

        Task task2 = new Task("Поиграть в компик", "Включить компьютер и поиграть", "14:00", "PT40M");
        inMemoryTaskManager.makeNewTask(task2);
        assertEquals(2, inMemoryTaskManager.taskMap.get(2).getId());

        Epic epic = new Epic("Купить продукты", "Сходить в магазин и прибарахлиться");
        inMemoryTaskManager.makeNewEpic(epic);
        assertEquals(3, inMemoryTaskManager.epicMap.get(3).getId());

        SubTask subTask = new SubTask("Купить молоко", "Купить молоко Простоквашино", 3, "12:01", "PT10M");
        inMemoryTaskManager.makeNewSubTask(subTask);
        assertEquals(4, inMemoryTaskManager.subTaskMap.get(4).getId());

        SubTask subTask2 = new SubTask("Купить мясо", "Купить мясо говядины вырезку", 3, "12:12", "PT10M");
        inMemoryTaskManager.makeNewSubTask(subTask2);
        assertEquals(5, inMemoryTaskManager.subTaskMap.get(5).getId());
    }

    @Test
    void canChangeTask() {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        Task task1 = new Task("Погулять", "Выйти на улицу и прогуляться");
        inMemoryTaskManager.makeNewTask(task1);

        Task task2 = new Task("Поплавать в бассейне", "Сходить в бассейн и поплавать", 1, StatusTypes.IN_PROGRESS);
        inMemoryTaskManager.changeTask(task2);

        assertEquals(task2, inMemoryTaskManager.getTaskById(1));
    }

    @Test
    void canChangeEpic() {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        Epic epic1 = new Epic("Купить продукты", "Сходить в магазин и прибарахлиться");
        inMemoryTaskManager.makeNewEpic(epic1);

        Epic epic2 = new Epic("Купить бытовую химию", "Сходить в бытовой магазин", 1);
        inMemoryTaskManager.changeEpic(epic2);

        assertEquals(epic2, inMemoryTaskManager.getEpicById(1));
    }

    @Test
    void canChangeSubTask() {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        Epic epic1 = new Epic("Купить продукты", "Сходить в магазин и прибарахлиться");
        inMemoryTaskManager.makeNewEpic(epic1);

        SubTask subTask1 = new SubTask("Купить молоко", "Купить молоко Простоквашино", 1, "12:01", "PT10M");
        inMemoryTaskManager.makeNewSubTask(subTask1);

        SubTask subTask2 = new SubTask("Купить молоко сделано", "Купить молоко Простоквашино",
                2, 1, "12:01", "PT10M", StatusTypes.DONE);
        inMemoryTaskManager.changeSubTask(subTask2);

        assertEquals(subTask2, inMemoryTaskManager.getSubTaskById(2));
    }

    @Test
    void canDeleteTask() {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        Task task1 = new Task("Погулять", "Выйти на улицу и прогуляться");
        inMemoryTaskManager.makeNewTask(task1);

        Task task2 = new Task("Поиграть в компик", "Включить компьютер и поиграть");
        inMemoryTaskManager.makeNewTask(task2);

        inMemoryTaskManager.deleteTaskById(1);

        assertEquals(1, inMemoryTaskManager.getAllTasks().size());
    }

    @Test
    void canDeleteEpic() {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

        Epic epic = new Epic("Купить продукты", "Сходить в магазин и прибарахлиться");
        inMemoryTaskManager.makeNewEpic(epic);

        SubTask subTask = new SubTask("Купить молоко", "Купить молоко Простоквашино", 1, "12:01", "PT10M");
        inMemoryTaskManager.makeNewSubTask(subTask);

        SubTask subTask2 = new SubTask("Купить мясо", "Купить мясо говядины", 1, "12:12", "PT10M");
        inMemoryTaskManager.makeNewSubTask(subTask2);

        SubTask subTask3 = new SubTask("Купить яйца", "Купить яйцо куриное 10шт С1", 1, "12:23", "PT10M");
        inMemoryTaskManager.makeNewSubTask(subTask3);

        Epic epic2 = new Epic("Забрать жену", "Взять машину и довезти жену до дома", "18:00", "PT0M");
        inMemoryTaskManager.makeNewEpic(epic2);

        SubTask subTask4 = new SubTask("Взять машину", "Дойти до гаража и взять машину", 5, "18:01", "PT20M");
        inMemoryTaskManager.makeNewSubTask(subTask4);

        SubTask subTask5 = new SubTask("Довезти жену", "Доехать до работы жены и забрать ее", 5, "18:22", "PT20M");
        inMemoryTaskManager.makeNewSubTask(subTask5);

        SubTask subTask6 = new SubTask("Поставить машину", "Поставить машину в гараж, вернуться", 5, "18:43", "PT20M");
        inMemoryTaskManager.makeNewSubTask(subTask6);

        inMemoryTaskManager.deleteEpicById(1);

        assertEquals(1, inMemoryTaskManager.getAllEpics().size());
        assertEquals(3, inMemoryTaskManager.getAllSubTasks().size());
    }

    @Test
    void canDeleteSubTask() {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

        Epic epic = new Epic("Купить продукты", "Сходить в магазин и прибарахлиться");
        inMemoryTaskManager.makeNewEpic(epic);

        SubTask subTask = new SubTask("Купить молоко", "Купить молоко Простоквашино", 1, "12:01", "PT10M");
        inMemoryTaskManager.makeNewSubTask(subTask);

        SubTask subTask2 = new SubTask("Купить мясо", "Купить мясо говядины, вырезку", 1, "12:12", "PT10M");
        inMemoryTaskManager.makeNewSubTask(subTask2);

        SubTask subTask3 = new SubTask("Купить яйца", "Купить яйцо куриное, 10шт, С1", 1, "12:23", "PT10M");
        inMemoryTaskManager.makeNewSubTask(subTask3);


        inMemoryTaskManager.deleteSubTaskById(4);

        assertEquals(1, inMemoryTaskManager.getAllEpics().size());
        assertEquals(2, inMemoryTaskManager.getAllSubTasks().size());
        assertEquals(2, epic.getSubTaskList().size());
        assertFalse(epic.getSubTaskList().contains(4));
    }

    @Test
    void canGetAllSubTasksByEpicId() {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

        Epic epic = new Epic("Купить продукты", "Сходить в магазин и прибарахлиться");
        inMemoryTaskManager.makeNewEpic(epic);

        SubTask subTask = new SubTask("Купить молоко", "Купить молоко Простоквашино", 1, "12:01", "PT10M");
        inMemoryTaskManager.makeNewSubTask(subTask);

        SubTask subTask2 = new SubTask("Купить мясо", "Купить мясо говядины, вырезку", 1, "12:12", "PT10M");
        inMemoryTaskManager.makeNewSubTask(subTask2);

        SubTask subTask3 = new SubTask("Купить яйца", "Купить яйцо куриное 10шт С1", 1, "12:23", "PT10M");
        inMemoryTaskManager.makeNewSubTask(subTask3);

        assertEquals(3, inMemoryTaskManager.getAllSubTasksByEpicId(1).size());
    }

    @Test
    void canUpdateStatusEpic() {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        Epic epic1 = new Epic("Купить продукты", "Сходить в магазин и прибарахлиться");
        inMemoryTaskManager.makeNewEpic(epic1);

        SubTask subTask1 = new SubTask("Купить молоко", "Купить молоко Простоквашино", 1, "12:01", "PT10M");
        inMemoryTaskManager.makeNewSubTask(subTask1);

        SubTask subTask2 = new SubTask("Купить молоко сделано", "Купить молоко Простоквашино",
                2, 1, "12:12", "PT10M", StatusTypes.IN_PROGRESS);
        inMemoryTaskManager.changeSubTask(subTask2);

        assertEquals(StatusTypes.IN_PROGRESS, inMemoryTaskManager.getEpicById(1).getStatus());
    }

    @Test
    void inMemoryTaskManagerSaveTaskEpicOrSubTask() {

        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        Task task = new Task("Погулять", "Выйти на улицу и прогуляться", "13:30", "PT20M");
        inMemoryTaskManager.makeNewTask(task);
        assertEquals(1, inMemoryTaskManager.taskMap.get(1).getId());

        Task task2 = new Task("Поиграть в компик", "Включить компьютер и поиграть", "14:00", "PT40M");
        inMemoryTaskManager.makeNewTask(task2);
        assertEquals(2, inMemoryTaskManager.taskMap.get(2).getId());

        Epic epic = new Epic("Купить продукты", "Сходить в магазин и прибарахлиться");
        inMemoryTaskManager.makeNewEpic(epic);
        assertEquals(3, inMemoryTaskManager.epicMap.get(3).getId());

        SubTask subTask = new SubTask("Купить молоко", "Купить молоко Простоквашино", 3, "12:01", "PT10M");
        inMemoryTaskManager.makeNewSubTask(subTask);
        assertEquals(4, inMemoryTaskManager.subTaskMap.get(4).getId());

        SubTask subTask2 = new SubTask("Купить мясо", "Купить мясо говядины, вырезку", 3, "12:12", "PT10M");
        inMemoryTaskManager.makeNewSubTask(subTask2);
        assertEquals(5, inMemoryTaskManager.subTaskMap.get(5).getId());

        inMemoryTaskManager.getTaskById(1);
        inMemoryTaskManager.getTaskById(2);
        inMemoryTaskManager.getEpicById(3);
        inMemoryTaskManager.getSubTaskById(4);
        inMemoryTaskManager.getSubTaskById(5);

        List<Task> history = inMemoryTaskManager.getHistory();

        assertTrue(history.contains(task));
        assertTrue(history.contains(task2));
        assertTrue(history.contains(epic));
        assertTrue(history.contains(subTask));
        assertTrue(history.contains(subTask2));
    }

    @Test
    void canAddSubTaskToTimeLine() {
        String path = "C:\\Users\\1\\Videos\\java-kanban\\test.csv";
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(path);

        Task task = new Task("Погулять", "Выйти на улицу и прогуляться", "13:30", "PT20M");
        fileBackedTaskManager.makeNewTask(task);
        assertEquals(1, fileBackedTaskManager.taskMap.get(1).getId());

        Task task2 = new Task("Поиграть в компик", "Включить компьютер и поиграть", "14:00", "PT40M");
        fileBackedTaskManager.makeNewTask(task2);
        assertEquals(2, fileBackedTaskManager.taskMap.get(2).getId());

        Epic epic = new Epic("Купить продукты", "Сходить в магазин и прибарахлиться");
        fileBackedTaskManager.makeNewEpic(epic);
        assertEquals(3, fileBackedTaskManager.epicMap.get(3).getId());

        SubTask subTask = new SubTask("Купить молоко", "Купить молоко Простоквашино", 3, "12:01", "PT10M");
        fileBackedTaskManager.makeNewSubTask(subTask);
        assertEquals(4, fileBackedTaskManager.subTaskMap.get(4).getId());
    }

    @Test
    void canAddTaskWithOutTIme() {
        String path = "C:\\Users\\1\\Videos\\java-kanban\\test.csv";
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(path);

        Task task = new Task("Погулять", "Выйти на улицу и прогуляться");
        fileBackedTaskManager.makeNewTask(task);
        assertEquals(1, fileBackedTaskManager.taskMap.get(1).getId());

        Task task2 = new Task("Поиграть в компик", "Включить компьютер и поиграть");
        fileBackedTaskManager.makeNewTask(task2);
        assertEquals(2, fileBackedTaskManager.taskMap.get(2).getId());
    }
}