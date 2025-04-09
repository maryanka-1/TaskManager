public class Subtask extends Task {

    private final int idEpic;

    public Subtask(String name, String description, int idEpic) {
        super(name, description);
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
                '}';

    }
}
