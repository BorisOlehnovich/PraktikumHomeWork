package practicum.organizer;

import java.util.ArrayList;

public interface HistoryManager {
    void addTask (Task task);
    ArrayList<Task> getHistory();
}