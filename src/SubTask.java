public class SubTask extends Task {

    private int parrentId;

    public SubTask(String name, String discription, int parrentId) {
        super.name = name;
        super.description = discription;
        super.type = Types.SUBTASK;
        this.status = StatusTypes.NEW;
        this.parrentId = parrentId;
    }

    public SubTask(String name, String discription, int parrentId, int id, StatusTypes statusTypes) {
        this.name = name;
        this.description = discription;
        this.parrentId = parrentId;
        this.id = id;
        this.status = statusTypes;

    }

    public SubTask(String name, String discription, int id, StatusTypes statusTypes, int parrentId) {
        this.name = name;
        this.description = discription;
        this.id = id;
        this.status = statusTypes;
        this.parrentId = parrentId;
    }
    @Override
    public String toString() {
        return "Подзадача:'" + name +
                "', id:'" + id +
                "', С описанием:'" + description +
                "', C текущим статусом:'" + status + "'";
    }
    @Override
    public String toStringToFile() {
        return id + "," + type + "," + name + "," +
                status + "," + description + "," + parrentId;
    }

    public int getParrentId() {
        return parrentId;
    }

}
