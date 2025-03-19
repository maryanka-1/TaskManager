import java.util.*;


public class InMemoryHistoryManager implements HistoryManager {
    private CustomLinkedList<Task> history = new CustomLinkedList<>();
    private Map<Integer, Node<Task>> checkUnique = new HashMap<>();

//    private static final int SIZE = 10;

    @Override
    public void addToHistory(Task task) {
        if (task == null) {
            return;
        }
        if (!checkUnique.containsKey(task.getId())) {
            checkUnique.put(task.getId(), history.linkLast(task));
        } else {
            history.removeCLL(checkUnique.get(task.getId()));
            checkUnique.put(task.getId(), history.linkLast(task));
        }
    }

    @Override
    public ArrayList<Task> getHistory() {
        ArrayList<Task> result = new ArrayList<>();
        Node<Task> temp = history.head;
        for (int i = 0; i < history.size; i++) {
            result.add(temp.value);
            temp = temp.next;
        }
        return result;
    }

    @Override
    public void remove(int id) {
        if (checkUnique.containsKey(id)) {
            history.removeCLL(checkUnique.get(id));
            checkUnique.remove(id);
        }
    }

    @Override
    public void removeAll() {
        history = null;
        checkUnique = null;

    }

    public class CustomLinkedList<T> {
        private Node<T> head;
        private Node<T> tail;
        private int size = 0;

        private Node<T> linkLast(T element) {
            if (size == 0) {
                head = new Node<>(element, null, null);
                tail = head;
                size++;
                return head;
            } else {
                Node<T> newNode = new Node<>(element, tail, null);
                tail.next = newNode;
                tail = newNode;
                size++;
                return newNode;
            }
        }

        private void removeCLL(Node<T> element) {
            if (element == head) {
                head = element.next;
                element.next.prev = null;
                size--;
            } else if(element == tail){
                element.prev.next = null;
                tail = element.prev;
                size--;
            }
            else{
                element.prev.next = element.next;
                element.next.prev = element.prev;
                size--;
            }
        }

    }

}
