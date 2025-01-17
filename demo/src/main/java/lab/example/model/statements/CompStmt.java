package lab.example.model.statements;
import lab.example.exceptions.ExpressionException;
import lab.example.exceptions.KeyNotFoundException;
import lab.example.exceptions.StatementException;
import lab.example.model.adt.IMyMap;
import lab.example.model.adt.IMyStack;
import lab.example.model.state.ProgramState;
import lab.example.model.types.IType;

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
