package lab.example.model.adt;

import java.util.List;
import java.util.Map;

import javafx.util.Pair;
import lab.example.exceptions.KeyNotFoundException;
import lab.example.model.statements.IStmt;

public interface IProcTable<K, V> {
    public boolean exists(String procName);
    public void set(String procName, Pair<List<String>, IStmt> val);
    public Pair<List<String>, IStmt> get(String procName) throws KeyNotFoundException;
    public Map<String, Pair<List<String>, IStmt>> getContent();
} 
