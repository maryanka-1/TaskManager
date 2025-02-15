import java.util.HashMap;
import java.util.List;


public class Task {

    private String name;
    private String description;
    private Integer id;
    private Status status;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        id = Manager.setId(Manager.getId());
        this.status = Status.NEW;
    }
    public Task(String name, String description, Integer id, Status status) {
        this.name = name;
        this.description = description;
        this.id = id;
        if(status == Status.NEW) {
            this.status = Status.IN_PROGRESS;
        } else this.status = Status.DONE;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Task:" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '\'';

    }
}

