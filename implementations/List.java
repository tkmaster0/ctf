/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package implementations;

/**
 *
 * @author Dinis
 */
import java.util.Iterator;
import java.util.NoSuchElementException;
import interfaces.ListADT;

public class List<T> implements ListADT<T> {
    private final static int DEFAULT_CAPACITY = 10;
    private int rear;
    private T[] list;

    // Construtor
    @SuppressWarnings("unchecked")
    public List() {
        rear = 0;
        list = (T[]) new Object[DEFAULT_CAPACITY];
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
        T result = list[0];
        rear--;
        // Shift elements
        for (int i = 0; i < rear; i++) {
            list[i] = list[i + 1];
        }
        list[rear] = null; // Help garbage collection
        return result;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
        T result = list[rear - 1];
        list[rear - 1] = null; // Help garbage collection
        rear--;
        return result;
    }

    @Override
    public T remove(T element) {
        int index = find(element);
        if (index == -1) {
            throw new NoSuchElementException("Element not found");
        }
        T result = list[index];
        for (int i = index; i < rear - 1; i++) {
            list[i] = list[i + 1];
        }
        list[rear - 1] = null; // Help garbage collection
        rear--;
        return result;
    }

    @Override
    public T first() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
        return list[0];
    }

    @Override
    public T last() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
        return list[rear - 1];
    }

    @Override
    public boolean contains(T target) {
        return find(target) != -1;
    }

    private int find(T target) {
        for (int i = 0; i < rear; i++) {
            if (list[i].equals(target)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return rear == 0;
    }

    @Override
    public int size() {
        return rear;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < rear && list[currentIndex] != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return list[currentIndex++];
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < rear; i++) {
            sb.append(list[i]);
            if (i < rear - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}

