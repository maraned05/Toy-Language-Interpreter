package model.statements;

import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import exceptions.StatementException;
import model.expressions.IExpression;
import model.state.ProgramState;
import model.types.RefType;
import model.values.IValue;
import model.values.RefValue;

public class HeapAllocStmt implements IStmt {
    private IExpression expr;
    private String varName;

    public HeapAllocStmt(IExpression e, String v) {
        this.expr = e;
        this.varName = v;
    }

    public ProgramState execute (ProgramState state) throws StatementException, ExpressionException {
        IValue varValue;
        try {
            varValue = state.getSymTable().get(varName);
            if (! (varValue.getType() instanceof RefType))
                throw new StatementException("The variable isn't of reference type.");
        }
        catch (KeyNotFoundException e) {
            throw new StatementException("The variable is not defined.");
        }

        var res = this.expr.evaluate(state.getSymTable(), state.getHeap());
        if (! (res.getType().equals(((RefValue)varValue).getLocationType())))
            throw new ExpressionException("The types don't match.");

        int newAddr = state.getHeap().allocate(res);
        state.getSymTable().insert(varName, new RefValue(newAddr, res.getType()));
        
        return state;
    }

    @Override
    public String toString() {
        return "new(" + this.varName + ", " + this.expr.toString() + ")";
    }
    
    @Override
    public IStmt deepCopy() {
        return new HeapAllocStmt(expr, varName);
    }
}
