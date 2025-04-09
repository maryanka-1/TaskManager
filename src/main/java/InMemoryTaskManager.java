import java.util.*;

public class InMemoryTaskManager implements TaskManager {


    private int id = 1;
    public Map<Integer, Task> tasks = new HashMap<>();
    public Map<Integer, Subtask> subtasks = new HashMap<>();
    public Map<Integer, Epic> epics = new HashMap<>();
    public final HistoryManager historyManager;

    public InMemoryTaskManager(HistoryManager historyManager) {
        this.historyManager = historyManager;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void add(Task task) {
        task.setId(id);
        id++;
        tasks.put(task.getId(), task);
    }

    @Override
    public void add(Subtask subtask) {
        subtask.setId(id);
        epics.get(subtask.getEpicId()).setSubtaskId(subtask);
        subtasks.put(subtask.getId(), subtask);
        id++;
    }

    @Override
    public void add(Epic epic) {
        epic.setId(id);
        id++;
        epics.put(epic.getId(), epic);
    }

    @Override
    public void clearAll() {
        tasks.clear();
        epics.clear();
        subtasks.clear();
        historyManager.removeAll();
    }

    @Override
    public void clearTasks() {
        tasks.keySet()
                .forEach(id -> historyManager.remove(id));
        tasks.clear();
    }

    @Override
    public void clearSubtasks() {
        subtasks.keySet()
                .forEach(id -> historyManager.remove(id));

        subtasks.clear();
    }

    @Override
    public void clearEpics() {
        subtasks.keySet()
                .forEach(id -> historyManager.remove(id));
        subtasks.clear();
        epics.keySet()
                .forEach(id -> historyManager.remove(id));
        epics.clear();
    }

    @Override
    public Map<Integer, Task> getTasks() {
        return tasks;
    }

    @Override
    public Map<Integer, Subtask> getSubtasks() {
        return subtasks;
    }

    @Override
    public Map<Integer, Epic> getEpics() {
        return epics;
    }

    @Override
    public Task getTask(int id) {
        historyManager.addToHistory(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public Subtask getSubtask(int id) {
        historyManager.addToHistory(subtasks.get(id));
        return subtasks.get(id);
    }

    @Override
    public Epic getEpic(int id) {
        historyManager.addToHistory(epics.get(id));
        return epics.get(id);
    }

    @Override
    public void removeTask(int id) {
        tasks.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void removeSubtask(int id) {
        subtasks.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void removeEpic(int id) {
        epics.get(id).getSubtask().forEach(i -> {
            removeSubtask(i);
            historyManager.remove(i);
        });
        historyManager.remove(id);
        epics.remove(id);
    }

    @Override
    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        }
    }

    @Override
    public void updateTask(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            epics.put(epic.getId(), epic);
        }
    }

    @Override
    public void updateTask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getId())) {
            subtasks.put(subtask.getId(), subtask);
            updateEpicStatus(subtask);
        }
    }

    private void updateEpicStatus(Subtask subtask1) {
        List<Integer> checked = epics.get(subtask1.getEpicId()).getSubtask();
        int done = 0;
        int news = 0;
        for (Integer i : checked) {
            if (subtasks.containsKey(i)) {
                switch (subtasks.get(i).getStatus()) {
                    case NEW:
                        news++;
                        break;
                    case DONE:
                        done++;
                        break;
                }
            }
        }
        if (done == checked.size()) {
            epics.get(subtask1.getEpicId()).setStatus(Status.DONE);
        } else if (news == checked.size()) {
            epics.get(subtask1.getEpicId()).setStatus(Status.NEW);
        } else epics.get(subtask1.getEpicId()).setStatus(Status.IN_PROGRESS);
    }

    @Override
    public List<Integer> getSubtasksEpic(Epic epic) {
        return epic.getSubtask();
    }
}
