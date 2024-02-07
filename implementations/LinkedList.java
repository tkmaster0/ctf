package implementations;

import interfaces.ListADT;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class LinkedList<T> implements ListADT<T> {

    private Node<T> head;
    private int size;

    public LinkedList() {
        head = null;
        size = 0;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
        Node<T> temp = head;
        head = head.next;
        size--;
        return temp.element;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
        if (size == 1) {
            return removeFirst();
        }
        Node<T> current = head;
        while (current.next.next != null) {
            current = current.next;
        }
        Node<T> temp = current.next;
        current.next = null;
        size--;
        return temp.element;
    }

    @Override
    public T remove(T element) {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
        if (head.element.equals(element)) {
            return removeFirst();
        }
        Node<T> current = head;
        while (current.next != null && !current.next.element.equals(element)) {
            current = current.next;
        }
        if (current.next == null) {
            throw new NoSuchElementException("Element not found");
        }
        Node<T> temp = current.next;
        current.next = current.next.next;
        size--;
        return temp.element;
    }

    @Override
    public T first() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
        return head.element;
    }

    @Override
    public T last() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
        Node<T> current = head;
        while (current.next != null) {
            current = current.next;
        }
        return current.element;
    }

    @Override
    public boolean contains(T target) {
        Node<T> current = head;
        while (current != null) {
            if (current.element.equals(target)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T element = current.element;
                current = current.next;
                return element;
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<T> current = head;
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

    /**
     * Adiciona um elemento ao final da lista.
     *
     * @param element Elemento a ser adicionado.
     */
    public void addLast(T element) {
        Node<T> newNode = new Node<>(element, null);
        if (isEmpty()) {
            head = newNode;
        } else {
            Node<T> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    /**
     * Remove o primeiro elemento que satisfaz o predicado.
     *
     * @param predicate Predicado para testar elementos.
     * @return O elemento removido.
     */
    public T removeIf(Predicate<T> predicate) {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }

        Node<T> current = head;
        Node<T> previous = null;
        while (current != null && !predicate.test(current.element)) {
            previous = current;
            current = current.next;
        }

        if (current == null) {
            throw new NoSuchElementException("Element not found");
        }

        if (previous == null) {
            // O elemento a ser removido é o cabeçalho
            head = head.next;
        } else {
            previous.next = current.next;
        }

        size--;
        return current.element;
    }
}
