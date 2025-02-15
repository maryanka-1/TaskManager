public class Subtask extends Task {
    private final Epic epic;


    public Subtask(String name, String description, Epic epic) {
        super(name, description);
        this.epic = epic;
        this.epic.addSubtask(getId());
    }

    public Subtask(String name, String description, Integer id,  Status status, Epic epic) {
        super(name, description, id, status);
        this.epic = epic;
        this.epic.addSubtask(id);
    }

    public Epic getEpic() {
        return epic;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", epic=" + epic.getName() +
                '}';

    }
}
