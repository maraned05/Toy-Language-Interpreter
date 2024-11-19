package model.statements;

import model.expressions.IExpression;
import model.state.ProgramState;
import model.values.IValue;

public class ReadHeapStatement implements IStmt {
    private IExpression expression;

    public ReadHeapStatement(IExpression exp) {
        this.expression = exp;
    }

    public ProgramState execute (ProgramState state) throws StatementException, ExpressionException {
        IValue valueExp = this.expression.evaluate(state.getSymTable());

    }

    public IStmt deepCopy() {
        return new ReadHeapStatement(expression);
    }
}
