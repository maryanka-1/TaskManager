import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    InMemoryTaskManager manager = new InMemoryTaskManager(Managers.getDefaultHistory());
    Epic firstEpic = new Epic("To-do list", "List of all subtask");
    Epic secondEpic = new Epic("Empty list", "Empty list for test");
    Subtask firstSubtask = new Subtask("First subtask", "To do smth", LocalDateTime.of(2025, 05, 1, 10, 0), 15,1);
    Subtask secondSubtask = new Subtask("Second subtask", "To do smth", LocalDateTime.of(2025, 05, 1, 10, 30), 15,1);
    Subtask thirdSubtask = new Subtask("Third subtask", "To do smth", LocalDateTime.of(2025, 05, 1, 11, 0), 15,1);

    @BeforeEach
    void setUp(){
        manager.add(firstEpic);
        manager.add(secondEpic);
        manager.add(firstSubtask);
        manager.add(secondSubtask);
        manager.add(thirdSubtask);
    }

    @Test
    public void shouldGetStatusWithEmptyListSubtasks() {
        Status expected = Status.NEW;
        Status actual = secondEpic.getStatus();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldGetStatusAllNewSubtasks(){
        Status expected = Status.NEW;
        Status actual = firstEpic.getStatus();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldGetStatusAllDoneSubtasks(){
        firstSubtask.setStatus(Status.DONE);
        secondSubtask.setStatus(Status.DONE);
        thirdSubtask.setStatus(Status.DONE);
        manager.updateTask(thirdSubtask);
        manager.updateTask(secondEpic);
        manager.updateTask(firstEpic);
        Status expected = Status.DONE;
        Status actual = firstEpic.getStatus();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldGetStatusDoneAndNewSubtasks(){
        thirdSubtask.setStatus(Status.DONE);
        manager.updateTask(thirdSubtask);
        Status expected = Status.IN_PROGRESS;
        Status actual = firstEpic.getStatus();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldGetStatusInProgressSubtasks(){
        firstSubtask.setStatus(Status.IN_PROGRESS);
        secondSubtask.setStatus(Status.IN_PROGRESS);
        thirdSubtask.setStatus(Status.IN_PROGRESS);
        manager.updateTask(firstSubtask);
        manager.updateTask(secondSubtask);
        manager.updateTask(thirdSubtask);
        Status expected = Status.IN_PROGRESS;
        Status actual = firstEpic.getStatus();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldGetSubtasks(){
        int actual = firstEpic.getSubtask().size();
        int expected = 3;
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldGetSubtasksEmpty(){
        int actual = secondEpic.getSubtask().size();
        int expected = 0;
        assertThat(actual).isEqualTo(expected);
    }

}