package practicum.organizer;

import java.util.List;

public interface HistoryManager {
    void addTask (Task task);
    void remove(int id);
    List<Task> getHistory();
    void updateTask (Task task);

}
