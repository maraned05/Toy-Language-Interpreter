package lab.example.model.statements;

import lab.example.exceptions.ExpressionException;
import lab.example.exceptions.KeyNotFoundException;
import lab.example.exceptions.StatementException;
import lab.example.model.adt.IMyMap;
import lab.example.model.adt.MyStack;
import lab.example.model.state.ProgramState;
import lab.example.model.types.IType;

public class ForkStmt implements IStmt{
    private IStmt statement;

    public ForkStmt(IStmt stmt) {
        this.statement = stmt;
    }

    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException {
        ProgramState newState = new ProgramState(new MyStack<IStmt>(), state.getSymTable().deepCopy(), state.getOut(), 
        this.statement, state.getFileTable(), state.getHeap(), state.getLockTable());
        return newState;
    }

    public IMyMap<String, IType> typeCheck (IMyMap<String, IType> typeEnv) throws KeyNotFoundException, ExpressionException, StatementException {
        this.statement.typeCheck(typeEnv.deepCopy());
        return typeEnv;
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
