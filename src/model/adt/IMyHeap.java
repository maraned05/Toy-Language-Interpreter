package model.adt;

import exceptions.KeyNotFoundException;
import model.values.IValue;

public interface IMyHeap<K, V> {
    int allocate(IValue value);
    void delete(int addr) throws KeyNotFoundException;
    boolean exists(int addr);
    void set(int addr, IValue value);
    IValue get(int addr) throws KeyNotFoundException;
    IMyMap<Integer, IValue> getValues();
}
