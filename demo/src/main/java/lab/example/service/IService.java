package lab.example.service;

import java.util.List;

import javafx.util.Pair;
import lab.example.exceptions.KeyNotFoundException;
import lab.example.exceptions.RepoException;
import lab.example.model.state.ProgramState;

public interface IService {
    public ProgramState getProgramState (int id) throws KeyNotFoundException;
    public List<Integer> getProgramsId();
    public List<Pair<Integer, String>> getHeapTable();
    public List<Pair<Integer, Pair<Integer, List<Integer>>>> getBarrierTable();
    public List<String> getOutList();
    public List<String> getFileTable();
    public List<String> getExeStack(int id) throws RepoException;
    public List<Pair<String, String>> getSymbTable(int id) throws RepoException;
    public void runTypeChecker() throws Exception;
    public void oneStepForAllPrg() throws Exception;
    public int getNoOfPrgStates();
    public void closeService();
}
