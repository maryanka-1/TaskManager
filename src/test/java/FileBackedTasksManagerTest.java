import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager>{
    private final String nameFile = "fileTest.csv";

    @Override
    public FileBackedTasksManager createTaskManager() {
            return new FileBackedTasksManager(historyManager, nameFile);
    }

    @Test
    public void shouldSave(){
        FileBackedTasksManager temp = new FileBackedTasksManager(historyManager, "main/java");
        ManagerSaveException actual = assertThrows(ManagerSaveException.class, ()-> temp.save());
    }

    @Test
    public void shouldLoadFromFile() throws FileNotFoundException {
        taskManager.add(task);
        taskManager.add(epic);
        taskManager.add(new Subtask("Thid sub", "Description sub",
                LocalDateTime.of(2025, 05, 1, 17, 0), 15,2));
        taskManager.getTask(1);
        taskManager.getEpic(2);
        taskManager.getSubtask(3);
        taskManager.save();
        FileBackedTasksManager newTaskManager = FileBackedTasksManager.loadFromFile("fileTest.csv");
        Map<Integer, Task> expected = taskManager.getTasks();
        Map<Integer, Task> actual= newTaskManager.getTasks();
        assertThat(actual).isEqualTo(expected);
        Map<Integer, Epic> expected1 = taskManager.getEpics();
        Map<Integer, Epic> actual1= newTaskManager.getEpics();
        assertThat(actual1).isEqualTo(expected1);
        Map<Integer, Subtask> expected2 = taskManager.getSubtasks();
        Map<Integer, Subtask> actual2= newTaskManager.getSubtasks();
        assertThat(actual2).isEqualTo(expected2);
        List<Task> expected3 = taskManager.historyManager.getHistory();
        List<Task> actual3 = newTaskManager.historyManager.getHistory();
        assertThat(actual3).isEqualTo(expected3);
    }

}