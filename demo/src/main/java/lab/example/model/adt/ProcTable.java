package lab.example.model.adt;

import java.util.List;
import java.util.Map;

import javafx.util.Pair;
import lab.example.exceptions.KeyNotFoundException;
import lab.example.model.statements.IStmt;

public class ProcTable<K, V> implements IProcTable<K, V> {
    private MyMap<String, Pair<List<String>, IStmt>> values;

    public ProcTable() {
        this.values = new MyMap<String, Pair<List<String>, IStmt>> ();
    }

    public boolean exists(String procName) {
        return this.values.contains(procName);
    }

    public void set(String procName, Pair<List<String>, IStmt> val) {
        this.values.insert(procName, val);
    }

    public Pair<List<String>, IStmt> get(String procName) throws KeyNotFoundException {
        return this.values.get(procName);
    }

    public Map<String, Pair<List<String>, IStmt>> getContent() {
        return this.values.getContent();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (String key : this.values.keySet()) {
            Pair<List<String>, IStmt> procStats;
            try {
                procStats = this.values.get(key);
            }
            catch (KeyNotFoundException e) {
                return null;
            }
            List<String> parameters =  procStats.getKey();
            str.append("procedure " + key + " (");
            for (int i = 0; i < parameters.size(); i++) {
                if (i == parameters.size() - 1)
                    str.append(parameters.get(i));

                else str.append(parameters.get(i) + ", ");
            }
            str.append(") " + procStats.getValue().toString() + "\n");
        }
        return str.toString();
    }
}
