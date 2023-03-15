package practicum.organizer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    TaskManager manager;
    HistoryManager historyManager;

    @BeforeEach
    public void beforeEach(){
        manager = Managers.getDefault();
        historyManager = Managers.getDefaultHistory();
    }

    @Test
    void addNewTask (){

        ArrayList<String> description = new ArrayList<>();
        description.add("Описание задачи");
        Task task = new Task(manager.generateId(), "Задача", description, Status.NEW);
        manager.addNewTask(TypeOfTask.TASK, task);

        Task savedTask = manager.getTaskFromId(1);

        assertNotNull(savedTask, "задача не найдена");
        assertEquals(task, savedTask, "задачи не совпадают");

        List<Task> tasks = manager.getAllTasks();

        assertNotNull(tasks, "задачи не возвращаются");
        assertEquals(1, tasks.size(), "неверное количество задач");
        assertEquals(task, tasks.get(0), "задачи не совпадают");
    }

    @Test
    void EpicMustBeAddedWithCorrectStatus (){
        String firstEpicTitle = "Отремонтировать машину";
        int firstEpicId = manager.generateId();
        ArrayList<SubTask> firstEpicSubTask = new ArrayList<>();


        String firstEpicSubTaskTitle1 = "Купить запчасти";
        ArrayList<String> firstEpicSubtaskDescription1 = new ArrayList<>();
        firstEpicSubtaskDescription1.add("Масло");
        firstEpicSubtaskDescription1.add("Масляный фильтр");
        firstEpicSubtaskDescription1.add("Воздушный фильтр");
        SubTask subTask1ForFirstEpic = new SubTask(manager.generateId(), firstEpicSubTaskTitle1,
                firstEpicSubtaskDescription1, Status.NEW, firstEpicId );
        firstEpicSubTask.add(subTask1ForFirstEpic);

        String firstEpicSubTaskTitle2 = "Выбрать автосервис";
        ArrayList<String> firstEpicSubtaskDescription2 = new ArrayList<>();
        firstEpicSubtaskDescription2.add("Согласовать время");
        firstEpicSubtaskDescription2.add("Оплатить ремонт");
        SubTask subTask2ForFirstEpic = new SubTask(manager.generateId(), firstEpicSubTaskTitle2,
                firstEpicSubtaskDescription2,Status.NEW, firstEpicId);
        firstEpicSubTask.add(subTask2ForFirstEpic);

        Epic epic = new Epic(firstEpicId, firstEpicTitle, firstEpicSubTask);
        manager.addNewTask(TypeOfTask.EPIC, epic);

        Epic savedEpic = (Epic) manager.getTaskFromId(1);

        assertNotNull(savedEpic, "Эпик не найден");
        assertEquals(epic, savedEpic, "Эпики не свопадают");
        assertEquals(Status.NEW, savedEpic.getStatus(), "Статус не совпадает New");

        subTask1ForFirstEpic.setStatus(Status.IN_PROGRESS);
        manager.updateTask(subTask1ForFirstEpic, TypeOfTask.SUB_TASK);

        assertEquals(Status.IN_PROGRESS, manager.getTaskFromId(1).getStatus(), "Статус не совпадает INProgress");

        subTask2ForFirstEpic.setStatus(Status.IN_PROGRESS);
        manager.updateTask(subTask2ForFirstEpic, TypeOfTask.SUB_TASK);

        assertEquals(Status.IN_PROGRESS, manager.getTaskFromId(1).getStatus(), "Статус не совпадает INProgress");

        subTask1ForFirstEpic.setStatus(Status.DONE);
        manager.updateTask(subTask1ForFirstEpic, TypeOfTask.SUB_TASK);

        assertEquals(Status.IN_PROGRESS, manager.getTaskFromId(1).getStatus(), "Статус не совпадает INProgress");

        subTask1ForFirstEpic.setStatus(Status.NEW);
        manager.updateTask(subTask1ForFirstEpic, TypeOfTask.SUB_TASK);
        subTask2ForFirstEpic.setStatus(Status.DONE);
        manager.updateTask(subTask2ForFirstEpic, TypeOfTask.SUB_TASK);

        assertEquals(Status.IN_PROGRESS, manager.getTaskFromId(1).getStatus(), "Статус не совпадает INProgress");

        subTask1ForFirstEpic.setStatus(Status.DONE);
        manager.updateTask(subTask1ForFirstEpic, TypeOfTask.SUB_TASK);

        assertEquals(Status.DONE, manager.getTaskFromId(1).getStatus(), "Статус не совпадает Done");

    }

    @Test
    void addEpicWithEmptyDescription(){
        String firstEpicTitle = "Отремонтировать машину";
        int firstEpicId = manager.generateId();
        ArrayList<SubTask> firstEpicSubTask = new ArrayList<>();
        Epic epic = new Epic(firstEpicId, firstEpicTitle, firstEpicSubTask);
        manager.addNewTask(TypeOfTask.EPIC, epic);

        Epic savedEpic = (Epic)manager.getTaskFromId(1);

        assertNotNull(savedEpic, "Задача не найдена");
        assertEquals(epic, savedEpic, "Задачи не совпадают");
        assertTrue(savedEpic.getDescriptionEpic().isEmpty(), "Описание не пустое");
        assertEquals(Status.NEW, savedEpic.getStatus(), "Статус задачи указан не верно");
    }

    @Test
    void taskDeletedFromId () {
        String firstEpicTitle = "Отремонтировать машину";
        int firstEpicId = manager.generateId();
        ArrayList<SubTask> firstEpicSubTask = new ArrayList<>();


        String firstEpicSubTaskTitle1 = "Купить запчасти";
        ArrayList<String> firstEpicSubtaskDescription1 = new ArrayList<>();
        firstEpicSubtaskDescription1.add("Масло");
        firstEpicSubtaskDescription1.add("Масляный фильтр");
        firstEpicSubtaskDescription1.add("Воздушный фильтр");
        SubTask subTask1ForFirstEpic = new SubTask(manager.generateId(), firstEpicSubTaskTitle1,
                firstEpicSubtaskDescription1, Status.NEW, firstEpicId );
        firstEpicSubTask.add(subTask1ForFirstEpic);

        String firstEpicSubTaskTitle2 = "Выбрать автосервис";
        ArrayList<String> firstEpicSubtaskDescription2 = new ArrayList<>();
        firstEpicSubtaskDescription2.add("Согласовать время");
        firstEpicSubtaskDescription2.add("Оплатить ремонт");
        SubTask subTask2ForFirstEpic = new SubTask(manager.generateId(), firstEpicSubTaskTitle2,
                firstEpicSubtaskDescription2,Status.NEW, firstEpicId);
        firstEpicSubTask.add(subTask2ForFirstEpic);

        Epic epic = new Epic(firstEpicId, firstEpicTitle, firstEpicSubTask);
        manager.addNewTask(TypeOfTask.EPIC, epic);

        ArrayList<String> description = new ArrayList<>();
        description.add("Описание задачи");
        Task task = new Task(manager.generateId(), "Задача", description, Status.NEW);
        manager.addNewTask(TypeOfTask.TASK, task);

        manager.deleteTaskFromId(4);
        assertEquals(3, manager.getAllTasks().size(),
                "Количество задач после удаления не совпадает с ожидаемым");
        assertNull(manager.getTaskFromId(4), "Задача была найдена после удаления");

        manager.deleteTaskFromId(2);
        assertEquals(2, manager.getAllTasks().size(),
                "Количество задач после удаления не совпадает с ожидаемым");
        assertNull(manager.getTaskFromId(2), "Задача была найдена после удаления");

        Epic epicAfterDeleteSubtask = (Epic) manager.getTaskFromId(1);
        assertEquals(1, epicAfterDeleteSubtask.getDescriptionEpic().size(),
                "размер подзадач эпика не совпадает с ожидаемы");

        manager.deleteTaskFromId(1);
        assertTrue(manager.getAllTasks().isEmpty());

    }



    @Test
    void taskUpdatedFromId(){
        String firstEpicTitle = "Отремонтировать машину";
        int firstEpicId = manager.generateId();
        ArrayList<SubTask> firstEpicSubTask = new ArrayList<>();


        String firstEpicSubTaskTitle1 = "Купить запчасти";
        ArrayList<String> firstEpicSubtaskDescription1 = new ArrayList<>();
        firstEpicSubtaskDescription1.add("Масло");
        firstEpicSubtaskDescription1.add("Масляный фильтр");
        firstEpicSubtaskDescription1.add("Воздушный фильтр");
        SubTask subTask1ForFirstEpic = new SubTask(manager.generateId(), firstEpicSubTaskTitle1,
                firstEpicSubtaskDescription1, Status.NEW, firstEpicId );
        firstEpicSubTask.add(subTask1ForFirstEpic);

        String firstEpicSubTaskTitle2 = "Выбрать автосервис";
        ArrayList<String> firstEpicSubtaskDescription2 = new ArrayList<>();
        firstEpicSubtaskDescription2.add("Согласовать время");
        firstEpicSubtaskDescription2.add("Оплатить ремонт");
        SubTask subTask2ForFirstEpic = new SubTask(manager.generateId(), firstEpicSubTaskTitle2,
                firstEpicSubtaskDescription2,Status.NEW, firstEpicId);
        firstEpicSubTask.add(subTask2ForFirstEpic);

        Epic epic = new Epic(firstEpicId, firstEpicTitle, firstEpicSubTask);
        manager.addNewTask(TypeOfTask.EPIC, epic);

        ArrayList<String> description = new ArrayList<>();
        description.add("Описание задачи");
        Task task = new Task(manager.generateId(), "Задача", description, Status.NEW);
        manager.addNewTask(TypeOfTask.TASK, task);

        task.setTitle("Изменили задачу");
        manager.updateTask(task, TypeOfTask.TASK);
        assertEquals(task, manager.getTaskFromId(4));

        subTask1ForFirstEpic.setTitle("Изменили подзадачу");
        manager.updateTask(subTask1ForFirstEpic, TypeOfTask.SUB_TASK);
        assertEquals(subTask1ForFirstEpic, manager.getTaskFromId(2));
        Epic epicAfterUpdateSubTask = (Epic)manager.getTaskFromId(1);
        assertEquals(subTask1ForFirstEpic, epicAfterUpdateSubTask.getDescriptionEpic().get(0));

        subTask2ForFirstEpic.setTitle("Изменили подзадачу для обновления Эпика");
        firstEpicSubTask.add(1,subTask2ForFirstEpic);
        Epic epicBeforeUpdate = new Epic(1, "Обновили Эпик", firstEpicSubTask);
        manager.updateTask(epicBeforeUpdate, TypeOfTask.EPIC);
        Epic epicAfterUpdate = (Epic) manager.getTaskFromId(1);
        assertEquals(epicBeforeUpdate, epicAfterUpdate, "Эпики не совпали");
        SubTask subTaskFromEpicDescription = epicAfterUpdate.getDescriptionEpic().get(1);
        assertEquals(subTask2ForFirstEpic, subTaskFromEpicDescription, "подзадачи внутри эпика не совпали");
        assertEquals(subTask2ForFirstEpic, manager.getTaskFromId(3),
                "подзадача не обновилась после обновления эпика");


    }

    @Test
    void addUpdateAndRemoveTaskToHistory(){
        ArrayList<String> description = new ArrayList<>();
        description.add("Описание задачи");
        Task task = new Task(manager.generateId(), "Задача", description, Status.NEW);
        historyManager.addTask(task);

        List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История пустая");
        assertEquals(1, history.size(), "История пустая");

        task.setTitle("Изменили задачу");
        historyManager.updateTask(task);
        history = historyManager.getHistory();
        assertEquals(task, history.get(0), "Задачи не совпали");

        historyManager.remove(1);
        history = historyManager.getHistory();
        assertTrue(history.isEmpty(), "История не пуста");

    }

    @Test
    void SaveAndLoadFromFile() {
        String firstEpicTitle = "Отремонтировать машину";
        int firstEpicId = manager.generateId();
        ArrayList<SubTask> firstEpicSubTask = new ArrayList<>();


        String firstEpicSubTaskTitle1 = "Купить запчасти";
        ArrayList<String> firstEpicSubtaskDescription1 = new ArrayList<>();
        firstEpicSubtaskDescription1.add("Масло");
        firstEpicSubtaskDescription1.add("Масляный фильтр");
        firstEpicSubtaskDescription1.add("Воздушный фильтр");
        SubTask subTask1ForFirstEpic = new SubTask(manager.generateId(), firstEpicSubTaskTitle1,
                firstEpicSubtaskDescription1, Status.NEW, firstEpicId);
        subTask1ForFirstEpic.setStartTime(LocalDateTime.now());
        subTask1ForFirstEpic.setDuration(Duration.ofMinutes(5));
        firstEpicSubTask.add(subTask1ForFirstEpic);

        String firstEpicSubTaskTitle2 = "Выбрать автосервис";
        ArrayList<String> firstEpicSubtaskDescription2 = new ArrayList<>();
        firstEpicSubtaskDescription2.add("Согласовать время");
        firstEpicSubtaskDescription2.add("Оплатить ремонт");
        SubTask subTask2ForFirstEpic = new SubTask(manager.generateId(), firstEpicSubTaskTitle2,
                firstEpicSubtaskDescription2, Status.NEW, firstEpicId);
        subTask2ForFirstEpic.setStartTime(LocalDateTime.now().plus(Duration.ofHours(1)));
        subTask2ForFirstEpic.setDuration(Duration.ofMinutes(15));
        firstEpicSubTask.add(subTask2ForFirstEpic);

        Epic epic = new Epic(firstEpicId, firstEpicTitle, firstEpicSubTask);
        manager.addNewTask(TypeOfTask.EPIC, epic);

        ArrayList<String> description = new ArrayList<>();
        description.add("Описание задачи");
        Task task = new Task(manager.generateId(), "Задача", description, Status.NEW);
        task.setStartTime(LocalDateTime.now().plus(Duration.ofHours(3)));
        task.setDuration(Duration.ofMinutes(15));
        manager.addNewTask(TypeOfTask.TASK, task);

        manager.getTaskFromId(1);
        manager.getTaskFromId(4);
        manager.getTaskFromId(3);

        List<Task> savedTask = manager.getAllTasks();

        TaskManager manager1 = Managers.getDefault();
        ((FileBackedTaskManager) manager1).load();

        List<Task> loadedTasks = manager1.getAllTasks();

        System.out.println();

        assertEquals(savedTask.size(), loadedTasks.size(), "Размер массива задач не совпадает");


    }
}




