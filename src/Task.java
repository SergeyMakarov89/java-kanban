public class Task {
    protected String name;
    protected int id;
    protected String description;
    protected StatusTypes status;

    protected Types type;

    public Task() {

    }

    public Task(String name, String discription) {
        this.name = name;
        this.description = discription;
        this.status = StatusTypes.NEW;
        this.type = Types.TASK;
    }

    public Task(String name, String discription, int id, StatusTypes statusTypes) {
        this.name = name;
        this.description = discription;
        this.id = id;
        this.status = statusTypes;
    }


    @Override
    public String toString() {
        return "Задача:'" + name +
                "', id:'" + id +
                "', С описанием:'" + description +
                "', C текущим статусом:'" + status + "'";
    }

    public String toStringToFile() {
        return id + "," + type + "," + name + "," +
                status + "," + description;
    }

    public int getId() {
        return id;
    }


    public StatusTypes getStatus() {
        return status;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(StatusTypes status) {
        this.status = status;
    }
}
