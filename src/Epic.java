import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Epic extends Task {
    private ArrayList<Integer> subTaskList;
    protected LocalTime endTimeEpic;

    public Epic(String name, String discription) {
        super.name = name;
        super.description = discription;
        super.type = Types.EPIC;
        this.status = StatusTypes.NEW;
        this.subTaskList = new ArrayList<Integer>();
    }


    public Epic(String name, String discription, int id) {
        this.name = name;
        this.description = discription;
        this.id = id;
        this.subTaskList = new ArrayList<Integer>();
    }

    public Epic(String name, String discription, int id, StatusTypes statusTypes) {
        this.name = name;
        this.description = discription;
        this.id = id;
        this.status = statusTypes;
        this.subTaskList = new ArrayList<Integer>();
    }

    public Epic(String name, String discription, String startTime) {
        this.name = name;
        this.description = discription;
        this.startTime = LocalTime.parse(startTime);
        this.status = StatusTypes.NEW;
        this.type = Types.EPIC;
        this.subTaskList = new ArrayList<Integer>();
        this.duration = null;
    }

    public Epic(String name, String discription, int id, StatusTypes statusTypes, String startTime) {
        this.name = name;
        this.description = discription;
        this.id = id;
        this.status = statusTypes;
        this.subTaskList = new ArrayList<Integer>();
        this.startTime = LocalTime.parse(startTime);
        this.duration = null;
    }

    public Epic(String name, String discription, int id, StatusTypes statusTypes, LocalTime startTime) {
        this.name = name;
        this.description = discription;
        this.id = id;
        this.status = statusTypes;
        this.subTaskList = new ArrayList<Integer>();
        this.startTime = startTime;
    }

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

    public Epic(String name, String discription, int id, String startTime) {
        this.name = name;
        this.description = discription;
        this.id = id;
        this.startTime = LocalTime.parse(startTime);
        this.status = StatusTypes.NEW;
        this.type = Types.EPIC;
        this.subTaskList = new ArrayList<Integer>();
        this.duration = null;
    }

    public Epic(String name, String discription, String startTime, String duranation) {
        this.name = name;
        this.description = discription;
        this.id = id;
        this.startTime = LocalTime.parse(startTime);
        this.status = StatusTypes.NEW;
        this.type = Types.EPIC;
        this.subTaskList = new ArrayList<Integer>();
        this.duration = Duration.parse(duranation);
    }

    public Epic(String name, String discription, int id, String startTime, String duranation) {
        this.name = name;
        this.description = discription;
        this.id = id;
        this.startTime = LocalTime.parse(startTime);
        this.status = StatusTypes.NEW;
        this.type = Types.EPIC;
        this.subTaskList = new ArrayList<Integer>();
        this.duration = Duration.parse(duranation);
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

    public void setEndTimeEpic(SubTask subTask) {
        if (!(subTaskList.isEmpty())) {
            if (subTask.getParrentId() == id) {
                if (endTimeEpic == null) {
                    endTimeEpic = startTime.plus(subTask.duration);
                } else {
                    endTimeEpic = endTimeEpic.plus(subTask.duration);
                }

            }

        }
    }

    public void setDurationEpic(SubTask subTask) {
        if (!(subTaskList.isEmpty())) {
            if (subTask.getParrentId() == id) {
                if (duration == null) {
                    duration = subTask.duration;
                } else {
                    duration = duration.plus(subTask.duration);
                }

            }
        }
    }

    public void setDurationEpicAfterRemovingSubTask(Duration newDuration) {
        super.duration = newDuration;
    }

    public LocalTime getEndTimeEpic() {
        return endTimeEpic;
    }

}
