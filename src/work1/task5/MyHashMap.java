package work1.task5;

import java.util.*;
import java.util.Objects;

public class MyHashMap<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    Node<K, V>[] table;
    private int size;
    private int threshold;
    private final float loadFactor;

    public MyHashMap() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap(int expectedSize) {
        this((int) (expectedSize / DEFAULT_LOAD_FACTOR) + 1, DEFAULT_LOAD_FACTOR);
    }

    @SuppressWarnings("unchecked")
    public MyHashMap(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal capacity: " + initialCapacity);
        if (loadFactor <= 0)
            throw new IllegalArgumentException("Illegal load factor: " + loadFactor);
        this.loadFactor = loadFactor;
        this.table = new Node[initialCapacity];
        this.threshold = (int) (initialCapacity * loadFactor);
    }

    public MyHashMap(Map<? extends K, ? extends V> m) {
        this(m.size());
        putAll(m);
    }

    // --- Публичные методы ---
    public boolean put(K key, V value) {
        if (size >= threshold) resize();
        int hash = hash(key);
        int i = indexFor(hash, table.length);

        for (Node<K, V> e = table[i]; e != null; e = e.next) {
            if (e.hash == hash && Objects.equals(key, e.key)) {
                e.value = value;
                return true;
            }
        }
        Node<K, V> newNode = new Node<>(hash, key, value, table[i]);
        table[i] = newNode;
        size++;
        return true;
    }

    public V get(Object key) {
        int hash = hash(key);
        int i = indexFor(hash, table.length);
        for (Node<K, V> e = table[i]; e != null; e = e.next) {
            if (e.hash == hash && Objects.equals(key, e.key)) {
                return e.value;
            }
        }
        return null;
    }

    public boolean putAll(Map<? extends K, ? extends V> map) {
        for (Map.Entry<? extends K, ? extends V> e : map.entrySet()) {
            put(e.getKey(), e.getValue());
        }
        return true;
    }

    public V remove(Object key) {
        Node<K, V> e = removeNode(hash(key), key, null, false);
        return e == null ? null : e.value;
    }

    public boolean remove(Object key, Object value) {
        return removeNode(hash(key), key, value, true) != null;
    }

    public boolean containsKey(Object key) {
        int hash = hash(key);
        int i = indexFor(hash, table.length);
        for (Node<K, V> e = table[i]; e != null; e = e.next) {
            if (e.hash == hash && Objects.equals(key, e.key)) return true;
        }
        return false;
    }

    public boolean containsValue(Object value) {
        for (Node<K, V> bucket : table) {
            Node<K, V> e = bucket;
            while (e != null) {
                if (Objects.equals(value, e.value)) return true;
                e = e.next;
            }
        }
        return false;
    }

    public boolean replace(K key, V value) {
        int hash = hash(key);
        int i = indexFor(hash, table.length);
        for (Node<K, V> e = table[i]; e != null; e = e.next) {
            if (e.hash == hash && Objects.equals(key, e.key)) {
                e.value = value;
                return true;
            }
        }
        return false;
    }

    public boolean removeAll(Map<K, V> map) {
        boolean modified = false;
        for (K key : map.keySet()) {
            if (containsKey(key)) {
                remove(key);
                modified = true;
            }
        }
        return modified;
    }

    public int size() {
        return size;
    }

    @Override
    public String toString() {
        if (size == 0) return "{}";
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        boolean first = true;
        for (Node<K, V> bucket : table) {
            Node<K, V> e = bucket;
            while (e != null) {
                if (!first) sb.append(", ");
                sb.append(e.key);
                sb.append('=');
                sb.append(e.value);
                first = false;
                e = e.next;
            }
        }
        return sb.append('}').toString();
    }

    @Override
    public int hashCode() {
        int h = 0;
        for (Node<K, V> bucket : table) {
            Node<K, V> e = bucket;
            while (e != null) {
                h += e.hashCode();
                e = e.next;
            }
        }
        return h;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof MyHashMap)) return false;
        MyHashMap<?, ?> m = (MyHashMap<?, ?>) obj;
        if (m.size() != size()) return false;
        for (Node<K, V> bucket : table) {
            Node<K, V> e = bucket;
            while (e != null) {
                V value = e.value;
                Object otherValue = m.get(e.key);
                if (otherValue == null ? value != null : !otherValue.equals(value)) {
                    return false;
                }
                e = e.next;
            }
        }
        return true;
    }

    // --- Приватные методы ---
    private int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    private int indexFor(int h, int length) {
        return h & (length - 1);
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        int oldCapacity = table.length;
        int newCapacity = oldCapacity * 2;
        Node<K, V>[] newTable = new Node[newCapacity];

        for (Node<K, V> e : table) {
            while (e != null) {
                Node<K, V> next = e.next;
                int i = indexFor(e.hash, newCapacity);
                e.next = newTable[i];
                newTable[i] = e;
                e = next;
            }
        }
        table = newTable;
        threshold = (int) (newCapacity * loadFactor);
    }

    final Node<K, V> removeNode(int hash, Object key, Object value, boolean matchValue) {
        int i = indexFor(hash, table.length);
        Node<K, V> prev = null;
        Node<K, V> e = table[i];

        while (e != null) {
            Node<K, V> next = e.next;
            if (e.hash == hash && Objects.equals(key, e.key)) {
                if (!matchValue || Objects.equals(value, e.value)) {
                    if (prev == null) {
                        table[i] = next;
                    } else {
                        prev.next = next;
                    }
                    size--;
                    return e;
                }
            }
            prev = e;
            e = next;
        }
        return null;
    }

    // --- Вложенный класс в конце ---
    static class Node<K, V> {
        final int hash;
        final K key;
        V value;
        Node<K, V> next;

        Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public final String toString() {
            return key + "=" + value;
        }

        public final int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }

        public final boolean equals(Object o) {
            if (o == this) return true;
            if (o instanceof Node) {
                Node<?, ?> e = (Node<?, ?>) o;
                return Objects.equals(key, e.key) && Objects.equals(value, e.value);
            }
            return false;
        }
    }
}