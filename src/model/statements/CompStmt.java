package model.statements;
import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import exceptions.StatementException;
import model.adt.IMyMap;
import model.adt.IMyStack;
import model.state.ProgramState;
import model.types.IType;

public class CompStmt implements IStmt{
    private IStmt first;
    private IStmt second;

    public CompStmt(IStmt f, IStmt s) {
        this.first = f;
        this.second = s;
    }

    public ProgramState execute (ProgramState state) {
        IMyStack<IStmt> stack = state.getExeStack();
        stack.push(this.second);
        stack.push(this.first);
        return null;
    }

    public IMyMap<String, IType> typeCheck (IMyMap<String, IType> typeEnv) throws KeyNotFoundException, ExpressionException, StatementException {        
        return this.second.typeCheck(this.first.typeCheck(typeEnv));
    }

    @Override
    public String toString() {
        return "( " + this.first.toString() + "; " + this.second.toString() + " )";
    }

    public IStmt deepCopy() {
        return new CompStmt(first, second);
    }
}
