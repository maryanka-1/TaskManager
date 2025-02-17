import java.util.HashMap;
import java.util.Map;

public class Epic extends Task {


    private Map<Integer,Subtask> subtasksEpic = new HashMap<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public Map<Integer, Subtask> getSubtask() {
        return subtasksEpic;
    }

    public void setSubtasksEpic(Subtask subtaskEpic) {
        subtasksEpic.put(subtaskEpic.getId(),subtaskEpic);
    }

    @Override
    public String toString() {
        return "Task:" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", subtasksEpic=" + subtasksEpic +
                '\'';
    }
}
