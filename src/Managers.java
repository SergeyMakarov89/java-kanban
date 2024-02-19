import java.util.ArrayList;
import java.util.List;

public class Managers {
    public static InMemoryTaskManager makeInMemoryTaskManager() {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        return inMemoryTaskManager;
    }
}
