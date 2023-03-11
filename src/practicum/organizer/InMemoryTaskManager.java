package practicum.organizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager  implements TaskManager {
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap <Integer, Epic> epics = new HashMap<>();
    private HashMap <Integer, SubTask> subTasks = new HashMap<>();
    private HistoryManager historyManager = Managers.getDefaultHistory();

    private int id = 0;

    //генератор уникального ID
    @Override
    public int generateId (){
        return ++id;
    }
    @Override
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
    @Override
    public ArrayList<Task> getAllTasks () {
        ArrayList<Task> allTasks = new ArrayList<>();
        allTasks.addAll(tasks.values());
        allTasks.addAll(epics.values());
        allTasks.addAll(subTasks.values());
        return allTasks;
    }

    //Удаление всех задач
    @Override
    public void deleteAllTasks(){
        tasks.clear();
        epics.clear();
        subTasks.clear();
        id = 0;
    }

    //Получение задачи по ID
    @Override
    public Task getTaskFromId (int id) {
        if (tasks.containsKey(id)) {
            Task task = tasks.get(id);
            historyManager.addTask(task);
            return task;
        } else if (epics.containsKey(id)) {
            Epic epic = epics.get(id);
            historyManager.addTask(epic);
            return epic;
        } else if (subTasks.containsKey(id)) {
            SubTask subTask = subTasks.get(id);
            historyManager.addTask(subTask);
            return subTask;
        } else {
            System.out.println("задачи с таким Id не существует");
            return null;
        }
    }

    //Обновление Задачи по ID
    @Override
    public void updateTask (Task task, TypeOfTask type) {
        switch (type) {
            case TASK:
                tasks.put(task.getId(), task);
                historyManager.updateTask(task);
                break;

            case EPIC:
                epics.put(task.getId(), (Epic) task);
                historyManager.updateTask(task);
                break;

            case SUB_TASK:
                subTasks.put(task.getId(), (SubTask) task);
                historyManager.updateTask(task);

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
                historyManager.updateTask(newEpic);
                break;

            default:
                System.out.println("Не правильно указан тип задачи");
        }
    }

    //Удаление задачи по идентификатору
    @Override
    public void deleteTaskFromId (int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
            historyManager.remove(id);
        } else if (epics.containsKey(id)) {
            // Удаляем Эпик следовательно удаляем все подзадачи которые в него входят
            ArrayList<SubTask> subTasksToRemove = new ArrayList<>();
            subTasksToRemove = epics.get(id).getDescriptionEpic();
            for (SubTask subTask : subTasksToRemove) {
                subTasks.remove(subTask.getId());
                historyManager.remove(subTask.getId());
            }
            epics.remove(id);
            historyManager.remove(id);
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
            epics.put(epicId, newEpic);
            historyManager.remove(id);
            historyManager.updateTask(newEpic);
        } else {
            System.out.println("Задачи с таким идентификатором не существует");
        }
    }

    // получение списка всех подзадач для определенного Эпика
    @Override
    public ArrayList<SubTask> getSubTasksFromEpicId (int id) {
        return epics.get(id).getDescriptionEpic();
    }

    @Override
    public List<Task> history() {
        return historyManager.getHistory();
    }

    public HistoryManager getHistoryManager() {
        return historyManager;
    }
}
