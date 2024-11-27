package controller;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.List;
import exceptions.EmptyStackException;
import exceptions.ExpressionException;
import exceptions.RepoException;
import exceptions.StatementException;
import model.adt.IMyStack;
import model.state.ProgramState;
import model.statements.IStmt;
import model.types.RefType;
import model.values.IValue;
import model.values.RefValue;
import repository.IRepository;
import repository.Repository;

public class Controller implements IController{
    private IRepository repo;
    private boolean displayFlag;

    public Controller(Repository r, boolean flag) {
        this.repo = r;
        this.displayFlag = flag;
    }

    public Map<Integer, IValue> GarbageCollector(List<Integer> symTblAddr, Map<Integer, IValue> heap) {
        return heap.entrySet().stream().filter(e -> symTblAddr.contains(e.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public List<Integer> getAddrFromSymTable (Collection<IValue> symTbl, Map<Integer, IValue> heap) {
        List<Integer> result = new ArrayList<Integer>();
        for (IValue v : symTbl) {
            if (v instanceof RefValue) {
                RefValue v1 = (RefValue) v;
                if (v1.getAddress() != 0) {
                    result.add(v1.getAddress());
                    while (v1.getLocationType() instanceof RefType) {   
                        v1 = (RefValue) heap.get(v1.getAddress());
                        result.add(v1.getAddress());
                    }
                }
                //result.add(v1.getAddress());
            }
        }
        return result;
        // return symTbl.stream().filter(v -> v instanceof RefValue).map(v -> {RefValue v1 = (RefValue) v; return v1.getAddress();}).collect(Collectors.toList());
    } 

    public ProgramState oneStep(ProgramState state) throws EmptyStackException, StatementException, ExpressionException {
        IMyStack<IStmt> stack = state.getExeStack();
        if (stack.isEmpty())
            throw new EmptyStackException("Execution stack is empty!");

        IStmt currentStmt = stack.pop();
        return currentStmt.execute(state);
    }

    public void allStep() throws EmptyStackException, StatementException, ExpressionException, RepoException {
        ProgramState currentPrgState = this.repo.getCurrent();
        if (this.displayFlag) 
            displayPrgState(currentPrgState);

        this.repo.logPrgStateExec();
        while (! currentPrgState.getExeStack().isEmpty()) {
            ProgramState newState = oneStep(currentPrgState);
            if (this.displayFlag)
                displayPrgState(newState);
            
            //currentPrgState.getHeap().setContent(GarbageCollector(getAddrFromSymTable(currentPrgState.getSymTable().getContent().values()), currentPrgState.getHeap().getContent())); 
            currentPrgState.getHeap().setContent(GarbageCollector(getAddrFromSymTable(currentPrgState.getSymTable().getContent().values(), currentPrgState.getHeap().getContent()), currentPrgState.getHeap().getContent())); 
            this.repo.logPrgStateExec();
        }
    }

    public void displayPrgState(ProgramState state) {
        System.out.println(state.toString() + '\n');
    }

    public void addPrgState(ProgramState state) {
        this.repo.add(state);
    }
}
