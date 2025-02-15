public class Main {
    public static void main(String[] args) {
        Task first = Manager.createTask("Goods", "Buy presents");
        Task second = Manager.createTask("Clear", "Make a clearing up");
        Epic firstEpic = Manager.createEpic("Cook salad", "Cook vegetable salad");
        Subtask fSub = Manager.createSubtask("Prepare", "Boil vegetables", firstEpic);
        Subtask sSub = Manager.createSubtask("Cut", "Cut vegetables", firstEpic);
        Epic secondEpic = Manager.createEpic("Set the table", "Prepare for guests");
        Subtask fSubSecondEpic = Manager.createSubtask("Plates", "Prepare plates for guests", secondEpic);
        System.out.println("Все задачи: "+ Manager.getAllTasks());
        System.out.println("Все эпики: " + Manager.getAllEpic());
        System.out.println("Все подзадачи: " + Manager.getAllSubtasks());
        Manager.removeById(3);
        System.out.println("После удаления:");
        System.out.println("Все задачи: "+ Manager.getAllTasks());
        System.out.println("Все эпики: " + Manager.getAllEpic());
        System.out.println("Все подзадачи: " + Manager.getAllSubtasks());
        System.out.println("обновляем: ");
        Manager.updateTask(first);
        System.out.println("Все задачи: "+ Manager.getAllTasks());

    }
}
