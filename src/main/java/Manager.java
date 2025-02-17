import java.util.*;

public class Manager {

    private int id = 1;
    public Map<Integer, Task> tasks = new HashMap<>();
    public Map<Integer, Subtask> subtasks = new HashMap<>();
    public Map<Integer, Epic> epics = new HashMap<>();

    public <T extends Task> void add(T task) {
        task.setId(id);
        id++;
        if (task instanceof Subtask) {
            ((Subtask) task).getEpic().setSubtasksEpic((Subtask) task);
            subtasks.put(task.getId(), (Subtask) task);
        } else if (task instanceof Task){
            tasks.put(task.getId(), task);
        } else epics.put(task.getId(), (Epic) task);

    }

    public void clearAll() {
        tasks.clear();
        epics.clear();
        subtasks.clear();
    }

    public void clearTasks() {
        tasks.clear();
    }

    public void clearSubtasks() {
        subtasks.clear();
    }

    public void clearEpics() {
        subtasks.clear();
        epics.clear();
    }

    public Map<Integer, Task> getTasks() {
        return tasks;
    }

    public Map<Integer, Subtask> getSubtasks() {
        return subtasks;
    }

    public Map<Integer, Epic> getEpics() {
        return epics;
    }

    public Task getTask(int id) {
        return tasks.get(id);
    }

    public Subtask getSubtask(int id) {
        return subtasks.get(id);
    }

    public Epic getEpic(int id) {
        return epics.get(id);
    }

    public void removeTask(int id) {
        tasks.remove(id);
    }

    public void removeSubtask(int id) {
        subtasks.remove(id);
    }

    public void removeEpic(int id) {
        for (Subtask subtask : epics.get(id).getSubtask().values()) {
            removeSubtask(subtask.getId());
        }
        epics.remove(id);
    }

    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        }
    }

    public void updateTask(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            epics.put(epic.getId(), epic);
        }
    }

    public void updateTask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getId())) {
            subtasks.put(subtask.getId(), subtask);
            subtask.getEpic().setSubtasksEpic(subtask);
            subtask.getEpic().setStatus(checkStatusSubtask(subtask));
        }
    }

    private Status checkStatusSubtask(Subtask subtask1) {
        Map<Integer, Subtask> checked = subtask1.getEpic().getSubtask();
        int done = 0;
        int news = 0;
        for (Subtask subtask : checked.values()) {
            switch (subtask.getStatus()) {
                case NEW:
                    news++;
                    break;
                case DONE:
                    done++;
                    break;
            }
        }
        if (done == checked.size()) {
            return Status.DONE;
        } else if (news == checked.size()) {
            return Status.NEW;
        } else return Status.IN_PROGRESS;
    }

    public Map<Integer, Subtask> getSubtasksEpic(Epic epic) {
        return epic.getSubtask();
    }


}
