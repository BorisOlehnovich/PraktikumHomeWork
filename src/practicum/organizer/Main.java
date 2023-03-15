package practicum.organizer;

import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();


     /*     ArrayList<String> description1 = new ArrayList<>();
        description1.add("Разморозить курицу");
        description1.add("Почистить картошку");
        description1.add("Запечь в духовке 40 мин 200 градусов");
        Task task1 = new Task(manager.generateId(), "Приготовить ужин",
                description1 , Status.NEW);
        manager.addNewTask(TypeOfTask.TASK, task1 );


        ArrayList<String> description2 = new ArrayList<>();
        description2.add("Сравнить цены на озон и детском мире");
        description2.add("Скинуть ссылку жене");
        Task task2 = new Task(manager.generateId(), "Купить подгузники",
                description2 , Status.NEW);
        manager.addNewTask(TypeOfTask.TASK, task2 );


        String firstEpicTitle = "Отремонтировать машину";
        int firstEpicId = manager.generateId();
        ArrayList<SubTask> firstEpicSubTask = new ArrayList<>();


        String firstEpicSubTaskTitle1 = "Купить запчасти";
        ArrayList<String> firstEpicSubtaskDescription1 = new ArrayList<>();
        firstEpicSubtaskDescription1.add("Масло");
        firstEpicSubtaskDescription1.add("Масляный фильтр");
        firstEpicSubtaskDescription1.add("Воздушный фильтр");
        SubTask subTask1ForFirstEpic = new SubTask(manager.generateId(), firstEpicSubTaskTitle1,
                firstEpicSubtaskDescription1, Status.IN_PROGRESS, firstEpicId );
        manager.addNewTask(TypeOfTask.SUB_TASK, subTask1ForFirstEpic);
        firstEpicSubTask.add(subTask1ForFirstEpic);

        String firstEpicSubTaskTitle2 = "Выбрать автосервис";
        ArrayList<String> firstEpicSubtaskDescription2 = new ArrayList<>();
        firstEpicSubtaskDescription2.add("Согласовать время");
        firstEpicSubtaskDescription2.add("Оплатить ремонт");
        SubTask subTask2ForFirstEpic = new SubTask(manager.generateId(), firstEpicSubTaskTitle2,
                firstEpicSubtaskDescription2,Status.NEW, firstEpicId);
        manager.addNewTask(TypeOfTask.SUB_TASK, subTask2ForFirstEpic);
        firstEpicSubTask.add(subTask2ForFirstEpic);

        manager.addNewTask(TypeOfTask.EPIC,
                new Epic(firstEpicId, firstEpicTitle, firstEpicSubTask));

        String secondEpicTitle = "Сходить в туалет";
        int secondEpicId = manager.generateId();
        ArrayList<SubTask> secondEpicSubTasks = new ArrayList<>();

        String secondEpicSubTaskTitle1 = "Проверить что все на месте";
        ArrayList<String> secondEpicSubTaskDescription1 = new ArrayList<>();
        secondEpicSubTaskDescription1.add("Туалетная бумага");
        secondEpicSubTaskDescription1.add("Вода в бачке");
        SubTask subTask1ForSecondEpic = new SubTask(manager.generateId(), secondEpicSubTaskTitle1,
                secondEpicSubTaskDescription1, Status.NEW, secondEpicId);
        manager.addNewTask(TypeOfTask.SUB_TASK, subTask1ForSecondEpic);
        secondEpicSubTasks.add(subTask1ForSecondEpic);

        manager.addNewTask(TypeOfTask.EPIC,
                new Epic(secondEpicId, secondEpicTitle, secondEpicSubTasks));


        manager.getTaskFromId(1);
        manager.getTaskFromId(3);
        manager.getTaskFromId(5);
        manager.getTaskFromId(1);
        manager.getTaskFromId(3);
        manager.getTaskFromId(5);
        manager.getTaskFromId(1);
        manager.getTaskFromId(3);
        manager.getTaskFromId(5);
        manager.getTaskFromId(2);
        manager.getTaskFromId(5);
        manager.getTaskFromId(3);
*/




        ((FileBackedTaskManager)manager).load();

        System.out.println("Список важных дел:");
        Set<Task> tasks = manager.getTasksThreeSet();
        for (Task task: tasks) {
            System.out.println(task.toString());
        }




    }

}
