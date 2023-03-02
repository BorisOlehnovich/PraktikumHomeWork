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

        for (SubTask subTask : descriptionEpic) {
            if (!subTask.getStatus().equals(Status.NEW)){
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
}
