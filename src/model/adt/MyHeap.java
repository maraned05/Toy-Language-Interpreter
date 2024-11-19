package model.adt;

import exceptions.KeyNotFoundException;
import model.values.IValue;

public class MyHeap<K, V> implements IMyHeap<K, V>{
    private MyMap<Integer, IValue> values;
    private int freeAddr;

    public MyHeap() {
        this.values = new MyMap<Integer, IValue>();
        this.freeAddr = 1;
    }

    public int allocate(IValue val) {
        this.values.insert(this.freeAddr++, val);
        return this.freeAddr - 1;
    }

    public boolean exists(int addr) {
        return this.values.contains(addr);
    }

    public void set(int addr, IValue val) {
        this.values.insert(addr, val);
    }

    public IValue get(int addr) throws KeyNotFoundException {
        return this.values.get(addr);
    }

    public void delete(int addr) throws KeyNotFoundException {
        this.values.remove(addr);
    }

    public IMyMap<Integer, IValue> getValues() {
        return this.values;
    }

    @Override
    public String toString() {
        return "Heap";
    }
}
