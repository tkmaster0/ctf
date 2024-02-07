package implementations;


public class LinkedStack<T> {
    private static class Node<T> {
        T element;
        Node<T> next;

        public Node(T element, Node<T> next) {
            this.element = element;
            this.next = next;
        }
    }

    private Node<T> top = null;
    private int size = 0;

    public boolean isEmpty() {
        return top == null;
    }

    public void push(T element) {
        top = new Node<>(element, top);
        size++;
    }

    public T pop() {
        if (isEmpty()) throw new java.util.NoSuchElementException("Stack is empty.");
        T element = top.element;
        top = top.next;
        size--;
        return element;
    }

    public T peek() {
        if (isEmpty()) throw new java.util.NoSuchElementException("Stack is empty.");
        return top.element;
    }

    public int size() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node<T> current = top;
        while (current != null) {
            sb.append(current.element.toString());
            if (current.next != null) {
                sb.append(", ");
            }
            current = current.next;
        }
        sb.append("]");
        return sb.toString();
    }
}

