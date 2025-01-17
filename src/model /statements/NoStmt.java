package lab.example.model.statements;

import lab.example.exceptions.ExpressionException;
import lab.example.exceptions.KeyNotFoundException;
import lab.example.exceptions.StatementException;
import lab.example.model.adt.IMyMap;
import lab.example.model.state.ProgramState;
import lab.example.model.types.IType;

public class NoStmt implements IStmt{
    public NoStmt() {
        return;
    }

    public ProgramState execute (ProgramState state) {
        return null;
    }

    public IMyMap<String, IType> typeCheck (IMyMap<String, IType> typeEnv) throws KeyNotFoundException, ExpressionException, StatementException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "No Statement.";
    }

    public IStmt deepCopy() {
        return new NoStmt();
    }
}
