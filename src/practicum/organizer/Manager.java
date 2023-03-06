package practicum.organizer;

import java.util.ArrayList;
import java.util.HashMap;

public interface Manager {




    //генератор уникального ID
     int generateId ();

    //Добавление новой задачи
     void addNewTask(TypeOfTask type, Object obj);

    //получение списка всех задач в один список
     ArrayList<Task> getAllTasks ();

    //Удаление всех задач
     void deleteAllTasks();

    //Получение задачи по ID
     Task getTaskFromId (int id);

    //Обновление Задачи по ID
     void updateTask (Task task, TypeOfTask type);

    //Удаление задачи по идентификатору
     void deleteTaskFromId (int id);

    // получение списка всех подзадач для определенного Эпика
    ArrayList<SubTask> getSubTasksFromEpicId (int id);

    // История просмотра - последние 10 задач просмотренные пользователем

    ArrayList<Task> history();

}
