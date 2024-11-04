package model.adt;
import exceptions.KeyNotFoundException;

public interface IMyMap<K, V> {
    V get(K key) throws KeyNotFoundException;
    void insert(K key, V value);
    boolean contains(K key);
    void remove(K key) throws KeyNotFoundException;
}
