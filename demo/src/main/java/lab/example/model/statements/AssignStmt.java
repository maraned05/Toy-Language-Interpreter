package lab.example.model.statements;

import lab.example.exceptions.ExpressionException;
import lab.example.exceptions.KeyNotFoundException;
import lab.example.exceptions.StatementException;
import lab.example.model.adt.IMyMap;
import lab.example.model.expressions.IExpression;
import lab.example.model.state.ProgramState;
import lab.example.model.types.IType;
import lab.example.model.values.IValue;

public class AssignStmt implements IStmt {
    private String variable;
    private IExpression exp;

    public AssignStmt (String var, IExpression e) {
        this.variable = var;
        this.exp = e;
    }

    public ProgramState execute (ProgramState state) throws ExpressionException, StatementException {
        if (!state.getSymTable().contains(this.variable)) {
            throw new ExpressionException("The variable is not defined.");
        }


        IValue evalValue = this.exp.evaluate(state.getSymTable(), state.getHeap());
        try {
            IType type = state.getSymTable().get(this.variable).getType();
            if (evalValue.getType().equals(type)) 
                state.getSymTable().insert(this.variable, evalValue);

            else throw new StatementException("The values do not match.");

            return null;
        }
        catch (KeyNotFoundException e) {
            throw new ExpressionException("Invalid variable!");
        }

    }

    public IMyMap<String, IType> typeCheck (IMyMap<String, IType> typeEnv) throws KeyNotFoundException, ExpressionException, StatementException {
        IType typeVar = typeEnv.get(this.variable);
        IType typeExp = this.exp.typeCheck(typeEnv);
        if (typeVar.equals(typeExp)) {
            return typeEnv;
        }
        else throw new StatementException("Typechecker Error: The RHS and LHS have different types.");
    }

    @Override
    public String toString() {
        return this.variable + " = " + this.exp.toString();
    }

    public IStmt deepCopy() {
        return new AssignStmt(variable, exp);
    }

}   
