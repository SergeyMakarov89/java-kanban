import java.util.ArrayList;

public class Epic {
    String name;
    int id;
    StatusTypes status;
    ArrayList<SubTask> subTasks;

    public Epic(String name, int id) {
        this.name = name;
        this.id = id;
        this.status = StatusTypes.NEW;
        this.subTasks = new ArrayList<SubTask>();
    }

    @Override
    public String toString() {
        return "Эпик:'" + name +
                "', id:'" + id +
                "', C текущим статусом:'" + status + "'";

    }
}
