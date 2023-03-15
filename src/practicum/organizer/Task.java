package practicum.organizer;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class Task implements Comparable<Task> {
    private int id;
    private String title;
    private ArrayList<String> description;
    private Status status;

    private LocalDateTime startTime;

    private Duration duration;

    public Task(int id, String title, ArrayList<String> description, Status status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public Task(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getDescription() {
        return description;
    }

    public void setDescription(ArrayList<String> description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(title, task.title) && Objects.equals(description, task.description) && status == task.status && Objects.equals(startTime, task.startTime) && Objects.equals(duration, task.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, status, startTime, duration);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description=" + description +
                ", status=" + status +
                ", startTime=" + startTime +
                ", duration=" + duration +
                '}';
    }

    public String descriptionToString () {
        StringBuilder builder = new StringBuilder();
        for (String str : description) {
            builder.append(",").append(str);
        }
        return builder.toString();
    }

    public String TaskToString() {
        return TypeOfTask.TASK + "," + id + "," + title + "," +
                status + "," + startTime + "," + duration + descriptionToString();
    }

    @Override
    public int compareTo(Task o) {
        if (getStartTime().isEqual(o.getStartTime()))
            return 0;
        return getStartTime().isBefore(o.getStartTime())? -1 : 1;
    }
}
