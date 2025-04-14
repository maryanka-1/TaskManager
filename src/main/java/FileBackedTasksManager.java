import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class FileBackedTasksManager extends InMemoryTaskManager {
    private final String file;

    public FileBackedTasksManager(HistoryManager historyManager, String file) {
        super(historyManager);
        this.file = file;
    }

    public void save() {
        Path path = Paths.get(file);
        try {
            if (Files.exists(path)) {
                Files.delete(path);
            }
            Files.createFile(path);
            try (FileWriter fr = new FileWriter(file)) {
                fr.write("id,type,name,status,description,idEpic\n");
                for (Map.Entry<Integer, Task> entry : tasks.entrySet()) {
                    fr.write(toString(entry.getValue()));
                }
                for (Map.Entry<Integer, Epic> entry : epics.entrySet()) {
                    fr.write(toString(entry.getValue()));
                }
                for (Map.Entry<Integer, Subtask> entry : subtasks.entrySet()) {
                    fr.write(toString(entry.getValue()));
                }
                fr.write("\n" + historyToString(historyManager));
            } catch (IOException e) {
                throw new ManagerSaveException("Ошибка при создании файла сохранения");
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при создании файла");
        }
    }


    @Override
    public void add(Task task) {
        super.add(task);
        save();
    }

    @Override
    public void add(Subtask subtask) {
        super.add(subtask);
        save();
    }

    @Override
    public void add(Epic epic) {
        super.add(epic);
        save();
    }

    @Override
    public void clearAll() {
        super.clearAll();
        save();
    }

    @Override
    public void clearTasks() {
        super.clearTasks();
        save();
    }

    @Override
    public void clearSubtasks() {
        super.clearSubtasks();
        save();
    }

    @Override
    public void clearEpics() {
        super.clearEpics();
        save();
    }

    @Override
    public Task getTask(int id) {
        Task task = super.getTask(id);
        save();
        return task;
    }

    @Override
    public Subtask getSubtask(int id) {
        Subtask subtask = super.getSubtask(id);
        save();
        return subtask;
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = super.getEpic(id);
        save();
        return epic;
    }

    @Override
    public void removeTask(int id) {
        super.removeTask(id);
        save();
    }

    @Override
    public void removeSubtask(int id) {
        super.removeSubtask(id);
        save();
    }

    @Override
    public void removeEpic(int id) {
        super.removeEpic(id);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateTask(Epic epic) {
        super.updateTask(epic);
        save();
    }

    @Override
    public void updateTask(Subtask subtask) {
        super.updateTask(subtask);
        save();
    }

    private String toString(Task task) {
        StringBuilder result = new StringBuilder();
        result.append(task.getId()).append(",");
        result.append(task.getType()).append(",");
        result.append(task.getName()).append(",");
        result.append(task.getStatus()).append(",");
        result.append(task.getDescription()).append(",");
        if (task.getClass() == Subtask.class) {
            Subtask subtask = (Subtask) task;
            result.append(subtask.getEpicId()).append("\n");
        } else result.append("\n");
        return String.valueOf(result);
    }

    private static String historyToString(HistoryManager manager) {
        StringBuilder stringBuilder = new StringBuilder();
        List<Task> history = manager.getHistory();
        for (int i = 0; i < history.size(); i++) {
            stringBuilder.append(history.get(i).getId());
            if (i < history.size() - 1) {
                stringBuilder.append(",");
            }
        }
        return String.valueOf(stringBuilder);
    }

    private Task fromString(String value) {
        String[] arrayForCreateTask = value.split(",");
        Task result = null;
        switch (arrayForCreateTask[1]){
            case "SUBTASK":
                result = new Subtask(arrayForCreateTask[2], arrayForCreateTask[4], Integer.parseInt(arrayForCreateTask[5]));
                result.setType(TypeTask.SUBTASK);
                break;
            case "TASK":
                result = new Task(arrayForCreateTask[2], arrayForCreateTask[4]);
                result.setType(TypeTask.TASK);
                break;
            case "EPIC":
                result = new Epic(arrayForCreateTask[2], arrayForCreateTask[4]);
                result.setType(TypeTask.EPIC);
                break;
            default:
                System.out.println("Некорректный тип строки");
        }
            result.setStatus(Status.valueOf(arrayForCreateTask[3]));
            result.setId(Integer.parseInt(arrayForCreateTask[0]));
        return result;
    }

    private static List<Integer> historyFromString(String value) {
        String[] arrayTaskIdForHistoryManager = value.split(",");
        List<Integer> result = new ArrayList<>();
        Arrays.stream(arrayTaskIdForHistoryManager)
                .forEach(string-> result.add(Integer.parseInt(string)));
        return result;
    }

    public static FileBackedTasksManager loadFromFile(String file) throws FileNotFoundException {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(Managers.getDefaultHistory(), "newFile.csv");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int idMax = 0;
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    String[] arrayLine = line.split(",");
                    String tmp = arrayLine[1];
                    if (tmp.equals("TASK") ||
                            tmp.equals("EPIC") ||
                            tmp.equals("SUBTASK")) {
                        int idTask = Integer.parseInt(arrayLine[0]);
                        if (idMax < idTask) {
                            idMax = idTask;
                        }
                        fileBackedTasksManager.setId(idTask);
                        switch (tmp) {
                            case "TASK":
                                fileBackedTasksManager.add(fileBackedTasksManager.fromString(line));
                                break;
                            case "EPIC":
                                fileBackedTasksManager.add((Epic) fileBackedTasksManager.fromString(line));
                                break;
                            case "SUBTASK":
                                fileBackedTasksManager.add((Subtask) fileBackedTasksManager.fromString(line));
                                break;
                        }
                    } else if (!line.startsWith("id,type")) {
                        List<Integer> listIdTaskForHistory = historyFromString(line);
                        for (Integer integer : listIdTaskForHistory) {
                            if (fileBackedTasksManager.tasks.containsKey(integer)) {
                                fileBackedTasksManager.historyManager.addToHistory(fileBackedTasksManager.tasks.get(integer));
                            }
                            if (fileBackedTasksManager.epics.containsKey(integer)) {
                                fileBackedTasksManager.historyManager.addToHistory(fileBackedTasksManager.epics.get(integer));
                            }
                            if (fileBackedTasksManager.subtasks.containsKey(integer)) {
                                fileBackedTasksManager.historyManager.addToHistory(fileBackedTasksManager.subtasks.get(integer));
                            }
                        }
                    }
                }
            }
        }
        catch (IOException e) {
            fileBackedTasksManager.save();
        }
        return fileBackedTasksManager;
    }
}
