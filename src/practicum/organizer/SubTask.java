package practicum.organizer;

import java.util.ArrayList;

public class SubTask extends Task {

    private int epicId;

    public SubTask(int id, String title, ArrayList<String> description, Status status, int epicId) {
        super(id, title, description, status);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                " ID= " + getId() +
                " epicId= " + epicId +
                " title= " + getTitle() +
                " Description= " + getDescription() +
                " Status= " + getStatus() +
                '}';
    }

    @Override
    public String TaskToString() {
        String str = TypeOfTask.SUB_TASK + "," + getId() + "," + getEpicId()  + ","
                + getTitle() + "," + getStatus() + descriptionToString();
        return TypeOfTask.SUB_TASK + "," + getId() + "," + getEpicId()  + ","
                + getTitle() + "," + getStatus() + descriptionToString();
    }


}
