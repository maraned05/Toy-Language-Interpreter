package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.expressions.IExpression;
import model.state.ProgramState;
import model.types.BoolType;
import model.values.BoolValue;
import model.values.IValue;

public class WhileStmt implements IStmt{
    private IExpression exp;
    private IStmt statement;

    public WhileStmt(IExpression e, IStmt s) {
        this.exp = e;
        this.statement = s;
    }

    public ProgramState execute (ProgramState state) throws StatementException, ExpressionException {
        IValue res = this.exp.evaluate(state.getSymTable(), state.getHeap());
        if (! (res.getType().equals(new BoolType())))   
            throw new ExpressionException("Expression isn't of bool type.");

        if (((BoolValue)res).getValue()) {
            state.getExeStack().push(this);
            state.getExeStack().push(statement);
            
        }

        return state;
    }

    @Override
    public IStmt deepCopy() {
        return new WhileStmt(exp, statement);
    }

    @Override
    public String toString() {
        return "while (" + this.exp.toString() + ") do " + this.statement.toString();
    }
}
