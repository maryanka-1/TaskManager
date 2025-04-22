import java.util.*;


public class InMemoryHistoryManager implements HistoryManager {
    private CustomLinkedList<Task> history = new CustomLinkedList<>();

    @Override
    public void addToHistory(Task task) {
        if (task == null) {
            return;
        }
        history.linkLast(task);
    }

    @Override
    public ArrayList<Task> getHistory() {
        ArrayList<Task> result = new ArrayList<>();
        if (history != null) {
            Node<Task> temp = history.head;
            for (int i = 0; i < history.size; i++) {
                result.add(temp.value);
                temp = temp.next;
            }
        }
        return result;
    }

    @Override
    public void remove(int id) {
        history.removeNode(history.nodeMap.get(id));
    }

    @Override
    public void removeAll() {
        history = null;
    }

    static class CustomLinkedList<T extends Task> {
        private final Map<Integer, Node<T>> nodeMap = new HashMap<>();
        private Node<T> head;
        private Node<T> tail;
        private int size = 0;

        private void linkLast(T element) {
            if (size == 0) {
                head = new Node<>(element, null, null);
                tail = head;
                size++;
                nodeMap.put(element.getId(), head);
            } else {
                Node<T> newNode = new Node<>(element, tail, null);
                tail.next = newNode;
                tail = newNode;
                size++;
                if (nodeMap.containsKey(element.getId())) {
                    removeNode(nodeMap.get(element.getId()));
                }
                nodeMap.put(element.getId(), newNode);
            }
        }

        private void removeNode(Node<T> element) {
            if (element == null) {
                return;
            }
            if (element == head) {
                head = element.next;
                if (head != null) {
                    head.prev = null;
                } else {
                    tail = null;
                }
            } else if (element == tail) {
                tail = element.prev;
                if (tail != null) {
                    tail.next = null;
                } else {
                    head = null;
                }
            } else {
                element.prev.next = element.next;
                element.next.prev = element.prev;
            }
            size--;
            nodeMap.remove(element.value.getId());
        }
    }

    static class Node<T> {
        public T value;
        public Node<T> prev;
        public Node<T> next;

        public Node(T value, Node<T> prev, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
