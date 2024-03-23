import java.io.IOException;
import java.util.List;

public interface TaskManager {
    void makeNewTask(Task task) throws IOException, ManagerSaveException;

    void makeNewEpic(Epic epic) throws IOException, ManagerSaveException;

    void makeNewSubTask(SubTask subTask) throws IOException, ManagerSaveException;

    void changeTask(Task task) throws IOException, ManagerSaveException;

    void changeEpic(Epic epic) throws IOException, ManagerSaveException;

    void changeSubTask(SubTask subTask) throws IOException, ManagerSaveException;

    void deleteTaskById(int removingId) throws IOException, ManagerSaveException;

    void deleteEpicById(int removingId) throws IOException, ManagerSaveException;

    void deleteSubTaskById(int removingId) throws IOException, ManagerSaveException;

    void updateStatusEpic(int epicId);

    Task getTaskById(int id);

    Epic getEpicById(int id);

    SubTask getSubTaskById(int id);

    List<Task> getAllTasks();

    List<Epic> getAllEpics();

    List<SubTask> getAllSubTasks();

    List<Integer> getAllSubTasksByEpicId(int epicId);

    List<Task> getHistory();

}
