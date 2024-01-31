public class SubTask extends Epic {
    String name;
    int id;
    StatusTypes status;
    int parentEpicId;

    public SubTask(Epic epic, String name, int id) {
        super(epic.name, epic.id);
        this.id = id;
        this.name = name;
        this.status = StatusTypes.NEW;
        this.parentEpicId = epic.id;
    }

    @Override
    public String toString() {
        return "Подзадача:'" + name +
                "', C текущим статусом:'" + status + "'";
    }
}
