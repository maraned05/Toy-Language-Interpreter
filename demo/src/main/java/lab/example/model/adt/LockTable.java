package lab.example.model.adt;

import java.util.Map;

import lab.example.exceptions.KeyNotFoundException;

public class LockTable implements ILockTable {
    private MyMap<Integer, Integer> values;
    private int freeAddr;

    public LockTable() {
        this.values = new MyMap<Integer, Integer>();
        this.freeAddr = 1;
    }

    public int createLock() {
        this.values.insert(this.freeAddr++, -1);
        return this.freeAddr - 1;
    }

    public boolean exists(int addr) {
        return this.values.contains(addr);
    }

    public void set(int addr, int progId) {
        this.values.insert(addr, progId);
    }

    public int get(int addr) throws KeyNotFoundException {
        return this.values.get(addr);
    }

    public void delete(int addr) throws KeyNotFoundException {
        this.values.remove(addr);
    }

    public void setContent (Map<Integer, Integer> r) {
        this.values.setContent(r);
    }

    public Map<Integer, Integer> getContent() {
        return this.values.getContent();
    }
 
    public IMyMap<Integer, Integer> getValues() {
        return this.values;
    }

    @Override
    public String toString() {
        return this.values.toString();
    }
}
