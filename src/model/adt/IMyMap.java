package model.adt;
import java.util.Map;

import exceptions.KeyNotFoundException;

public interface IMyMap<K, V> {
    V get(K key) throws KeyNotFoundException;
    void insert(K key, V value);
    boolean contains(K key);
    void remove(K key) throws KeyNotFoundException;
    void setContent(Map<K, V> r);
    Map<K, V> getContent();
}
