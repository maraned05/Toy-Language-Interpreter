package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.adt.MyStack;
import model.state.ProgramState;

public class ForkStmt implements IStmt{
    private IStmt statement;

    public ForkStmt(IStmt stmt) {
        this.statement = stmt;
    }

    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException {
        ProgramState newState = new ProgramState(new MyStack<IStmt>(), state.getSymTable().deepCopy(), state.getOut(), this.statement, state.getFileTable(), state.getHeap());
        return newState;
    }

    @Override
    public IStmt deepCopy() {
        return new ForkStmt(statement);
    }

    @Override
    public String toString() {
        return "fork(" + this.statement.toString() + ")";
    }
}
