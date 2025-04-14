import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

//        InMemoryTaskManager manager = new InMemoryTaskManager(Managers.getDefaultHistory());
        FileBackedTasksManager fileManager = new FileBackedTasksManager(Managers.getDefaultHistory(), "file.csv");
        Task first = new Task("Buy products", "Buy all products from list");
        fileManager.add(first);
        Task second = new Task("invite guests", "Sent messeges to guests");
        fileManager.add(second);
        Epic fEpic = new Epic("Prepare table", "Prepare table for guests");
        fileManager.add(fEpic);
        Subtask tablecloth = new Subtask("Tablecloth", "Cover the table with a tablecloth", fEpic.getId());
        fileManager.add(tablecloth);
        Subtask plate = new Subtask("Plates", "arrange the plates", fEpic.getId());
        fileManager.add(plate);
        Subtask salad = new Subtask("Clear", "Clear room", fEpic.getId());
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

        FileBackedTasksManager fileBackedTasksManager = FileBackedTasksManager.loadFromFile("file.csv");
        System.out.println("\nСостояние нового fileBackedTasksManager после восстановления");
        System.out.println(fileBackedTasksManager.tasks);
        System.out.println(fileBackedTasksManager.epics);
        System.out.println(fileBackedTasksManager.subtasks);
        System.out.println(fileBackedTasksManager.historyManager.getHistory());
        System.out.println("\nСостояние после добавления новой задачи");
        fileBackedTasksManager.add(new Subtask("Salad", "Cook a salad", 7));
        fileBackedTasksManager.getSubtask(8);
        System.out.println(fileBackedTasksManager.tasks);
        System.out.println(fileBackedTasksManager.epics);
        System.out.println(fileBackedTasksManager.subtasks);
        System.out.println(fileBackedTasksManager.historyManager.getHistory());
    }
}
