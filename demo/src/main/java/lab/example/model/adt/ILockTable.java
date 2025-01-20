package lab.example.model.adt;

import java.util.Map;

import lab.example.exceptions.KeyNotFoundException;

public interface ILockTable {
    int createLock();
    void delete(int addr) throws KeyNotFoundException;
    boolean exists(int addr);
    void set(int addr, int progId);
    int get(int addr) throws KeyNotFoundException;
    Map<Integer, Integer> getContent();
    void setContent(Map<Integer, Integer> r);
    IMyMap<Integer, Integer> getValues();
}
