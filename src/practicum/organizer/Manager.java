package practicum.organizer;

import java.util.ArrayList;
import java.util.HashMap;

public class Manager {

    private HashMap <Integer, Task> tasks = new HashMap<>();
    private HashMap <Integer, Epic> epics = new HashMap<>();
    private HashMap <Integer, SubTask> subTasks = new HashMap<>();

    private int id = 0;

    //генератор уникального ID
    public int generateId (){
        return ++id;
    }

    public void addNewTask(TypeOfTask type, Object obj) {
        switch (type) {
            case TASK:
                Task task = (Task) obj;
                tasks.put(task.getId(), task);
                break;
            case EPIC:
                Epic epic = (Epic) obj;
                epics.put(epic.getId(),epic);
                break;
            case SUB_TASK:
                SubTask subTask = (SubTask) obj;
                subTasks.put(subTask.getId(), subTask);
                break;
            default:
                System.out.println("Неправильно указан тип задачи");
        }
    }


    //получение списка всех задач в один список
    public ArrayList<Task> getAllTasks () {
        ArrayList<Task> allTasks = new ArrayList<>();
        allTasks.addAll(tasks.values());
        allTasks.addAll(epics.values());
        allTasks.addAll(subTasks.values());
        return allTasks;
    }

    //Удаление всех задач
    public void deleteAllTasks(){
        tasks.clear();
        epics.clear();
        subTasks.clear();
        id = 0;
    }

    //Получение задачи по ID
    public Task getTaskFromId (int id) {
        if (tasks.containsKey(id)) {
            return tasks.get(id);
        } else if (epics.containsKey(id)) {
            return epics.get(id);
        } else if (subTasks.containsKey(id)) {
            return subTasks.get(id);
        } else {
            System.out.println("задачи с таким Id не существует");
            return null;
        }
    }

    //Обновление Задачи по ID
    public void updateTask (Task task, TypeOfTask type) {
        switch (type) {
            case TASK:
                tasks.put(task.getId(), task);
                break;

            case EPIC:
                epics.put(task.getId(), (Epic) task);
                break;

            case SUB_TASK:
                subTasks.put(task.getId(), (SubTask) task);

                // Если обновлен SubTask нужно обновить Эпик которому он соответствует.
                int epicId = ((SubTask) task).getEpicId();
                ArrayList<SubTask> newSubTasksListForEpic = new ArrayList<>();

                // Ищем все подзадачи которые соответствуют Эпику с конкретным ID
                for (SubTask subTask : subTasks.values()) {
                    if (subTask.getEpicId() == epicId) {
                        newSubTasksListForEpic.add(subTask);
                    }
                }
                Epic oldEpic = (Epic) getTaskFromId(epicId);
                Epic newEpic =  new Epic(epicId, oldEpic.getTitle(), newSubTasksListForEpic);
                updateTask(newEpic, TypeOfTask.EPIC);
                break;

            default:
                System.out.println("Не правильно указан тип задачи");
        }
    }

    //Удаление задачи по идентификатору
    public void deleteTaskFromId (int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
        } else if (epics.containsKey(id)) {
            // Удаляем Эпик следовательно удаляем все подзадачи которые в него входят
            ArrayList<SubTask> subTasksToRemove = new ArrayList<>();
            subTasksToRemove = epics.get(id).getDescriptionEpic();
            for (SubTask subTask : subTasksToRemove) {
                subTasks.remove(subTask.getId());
            }
            epics.remove(id);
        } else if (subTasks.containsKey(id)) {
            //Удаляем подзадачу из общего списка и из эпика
            int epicId = subTasks.get(id).getEpicId();
            ArrayList<SubTask> newSubtask = new ArrayList<>();
            subTasks.remove(id);
            for (SubTask subTask : subTasks.values()) {
                if (subTask.getEpicId() == epicId) {
                    newSubtask.add(subTask);
                }
            }
            String title = epics.get(epicId).getTitle();
            Epic newEpic = new Epic(epicId, title, newSubtask);
        } else {
            System.out.println("Задачи с таким идентификатором не существует");
        }
    }

    // получение списка всех подзадач для определенного Эпика
    public ArrayList<SubTask> getSubTasksFromEpicId (int id) {
        return epics.get(id).getDescriptionEpic();
    }

}
