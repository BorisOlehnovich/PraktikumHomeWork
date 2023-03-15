package practicum.organizer;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<SubTask> descriptionEpic;

    public Epic(int id, String title, ArrayList<SubTask> descriptionEpic) {
        super(id, title);
        this.descriptionEpic = descriptionEpic;

        updateEpicStatus();
        setStartTimeAndDuration();

    }

    public ArrayList<SubTask> getDescriptionEpic() {
        return descriptionEpic;
    }

    public void setDescriptionEpic(ArrayList<SubTask> descriptionEpic) {
        this.descriptionEpic = descriptionEpic;
        updateEpicStatus();
    }

    public void updateEpicStatus() {
        boolean isNew = true;
        boolean isDone = true;

        if (!descriptionEpic.isEmpty()) {
            for (SubTask subTask : descriptionEpic) {
                if (!subTask.getStatus().equals(Status.NEW)) {
                    isNew = false;
                }
                if (!subTask.getStatus().equals(Status.DONE)) {
                    isDone = false;
                }
            }

            if (isNew) {
                this.setStatus(Status.NEW);
            } else if (isDone) {
                this.setStatus(Status.DONE);
            } else {
                this.setStatus(Status.IN_PROGRESS);
            }
        } else {
            this.setStatus(Status.NEW);
        }
    }

    @Override
    public String toString() {
        StringBuilder toStr = new StringBuilder("Epic{ " +
                " ID= " + getId() +
                " Title= " + getTitle() +
                " Status= " + getStatus() +
                " Start time= " + getStartTime() +
                " Duration= " + getDuration());
        for (SubTask subTask : descriptionEpic) {
            toStr.append("\n\t").append(subTask.toString());
        }
        return toStr.toString();
    }

    public String descriptionToString() {
        StringBuilder builder = new StringBuilder();
        for (SubTask subTask : descriptionEpic) {
            builder.append(",").append(subTask.TaskToString());
        }
        return builder.toString();
    }

    @Override
    public String TaskToString() {
        String str = TypeOfTask.EPIC + "," + getId() + "," + getTitle() +
                "," + getStatus() + "," + getStartTime() + "," + getDuration()  + descriptionToString();
        return str;
    }

    private void setStartTimeAndDuration() {
        if (getDescriptionEpic().isEmpty()){
            System.out.println("Список подзадач пуст, расчитать время начала выполнения Эпика не получится");
            return;
        }
        LocalDateTime first = getDescriptionEpic().get(0).getStartTime();
        LocalDateTime last = getDescriptionEpic().get(0).getStartTime();
        Duration duration = getDescriptionEpic().get(0).getDuration();

        for (SubTask subTask : getDescriptionEpic()){
            if (first.isAfter(subTask.getStartTime())){
                first = subTask.getStartTime();
            }
            if (last.isBefore(subTask.getStartTime())){
                last = subTask.getStartTime();
                duration = subTask.getDuration();
            }
        }
        this.setStartTime(first);
        this.setDuration(Duration.between(first, last.plus(duration)));
    }


}










