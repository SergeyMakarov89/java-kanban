import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    protected Map<Integer, Node<Task>> taskMapHistory = new HashMap<>();
    protected Node<Task> head = null;
    protected Node<Task> tail = null;

    public void linkLast(Task task) {
        if (head == null && tail == null) {
            Node<Task> newNode = new Node<>(head, task, tail);
            taskMapHistory.put(task.getId(), newNode);
            head = newNode;
            tail = newNode;
        } else {
            Node<Task> oldHead = head;
            Node<Task> newNode = new Node<>(null, task, head);
            taskMapHistory.get(oldHead.getData().getId()).setPrev(newNode);
            head = newNode;
            taskMapHistory.put(task.getId(), newNode);
        }
    }

    public void remove(int id) {
        if (taskMapHistory.get(id) != null) {
            removeNode(taskMapHistory.get(id));
        }
    }

    public void removeNode(Node<Task> node) {
        Node<Task> next = node.getNext();
        Node<Task> prev = node.getPrev();
        if (head == node && tail == node) {
            head = null;
            tail = null;
        } else if (head == node) {
            head = next;
            head.setPrev(null);
        } else if (tail == node) {
            tail = prev;
            tail.setNext(null);
        } else {
            next.setPrev(prev);
            prev.setNext(next);
        }
        taskMapHistory.remove(node.getData().getId());
        node.setData(null);
    }

    @Override
    public void add(Task task) {
        if ((!taskMapHistory.isEmpty())) {
            remove(task.id);
        }
        linkLast(task);
    }

    public List<Task> getHistory() {
        return getTasks();
    }

    public List<Task> getTasks() {
        List<Task> taskList = new ArrayList<>();
        Node<Task> node = head;
        while (node != null) {
            taskList.add(node.getData());
            node = node.getNext();
        }
        return taskList;
    }
}
