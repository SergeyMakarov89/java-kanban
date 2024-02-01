public class Task {
    String  name;
    int id;
    String discription;
    StatusTypes status;

    public Task(String name, int id, String discription) {
        this.name = name;
        this.id = id;
        this.discription = discription;
        this.status = StatusTypes.NEW;
    }

    @Override
    public String toString() {
        return "Задача:'" + name +
                "', id:'" + id +
                "', С описанием:'" + discription +
                "', C текущим статусом:'" + status + "'";
    }

}
