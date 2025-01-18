package lab.example.model.statements;

import lab.example.exceptions.ExpressionException;
import lab.example.exceptions.KeyNotFoundException;
import lab.example.exceptions.StatementException;
import lab.example.model.adt.IMyMap;
import lab.example.model.expressions.IExpression;
import lab.example.model.state.ProgramState;
import lab.example.model.types.IType;
import lab.example.model.types.RefType;
import lab.example.model.values.IValue;
import lab.example.model.values.RefValue;

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

    public IMyMap<String, IType> typeCheck (IMyMap<String, IType> typeEnv) throws KeyNotFoundException, ExpressionException, StatementException {
        IType typeVar = typeEnv.get(this.varName);
        IType typeExp = this.exp.typeCheck(typeEnv);
        if (typeVar.equals(new RefType(typeExp)))
            return typeEnv;
        else throw new StatementException("Typechecker Error: The types don't match.");
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
