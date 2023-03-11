package practicum.organizer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager{
    Path path;

    public FileBackedTaskManager() {
        path = Paths.get("C:\\Users\\olehn\\dev\\PraktikumHomeWork\\src\\practicum\\organizer\\saveTasks.txt");
    }

    public FileBackedTaskManager(Path path) {
        this.path = path;
    }

    public void save() {
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException ioException) {
                System.out.println("Неудалось создать файл");
            }
        }

        try (FileWriter writer = new FileWriter(path.toString());
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {

            for (Task task : getAllTasks()) {
                bufferedWriter.write(task.TaskToString() + "\n");
            }

            bufferedWriter.write("\n");

            bufferedWriter.write(getHistoryManager().historyToString());

        } catch (IOException ioException) {
            System.out.println("Не удалось сделать запись в файл");
        }


    }

    public void load() {
        if (!Files.exists(path)) {
            System.out.println("Файл не найден");
        } else {
            try (FileReader reader = new FileReader(path.toString());
                 BufferedReader bufferedReader = new BufferedReader(reader)) {
                while (bufferedReader.ready()){
                    String[] line = bufferedReader.readLine().split(",");
                    if (line[0].isEmpty()) {
                        break;
                    }
                    switch (TypeOfTask.valueOf(line[0])){
                        case TASK:
                            Task task = taskFromString(line);
                            addNewTask(TypeOfTask.TASK, task);
                            break;
                        case EPIC:
                            Epic epic = epicFromString(line);
                            addNewTask(TypeOfTask.EPIC, epic);
                            break;
                        case SUB_TASK:
                            SubTask subTask = subTaskFromString(line);
                            addNewTask(TypeOfTask.SUB_TASK, subTask);
                            break;

                    }

                }
                if (bufferedReader.ready()) {
                    String lineOfHistory = bufferedReader.readLine();
                    historyFromString(lineOfHistory);
                }
                save();
            } catch (FileNotFoundException e) {
                System.out.println("Файл не найден");
            } catch (IOException ioException) {
                System.out.println("Не удалось прочитать файл");
            }

        }
    }

    @Override
    public void addNewTask(TypeOfTask type, Object obj) {
        super.addNewTask(type, obj);
        save();
    }

    @Override
    public void updateTask(Task task, TypeOfTask type) {
        super.updateTask(task, type);
        save();
    }

    @Override
    public void deleteTaskFromId(int id) {
        super.deleteTaskFromId(id);
        save();
    }

    @Override
    public Task getTaskFromId(int id) {
        Task task = super.getTaskFromId(id);
        save();
        return task;
    }

    public Task taskFromString(String[] allFields) {
        int id = Integer.parseInt(allFields[1]);
        String title = allFields[2];
        Status status = Status.valueOf(allFields[3]);
        ArrayList<String> description = new ArrayList<>();
        for (int i = 4; i < allFields.length; i++) {
            description.add(allFields[i]);
        }
        return new Task(id, title, description, status);

    }

    public SubTask subTaskFromString(String[] allFields) {
        int id = Integer.parseInt(allFields[1]);
        int epicId = Integer.parseInt(allFields[2]);
        String title = allFields[3];
        Status status = Status.valueOf(allFields[4]);
        ArrayList<String> description = new ArrayList<>();
        for (int i = 5; i < allFields.length; i++) {
            description.add(allFields[i]);
        }
        return new SubTask(id, title, description, status, epicId);
    }


    public Epic epicFromString(String[] allFields) {
        int id = Integer.parseInt(allFields[1]);
        String title = allFields[2];
        ArrayList<SubTask> description = new ArrayList<>();
        for (int i = 4; i <allFields.length; i++) {
            if (allFields[i].equals(TypeOfTask.SUB_TASK.toString())) {
                int start = i;

                while (!allFields[i + 1].equals(TypeOfTask.SUB_TASK.toString())) {
                    i++;
                    if (i == allFields.length - 1) {
                        break;
                    }
                }
                int stop = i+1;
                description.add(subTaskFromString(Arrays.copyOfRange(allFields, start, stop)));
            }
        }
        return new Epic(id, title, description);
    }

    public void historyFromString(String line) {
        String[] idFromLine = line.split(" ");
        ArrayList<Integer> ids = new ArrayList<>();
        for (int i = 0; i < idFromLine.length; i++) {
            ids.add(Integer.parseInt(idFromLine[i]));
        }
        for (Integer id : ids){
            getHistoryManager().addTask(getTaskFromId(id));
        }
    }

}
