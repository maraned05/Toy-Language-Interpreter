package model.statements;
import model.adt.IMyStack;
import model.state.ProgramState;

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


    @Override
    public String toString() {
        return "( " + this.first.toString() + "; " + this.second.toString() + " )";
    }

    public IStmt deepCopy() {
        return new CompStmt(first, second);
    }
}
