package controller;
import exceptions.EmptyStackException;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.adt.IMyStack;
import model.state.ProgramState;
import model.statements.IStmt;
import repository.IRepository;
import repository.Repository;

public class Controller implements IController{
    private IRepository repo;
    private boolean displayFlag;

    public Controller(Repository r, boolean flag) {
        this.repo = r;
        this.displayFlag = flag;
    }

    public ProgramState oneStep(ProgramState state) throws EmptyStackException, StatementException, ExpressionException {
        IMyStack<IStmt> stack = state.getExeStack();
        if (stack.isEmpty())
            throw new EmptyStackException("Execution stack is empty!");

        IStmt currentStmt = stack.pop();
        return currentStmt.execute(state);
    }

    public void allStep() throws EmptyStackException, StatementException, ExpressionException {
        ProgramState currentPrgState = this.repo.getCurrent();
        if (this.displayFlag)
            displayPrgState(currentPrgState);
        while (! currentPrgState.getExeStack().isEmpty()) {
            ProgramState newState = oneStep(currentPrgState);
            if (this.displayFlag)
                displayPrgState(newState);
        }
    }

    public void displayPrgState(ProgramState state) {
        System.out.println(state.toString());
    }

    public void addPrgState(ProgramState state) {
        this.repo.add(state);
    }
}
