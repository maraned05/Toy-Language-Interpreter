package lab.example.model.adt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.util.Pair;
import lab.example.exceptions.KeyNotFoundException;

public class BarrierTable implements IBarrierTable {
    private MyMap<Integer, Pair<Integer, List<Integer>>> values;
    private int freeAddr;

    public BarrierTable() {
        this.values = new MyMap<Integer, Pair<Integer, List<Integer>>>();
        this.freeAddr = 1;
    }

    public int createBarrier (int nr) {
        this.values.insert(this.freeAddr++, new Pair<Integer, List<Integer>>(nr, new ArrayList<Integer>()));
        return this.freeAddr - 1;
    }

    public boolean exists(int addr) {
        return this.values.contains(addr);
    }

    public void set(int addr, Pair<Integer, List<Integer>> barrier) {
        this.values.insert(addr, barrier);
    }

    public Pair<Integer, List<Integer>> get(int addr) throws KeyNotFoundException {
        return this.values.get(addr);
    }

    public void delete(int addr) throws KeyNotFoundException {
        this.values.remove(addr);
    }

    public void setContent (Map<Integer, Pair<Integer, List<Integer>>> r) {
        this.values.setContent(r);
    }

    public Map<Integer, Pair<Integer, List<Integer>>> getContent() {
        return this.values.getContent();
    }
 
    public IMyMap<Integer, Pair<Integer, List<Integer>>> getValues() {
        return this.values;
    }

    @Override
    public String toString() {
        return this.values.toString();
    }
}
