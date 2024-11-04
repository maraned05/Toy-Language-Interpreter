package controller;
import exceptions.EmptyStackException;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.adt.IMyStack;
import model.state.ProgramState;
import model.statements.IStmt;
import repository.IRepository;
import repository.Repository;

public class Controller {
    private IRepository repo;

    public Controller(Repository r) {
        this.repo = r;
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
        while (! currentPrgState.getExeStack().isEmpty()) {
            oneStep(currentPrgState);
        }
    }
}
