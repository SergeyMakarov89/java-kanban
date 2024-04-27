import java.time.Duration;
import java.time.LocalTime;

public class SubTask extends Task {

    private int parrentId;

    //конструктор для создания сабтаски без времени старта и продолжительности
    public SubTask(String name, String discription, int parrentId) {
        super.name = name;
        super.description = discription;
        super.type = Types.SUBTASK;
        this.status = StatusTypes.NEW;
        this.parrentId = parrentId;
    }

    //конструктор для изменения сабтаски без времени старта и продолжительности
    public SubTask(String name, String discription, int parrentId, int id, StatusTypes statusTypes) {
        this.name = name;
        this.description = discription;
        this.parrentId = parrentId;
        this.id = id;
        this.status = statusTypes;

    }

    //конструктор для создания сабтаски с временем старта и продолжительностью
    public SubTask(String name, String discription, int parrentId, String startTime, String duration) {
        super.name = name;
        super.description = discription;
        super.type = Types.SUBTASK;
        this.status = StatusTypes.NEW;
        this.parrentId = parrentId;
        this.startTime = LocalTime.parse(startTime);
        this.duration = Duration.parse(duration);
    }

    //конструктор для изменения сабтаски с временем старта и продолжительностью
    public SubTask(String name, String discription, int id, int parrentId, String startTime, String duration, String statusTypes) {
        super.name = name;
        super.description = discription;
        super.type = Types.SUBTASK;
        this.status = StatusTypes.valueOf(statusTypes);
        this.id = id;
        this.parrentId = parrentId;
        this.startTime = LocalTime.parse(startTime);
        this.duration = Duration.parse(duration);
    }

    //конструктор для восстановления сабтаски из файла с временем старта и продолжительностью
    public SubTask(String name, String discription, int id, StatusTypes statusTypes, LocalTime startTime, Duration duration, int parrentId) {
        this.name = name;
        this.description = discription;
        this.type = Types.SUBTASK;
        this.id = id;
        this.status = statusTypes;
        this.startTime = startTime;
        this.duration = duration;
        this.parrentId = parrentId;
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
