package model.statements;

import model.state.ProgramState;

public class NoStmt implements IStmt{
    public NoStmt() {
        return;
    }

    public ProgramState execute (ProgramState state) {
        return null;
    }

    @Override
    public String toString() {
        return "No Statement.";
    }

    public IStmt deepCopy() {
        return new NoStmt();
    }
}
