import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {


    private final List<Integer> subtaskId = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public List<Integer> getSubtask() {
        return subtaskId;
    }

    public void setSubtaskId(Subtask subtask) {
        subtaskId.add(subtask.getId());
    }

    @Override
    public String toString() {
        return "Task:" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", subtasksEpic=" + subtaskId +
                '\'';
    }
}
