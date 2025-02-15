import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Epic extends Task{
    private List<Integer> subtasksEpic = new ArrayList();
    public Epic(String name, String description) {
        super(name, description);
    }
    public Epic(String name, String description, List<Integer> subtasksEpic, Status status) {
        super(name, description);
        subtasksEpic.addAll(subtasksEpic);
    }

    public void addSubtask(int subtaskId) {
        subtasksEpic.add(subtaskId);
    }
    public List<Integer> getSubtask() {
        return subtasksEpic;
    }

    @Override
    public String toString() {
        return "Task:" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", subtasksEpic=" + subtasksEpic+
                '\'';
    }
}
