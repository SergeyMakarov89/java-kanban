public class Node<T> {
    private Task data;
    private Node<T> next;
    private Node<T> prev;

    public Node(Node<T> prev, Task data, Node<T> next) {
        this.prev = prev;
        this.data = data;
        this.next = next;

    }

    public Task getData() {
        return data;
    }

    public void setData(Task data) {
        this.data = data;
    }

    public void setPrev(Node<T> prev) {
        this.prev = prev;
    }

    public Node<T> getPrev() {
        return prev;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }
}