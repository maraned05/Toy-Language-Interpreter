package lab.example.model.statements;

import lab.example.exceptions.ExpressionException;
import lab.example.exceptions.KeyNotFoundException;
import lab.example.exceptions.StatementException;
import lab.example.model.adt.IMyMap;
import lab.example.model.state.ProgramState;
import lab.example.model.types.IType;
import lab.example.model.values.IValue;
import lab.example.model.values.IntValue;

public class UnlockStmt implements IStmt {
    private String var;

    public UnlockStmt (String v) {
        this.var = v;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException {
        try {
            IValue addr = state.getSymTable().get(var);   
            if (! (addr instanceof IntValue))
                throw new StatementException("Variable isn't mapped to an index!");

            int intAddr = ((IntValue) addr).getValue();

            try {
                ProgramState.lock.readLock().lock();
                int val = state.getLockTable().get(intAddr); 
                ProgramState.lock.readLock().unlock();
                if (val != -1) {
                    ProgramState.lock.writeLock().lock();
                    state.getLockTable().set(intAddr, -1);
                    ProgramState.lock.writeLock().unlock();
                }
                
                return null;    
            }
            catch (KeyNotFoundException e) {
                return null;
            }
        }
        catch (KeyNotFoundException e) {
            throw new StatementException("Undefined variable!");
        }
    }

    @Override
    public IMyMap<String, IType> typeCheck(IMyMap<String, IType> typeEnv) {
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new UnlockStmt(var);
    }

    @Override 
    public String toString() {
        return "unlock(" + this.var + ")";
    }
    
}
