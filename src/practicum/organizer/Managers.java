package practicum.organizer;

public class Managers {
    public static Manager getDefault () {
        return new InMemoryTaskManager();
    }
}
