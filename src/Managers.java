public class Managers {
    public static TaskManager makeInMemoryTaskManager() {
        TaskManager inMemoryTaskManager = new InMemoryTaskManager();
        return inMemoryTaskManager;
    }

    public static HistoryManager makeInMemoryHistoryManager() {
        HistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
        return inMemoryHistoryManager;
    }

    public static FileBackedTaskManager makeFileBackedTaskManager(String path) {
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(path);
        return fileBackedTaskManager;
    }

}
