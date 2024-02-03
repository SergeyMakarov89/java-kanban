import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subTaskList;

    Epic(String name, String discription) {
        super.name = name;
        super.description = discription;
        this.status = StatusTypes.NEW;
        this.subTaskList = new ArrayList<Integer>();
    }

    public Epic(String name, String discription, int id) {
        this.name = name;
        this.description = discription;
        this.id = id;
        this.subTaskList = new ArrayList<Integer>();
    }

    @Override
    public String toString() {
        return "Эпик:'" + name +
                "', id:'" + id +
                "', С описанием:'" + description +
                "', C текущим статусом:'" + status + "'";

    }

    public ArrayList<Integer> getSubTaskList() {
        return subTaskList;
    }

    public void setSubTaskList(ArrayList<Integer> subTaskList) {
        this.subTaskList = subTaskList;
    }
}
