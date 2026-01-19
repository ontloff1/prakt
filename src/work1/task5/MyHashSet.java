package work1.task5;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyHashSet<E> implements Iterable<E> {
    private final MyHashMap<E, Object> map;
    private static final Object PRESENT = new Object();

    // --- 2) Конструкторы ---
    public MyHashSet() {
        map = new MyHashMap<>();
    }

    public MyHashSet(int initialCapacity) {
        map = new MyHashMap<>(initialCapacity);
    }

    public MyHashSet(Collection<? extends E> c) {
        map = new MyHashMap<>(Math.max((int) (c.size() / .75f) + 1, 16));
        addAll(c);
    }

    public MyHashSet(E[] array) {
        this(Math.max((int) (array.length / .75f) + 1, 16));
        for (E e : array) add(e);
    }

    // --- 4) Методы ---
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

    public boolean removeAll(Collection<?> collection) {
        boolean modified = false;
        for (Object e : collection) {
            if (map.containsKey((E) e)) {
                map.remove((E) e);
                modified = true;
            }
        }
        return modified;
    }

    public boolean addAll(Collection<? extends E> collection) {
        boolean modified = false;
        for (E e : collection) {
            // Эквивалентно: modified = modified | add(e);
            // Если add(e) вернет true хотя бы раз, modified навсегда станет true
            modified |= add(e);
        }
        return modified;
    }

    public int size() { return map.size(); }
    public boolean contains(E element) { return map.containsKey(element); }

    public boolean containsAll(Collection<?> collection) {
        for (Object e : collection) {
            if (!contains((E) e)) return false;
        }
        return true;
    }

    // --- 6) HashCode & Equals ---
    @Override
    public int hashCode() { return map.hashCode(); }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof MyHashSet)) return false;
        MyHashSet<?> other = (MyHashSet<?>) obj;
        if (other.size() != size()) return false;
        return containsAll((Collection<?>) obj); // Упрощено
    }

    // ИСПРАВЛЕНИЕ: Используем StringBuilder для эффективной сборки строки
    @Override
    public String toString() {
        // Если пусто — сразу возвращаем скобки
        if (size() == 0) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append('[');

        // Используем наш итератор, чтобы пройти по элементам
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            E e = it.next();
            // Добавляем элемент (если элемент — это сама коллекция, пишем спец. текст, чтобы не было рекурсии)
            sb.append(e == this ? "(this Collection)" : e);

            // Если есть следующий элемент, добавляем запятую
            if (it.hasNext()) {
                sb.append(", ");
            }
        }

        // Закрываем скобку и превращаем в строку
        return sb.append(']').toString();
    }

    // --- 3) Iterable ---
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            MyHashMap.Node<E, Object>[] table = map.table;
            int index = 0;
            MyHashMap.Node<E, Object> current = null;
            MyHashMap.Node<E, Object> next = null;

            {
                // Ищем первый не null узел
                while (index < table.length && (next = table[index++]) == null);
            }

            public boolean hasNext() { return next != null; }

            public E next() {
                if (next == null) throw new NoSuchElementException();
                current = next;

                // Ищем следующий
                if ((next = next.next) == null) {
                    while (index < table.length && (next = table[index++]) == null);
                }
                return current.key;
            }
        };
    }
}