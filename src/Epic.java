import java.util.ArrayList;

public class Epic extends Task {
    ArrayList<Integer> subTaskList;

    Epic(Task task) {
        super(task.name, task.id, task.discription);
        this.status = StatusTypes.NEW;
        this.subTaskList = new ArrayList<Integer>();
    }

    @Override
    public String toString() {
        return "Эпик:'" + name +
                "', id:'" + id +
                "', С описанием:'" + discription +
                "', C текущим статусом:'" + status + "'";

    }
}
