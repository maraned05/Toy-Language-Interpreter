package model.expressions;

import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import model.adt.IMyHeap;
import model.adt.IMyMap;
import model.types.BoolType;
import model.types.IType;
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

    public IValue evaluate(IMyMap<String, IValue> symTable, IMyHeap<Integer, IValue> heap) throws ExpressionException {
        var leftExpression = expression1.evaluate(symTable, heap);
        var rightExpression = expression2.evaluate(symTable, heap);

        if(!leftExpression.getType().equals(new BoolType()) || !rightExpression.getType().equals(new BoolType())) {
            throw new ExpressionException("Left and right expressions are not the same");
        }
        if (operation == LogicalOperation.AND) {
            return new BoolValue(((BoolValue) leftExpression).getValue() && ((BoolValue) rightExpression).getValue());
        } else {
            return new BoolValue(((BoolValue) leftExpression).getValue() || ((BoolValue) rightExpression).getValue());
        }

    }

    public IType typeCheck(IMyMap<String, IType> typeEnv) throws KeyNotFoundException, ExpressionException {
        IType type1, type2;
        type1 = this.expression1.typeCheck(typeEnv);
        type2 = this.expression2.typeCheck(typeEnv);
        if (type1.equals(new BoolType())) {
            if (type2.equals(new BoolType()))
                return new BoolType();

            else throw new ExpressionException("Second expression is not of boolean type!");
        }
        else throw new ExpressionException("First expression is not of boolean type!");
    }

    public IExpression deepCopy() {
        return new LogicalExpression(expression1, expression2, operation);
    }

    @Override
    public String toString() {
        return expression1.toString() + " " + operation.toString().toLowerCase() + " " + expression2.toString();
    }
}
