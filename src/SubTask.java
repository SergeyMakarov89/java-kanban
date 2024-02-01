public class SubTask extends Task {

    int parrentId;
    SubTask(Task task, int parrentId) {
        super(task.name, task.id, task.discription);
        this.status = StatusTypes.NEW;
        this.parrentId = parrentId;
    }
    @Override
    public String toString() {
        return "Подзадача:'" + name +
                "', id:'" + id +
                "', С описанием:'" + discription +
                "', C текущим статусом:'" + status + "'";
    }

}
