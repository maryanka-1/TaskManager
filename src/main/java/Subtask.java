import java.time.LocalDateTime;

public class Subtask extends Task {

    private final int idEpic;

    public Subtask(String name, String description, LocalDateTime startTime, int duration, int idEpic) {
        super(name, description, startTime,duration);
        this.idEpic = idEpic;
        this.type = TypeTask.SUBTASK;
    }

    public int getEpicId() {
        return idEpic;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", epic=" + idEpic +
                ", time to Start="+getStartTime() + '\''+
                ", time to End="+getEndTime()+'\''+
                ", duration="+getDuration()+'\''+
                '}';


    }
}
