package lab.example.model.statements;

import lab.example.exceptions.ExpressionException;
import lab.example.exceptions.KeyNotFoundException;
import lab.example.exceptions.StatementException;
import lab.example.model.adt.IMyMap;
import lab.example.model.expressions.IExpression;
import lab.example.model.state.ProgramState;
import lab.example.model.types.IType;
import lab.example.model.types.IntType;
import lab.example.model.values.IValue;
import lab.example.model.values.IntValue;

public class CreateBarrierStmt implements IStmt {
    private String var;
    private IExpression exp;

    public CreateBarrierStmt (String v, IExpression e) {
        this.var = v;
        this.exp = e;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException {
        IValue expVal = this.exp.evaluate(state.getSymTable(), state.getHeap());
        if (! (expVal instanceof IntValue))
            throw new ExpressionException("The result of the exp. is not an integer!");

        int nr = ((IntValue) expVal).getValue();
        int newAddr;

        ProgramState.lock.writeLock().lock();
        newAddr = state.getBarrierTable().createBarrier(nr);
        ProgramState.lock.writeLock().unlock();

        try {
            IValue val = state.getSymTable().get(var);
            if (! (val instanceof IntValue))
                throw new StatementException("Variable isn't of type int!");
                
            state.getSymTable().insert(var, new IntValue(newAddr));
        }
        catch (KeyNotFoundException e) {
            throw new StatementException("Undefined variable!");
        }

        return null;
    }

    @Override
    public IMyMap<String, IType> typeCheck(IMyMap<String, IType> typeEnv) throws KeyNotFoundException, ExpressionException, StatementException {
        IType typeVar = typeEnv.get(this.var);
        IType typeExp = this.exp.typeCheck(typeEnv);

        if (! (typeVar instanceof IntType))
            throw new StatementException("Variable isn't of type int!");

        if (! (typeExp instanceof IntType))
            throw new StatementException("Expression isn't of type int!");

        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new CreateBarrierStmt(var, exp);
    }

    @Override 
    public String toString() {
        return "newBarrier(" + this.var + ")";
    }
    
}
