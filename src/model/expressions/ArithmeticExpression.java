package model.expressions;

import exceptions.ExpressionException;
import model.adt.IMyMap;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

public class ArithmeticExpression implements IExpression {
    private IExpression exp1;
    private IExpression exp2;
    private ArithmeticOperation operator;

    public ArithmeticExpression (IExpression e1, IExpression e2, ArithmeticOperation op) {
        this.exp1 = e1;
        this.exp2 = e2;
        this.operator = op;
    }

    public IValue evaluate(IMyMap<String, IValue> symTable) throws ExpressionException {
        var leftExpression = this.exp1.evaluate(symTable);
        var rightExpression = this.exp2.evaluate(symTable);

        if (! leftExpression.getType().equals(new IntType()) )
            throw new ExpressionException("First operand is not an integer!");

        if (! rightExpression.getType().equals(new IntType()) )
            throw new ExpressionException("Second operand is not an integer!");

        IntValue i1 = (IntValue)leftExpression;
        IntValue i2 = (IntValue)rightExpression;
        int n1 = i1.getValue(), n2 = i2.getValue();

        switch (this.operator) {
            case ADD:
                return new IntValue(n1 + n2);
        
            case SUBTRACT:
                return new IntValue(n1 - n2);

            case MULTIPLY:
                return new IntValue(n1 * n2);

            case DIVIDE:
                if (n2 == 0)
                    throw new ExpressionException("Division by zero!");

                return new IntValue(n1 / n2);

            default:
                return null;
        }
    }

    public IExpression deepCopy() {
        return new ArithmeticExpression(exp1, exp2, operator);
    }

    @Override
    public String toString() {
        return this.exp1.toString() + " " + String.valueOf(this.operator) + " " + this.exp2.toString();
    }
}
