package implementations;

import interfaces.QueueADT;

/**
 *
 * @author Dinis
 */
public class LinkedQueue<T> implements QueueADT<T> {

    @Override
    public int size() {
        int count = 0;
        Node<T> current = head;
        while (current != null) {
            count++;
            current = current.next;
        }
        return count;
    }

    private static class Node<T> {

        T element;
        Node<T> next;

        public Node(T element, Node<T> next) {
            this.element = element;
            this.next = next;
        }
    }

    private Node<T> head = null;
    private Node<T> tail = null;

    public boolean isEmpty() {
        return head == null;
    }

    public void enqueue(T element) {
        Node<T> newNode = new Node<>(element, null);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
    }

    public T dequeue() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        T element = head.element;
        head = head.next;
        if (head == null) {
            tail = null;
        }
        return element;
    }

    public T first() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        return head.element;
    }
}
