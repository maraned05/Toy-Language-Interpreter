package model.statements;

import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import exceptions.StatementException;
import model.expressions.IExpression;
import model.state.ProgramState;
import model.types.RefType;
import model.values.IValue;
import model.values.RefValue;

public class WriteHeapStmt implements IStmt {
    private IExpression exp;
    private String varName;

    public WriteHeapStmt(IExpression e, String v) {
        this.exp = e;
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
            throw new StatementException("The variable isn't declared.");
        }
        
        IValue expValue = this.exp.evaluate(state.getSymTable(), state.getHeap());
        if (! (expValue.getType().equals(((RefValue)varValue).getLocationType())))
            throw new StatementException("The types don't match.");

        state.getHeap().set(((RefValue)varValue).getAddress(), expValue);

        return null;
    }
    
    @Override
    public String toString() {
        return "wH(" + this.varName + ", " + this.exp.toString() + ")";
    }

    @Override
    public IStmt deepCopy() {
        return new WriteHeapStmt(exp, varName);
    }
}
