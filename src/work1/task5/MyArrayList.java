package work1.task5;

import java.util.*;

public class MyArrayList<E> implements Iterable<E> {
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] elementData;
    private int size;

    // --- Конструкторы ---
    public MyArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public MyArrayList(int initialCapacity) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Неверный размер: " + initialCapacity);
        this.elementData = new Object[initialCapacity];
        this.size = 0;
    }

    public MyArrayList(Collection<? extends E> c) {
        if (c == null || c.isEmpty()) {
            this.elementData = new Object[DEFAULT_CAPACITY];
            this.size = 0;
        } else {
            Object[] a = c.toArray();
            if (a.getClass() != Object[].class) {
                a = Arrays.copyOf(a, a.length, Object[].class);
            }
            this.elementData = a;
            this.size = a.length;
        }
    }

    @SafeVarargs
    public MyArrayList(E... array) {
        if (array == null || array.length == 0) {
            this.elementData = new Object[DEFAULT_CAPACITY];
            this.size = 0;
        } else {
            this.elementData = Arrays.copyOf(array, array.length);
            this.size = array.length;
        }
    }

    // --- Публичные методы ---
    public boolean add(E element) {
        ensureCapacity(size + 1);
        elementData[size++] = element;
        return true;
    }

    public void add(int index, E element) {
        checkIndexForAdd(index);
        ensureCapacity(size + 1);
        System.arraycopy(elementData, index, elementData, index + 1, size - index);
        elementData[index] = element;
        size++;
    }

    public boolean addAll(Collection<? extends E> collection) {
        if (collection == null || collection.isEmpty()) {
            return false;
        }
        Object[] a = collection.toArray();
        if (a.getClass() != Object[].class) {
            a = Arrays.copyOf(a, a.length, Object[].class);
        }
        int numNew = a.length;
        ensureCapacity(size + numNew);
        System.arraycopy(a, 0, elementData, size, numNew);
        size += numNew;
        return true;
    }

    @SuppressWarnings("unchecked")
    public E remove(int index) {
        checkIndex(index);
        E oldValue = (E) elementData[index];
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        }
        elementData[--size] = null;
        return oldValue;
    }

    public E remove(E element) {
        int index = indexOf(element);
        return index >= 0 ? remove(index) : null;
    }

    public boolean removeAll(Collection<?> collection) {
        boolean modified = false;
        for (Object item : collection) {
            while (contains((E) item)) {
                remove((E) item);
                modified = true;
            }
        }
        return modified;
    }

    public int size() {
        return size;
    }

    public E set(int index, E element) {
        checkIndex(index);
        E old = get(index);
        elementData[index] = element;
        return old;
    }

    @SuppressWarnings("unchecked")
    public E get(int index) {
        checkIndex(index);
        return (E) elementData[index];
    }

    public E getFirst() {
        if (size == 0) throw new NoSuchElementException();
        return get(0);
    }

    public E getLast() {
        if (size == 0) throw new NoSuchElementException();
        return get(size - 1);
    }

    public boolean contains(E element) {
        return indexOf(element) >= 0;
    }

    public boolean containsAll(Collection<?> collection) {
        for (Object e : collection) {
            if (!contains((E) e)) return false;
        }
        return true;
    }

    public int indexOf(E element) {
        if (element == null) {
            for (int i = 0; i < size; i++)
                if (elementData[i] == null) return i;
        } else {
            for (int i = 0; i < size; i++)
                if (element.equals(elementData[i])) return i;
        }
        return -1;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            int cursor = 0;

            @Override
            public boolean hasNext() {
                return cursor != size;
            }

            @SuppressWarnings("unchecked")
            @Override
            public E next() {
                if (cursor >= size) throw new NoSuchElementException();
                return (E) elementData[cursor++];
            }
        };
    }

    @Override
    public int hashCode() {
        int h = 1;
        for (int i = 0; i < size; i++) {
            h = 31 * h + (elementData[i] == null ? 0 : elementData[i].hashCode());
        }
        return h;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof MyArrayList)) return false;
        MyArrayList<?> other = (MyArrayList<?>) obj;
        if (size != other.size) return false;
        for (int i = 0; i < size; i++) {
            if (!Objects.equals(elementData[i], other.elementData[i])) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(", ", "[", "]");
        for (int i = 0; i < size; i++) {
            sj.add(String.valueOf(elementData[i]));
        }
        return sj.toString();
    }

    // --- Приватные методы ---
    private void ensureCapacity(int minCapacity) {
        if (minCapacity <= elementData.length) return;
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity < minCapacity) newCapacity = minCapacity;
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    private void checkIndexForAdd(int index) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }
}