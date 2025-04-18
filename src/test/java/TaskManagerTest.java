import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class TaskManagerTest<T extends TaskManager> {
    public T taskManager;
    public HistoryManager historyManager;
    Task task = new Task("First task", "Description task",
            LocalDateTime.of(2025, 5, 1, 9, 0), 15);
    Epic epic = new Epic("Second epic", "Description epic");

    public abstract T createTaskManager();

    @BeforeEach
    public void setUp() {
        historyManager = Managers.getDefaultHistory();
        taskManager = createTaskManager();

    }

    @Test
    public void shouldAddTask(){
        taskManager.add(task);
        int expected = 1;
        int actual = taskManager.getTasks().size();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldAddEpic(){
        taskManager.add(epic);
        int expected = 1;
        int actual = taskManager.getEpics().size();
        assertThat(actual).isEqualTo(expected);
    }
    @Test
    public void shouldAddSubtask(){
        taskManager.add(epic);
        Subtask subtask = new Subtask("Third subtask", "Description sub", LocalDateTime.of(2025, 5, 1, 10, 0), 15,1);
        taskManager.add(subtask);
        int expected = 1;
        int actual = taskManager.getSubtasks().size();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldClearAll(){
        taskManager.add(task);
        taskManager.add(epic);
        Subtask subtask = new Subtask("Third subtask", "Description sub", LocalDateTime.of(2025, 5, 1, 10, 0), 15,2);
        taskManager.add(subtask);
        taskManager.getTask(1);
        int expected = 1;
        int actual = historyManager.getHistory().size();
        assertThat(actual).isEqualTo(expected);
        actual = taskManager.getSubtasks().size();
        assertThat(actual).isEqualTo(expected);
        actual = taskManager.getTasks().size();
        assertThat(actual).isEqualTo(expected);
        actual = taskManager.getEpics().size();
        assertThat(actual).isEqualTo(expected);
        taskManager.clearAll();
        expected = 0;
        actual = historyManager.getHistory().size();
        assertThat(actual).isEqualTo(expected);
        actual = taskManager.getSubtasks().size();
        assertThat(actual).isEqualTo(expected);
        actual = taskManager.getTasks().size();
        assertThat(actual).isEqualTo(expected);
        actual = taskManager.getEpics().size();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldClearTasks(){
        taskManager.add(task);
        int expected = 1;
        int actual = taskManager.getTasks().size();
        assertThat(actual).isEqualTo(expected);
        taskManager.clearTasks();
        expected = 0;
        actual = taskManager.getTasks().size();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldClearEpics(){
        taskManager.add(epic);
        Subtask subtask = new Subtask("Third subtask", "Description sub", LocalDateTime.of(2025, 5, 1, 10, 0), 15,1);
        taskManager.add(subtask);
        int expected = 1;
        int actual = taskManager.getEpics().size();
        assertThat(actual).isEqualTo(expected);
        actual = taskManager.getSubtasks().size();
        assertThat(actual).isEqualTo(expected);
        taskManager.clearEpics();
        expected = 0;
        actual = taskManager.getTasks().size();
        assertThat(actual).isEqualTo(expected);
        actual = taskManager.getSubtasks().size();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldClearSubtasks(){
        taskManager.add(epic);
        Subtask subtask = new Subtask("Third subtask", "Description sub", LocalDateTime.of(2025, 5, 1, 10, 0), 15,1);
        taskManager.add(subtask);
        int expected = 1;
        int actual = taskManager.getSubtasks().size();
        assertThat(actual).isEqualTo(expected);
        taskManager.clearSubtasks();
        actual = taskManager.getEpics().size();
        assertThat(actual).isEqualTo(expected);
        expected = 0;
        actual = taskManager.getSubtasks().size();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldGetTasks(){
        taskManager.add(task);
        Map<Integer, Task> expected = new HashMap<>();
        expected.put(1, task);
        Map<Integer, Task> actual = taskManager.getTasks();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldGetNullTask(){
        Task expected = null;
        Task actual = taskManager.getTask(3);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldGetEpics(){
        taskManager.add(epic);
        Map<Integer, Epic> expected = new HashMap<>();
        expected.put(1, epic);
        Map<Integer, Epic> actual = taskManager.getEpics();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldGetNullTEpic(){
        Epic expected = null;
        Epic actual = taskManager.getEpic(3);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldGetSubtasks(){
        taskManager.add(epic);
        Subtask subtask = new Subtask("Third subtask", "Description sub", LocalDateTime.of(2025, 5, 1, 10, 0), 15,1);
        taskManager.add(subtask);
        Map<Integer, Subtask> expected = new HashMap<>();
        expected.put(2, subtask);
        Map<Integer, Subtask> actual = taskManager.getSubtasks();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldGetNullSubtask(){
        Subtask expected = null;
        Subtask actual = taskManager.getSubtask(3);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldRemoveTask(){
        taskManager.add(task);
        int expected = 1;
        int actual = taskManager.getTasks().size();
        assertThat(actual).isEqualTo(expected);
        taskManager.removeTask(1);
        expected = 0;
        actual = taskManager.getTasks().size();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldRemoveEpic(){
        taskManager.add(epic);
        Subtask subtask = new Subtask("Third subtask", "Description sub", LocalDateTime.of(2025, 5, 1, 10, 0), 15,1);
        taskManager.add(subtask);
        int expected = 1;
        int actual = taskManager.getEpics().size();
        assertThat(actual).isEqualTo(expected);
        actual = taskManager.getSubtasks().size();
        assertThat(actual).isEqualTo(expected);
        taskManager.removeEpic(1);
        expected = 0;
        actual = taskManager.getEpics().size();
        assertThat(actual).isEqualTo(expected);
        actual = taskManager.getSubtasks().size();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldRemoveSubtask(){
        taskManager.add(epic);
        Subtask subtask = new Subtask("Third subtask", "Description sub", LocalDateTime.of(2025, 5, 1, 10, 0), 15,1);
        taskManager.add(subtask);
        int expected = 1;
        int actual = taskManager.getEpics().size();
        assertThat(actual).isEqualTo(expected);
        actual = taskManager.getSubtasks().size();
        assertThat(actual).isEqualTo(expected);
        taskManager.removeSubtask(2);
        actual = taskManager.getEpics().size();
        assertThat(actual).isEqualTo(expected);
        expected = 0;
        actual = taskManager.getSubtasks().size();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldRemoveEpicCheckHistory(){
        taskManager.add(epic);
        taskManager.add(task);
        Task task2 = new Task("Task2", "Discription 2", LocalDateTime.of(2025, 5, 1, 12, 0), 15);
        taskManager.add(task2);
        Subtask subtask = new Subtask("Third subtask", "Description sub", LocalDateTime.of(2025, 5, 1, 10, 0), 15,1);
        taskManager.add(subtask);
        taskManager.getTask(2);
        taskManager.getEpic(1);
        taskManager.getSubtask(4);
        taskManager.getTask(3);
        taskManager.getTask(2);
        taskManager.removeEpic(1);
        List<Task> expected = new ArrayList<>();
        expected.add(task2);
        expected.add(task);
        List<Task> actual = historyManager.getHistory();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldRemoveFirst(){
        taskManager.add(task);
        taskManager.add(epic);
        taskManager.getTask(1);
        taskManager.getEpic(2);
        taskManager.removeTask(1);
        List<Task> expected = new ArrayList<>();
        expected.add(epic);
        List<Task> actual = historyManager.getHistory();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldRemoveLast(){
        taskManager.add(task);
        taskManager.add(epic);
        taskManager.getTask(1);
        taskManager.getEpic(2);
        taskManager.removeEpic(2);
        List<Task> expected = new ArrayList<>();
        expected.add(task);
        List<Task> actual = historyManager.getHistory();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldRemoveOnce(){
        taskManager.add(task);
        taskManager.getTask(1);
        taskManager.removeTask(1);
        List<Task> expected = new ArrayList<>();
        List<Task> actual = historyManager.getHistory();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldUpdateTask(){
        taskManager.add(task);
        Task actual = new Task("New", "NewTask", LocalDateTime.of(2025, 5, 1, 10, 0), 15);
        actual.setId(1);
        Task expected = taskManager.getTask(1);
        assertThat(actual).isNotEqualTo(expected);
        taskManager.updateTask(actual);
        expected = taskManager.getTask(1);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldUpdateEpic(){
        taskManager.add(epic);
        Subtask subtask = new Subtask("Third subtask", "Description sub", LocalDateTime.of(2025, 5, 1, 10, 0), 15,1);
        taskManager.add(subtask);
        Epic actual = new Epic("New", "NewTask");
        actual.setId(1);
        Epic expected = taskManager.getEpic(1);
        assertThat(actual).isNotEqualTo(expected);
        taskManager.updateTask(actual);
        expected = taskManager.getEpic(1);
        List<Integer> actual1 = taskManager.getSubtasksEpic(actual);
        List<Integer> expected2 = taskManager.getSubtasksEpic(expected);
        assertThat(actual).isEqualTo(expected);
        assertThat(actual1).isEqualTo(expected2);
    }

    @Test
    public void shouldUpdateSubtask(){
        taskManager.add(epic);
        Subtask sub1 = new Subtask("Third subtask", "Description sub", LocalDateTime.of(2025, 5, 1, 11, 0), 15,1);
        Subtask actual = new Subtask("Third subtask", "Description sub", LocalDateTime.of(2025, 5, 1, 10, 0), 15,1);
        taskManager.add(sub1);
        actual.setId(2);
        actual.setStatus(Status.DONE);
        Subtask expected = taskManager.getSubtask(2);
        assertThat(actual).isNotEqualTo(expected);
        taskManager.updateTask(actual);
        expected = taskManager.getSubtask(2);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldGetEpicOfSubtask(){
        taskManager.add(epic);
        Subtask subtask = new Subtask("Third subtask", "Description sub", LocalDateTime.of(2025, 5, 1, 10, 0), 15,1);
        taskManager.add(subtask);
        int actual = epic.getId();
        int expected = subtask.getEpicId();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldUpdateStatusEpicDone(){
        taskManager.add(epic);
        Subtask subtask1 = new Subtask("Third subtask", "Description sub", LocalDateTime.of(2025, 5, 1, 10, 0), 15,1);
        taskManager.add(subtask1);
        Subtask subtask2 = new Subtask("Third subtask", "Description sub", LocalDateTime.of(2025, 5, 1, 11, 0), 15,1);
        taskManager.add(subtask2);
        subtask1.setStatus(Status.DONE);
        subtask2.setStatus(Status.DONE);
        taskManager.updateTask(subtask1);
        taskManager.updateTask(subtask2);
        Status expected = Status.DONE;
        Status actual = taskManager.getEpic(1).getStatus();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldUpdateStatusEpicInProgress(){
        taskManager.add(epic);
        Subtask subtask1 = new Subtask("Third subtask", "Description sub", LocalDateTime.of(2025, 5, 1, 10, 0), 15,1);
        taskManager.add(subtask1);
        Subtask subtask2 = new Subtask("Third subtask", "Description sub", LocalDateTime.of(2025, 5, 1, 11, 0), 15,1);
        taskManager.add(subtask2);
        subtask2.setStatus(Status.DONE);
        taskManager.updateTask(subtask2);
        Status expected = Status.IN_PROGRESS;
        Status actual = taskManager.getEpic(1).getStatus();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldUpdateStatusEpicSubtaskInProgress(){
        taskManager.add(epic);
        Subtask subtask1 = new Subtask("Third subtask", "Description sub", LocalDateTime.of(2025, 5, 1, 10, 0), 15,1);
        taskManager.add(subtask1);
        Subtask subtask2 = new Subtask("Third subtask", "Description sub", LocalDateTime.of(2025, 5, 1, 11, 0), 15,1);
        taskManager.add(subtask2);
        subtask1.setStatus(Status.IN_PROGRESS);
        subtask2.setStatus(Status.IN_PROGRESS);
        taskManager.updateTask(subtask1);
        taskManager.updateTask(subtask2);
        Status expected = Status.IN_PROGRESS;
        Status actual = taskManager.getEpic(1).getStatus();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldUpdateStatusEpicSubtaskNewInProgress(){
        taskManager.add(epic);
        Subtask subtask1 = new Subtask("Third subtask", "Description sub", LocalDateTime.of(2025, 5, 1, 10, 0), 15,1);
        taskManager.add(subtask1);
        Subtask subtask2 = new Subtask("Third subtask", "Description sub", LocalDateTime.of(2025, 5, 1, 11, 0), 15,1);
        taskManager.add(subtask2);
        subtask1.setStatus(Status.IN_PROGRESS);
        taskManager.updateTask(subtask1);
        Status expected = Status.IN_PROGRESS;
        Status actual = taskManager.getEpic(1).getStatus();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldUpdateStatusEpicBackNew(){
        taskManager.add(epic);
        Subtask subtask1 = new Subtask("Third subtask", "Description sub", LocalDateTime.of(2025, 5, 1, 10, 0), 15,1);
        taskManager.add(subtask1);
        Subtask subtask2 = new Subtask("Third subtask", "Description sub", LocalDateTime.of(2025, 5, 1, 11, 0), 15,1);
        taskManager.add(subtask2);
        subtask1.setStatus(Status.IN_PROGRESS);
        taskManager.updateTask(subtask1);
        subtask1.setStatus(Status.NEW);
        taskManager.updateTask(subtask1);
        Status expected = Status.NEW;
        Status actual = taskManager.getEpic(1).getStatus();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldCheckOverlayTasks(){
        taskManager.add(task);
        Task newTask = new Task("First task", "Description task",
                LocalDateTime.of(2025, 5, 1, 9, 0), 15);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->taskManager.add(newTask));
        String expected = "Task was not added - incorrect time";
        String actual = exception.getMessage();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldCheckOverlaySubtasks(){
        taskManager.add(epic);
        Subtask sub1 = new Subtask("Third subtask", "Description sub", LocalDateTime.of(2025, 5, 1, 11, 0), 15,1);
        Subtask sub2 = new Subtask("Third subtask", "Description sub", LocalDateTime.of(2025, 5, 1, 11, 5), 15,1);
        taskManager.add(sub1);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->taskManager.add(sub2));
        String expected = "Task was not added - incorrect time";
        String actual = exception.getMessage();
        assertThat(actual).isEqualTo(expected);
    }
}