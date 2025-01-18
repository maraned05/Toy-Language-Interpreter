package lab.example.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import lab.example.exceptions.RepoException;
import lab.example.model.state.ProgramState;
import lab.example.model.values.IValue;
import lab.example.repository.IRepository;

public interface IController {
    public void runTypeChecker() throws Exception;
    public IRepository getRepository();
    public void setExecutor();
    public void closeExecutor();
    public Map<Integer, IValue> GarbageCollector(List<Integer> symTblAddr, Map<Integer, IValue> heap);
    public List<Integer> getAddrFromSymTable (Collection<IValue> symTable, Map<Integer, IValue> heap);
    public List<ProgramState> removeCompletedPrg(List<ProgramState> inPrgList);
    public void oneStepForAllPrg(List<ProgramState> states) throws Exception, RepoException, InterruptedException;
    public void allStep() throws Exception, RepoException, InterruptedException;
    public void displayPrgState(ProgramState state);
    public void addPrgState(ProgramState state);
}
