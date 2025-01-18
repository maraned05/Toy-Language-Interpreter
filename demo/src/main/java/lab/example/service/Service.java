package lab.example.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javafx.util.Pair;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import lab.example.exceptions.KeyNotFoundException;
import lab.example.exceptions.RepoException;
import lab.example.model.adt.IMyMap;
import lab.example.model.adt.MyMap;
import lab.example.model.state.ProgramState;
import lab.example.model.statements.IStmt;
import lab.example.model.types.IType;
import lab.example.model.types.RefType;
import lab.example.model.values.IValue;
import lab.example.model.values.RefValue;
import lab.example.model.values.StringValue;
import lab.example.repository.IRepository;

public class Service implements IService {
    private IRepository repository;
    private ExecutorService executor;

    public Service (IRepository repo) {
        this.repository = repo;
        this.executor = Executors.newFixedThreadPool(3);
    }

    public ProgramState getProgramState (int id) throws KeyNotFoundException {
        for (ProgramState state : this.repository.getPrgList()) {
            if (state.getId() == id)
                return state;
        }

        throw new KeyNotFoundException("Invalid id!");
    }

    public List<Integer> getProgramsId() {
        List<Integer> result = new ArrayList<Integer>();
        for (ProgramState state : this.repository.getPrgList())
            result.add(state.getId());

        return result;
    }

    public List<Pair<Integer, String>> getHeapTable() {
        List<Pair<Integer, String>> result = new ArrayList<Pair<Integer, String>>();
        Map<Integer, IValue> heap = this.repository.getPrgList().get(0).getHeap().getContent();
        for (int key : heap.keySet()) {
            Pair<Integer, String> elem = new Pair<Integer, String>(key, heap.get(key).toString());
            result.add(elem);
        }

        return result;
    }

    public List<String> getOutList() {
        List<String> result = new ArrayList<String>();
        for (IValue val : this.repository.getPrgList().get(0).getOut().getAll())
            result.add(val.toString());

        return result;
    }
    
    public List<String> getFileTable() {
        List<String> result = new ArrayList<String>();
        for (StringValue val : this.repository.getPrgList().get(0).getFileTable().keySet())
            result.add(val.getValue());

        return result;
    }

    public List<String> getExeStack(int id) throws RepoException {
        List<String> result = new ArrayList<String>();
        for (IStmt stmt : this.repository.getProgramState(id).getExeStack().getStack())
            result.add(stmt.toString());

        return result;
    }

    public List<Pair<String, String>> getSymbTable(int id) throws RepoException {
        List<Pair<String, String>> result = new ArrayList<Pair<String, String>>();
        Map<String, IValue> table = this.repository.getProgramState(id).getSymTable().getContent();
        for (String var : table.keySet()) {
            Pair<String, String> elem = new Pair<String, String>(var, table.get(var).toString());
            result.add(elem);
        }
        
        return result;
    }

    public void runTypeChecker() throws Exception {
        List<ProgramState> prgList = this.repository.getPrgList();
        for (ProgramState state : prgList) {
            IMyMap<String, IType> typeEnv = new MyMap<>();
            if (!state.getExeStack().isEmpty())
                state.getExeStack().peek().typeCheck(typeEnv);
        }
    }

    public Map<Integer, IValue> GarbageCollector(List<Integer> symTblAddr, Map<Integer, IValue> heap) {
        return heap.entrySet().stream().filter(e -> symTblAddr.contains(e.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public List<Integer> getAddrFromSymTable (Collection<IValue> symTable, Map<Integer, IValue> heap) {
        List<Integer> result = new ArrayList<Integer>();
        for (IValue v : symTable) {
            if (v instanceof RefValue) {
                RefValue v1 = (RefValue) v;
                if (v1.getAddress() != 0) {
                    result.add(v1.getAddress());

                    while (v1.getLocationType() instanceof RefType) {   
                        v1 = (RefValue) heap.get(v1.getAddress());
                        result.add(v1.getAddress());
                    }
                }
            }
        }
        return result;
    }

    public List<ProgramState> removeCompletedPrg(List<ProgramState> inPrgList) {
        return inPrgList.stream().filter(p -> p.isNotCompleted()).collect(Collectors.toList());
    }

    public void oneStepForAllPrg() throws Exception {
        this.repository.setPrgList(removeCompletedPrg(this.repository.getPrgList()));
        if (this.getNoOfPrgStates() == 0) {
            this.closeService();
            return;
        }

        List<ProgramState> states = this.repository.getPrgList();
        List<Integer> symbTables = new ArrayList<Integer>();
        for (ProgramState state : states) {
            List<Integer> list = getAddrFromSymTable(state.getSymTable().getContent().values(), state.getHeap().getContent());
            for (Integer elem : list) {
                if (! symbTables.contains(elem))
                    symbTables.add(elem);
            }
        }

        for (ProgramState state : states) 
            state.getHeap().setContent(GarbageCollector(symbTables, state.getHeap().getContent()));  

        List<Callable<ProgramState>> callList = states.stream().map((ProgramState s) -> (Callable<ProgramState>) (() -> {return s.oneStep(); })).collect(Collectors.toList());
        List<ProgramState> newPrgList = this.executor.invokeAll(callList).stream().map(future -> {
            try {
                return future.get();
            }
            catch (Exception e) {
               System.err.println(e.getMessage()); 
               return null;
            }  
        }).filter(p -> p != null).collect(Collectors.toList());
        
        states.addAll(newPrgList);
        //this.repository.setPrgList(removeCompletedPrg(states));
    }

    public int getNoOfPrgStates() {
        return this.repository.getPrgList().size();
    }

    public void closeService() {
        this.executor.shutdownNow();
    }
}   


