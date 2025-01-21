package lab.example.model.statements;

import javafx.util.Pair;
import java.util.List;
import lab.example.exceptions.ExpressionException;
import lab.example.exceptions.KeyNotFoundException;
import lab.example.exceptions.StatementException;
import lab.example.model.adt.IMyMap;
import lab.example.model.state.ProgramState;
import lab.example.model.types.IType;
import lab.example.model.types.IntType;
import lab.example.model.values.IValue;
import lab.example.model.values.IntValue;

public class AwaitStmt implements IStmt{
    private String var;

    public AwaitStmt (String v) {
        this.var = v;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException {
        try {
            IValue addr = state.getSymTable().get(var);   
            if (! (addr instanceof IntValue))
                throw new StatementException("Variable isn't of type int!");

            int intAddr = ((IntValue) addr).getValue();

            try {
                ProgramState.lock.readLock().lock();
                Pair<Integer, List<Integer>> val = state.getBarrierTable().get(intAddr); 
                List<Integer> list = val.getValue();
                int listLen = list.size();
                ProgramState.lock.readLock().unlock();

                if (val.getKey() > listLen) {
                    if (! list.contains(state.getId())) {
                        ProgramState.lock.writeLock().lock();
                        list.add(state.getId());
                        ProgramState.lock.writeLock().unlock();
                    }
                   
                    state.getExeStack().push(new AwaitStmt(var));
                }
                
                return null;

            }
            catch (KeyNotFoundException e) {
                throw new StatementException("Variable isn't mapped to an index!");
            }

        }
        catch (KeyNotFoundException e) {
            throw new StatementException("Undefined variable!");
        }
    }

    @Override
    public IMyMap<String, IType> typeCheck(IMyMap<String, IType> typeEnv) throws KeyNotFoundException, ExpressionException, StatementException {
        IType typeVar = typeEnv.get(this.var);
        if (! (typeVar instanceof IntType))
            throw new StatementException("Variable isn't of type int!");

        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new AwaitStmt(var);
    }

    @Override 
    public String toString() {
        return "await(" + this.var + ")";
    }
    
}
