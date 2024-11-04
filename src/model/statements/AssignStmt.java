package model.statements;

import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import exceptions.StatementException;
import model.expressions.IExpression;
import model.state.ProgramState;
import model.types.IType;
import model.values.IValue;

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


        IValue evalValue = this.exp.evaluate(state.getSymTable());
        try {
            IType type = state.getSymTable().get(this.variable).getType();
            if (evalValue.getType().equals(type)) 
                state.getSymTable().insert(this.variable, evalValue);

            else throw new StatementException("The values do not match.");

            return state;
        }
        catch (KeyNotFoundException e) {
            throw new ExpressionException("Invalid variable!");
        }

    }

    @Override
    public String toString() {
        return this.variable + " = " + this.exp.toString();
    }

    public IStmt deepCopy() {
        return new AssignStmt(variable, exp);
    }

}   
