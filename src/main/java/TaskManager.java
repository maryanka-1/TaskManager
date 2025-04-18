import java.util.List;
import java.util.Map;
import java.util.Set;

public interface TaskManager {

    public void add(Task task);

    public void add(Subtask subtask);

    public void add(Epic epic);

    public void clearAll();

    public void clearTasks();

    public void clearSubtasks();

    public void clearEpics();

    public Map<Integer, Task> getTasks();

    public Map<Integer, Subtask> getSubtasks();

    public Map<Integer, Epic> getEpics();

    public Task getTask(int id);

    public Subtask getSubtask(int id);

    public Epic getEpic(int id);

    public void removeTask(int id);

    public void removeSubtask(int id);

    public void removeEpic(int id);

    public void updateTask(Task task);

    public void updateTask(Epic epic);

    public void updateTask(Subtask subtask);

    public List<Integer> getSubtasksEpic(Epic epic);

    public Set<Task> getPrioritizedTasks();
}


