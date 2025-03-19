import java.util.List;

public interface HistoryManager {
    public void addToHistory(Task task);
    public List<Task> getHistory();
    public void remove(int id);
    public void removeAll();

}
