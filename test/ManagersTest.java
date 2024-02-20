import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {
    @Test
    void ManagersReturnInMemoryTaskManager() {
        assertTrue(Managers.makeInMemoryTaskManager() instanceof InMemoryTaskManager);
    }

    @Test
    void ManagersReturnInMemoryHistoryManager() {
        assertTrue(Managers.makeInMemoryHistoryManager() instanceof InMemoryHistoryManager);
    }
}