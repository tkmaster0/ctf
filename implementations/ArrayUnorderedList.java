package implementations;

import interfaces.UnorderedListADT;


public class ArrayUnorderedList<T> implements UnorderedListADT<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private T[] array;
    private int size;

    @SuppressWarnings("unchecked")
    public ArrayUnorderedList() {
        array = (T[]) new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    @Override
    public void addToFront(T element) {
        if (size == array.length) resize();
        for (int i = size; i > 0; i--) {
            array[i] = array[i - 1];
        }
        array[0] = element;
        size++;
    }

    @Override
    public void addToRear(T element) {
        add(element); // This method already appends to the end of the list.
    }
    
    public void add(T element) {
        if (size == array.length) {
            resize();
        }
        array[size++] = element;
    }    

    @Override
    public void addAfter(T element, T target) {
        if (size == array.length) resize();
        int targetIndex = -1;
        for (int i = 0; i < size; i++) {
            if (array[i].equals(target)) {
                targetIndex = i;
                break;
            }
        }
        if (targetIndex == -1) throw new RuntimeException("Target element not found.");

        for (int i = size; i > targetIndex + 1; i--) {
            array[i] = array[i - 1];
        }
        array[targetIndex + 1] = element;
        size++;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) throw new RuntimeException("List is empty.");
        return remove(array[0]);
    }

    @Override
    public T removeLast() {
        if (isEmpty()) throw new RuntimeException("List is empty.");
        return remove(array[size - 1]);
    }

    @Override
    public T remove(T element) {
        for (int i = 0; i < size; i++) {
            if (array[i].equals(element)) {
                T removed = array[i];
                for (int j = i; j < size - 1; j++) {
                    array[j] = array[j + 1];
                }
                array[size - 1] = null;
                size--;
                return removed;
            }
        }
        throw new RuntimeException("Element not found.");
    }

    @Override
    public T first() {
        if (isEmpty()) throw new RuntimeException("List is empty.");
        return array[0];
    }

    @Override
    public T last() {
        if (isEmpty()) throw new RuntimeException("List is empty.");
        return array[size - 1];
    }

    @Override
    public boolean contains(T target) {
        for (int i = 0; i < size; i++) {
            if (array[i].equals(target)) {
                return true;
            }
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
    public java.util.Iterator<T> iterator() {
        return new java.util.Iterator<T>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size;
            }

            @Override
            public T next() {
                if (!hasNext()) throw new java.util.NoSuchElementException();
                return array[currentIndex++];
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(array[i]);
            if (i < size - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    private void resize() {
        @SuppressWarnings("unchecked")
        T[] newArray = (T[]) new Object[array.length * 2];
        System.arraycopy(array, 0, newArray, 0, array.length);
        array = newArray;
    }
}


