package lab.example.model.adt;
import java.util.Map;
import java.util.Set;

import lab.example.exceptions.KeyNotFoundException;

public interface IMyMap<K, V> {
    Set<K> keySet();
    Set<Map.Entry<K, V>> entrySet();
    V get(K key) throws KeyNotFoundException;
    void insert(K key, V value);
    boolean contains(K key);
    void remove(K key) throws KeyNotFoundException;
    void setContent(Map<K, V> r);
    Map<K, V> getContent();
    IMyMap<K, V> deepCopy();
}
