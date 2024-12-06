package model.adt;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import exceptions.KeyNotFoundException;

public class MyMap<K, V> implements IMyMap<K, V> {
    private Map<K, V> map;

    public MyMap() {
        this.map = new HashMap<K, V>();
    }

    public Set<Map.Entry<K, V>> entrySet() {
        return this.map.entrySet();
    }

    public V get(K key) throws KeyNotFoundException {
        if (! this.map.containsKey(key))
            throw new KeyNotFoundException("Key not found!");

        return map.get(key);
    }

    public void insert(K key, V value) {
        this.map.put(key, value);
    }

    public void setContent (Map<K, V> r) {
        this.map = r;
    }

    public Map<K, V> getContent() {
        return this.map;
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
            str.append(", ");
        }
        return str.toString();
    }

    public MyMap<K, V> deepCopy() {
        MyMap<K, V> newMap = new MyMap<K, V>();
        newMap.setContent(this.map.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue())));
        return newMap;
    }
}
