package lab.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.util.Pair;

import lab.example.controller.IController;
import lab.example.exceptions.KeyNotFoundException;
import lab.example.exceptions.RepoException;
import lab.example.model.state.ProgramState;
import lab.example.model.statements.IStmt;
import lab.example.model.values.IValue;
import lab.example.model.values.StringValue;
import lab.example.repository.IRepository;

public class Service implements IService {
    private IController controller;

    public Service (IController controller) {
        this.controller = controller;
        this.controller.setExecutor();
    }

    public ProgramState getProgramState (int id) throws KeyNotFoundException {
        for (ProgramState state : this.controller.getRepository().getPrgList()) {
            if (state.getId() == id)
                return state;
        }

        throw new KeyNotFoundException("Invalid id!");
    }

    public List<Integer> getProgramsId() {
        List<Integer> result = new ArrayList<Integer>();
        for (ProgramState state : this.controller.getRepository().getPrgList())
            result.add(state.getId());

        return result;
    }

    public List<Pair<Integer, String>> getHeapTable() {
        List<Pair<Integer, String>> result = new ArrayList<Pair<Integer, String>>();
        Map<Integer, IValue> heap = this.controller.getRepository().getPrgList().get(0).getHeap().getContent();
        for (int key : heap.keySet()) {
            Pair<Integer, String> elem = new Pair<Integer, String>(key, heap.get(key).toString());
            result.add(elem);
        }

        return result;
    }

    public List<Pair<Integer, Pair<Integer, List<Integer>>>> getBarrierTable() {
        List<Pair<Integer, Pair<Integer, List<Integer>>>> result = new ArrayList<Pair<Integer, Pair<Integer, List<Integer>>>>();
        Map<Integer, Pair<Integer, List<Integer>>> barrierTable = this.controller.getRepository().getPrgList().get(0).getBarrierTable().getContent();
        for (int key : barrierTable.keySet()) {
            Pair<Integer, List<Integer>> tableVal = barrierTable.get(key);
            Pair<Integer, Pair<Integer, List<Integer>>> elem = new Pair<Integer,Pair<Integer,List<Integer>>>(key, tableVal);
            result.add(elem);
        }

        return result;
    }

    public List<String> getOutList() {
        List<String> result = new ArrayList<String>();
        for (IValue val : this.controller.getRepository().getPrgList().get(0).getOut().getAll())
            result.add(val.toString());

        return result;
    }
    
    public List<String> getFileTable() {
        List<String> result = new ArrayList<String>();
        for (StringValue val : this.controller.getRepository().getPrgList().get(0).getFileTable().keySet())
            result.add(val.getValue());

        return result;
    }

    public List<String> getExeStack(int id) throws RepoException {
        List<String> result = new ArrayList<String>();
        for (IStmt stmt : this.controller.getRepository().getProgramState(id).getExeStack().getStack())
            result.add(stmt.toString());

        return result;
    }

    public List<Pair<String, String>> getSymbTable(int id) throws RepoException {
        List<Pair<String, String>> result = new ArrayList<Pair<String, String>>();
        Map<String, IValue> table = this.controller.getRepository().getProgramState(id).getSymTable().getContent();
        for (String var : table.keySet()) {
            Pair<String, String> elem = new Pair<String, String>(var, table.get(var).toString());
            result.add(elem);
        }
        
        return result;
    }

    public void runTypeChecker() throws Exception {
        this.controller.runTypeChecker();
    }

    public void oneStepForAllPrg() throws Exception {
        IRepository repository = this.controller.getRepository();
        repository.setPrgList(this.controller.removeCompletedPrg(repository.getPrgList()));
        
        List<ProgramState> states = repository.getPrgList();
        List<Integer> symbTables = new ArrayList<Integer>();
        for (ProgramState state : states) {
            List<Integer> list = this.controller.getAddrFromSymTable(state.getSymTable().getContent().values(), state.getHeap().getContent());
            for (Integer elem : list) {
                if (! symbTables.contains(elem))
                    symbTables.add(elem);
            }
        }

        for (ProgramState state : states) 
            state.getHeap().setContent(this.controller.GarbageCollector(symbTables, state.getHeap().getContent()));  

        this.controller.oneStepForAllPrg(states);
    }

    public int getNoOfPrgStates() {
        return this.controller.getRepository().getPrgList().size();
    }

    public void closeService() {
        this.controller.closeExecutor();
    }
}   


