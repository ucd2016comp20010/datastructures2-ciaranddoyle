package project20280.hashtable;

import project20280.interfaces.Entry;

import java.util.ArrayList;

public class ProbeHashMap<K, V> extends AbstractHashMap<K, V> {
    private MapEntry<K, V>[] table;
    private final MapEntry<K, V> DEFUNCT = new MapEntry<>(null, null);

    public ProbeHashMap() {
        super();
    }

    /**
     * Creates a hash table with given capacity and prime factor 109345121.
     */
    public ProbeHashMap(int cap) {
        super(cap);
    }

    /**
     * Creates a hash table with the given capacity and prime factor.
     */
    public ProbeHashMap(int cap, int p) {
        super(cap, p);
    }

    @Override
    protected void createTable() {
        table = new MapEntry[capacity];
    }

    int findSlot(int h, K k) {
        int avail = -1;
        int slot = h;
        do {
            if (table[slot] == null) {
                if (avail == -1) avail = slot;
                break;                              // truly empty, stop
            } else if (table[slot] == DEFUNCT) {
                if (avail == -1) avail = slot;      // remember first defunct slot
            } else if (table[slot].getKey().equals(k)) {
                return slot;                        // found the key, return positive
            }
            slot = (slot + 1) % capacity;
        } while (slot != h);
        return -(avail + 1);                        // not found, encode avail slot
    }

    @Override
    protected V bucketGet(int h, K k) {
        int j = findSlot(h, k);
        if (j < 0) return null;
        return table[j].getValue();
    }

    @Override
    protected V bucketPut(int h, K k, V v) {
        int j = findSlot(h, k);
        if (j >= 0) {                          // key already exists, update
            V old = table[j].getValue();
            table[j].setValue(v);
            return old;
        }
        table[-(j + 1)] = new MapEntry<>(k, v); // insert at available slot
        n++;
        return null;
    }

    @Override
    protected V bucketRemove(int h, K k) {
        int j = findSlot(h, k);
        if (j < 0) return null;               // not found
        V old = table[j].getValue();
        table[j] = DEFUNCT;                   // mark as defunct, don't leave null
        n--;
        return old;
    }

    @Override
    public Iterable<Entry<K, V>> entrySet() {
        ArrayList<Entry<K, V>> entries = new ArrayList<>();
        for (int i = 0; i < capacity; i++) {
            if (table[i] != null && table[i] != DEFUNCT)
                entries.add(table[i]);
        }
        return entries;
    }
}
