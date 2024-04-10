import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;

public class Epic extends Task {
    private ArrayList<Integer> subTaskList;
    protected LocalTime endTimeEpic;

    //конструктор для создания эпика без времени старта и продолжительности
    public Epic(String name, String discription) {
        super.name = name;
        super.description = discription;
        super.type = Types.EPIC;
        this.status = StatusTypes.NEW;
        this.subTaskList = new ArrayList<Integer>();
    }

    //конструктор для изменения эпика без времени старта и продолжительности
    public Epic(String name, String discription, int id) {
        this.name = name;
        this.description = discription;
        this.id = id;
        this.subTaskList = new ArrayList<Integer>();
    }

    //конструктор для создания эпика с временем старта и продолжительностью
    public Epic(String name, String discription, String startTime, String duration) {
        this.name = name;
        this.description = discription;
        this.id = id;
        this.startTime = LocalTime.parse(startTime);
        this.status = StatusTypes.NEW;
        this.type = Types.EPIC;
        this.subTaskList = new ArrayList<Integer>();
        this.duration = Duration.parse(duration);
    }

    //конструктор для изменения эпика с временем старта и продолжительностью
    public Epic(String name, String discription, int id, String startTime, String duration) {
        this.name = name;
        this.description = discription;
        this.id = id;
        this.status = StatusTypes.NEW;
        this.type = Types.EPIC;
        this.subTaskList = new ArrayList<Integer>();
        this.startTime = LocalTime.parse(startTime);
        this.duration = Duration.parse(duration);
    }

    //конструктор для восстановления эпика из файла с временем старта и продолжительностью
    public Epic(String name, String discription, int id, StatusTypes statusTypes, LocalTime startTime, Duration duration) {
        this.name = name;
        this.description = discription;
        this.id = id;
        this.type = Types.EPIC;
        this.status = statusTypes;
        this.subTaskList = new ArrayList<Integer>();
        this.startTime = startTime;
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Эпик:'" + name +
                "', id:'" + id +
                "', С описанием:'" + description +
                "', C текущим статусом:'" + status +
                "', С временем старта:'" + startTime +
                "', С продолжительностью:'" + duration + "'";

    }

    public ArrayList<Integer> getSubTaskList() {
        return subTaskList;
    }

    public void setSubTaskList(ArrayList<Integer> subTaskList) {
        if (subTaskList.contains(this.getId())) {
            System.out.println("id Эпика не может быть добавлен в список Подзадач этого же Эпика");
            return;
        }
        this.subTaskList = subTaskList;
    }

    public LocalTime getEndTimeEpic() {
        return endTimeEpic;
    }

    public void updateEpicStartTimeDurationAndEndTime(Map<Integer, SubTask> subTaskMap) {
        LocalTime minStartTime = LocalTime.of(23, 59, 59);
        duration = Duration.parse("PT0M");
        for (Integer i : subTaskList) {
            for (SubTask subTask : subTaskMap.values()) {
                if (subTask.getParrentId() == this.getId() && minStartTime.isAfter(subTask.getStartTime())) {
                    minStartTime = subTask.getStartTime();
                }
            }
            this.startTime = minStartTime;
            this.duration = duration.plus(subTaskMap.get(i).getDuration());
            this.endTimeEpic = this.getStartTime().plus(this.getDuration());
        }
    }
}
