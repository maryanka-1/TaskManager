import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    private int id = 1;
    public Map<Integer, Task> tasks = new HashMap<>();
    public Map<Integer, Subtask> subtasks = new HashMap<>();
    public Map<Integer, Epic> epics = new HashMap<>();
    public final HistoryManager historyManager;
    public Set<Task> prioritizedTasks = new TreeSet<>();

    public InMemoryTaskManager(HistoryManager historyManager) {
        this.historyManager = historyManager;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCounterId(int id) {
        this.id = id;
    }

    @Override
    public void add(Task task) {
        if (!checkOverlay(task)) {
            task.setId(id);
            id++;
            tasks.put(task.getId(), task);
            prioritizedTasks.add(task);
        } else throw new IllegalArgumentException("Task was not added - incorrect time");
    }

    @Override
    public void add(Subtask subtask) {
        if (!checkOverlay(subtask)) {
            subtask.setId(id);
            id++;
            epics.get(subtask.getEpicId()).setSubtaskId(subtask);
            subtasks.put(subtask.getId(), subtask);
            calculateTimeEpic(epics.get(subtask.getEpicId()));
            prioritizedTasks.add(subtask);
        } else throw new IllegalArgumentException("Task was not added - incorrect time");
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
        epics.values()
                .forEach(epic -> {
                    epic.setStartTime(null);
                    epic.setEndTime(null);
                    epic.setDuration(0);
                });
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
        prioritizedTasks.remove(tasks.get(id));
        tasks.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void removeSubtask(int id) {
        if (subtasks.containsKey(id)) {
            Epic epic = epics.get(subtasks.get(id).getEpicId());
            epic.removeSubtaskInEpic(id);
            prioritizedTasks.remove(subtasks.get(id));

            subtasks.remove(id);
            historyManager.remove(id);
            calculateTimeEpic(epic);
        }
    }

    @Override
    public void removeEpic(int id) {
        List<Integer> list = new ArrayList<>(epics.get(id).getSubtask());
        for (Integer integer : list) {
            removeSubtask(integer);
            historyManager.remove(integer);
        }
        historyManager.remove(id);
        epics.remove(id);
    }

    @Override
    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.remove(tasks.get(task.getId()));
            tasks.put(task.getId(), task);
            prioritizedTasks.add(task);
        }
    }

    @Override
    public void updateTask(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            List<Integer> list = epics.get(epic.getId()).getSubtask();
            epic.setSubtaskId(list);
            calculateTimeEpic(epic);
            epics.put(epic.getId(), epic);
        }
    }

    @Override
    public void updateTask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getId())) {
            subtasks.remove(subtasks.get(subtask.getId()));
            epics.get(subtask.getEpicId()).setSubtaskId(subtask);
            subtasks.put(subtask.getId(), subtask);
            calculateTimeEpic(epics.get(subtask.getEpicId()));
            prioritizedTasks.add(subtask);
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
        calculateTimeEpic(epics.get(subtask1.getEpicId()));
    }

    @Override
    public List<Integer> getSubtasksEpic(Epic epic) {
        return epic.getSubtask();
    }

    @Override
    public Set<Task> getPrioritizedTasks() {
        return prioritizedTasks;
    }

    private void calculateTimeEpic(Epic epic) {
        LocalDateTime start = null;
        LocalDateTime end = null;
        int duration = 0;
        if (epic.getSubtask() != null) {
            for (Integer idSub : epic.getSubtask()) {
                if (start == null || start.isAfter(subtasks.get(idSub).getStartTime())) {
                    start = subtasks.get(idSub).getStartTime();
                }
                if (end == null || end.isBefore(subtasks.get(idSub).getEndTime())) {
                    end = subtasks.get(idSub).getEndTime();
                }
                duration += subtasks.get(idSub).getDuration();
            }
        }
        epic.setStartTime(start);
        epic.setEndTime(end);
        epic.setDuration(duration);
    }

    private boolean isOverlay(Task first, Task second) {
        LocalDateTime start1 = first.getStartTime();
        LocalDateTime end1 = first.getEndTime();
        LocalDateTime start2 = second.getStartTime();
        LocalDateTime end2 = second.getEndTime();
        return (start1.isBefore(end2) && (end1.isAfter(start2)));
    }

    private boolean checkOverlay(Task newTask) {
        for (Task task : tasks.values()) {
            if (isOverlay(task, newTask)) {
                return true;
            }
        }
        for (Subtask subtask : subtasks.values()) {
            if (isOverlay(subtask, newTask)) {
                return true;
            }
        }
        return false;
    }
}
