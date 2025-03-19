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
        Subtask salad = new Subtask("Clear", "Clear room", fEpic.getId());
        manager.add(salad);
        Epic sEpic = new Epic("Cook", "Cook dishes for dinner");
        manager.add(sEpic);


        manager.getEpic(3);
        System.out.println(manager.historyManager.getHistory());
        manager.getTask(2);
        System.out.println(manager.historyManager.getHistory());
        manager.getEpic(3);
        System.out.println(manager.historyManager.getHistory());
        manager.getSubtask(4);
        System.out.println(manager.historyManager.getHistory());
        manager.getSubtask(5);
        System.out.println(manager.historyManager.getHistory());
        manager.getSubtask(6);
        System.out.println(manager.historyManager.getHistory());

        manager.removeEpic(3);

        manager.getTask(1);
        System.out.println(manager.historyManager.getHistory());

    }
}
