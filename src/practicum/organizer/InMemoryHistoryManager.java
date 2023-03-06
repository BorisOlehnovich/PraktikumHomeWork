package practicum.organizer;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager{

    private ArrayList<Task> taskViewHistory = new ArrayList<>();

    @Override
    public void addTask(Task task) {
        if (taskViewHistory.size() >= 10) {
            taskViewHistory.remove(0);
        }
        taskViewHistory.add(task);
    }

    @Override
    public ArrayList<Task> getHistory() {
        return taskViewHistory;
    }
}
