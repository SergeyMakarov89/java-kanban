import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    List<Task> taskListHistory = new ArrayList<>();
    @Override
    public void add(Task task) {
        if(taskListHistory.size() == 10) {
            taskListHistory.remove(0);
        }
        taskListHistory.add(task);
    }

    @Override
    public List<Task> getHistory() {
        System.out.println(taskListHistory);
        return taskListHistory;
    }
}
