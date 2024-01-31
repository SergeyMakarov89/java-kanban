public class Task {
    String name;
    String discription;
    int id;
    StatusTypes status;

    public Task(String name, String discription, int id) {
        this.name = name;
        this.discription = discription;
        this.id = id;
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
