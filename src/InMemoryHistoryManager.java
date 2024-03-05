import org.w3c.dom.Node;

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
        Node<Task> next = node.next;
        Node<Task> prev = node.prev;
        node.data = null;
        if (head == node && tail == node) {
            head = null;
            tail = null;
        } else if (head == node) {
            head = next;
            head.prev = null;
        } else if (tail == node) {
            tail = prev;
            tail.next = null;
        } else {
            next.prev = prev;
            prev.next = next;
        }
    }

    @Override
    public void add(Task task) {
        if (!taskMapHistory.isEmpty()) {
            remove(task.id);
        }
        linkLast(task);
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    public List<Task> getTasks() {
        List<Task> taskList = new ArrayList<>();
        Node<Task> node = head;
        while (!(node == null)) {
            taskList.add(node.getData());
            node = node.next;
        }
        return taskList;
    }

    public class Node<T> {
        public T data;
        public Node<T> next;
        public Node<T> prev;

        public  Node(Node<T> prev, T data, Node<T> next) {
            this.prev = prev;
            this.data = data;
            this.next = next;

        }

        public T getData() {
            return data;
        }

        public void setPrev(Node<T> prev) {
            this.prev = prev;
        }
    }

}
