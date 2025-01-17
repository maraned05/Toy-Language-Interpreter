package lab.example.model.adt;

import lab.example.exceptions.KeyNotFoundException;
import lab.example.model.values.IValue;
import java.util.Map;

public interface IMyHeap<K, V> {
    int allocate(IValue value);
    void delete(int addr) throws KeyNotFoundException;
    boolean exists(int addr);
    void set(int addr, IValue value);
    IValue get(int addr) throws KeyNotFoundException;
    Map<Integer, IValue> getContent();
    void setContent(Map<Integer, IValue> r);
    IMyMap<Integer, IValue> getValues();
}
