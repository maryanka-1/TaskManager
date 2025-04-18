import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task implements Comparable<Task>{

    private String name;
    private String description;
    private Integer id;
    private Status status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer duration;
    TypeTask type;

    public Task(String name, String description, LocalDateTime startTime, int duration) {
        this.name = name;
        this.description = description;
        this.status = Status.NEW;
        this.type = TypeTask.TASK;
        this.startTime = startTime;
        this.duration = duration;
        endTime = calculateEndTime();
    }

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.status = Status.NEW;
        this.type = TypeTask.TASK;
    }

    private LocalDateTime calculateEndTime(){
        if(startTime==null || duration == null){
            return null;
        } else {
            return startTime.plusMinutes(duration);
        }
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getId() {
        return id;
    }

    public TypeTask getType() {
        return type;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDateTime getStartTime(){
        return startTime;
    }
    public LocalDateTime getEndTime(){
        return  endTime;
    }

    public Integer getDuration(){
        return duration;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setType(TypeTask type) {
        this.type = type;
    }

    public void setStartTime(LocalDateTime time){
        this.startTime = time;
    }

    public void setEndTime(LocalDateTime time){
        this.endTime = time;
    }

    public void setDuration(int duration){
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Task:" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status + '\''+
                ", time to Start="+startTime + '\''+
                ", time to End="+endTime+'\''+
                ", duration="+duration+'\'';

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(name, task.name) && Objects.equals(description, task.description) && Objects.equals(id, task.id) && status == task.status && type == task.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, id, status, type);
    }

    @Override
    public int compareTo(Task other) {
        LocalDateTime start1 = this.startTime;
        LocalDateTime start2 = other.startTime;
        if (start1 == null && start2 == null) {
            return 0;
        }
        if (start1==null) {
            return 1;
        }
        if (start2==null) {
            return -1;
        }
        return this.startTime.compareTo(other.startTime);
    }
}

