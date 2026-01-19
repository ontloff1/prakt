package work1.task5;

import java.util.*;

// Реализуем Iterable, чтобы можно было использовать в цикле for-each
public class MyArrayList<E> implements Iterable<E> {
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] elementData;
    private int size;

    // --- 2) Конструкторы ---
    public MyArrayList() {
        this.elementData = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    public MyArrayList(int initialCapacity) {
        if (initialCapacity < 0) throw new IllegalArgumentException("Неверный размер: " + initialCapacity);
        this.elementData = new Object[initialCapacity];
        this.size = 0;
    }

    public MyArrayList(Collection<? extends E> c) {
        elementData = c.toArray();
        size = elementData.length;
        // Защита от особенностей toArray некоторых коллекций
        if (elementData.getClass() != Object[].class) {
            elementData = Arrays.copyOf(elementData, size, Object[].class);
        }
    }

    public MyArrayList(E[] array) {
        this.elementData = Arrays.copyOf(array, array.length);
        this.size = array.length;
    }

    // --- 7) Логика расширения (x1.5) ---
    private void ensureCapacity(int minCapacity) {
        if (minCapacity - elementData.length > 0) {
            int oldCapacity = elementData.length;
            // Увеличение в 1.5 раза: old + old / 2
            int newCapacity = oldCapacity + (oldCapacity >> 1);
            if (newCapacity - minCapacity < 0) newCapacity = minCapacity;
            elementData = Arrays.copyOf(elementData, newCapacity);
        }
    }

    // --- 4) Реализация методов ---

    public boolean add(E element) {
        ensureCapacity(size + 1);
        elementData[size++] = element;
        return true;
    }

    public boolean add(int index, E element) {
        checkIndexForAdd(index);
        ensureCapacity(size + 1);
        System.arraycopy(elementData, index, elementData, index + 1, size - index);
        elementData[index] = element;
        size++;
        return true;
    }

    public boolean addAll(Collection<? extends E> collection) {
        Object[] a = collection.toArray();
        int numNew = a.length;
        ensureCapacity(size + numNew);
        System.arraycopy(a, 0, elementData, size, numNew);
        size += numNew;
        return numNew != 0;
    }

    @SuppressWarnings("unchecked")
    public E remove(int index) {
        checkIndex(index);
        E oldValue = (E) elementData[index];
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        }
        elementData[--size] = null; // Помогаем сборщику мусора (GC)
        return oldValue;
    }

    // По заданию должен возвращать E (удаленный элемент), а не boolean
    @SuppressWarnings("unchecked")
    public E remove(E element) {
        if (element == null) {
            for (int i = 0; i < size; i++) {
                if (elementData[i] == null) return remove(i);
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (element.equals(elementData[i])) return remove(i);
            }
        }
        return null;
    }

    public boolean removeAll(Collection<?> collection) {
        boolean modified = false;
        for (Object item : collection) {
            // Удаляем пока есть вхождения (если дубликаты)
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

    public boolean set(int index, E element) {
        checkIndex(index);
        elementData[index] = element;
        return true;
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

    public boolean containsAll(Collection<E> collection) {
        for (E e : collection) {
            if (!contains(e)) return false;
        }
        return true;
    }

    // Вспомогательный метод для поиска индекса
    private int indexOf(E element) {
        if (element == null) {
            for (int i = 0; i < size; i++)
                if (elementData[i] == null) return i;
        } else {
            for (int i = 0; i < size; i++)
                if (element.equals(elementData[i])) return i;
        }
        return -1;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    private void checkIndexForAdd(int index) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    // --- 3) Iterable ---
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

    // --- 6) hashCode и equals ---
    @Override
    public int hashCode() {
        int hashCode = 1;
        for (int i = 0; i < size; i++) {
            E e = get(i);
            hashCode = 31 * hashCode + (e == null ? 0 : e.hashCode());
        }
        return hashCode;
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
        if (size == 0) return "[]";
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < size; i++) {
            sb.append(elementData[i]);
            if (i == size - 1) return sb.append(']').toString();
            sb.append(", ");
        }
        return sb.toString();
    }
}