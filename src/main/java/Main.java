import java.io.FileNotFoundException;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

//        InMemoryTaskManager fileManager = new InMemoryTaskManager(Managers.getDefaultHistory());
        FileBackedTasksManager fileManager = new FileBackedTasksManager(Managers.getDefaultHistory(), "file.csv");
        Task first = new Task("Buy products", "Buy all products from list",
                LocalDateTime.of(2025, 05, 1, 9, 0), 15);
        fileManager.add(first);
        Task second = new Task("invite guests", "Sent messeges to guests",
                LocalDateTime.of(2025, 05, 1, 11, 30), 15);
        fileManager.add(second);
        Epic fEpic = new Epic("Prepare table", "Prepare table for guests");
        fileManager.add(fEpic);
        Subtask tablecloth = new Subtask("Tablecloth", "Cover the table with a tablecloth", LocalDateTime.of(2025, 05, 1, 11, 0), 15, fEpic.getId());
        fileManager.add(tablecloth);
        Subtask plate = new Subtask("Plates", "arrange the plates", LocalDateTime.of(2025, 05, 1, 12, 0), 15,fEpic.getId());
        fileManager.add(plate);
        Subtask salad = new Subtask("Clear", "Clear room", LocalDateTime.of(2025, 05, 1, 12, 15), 15,fEpic.getId());
        fileManager.add(salad);
        Epic sEpic = new Epic("Cook", "Cook dishes for dinner");
        fileManager.add(sEpic);


        fileManager.getEpic(3);
        System.out.println(fileManager.historyManager.getHistory());
        fileManager.getTask(2);
        System.out.println(fileManager.historyManager.getHistory());
        fileManager.getEpic(3);
        System.out.println(fileManager.historyManager.getHistory());
        fileManager.getSubtask(4);
        System.out.println(fileManager.historyManager.getHistory());
        fileManager.getSubtask(5);
        System.out.println(fileManager.historyManager.getHistory());
        fileManager.getSubtask(6);
        System.out.println(fileManager.historyManager.getHistory());
        fileManager.removeEpic(3);
        fileManager.getTask(1);
        System.out.println(fileManager.historyManager.getHistory());
        System.out.println(fileManager.getEpic(3));
        fileManager.removeSubtask(4);
        System.out.println(fileManager.getEpic(3));
        FileBackedTasksManager fileBackedTasksManager = FileBackedTasksManager.loadFromFile("file.csv");
        System.out.println("\nСостояние нового fileBackedTasksManager после восстановления");
        System.out.println(fileBackedTasksManager.tasks);
        System.out.println(fileBackedTasksManager.epics);
        System.out.println(fileBackedTasksManager.subtasks);
        System.out.println(fileBackedTasksManager.historyManager.getHistory());
        System.out.println("\nСостояние после добавления новой задачи");
        fileBackedTasksManager.add(new Subtask("Salad", "Cook a salad", LocalDateTime.of(2025, 05, 1, 12, 45),15, 7));
        System.out.println();
        fileBackedTasksManager.getSubtask(8);
        System.out.println(fileBackedTasksManager.tasks);
        System.out.println(fileBackedTasksManager.epics);
        System.out.println(fileBackedTasksManager.subtasks);
        System.out.println(fileBackedTasksManager.historyManager.getHistory());
        System.out.println(fileManager.getPrioritizedTasks());
    }
}
