package practicum.organizer;

public class NodeOfHistoryList {
    private Task node;
    private NodeOfHistoryList next;
    private   NodeOfHistoryList prev;

    public NodeOfHistoryList(Task task) {
        node = task;
    }

    public Task getNode() {
        return node;
    }

    public void setNode(Task node) {
        this.node = node;
    }

    public NodeOfHistoryList getNext() {
        return next;
    }

    public void setNext(NodeOfHistoryList next) {
        this.next = next;
    }

    public NodeOfHistoryList getPrev() {
        return prev;
    }

    public void setPrev(NodeOfHistoryList prev) {
        this.prev = prev;
    }
}
