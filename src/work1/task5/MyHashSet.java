package work1.task5;

import java.util.*;

public class MyHashSet<E> implements Iterable<E> {
    private final MyHashMap<E, Object> map;
    private static final Object PRESENT = new Object();

    // --- Конструкторы ---
    public MyHashSet() {
        map = new MyHashMap<>();
    }

    public MyHashSet(int expectedSize) {
        map = new MyHashMap<>(expectedSize);
    }

    public MyHashSet(Collection<? extends E> c) {
        map = new MyHashMap<>(c.size());
        addAll(c);
    }

    public MyHashSet(E[] array) {
        if (array == null || array.length == 0) {
            map = new MyHashMap<>();
        } else {
            map = new MyHashMap<>(array.length);
            for (E e : array) add(e);
        }
    }

    // --- Методы ---
    public boolean add(E element) {
        if (map.containsKey(element)) return false;
        map.put(element, PRESENT);
        return true;
    }

    public E remove(E element) {
        if (map.containsKey(element)) {
            map.remove(element);
            return element;
        }
        return null;
    }

    // Исправлено: принимает Collection<? extends E>
    public boolean removeAll(Collection<? extends E> collection) {
        boolean modified = false;
        for (E e : collection) {
            if (map.containsKey(e)) {
                map.remove(e);
                modified = true;
            }
        }
        return modified;
    }

    public boolean addAll(Collection<? extends E> collection) {
        boolean modified = false;
        for (E e : collection) {
            modified |= add(e);
        }
        return modified;
    }

    public int size() {
        return map.size();
    }

    public boolean contains(E element) {
        return map.containsKey(element);
    }

    // 🔥 КЛЮЧЕВОЕ ИСПРАВЛЕНИЕ: принимает Collection<?>
    public boolean containsAll(Collection<?> collection) {
        for (Object o : collection) {
            // Приведение безопасно: внутри используется equals
            if (!contains((E) o)) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return map.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Set)) return false;
        Set<?> other = (Set<?>) obj;
        if (other.size() != size()) return false;
        try {
            // Теперь компилируется: containsAll принимает Collection<?>
            return containsAll(other);
        } catch (ClassCastException | NullPointerException unused) {
            return false;
        }
    }

    @Override
    public String toString() {
        if (size() == 0) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            E e = it.next();
            sb.append(e == this ? "(this Collection)" : e);
            if (it.hasNext()) {
                sb.append(", ");
            }
        }
        return sb.append(']').toString();
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            MyHashMap.Node<E, Object>[] table = map.table;
            int index = 0;
            MyHashMap.Node<E, Object> next = null;

            {
                while (index < table.length && (next = table[index++]) == null);
            }

            @Override
            public boolean hasNext() {
                return next != null;
            }

            @Override
            public E next() {
                if (next == null) throw new NoSuchElementException();
                MyHashMap.Node<E, Object> current = next;
                if ((next = next.next) == null) {
                    while (index < table.length && (next = table[index++]) == null);
                }
                return current.key;
            }
        };
    }
}