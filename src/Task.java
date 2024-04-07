import java.time.Duration;
import java.time.LocalTime;

public class Task {
    protected String name;
    protected int id;
    protected String description;
    protected StatusTypes status;
    protected Types type;
    protected Duration duration;
    protected LocalTime startTime;

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

    public Task(String name, String discription, String startTime, String duration) {
        this.name = name;
        this.description = discription;
        this.status = StatusTypes.NEW;
        this.type = Types.TASK;
        this.startTime = LocalTime.parse(startTime);
        this.duration = Duration.parse(duration);
    }

    public Task(String name, String discription, int id, StatusTypes statusTypes, LocalTime startTime, Duration duration) {
        this.name = name;
        this.description = discription;
        this.id = id;
        this.status = statusTypes;
        this.startTime = startTime;
        this.duration = duration;
    }

    public Task(String name, String discription, int id, String startTime, String duration) {
        this.name = name;
        this.description = discription;
        this.id = id;
        this.status = StatusTypes.NEW;
        this.type = Types.TASK;
        this.startTime = LocalTime.parse(startTime);
        this.duration = Duration.parse(duration);
    }

    @Override
    public String toString() {
        return "Задача:'" + name +
                "', id:'" + id +
                "', С описанием:'" + description +
                "', C текущим статусом:'" + status +
                "', С временем старта:'" + startTime +
                "', С продолжительностью:'" + duration + "'";
    }

    public String toStringToFile() {
        return id + "," + type + "," + name + "," +
                status + "," + description + "," + startTime + "," + duration;
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

    public LocalTime getEndTime() {
        LocalTime endTime = startTime.plus(duration);
        return endTime;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public Types getLocalClass() {
        return this.type;
    }
}
