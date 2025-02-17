public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();
        Task first = new Task("Buy products", "Buy all products from list");
        Task second = new Task("invite guests", "Sent messeges to guests");
        Epic fEpic = new Epic("Prepare table", "Prepare table for guests");
        Subtask tablecloth = new Subtask("Tablecloth", "Cover the table with a tablecloth", fEpic);
        Subtask plate = new Subtask("Plates", "arrange the plates", fEpic);
        Epic sEpic = new Epic("Cook", "Cook dishes for dinner");
        Subtask salad = new Subtask("Salad", "cut salad ingredients", sEpic);
        manager.add(first);
        manager.add(second);
        manager.add(fEpic);
        manager.add(tablecloth);
        manager.add(plate);
        manager.add(sEpic);
        manager.add(salad);
        System.out.println("All tasks: " + manager.tasks);
        System.out.println("All epics: " + manager.epics);
        System.out.println("All subtasks: " + manager.subtasks);

        Task newTask = new Task("Buy onion", "Buy red onion");
        newTask.setId(first.getId());
        newTask.setStatus(Status.IN_PROGRESS);
        manager.updateTask(newTask);

        Subtask newSubtask = new Subtask("Tablecloth", "Cover the table with a tablecloth", fEpic);
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

        System.out.println("After update: ");
        System.out.println("\nAll tasks: " + manager.tasks);
        System.out.println("All epics: " + manager.epics);
        System.out.println("All subtasks: " + manager.subtasks);
    }
}
