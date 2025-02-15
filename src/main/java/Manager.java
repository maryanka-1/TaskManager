import java.awt.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class Manager {

    private static int id = 1;
    private static Map<Integer, Task> tasks = new HashMap<>();
    private static Map<Integer, Subtask> subtasks = new HashMap<>();
    private static Map<Integer, Epic> epics = new HashMap<>();

    public static int getId() {
        return id;
    }
    public static int setId(int id) {
        Manager.id = id+1;
        return id;
    }

    public static Map<Integer, Task> getAllTasks(){
        return tasks;
    }
    public static Map<Integer, Subtask> getAllSubtasks(){
        return subtasks;
    }
    public static Map<Integer, Epic> getAllEpic(){
        return epics;
    }


    public static Task getTaskById(int id) {
        return tasks.get(id);
    }
    public static Subtask getSubtaskById(int id) {
        return subtasks.get(id);
    }
    public static Epic getEpicById(int id) {
        return epics.get(id);
    }

    public static boolean removeById(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
            return true;
        }
        if (subtasks.containsKey(id)) {
            subtasks.remove(id);
            return true;
        }
        if (epics.containsKey(id)) {
            clearSubtasksOfEpic(epics.get(id));
            epics.remove(id);
            return true;
        }
        return false;
    }

    public static void clearSubtasksOfEpic(Epic epic) {
        List<Integer> subtasksOfEpic = epic.getSubtask();
        for (Integer integer : subtasksOfEpic) {
            subtasks.remove(integer);
        }
    }

    public static void clearAll(){
        tasks.clear();
        subtasks.clear();
        epics.clear();
    }

    public static Task createTask(String name, String description) {
        Task task = new Task(name, description);
        tasks.put(task.getId(), task);
        return task;
    }
    public static Subtask createSubtask(String name, String description, Epic epic) {
        Subtask subtask = new Subtask(name, description, epic);
        subtasks.put(subtask.getId(), subtask);
        return subtask;
    }
    public static Epic createEpic(String name, String description) {
        Epic epic = new Epic(name, description);
        epics.put(epic.getId(), epic);
        return epic;
    }

    public static Task updateTask(Task oldTask) {
        Status temp = oldTask.getStatus();
        String name = oldTask.getName();
        String description = oldTask.getDescription();
        int idOld = oldTask.getId();
        Manager.removeById(oldTask.getId());
        Task newTask = new Task(name, description, idOld, temp);
        tasks.put(newTask.getId(), newTask);
        return newTask;
    }

    public static Task updateSubtask(Subtask oldTask) {
        Status tempStatus = oldTask.getStatus();
        String name = oldTask.getName();
        String description = oldTask.getDescription();
        Integer idOld = oldTask.getId();
        Epic tempEpic = oldTask.getEpic();
        Manager.removeById(oldTask.getId());
        Subtask newSubtask = new Subtask(name, description, idOld, tempStatus, tempEpic);
        subtasks.put(newSubtask.getId(), newSubtask);
        return newSubtask;
    }
    public static Epic updateEpic(Epic oldEpic) {
        Status temp = oldEpic.getStatus();
        String name = oldEpic.getName();
        String description = oldEpic.getDescription();
        Integer idOld = oldEpic.getId();
        List<Integer> subtasksOfEpic = oldEpic.getSubtask();
        //есть проблема с сохранением листа подзадач, и обновление статуса надо изменить у Epic
        Manager.removeById(oldEpic.getId());
        Epic newEpic = new Epic(name, description, subtasksOfEpic, temp);
        tasks.put(newEpic.getId(), newEpic);
        return newEpic;
    }


}
