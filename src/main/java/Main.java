public class Main {
    public static void main(String[] args) {

        InMemoryTaskManager manager = new InMemoryTaskManager(Managers.getDefaultHistory());
        Task first = new Task("Buy products", "Buy all products from list");
        manager.add(first);
        Task second = new Task("invite guests", "Sent messeges to guests");
        manager.add(second);
        Epic fEpic = new Epic("Prepare table", "Prepare table for guests");
        manager.add(fEpic);
        Subtask tablecloth = new Subtask("Tablecloth", "Cover the table with a tablecloth", fEpic.getId());
        manager.add(tablecloth);
        Subtask plate = new Subtask("Plates", "arrange the plates", fEpic.getId());
        manager.add(plate);
        Epic sEpic = new Epic("Cook", "Cook dishes for dinner");
        manager.add(sEpic);
        Subtask salad = new Subtask("Salad", "cut salad ingredients", sEpic.getId());
        manager.add(salad);

        System.out.println("All tasks: " + manager.tasks);
        System.out.println("All epics: " + manager.epics);
        System.out.println("All subtasks: " + manager.subtasks);

        Task newTask = new Task("Buy onion", "Buy red onion");
        newTask.setId(first.getId());
        newTask.setStatus(Status.IN_PROGRESS);
        manager.updateTask(newTask);

        Subtask newSubtask = new Subtask("Tablecloth", "Cover the table with a tablecloth", fEpic.getId());
        newSubtask.setId(tablecloth.getId());
        newSubtask.setStatus(Status.DONE);
        manager.updateTask(newSubtask);

        Subtask next = plate;
        next.setStatus(Status.DONE);
        manager.updateTask(next);

        System.out.println("After update: ");
        System.out.println("\nAll tasks: " + manager.tasks);
        System.out.println("All epics: " + manager.epics);
        System.out.println("All subtasks: " + manager.subtasks);

        manager.removeTask(first.getId());
        manager.removeEpic(fEpic.getId());
        manager.getEpic(6);
        System.out.println(manager.historyManager.getHistory());
        manager.getTask(2);
        System.out.println(manager.historyManager.getHistory());
        manager.getSubtask(7);
        System.out.println(manager.historyManager.getHistory());
        manager.getTask(2);
        System.out.println(manager.historyManager.getHistory());
        manager.getTask(2);
        System.out.println(manager.historyManager.getHistory());
        manager.getTask(7);
        System.out.println(manager.historyManager.getHistory());
        manager.getTask(2);
        System.out.println(manager.historyManager.getHistory());
        manager.getTask(2);
        System.out.println(manager.historyManager.getHistory());
        manager.getTask(6);
        System.out.println(manager.historyManager.getHistory());
        manager.getTask(2);
        System.out.println(manager.historyManager.getHistory());
        manager.getSubtask(7);
        System.out.println("After update: ");
        System.out.println("\nAll tasks: " + manager.tasks);
        System.out.println("All epics: " + manager.epics);
        System.out.println("All subtasks: " + manager.subtasks);
        System.out.println(manager.historyManager.getHistory());

    }
}
