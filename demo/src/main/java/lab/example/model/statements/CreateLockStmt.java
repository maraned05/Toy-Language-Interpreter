package lab.example.model.statements;

import lab.example.exceptions.ExpressionException;
import lab.example.exceptions.KeyNotFoundException;
import lab.example.exceptions.StatementException;
import lab.example.model.adt.IMyMap;
import lab.example.model.state.ProgramState;
import lab.example.model.types.IType;
import lab.example.model.values.IntValue;

public class CreateLockStmt implements IStmt {
    private String var;

    public CreateLockStmt (String v) {
        this.var = v;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException {
        int newAddr;
        ProgramState.lock.writeLock().lock();
        newAddr = state.getLockTable().createLock();
        ProgramState.lock.writeLock().unlock();
        state.getSymTable().insert(var, new IntValue(newAddr));
        return null;
    }

    @Override
    public IMyMap<String, IType> typeCheck(IMyMap<String, IType> typeEnv) throws KeyNotFoundException, ExpressionException, StatementException {
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new CreateLockStmt(var);
    }

    @Override 
    public String toString() {
        return "newlock(" + this.var + ")";
    }
    
}
