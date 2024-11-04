package model.adt;
import java.util.HashMap;
import java.util.Map;
import exceptions.KeyNotFoundException;

public class MyMap<K, V> implements IMyMap<K, V> {
    private Map<K, V> map;

    public MyMap() {
        this.map = new HashMap<K, V>();
    }

    public V get(K key) throws KeyNotFoundException {
        if (! this.map.containsKey(key))
            throw new KeyNotFoundException("Key not found!");

        return map.get(key);
    }

    public void insert(K key, V value) {
        this.map.put(key, value);
    }

    public boolean contains(K key) {
        return this.map.containsKey(key);
    }

    public void remove(K key) throws KeyNotFoundException {
        if (! this.map.containsKey(key))
            throw new KeyNotFoundException("Key not found!");

        this.map.remove(key);
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        for (K key : map.keySet()) {
            str.append(key.toString()).append(" -> ").append(map.get(key));
            str.append(" ");
        }
        return str.toString();
    }
}
