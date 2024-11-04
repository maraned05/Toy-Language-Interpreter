package model.expressions;

import exceptions.ExpressionException;
import model.adt.IMyMap;
import model.types.BoolType;
import model.values.BoolValue;
import model.values.IValue;

public class LogicalExpression implements IExpression {
    private IExpression expression1;
    private IExpression expression2;
    private LogicalOperation operation;

    public LogicalExpression(IExpression expression1, IExpression expression2, LogicalOperation operation) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operation = operation;
    }

    public IValue evaluate(IMyMap<String, IValue> symTable) throws ExpressionException {
        var leftExpression = expression1.evaluate(symTable);
        var rightExpression = expression2.evaluate(symTable);

        if(!leftExpression.getType().equals(new BoolType()) || !rightExpression.getType().equals(new BoolType())) {
            throw new ExpressionException("Left and right expressions are not the same");
        }
        if (operation == LogicalOperation.AND) {
            return new BoolValue(((BoolValue) leftExpression).getValue() && ((BoolValue) rightExpression).getValue());
        } else {
            return new BoolValue(((BoolValue) leftExpression).getValue() || ((BoolValue) rightExpression).getValue());
        }

    }

    public IExpression deepCopy() {
        return new LogicalExpression(expression1, expression2, operation);
    }

    @Override
    public String toString() {
        return expression1.toString() + " " + operation.toString().toLowerCase() + " " + expression2.toString();
    }
}
