package lab.example.controller;
import java.util.stream.Collectors;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.List;
import lab.example.exceptions.RepoException;
import lab.example.model.adt.IMyMap;
import lab.example.model.adt.MyMap;
import lab.example.model.state.ProgramState;
import lab.example.model.types.IType;
import lab.example.model.types.RefType;
import lab.example.model.values.IValue;
import lab.example.model.values.RefValue;
import lab.example.repository.IRepository;
import lab.example.repository.Repository;
import java.util.concurrent.*;   

public class Controller implements IController{
    private IRepository repo;
    private boolean displayFlag;
    private ExecutorService executor;

    public Controller(Repository r, boolean flag) {
        this.repo = r;
        this.displayFlag = flag;
    }

    public IRepository getRepository() {
        return this.repo;
    }

    public void setExecutor() {
        this.executor = Executors.newFixedThreadPool(3);
    }

    public void closeExecutor() {
        this.executor.shutdown();
    }

    public void runTypeChecker() throws Exception {
        List<ProgramState> prgList = this.repo.getPrgList();
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

    public void oneStepForAllPrg(List<ProgramState> states) throws Exception, RepoException, InterruptedException {
        List<Callable<ProgramState>> callList = states.stream().map((ProgramState s) -> (Callable<ProgramState>) (() -> {return s.oneStep(); })).collect(Collectors.toList());
        List<ProgramState> newPrgList = this.executor.invokeAll(callList).stream().map(future -> {
            try {
                return future.get();
            }
            catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }  
        }).filter(p -> p != null).collect(Collectors.toList());
        
        states.addAll(newPrgList);

        if (this.displayFlag) {
            for (ProgramState state : states) {
                this.repo.logPrgStateExec(state);
            }
        }

        this.repo.setPrgList(states);
    }

    public void allStep() throws Exception, RepoException, InterruptedException {
        this.setExecutor();
        this.runTypeChecker();
        this.repo.setPrgList(removeCompletedPrg(this.repo.getPrgList()));
        List<ProgramState> prgList = this.repo.getPrgList();

        if (this.displayFlag) {
            for (ProgramState state : prgList)
                this.repo.logPrgStateExec(state);
        }

        while (prgList.size() > 0) {
            List<Integer> symbTables = new ArrayList<Integer>();
            for (ProgramState state : prgList) {
                List<Integer> list = getAddrFromSymTable(state.getSymTable().getContent().values(), state.getHeap().getContent());
                for (Integer elem : list) {
                    if (! symbTables.contains(elem))
                        symbTables.add(elem);
                }
            }

            for (ProgramState state : prgList) 
                state.getHeap().setContent(GarbageCollector(symbTables, state.getHeap().getContent()));  
            
            
            oneStepForAllPrg(prgList);
            prgList = removeCompletedPrg(this.repo.getPrgList());
        }

        this.closeExecutor();
        this.repo.setPrgList(prgList);
    }

    public void displayPrgState(ProgramState state) {
        System.out.println(state.toString() + '\n');
    }

    public void addPrgState(ProgramState state) {
        this.repo.add(state);
    }
}
