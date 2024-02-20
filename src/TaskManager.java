import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    void makeNewTask(Task task);

    void makeNewEpic(Epic epic);

    void makeNewSubTask(SubTask subTask);

    void changeTask(Task task);

    void changeEpic(Epic epic);

    void changeSubTask(SubTask subTask);

    void deleteTaskById(int removingId);

    void deleteEpicById(int removingId);

    void deleteSubTaskById(int removingId);

    void updateStatusEpic(int epicId);

    Task getTaskById(int id);

    Epic getEpicById(int id);

    SubTask getSubTaskById(int id);

    List<Task> getAllTasks();

    List<Epic> getAllEpics();

    List<SubTask> getAllSubTasks();

    ArrayList<Integer> getAllSubTasksByEpicId(int epicId);

    List<Task> getHistory();

}
