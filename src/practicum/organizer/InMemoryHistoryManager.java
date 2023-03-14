package practicum.organizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{
    private NodeOfHistoryList head;
    private NodeOfHistoryList tail;
    private int size = 0;

    private HashMap<Integer, NodeOfHistoryList> historyHashMap = new HashMap<>();


    @Override
    public void addTask(Task task) {
        if (historyHashMap.containsKey(task.getId())) {
            removeNode(historyHashMap.get(task.getId()));
        }
        addLast(task);
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    @Override
    public void remove(int id) {
        removeNode(historyHashMap.get(id));
    }

    private void addLast(Task task) {
        if (size == 0) {
            head = new NodeOfHistoryList(task);
            size++;
            historyHashMap.put(task.getId(), head);
        } else if (size == 1){
            tail = new NodeOfHistoryList(task);
            head.setNext(tail);
            tail.setPrev(head);
            size++;
            historyHashMap.put(task.getId(), tail);
        } else {
            NodeOfHistoryList oldNode = tail;
            NodeOfHistoryList newNode = new NodeOfHistoryList(task);
            oldNode.setNext(newNode);
            newNode.setPrev(oldNode);
            tail = newNode;
            historyHashMap.put(task.getId(), tail);
            size++;
        }
    }

    private ArrayList<Task> getTasks () {
        ArrayList<Task> currentHistory = new ArrayList<>();
        NodeOfHistoryList node = head;
        if (size == 0) {
            System.out.println("Нет просмотренных заданий");
        } else {
            for (int i = 0; i < size; i++) {
                currentHistory.add(node.getNode());
                node = node.getNext();
            }
        }
        return currentHistory;
    }

    @Override
    public void updateTask (Task task){
        if (historyHashMap.containsKey(task.getId())){
            historyHashMap.get(task.getId()).setNode(task);
        }

    }

    private void removeNode (NodeOfHistoryList node) {
        if (node != null) {
            if (size == 1) {
                head = null;
            } else if (size == 2) {
                tail = null;
                head.setNext(null);
            } else {
                NodeOfHistoryList prevNode = node.getPrev();
                NodeOfHistoryList nextNode = node.getNext();
                if (prevNode == null) {
                    nextNode.setPrev(null);
                    head = nextNode;
                } else if (nextNode == null) {
                    prevNode.setNext(null);
                    tail = prevNode;
                } else {
                    prevNode.setNext(nextNode);
                    nextNode.setPrev(prevNode);
                }

            }
            historyHashMap.remove(node.getNode().getId());
            size--;
        }
    }

    @Override
    public String historyToString() {

        StringBuilder builder = new StringBuilder();
        for (Task task : getHistory()){
            builder.append(task.getId()).append(" ");
        }
        return builder.toString().trim();

    }
}
