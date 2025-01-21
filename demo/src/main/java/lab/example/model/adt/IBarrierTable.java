package lab.example.model.adt;

import java.util.List;
import java.util.Map;

import javafx.util.Pair;
import lab.example.exceptions.KeyNotFoundException;

public interface IBarrierTable {
    int createLock(int nr);
    void delete(int addr) throws KeyNotFoundException;
    boolean exists(int addr);
    void set(int addr, Pair<Integer, List<Integer>> barrier);
    Pair<Integer, List<Integer>> get(int addr) throws KeyNotFoundException;
    Map<Integer, Pair<Integer, List<Integer>>> getContent();
    void setContent (Map<Integer, Pair<Integer, List<Integer>>> r);
    IMyMap<Integer, Pair<Integer, List<Integer>>> getValues();
}
