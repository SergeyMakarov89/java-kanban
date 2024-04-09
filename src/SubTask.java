import java.time.Duration;
import java.time.LocalTime;

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

    public SubTask(String name, String discription, String id, String statusTypes, String startTime, String duration, String parrentId) {
        this.name = name;
        this.description = discription;
        this.type = Types.SUBTASK;
        this.id = Integer.parseInt(id);
        this.status = StatusTypes.valueOf(statusTypes);
        this.startTime = LocalTime.parse(startTime);
        this.duration = Duration.parse(duration);
        this.parrentId = Integer.parseInt(parrentId);
    }

    public SubTask(String name, String discription, int parrentId, String startTime, String duration) {
        super.name = name;
        super.description = discription;
        super.type = Types.SUBTASK;
        this.status = StatusTypes.NEW;
        this.parrentId = parrentId;
        this.startTime = LocalTime.parse(startTime);
        this.duration = Duration.parse(duration);
    }

    public SubTask(String name, String discription, int id, int parrentId, String startTime, String duration, StatusTypes statusTypes) {
        super.name = name;
        super.description = discription;
        super.type = Types.SUBTASK;
        this.status = statusTypes;
        this.id = id;
        this.parrentId = parrentId;
        this.startTime = LocalTime.parse(startTime);
        this.duration = Duration.parse(duration);
    }

    @Override
    public String toString() {
        return "Подзадача:'" + name +
                "', id:'" + id +
                "', С описанием:'" + description +
                "', C текущим статусом:'" + status + "'" +
                "', С временем старта:'" + startTime +
                "', С продолжительностью:'" + duration + "'";
    }

    @Override
    public String toStringToFile() {
        return id + "," + type + "," + name + "," +
                status + "," + description + "," + startTime + "," + duration + "," + parrentId;
    }

    public int getParrentId() {
        return parrentId;
    }

}
