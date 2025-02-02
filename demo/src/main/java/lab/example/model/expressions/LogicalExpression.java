package lab.example.model.expressions;

import lab.example.exceptions.ExpressionException;
import lab.example.exceptions.KeyNotFoundException;
import lab.example.model.adt.IMyHeap;
import lab.example.model.adt.IMyMap;
import lab.example.model.types.BoolType;
import lab.example.model.types.IType;
import lab.example.model.values.BoolValue;
import lab.example.model.values.IValue;

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

            else throw new ExpressionException("Typechecker Error: Second expression is not of boolean type!");
        }
        else throw new ExpressionException("Typechecker Error: First expression is not of boolean type!");
    }

    public IExpression deepCopy() {
        return new LogicalExpression(expression1, expression2, operation);
    }

    @Override
    public String toString() {
        return expression1.toString() + " " + operation.toString().toLowerCase() + " " + expression2.toString();
    }
}
