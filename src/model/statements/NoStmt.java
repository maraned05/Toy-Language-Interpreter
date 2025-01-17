package model.statements;

import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import exceptions.StatementException;
import model.adt.IMyMap;
import model.state.ProgramState;
import model.types.IType;

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
