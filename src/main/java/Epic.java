import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Epic extends Task {
    private List<Integer> subtaskId = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
        this.type = TypeTask.EPIC;
    }

    public List<Integer> getSubtask() {
        return subtaskId;
    }

    public void removeSubtaskInEpic(Integer obj){
        subtaskId.remove(obj);
    }

    public void setSubtaskId(List<Integer> list) {
        this.subtaskId = list;
    }

    public void setSubtaskId(Subtask subtask) {
        subtaskId.add(subtask.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subtaskId);
    }

    @Override
    public String toString() {
        return "Task:" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", subtasksEpic=" + subtaskId +
                ", time to Start=" + getStartTime() + '\'' +
                ", time to End=" + getEndTime() + '\'' +
                ", duration=" + getDuration() + '\'' +
                '}';
    }
}
