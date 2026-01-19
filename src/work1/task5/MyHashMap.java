package work1.task5;

import java.util.Map;
import java.util.Objects;

public class MyHashMap<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    // package-private (без модификатора), чтобы MyHashSet видел таблицу
    Node<K, V>[] table;
    private int size;
    private int threshold;
    private final float loadFactor;

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

        public final String toString() { return key + "=" + value; }
        public final int hashCode() { return Objects.hashCode(key) ^ Objects.hashCode(value); }
        public final boolean equals(Object o) {
            if (o == this) return true;
            if (o instanceof Node) {
                Node<?, ?> e = (Node<?, ?>) o;
                return Objects.equals(key, e.key) && Objects.equals(value, e.value);
            }
            return false;
        }
    }

    public MyHashMap() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    @SuppressWarnings("unchecked")
    public MyHashMap(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0) throw new IllegalArgumentException("Illegal capacity: " + initialCapacity);
        if (loadFactor <= 0) throw new IllegalArgumentException("Illegal load factor: " + loadFactor);
        this.loadFactor = loadFactor;
        this.table = new Node[initialCapacity];
        this.threshold = (int) (initialCapacity * loadFactor);
    }

    public MyHashMap(Map<? extends K, ? extends V> m) {
        this();
        putAll(m);
    }

    // --- Исправленные методы (Object вместо K) ---

    // Вспомогательный метод для хэша
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

    public boolean put(K key, V value) {
        if (size >= threshold) resize();
        int hash = hash(key);
        int i = indexFor(hash, table.length);

        for (Node<K, V> e = table[i]; e != null; e = e.next) {
            // Тут важно: Objects.equals принимает Object, так что всё ок
            if (e.hash == hash && Objects.equals(key, e.key)) {
                e.value = value;
                return true;
            }
        }
        Node<K, V> e = table[i];
        table[i] = new Node<>(hash, key, value, e);
        size++;
        return true;
    }

    public boolean putAll(Map<? extends K, ? extends V> map) {
        for (Map.Entry<? extends K, ? extends V> e : map.entrySet()) {
            put(e.getKey(), e.getValue());
        }
        return true;
    }

    // ИСПРАВЛЕНО: принимает Object key
    public V remove(Object key) {
        Node<K, V> e = removeNode(hash(key), key, null, false);
        return e == null ? null : e.value;
    }

    // ИСПРАВЛЕНО: принимает Object key
    public boolean remove(Object key, Object value) {
        return removeNode(hash(key), key, value, true) != null;
    }

    final Node<K, V> removeNode(int hash, Object key, Object value, boolean matchValue) {
        int i = indexFor(hash, table.length);
        Node<K, V> prev = table[i];
        Node<K, V> e = prev;

        while (e != null) {
            Node<K, V> next = e.next;
            if (e.hash == hash && Objects.equals(key, e.key)) {
                if (!matchValue || Objects.equals(value, e.value)) {
                    if (prev == e) table[i] = next;
                    else prev.next = next;
                    size--;
                    return e;
                }
            }
            prev = e;
            e = next;
        }
        return null;
    }

    // ИСПРАВЛЕНО: принимает Object key (для removeAll в HashSet это критично)
    public boolean containsKey(Object key) {
        int hash = hash(key);
        int i = indexFor(hash, table.length);
        for (Node<K, V> e = table[i]; e != null; e = e.next) {
            if (e.hash == hash && Objects.equals(key, e.key)) return true;
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

    // Здесь можно оставить K, так как удаляем ключи из другой мапы
    public boolean removeAll(Map<K, V> map) {
        boolean modified = false;
        for (K key : map.keySet()) {
            if (this.containsKey(key)) {
                this.remove(key);
                modified = true;
            }
        }
        return modified;
    }

    public int size() { return size; }

    public boolean containsValue(Object value) {
        for (Node<K, V> e : table) {
            for (; e != null; e = e.next) {
                if (Objects.equals(value, e.value)) return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        if (size == 0) return "{}";
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        boolean first = true;
        for (Node<K, V> e : table) {
            while (e != null) {
                if (!first) sb.append(", ");
                sb.append(e.key).append('=').append(e.value);
                first = false;
                e = e.next;
            }
        }
        return sb.append('}').toString();
    }

    @Override
    public int hashCode() {
        int h = 0;
        for (Node<K, V> e : table) {
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
        try {
            for (Node<K, V> e : table) {
                while (e != null) {
                    if (!m.containsKey(e.key)) return false;
                    e = e.next;
                }
            }
        } catch (ClassCastException | NullPointerException unused) {
            return false;
        }
        return true;
    }
}