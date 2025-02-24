import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{
    private final List<Task> history = new LinkedList<>();
    private static final int SIZE = 10;

    @Override
    public void addToHistory(Task task) {
        if (task == null) {
            return;
        }
        history.addFirst(task);
        if (history.size() > SIZE) {
            history.removeLast();
        }
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(history);
    }


}
