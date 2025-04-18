import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {
    @Override
    public InMemoryTaskManager createTaskManager() {
        return new InMemoryTaskManager(historyManager);
    }

}