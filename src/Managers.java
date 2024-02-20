import java.util.ArrayList;
import java.util.List;

public class Managers {
    public static TaskManager makeInMemoryTaskManager() {
        TaskManager inMemoryTaskManager = new InMemoryTaskManager();
        return inMemoryTaskManager;
    }

    public static HistoryManager makeInMemoryHistoryManager() {
        HistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
        return inMemoryHistoryManager;
    }
}
