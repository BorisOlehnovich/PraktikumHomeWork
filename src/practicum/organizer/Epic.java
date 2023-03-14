package practicum.organizer;

import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<SubTask> descriptionEpic;

    public Epic(int id, String title, ArrayList<SubTask> descriptionEpic) {
        super(id, title);
        this.descriptionEpic = descriptionEpic;

        updateEpicStatus();

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
                " Status= " + getStatus());
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
        String str = TypeOfTask.EPIC + "," + getId() + "," + getTitle() + "," + getStatus()  + descriptionToString();
        return str;
    }


}










