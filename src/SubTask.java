public class SubTask extends Task {

    private int parrentId;
    SubTask(String name, String discription, int parrentId) {
        super.name = name;
        super.description = discription;
        this.status = StatusTypes.NEW;
        this.parrentId = parrentId;
    }

    SubTask(String name, String discription, int parrentId, int id, StatusTypes statusTypes) {
        this.name = name;
        this.description = discription;
        this.parrentId = parrentId;
        this.id = id;
        this.status = statusTypes;

    }
    @Override
    public String toString() {
        return "Подзадача:'" + name +
                "', id:'" + id +
                "', С описанием:'" + description +
                "', C текущим статусом:'" + status + "'";
    }

    public int getParrentId() {
        return parrentId;
    }

}
