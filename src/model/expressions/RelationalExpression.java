package model.expressions;

import exceptions.ExpressionException;
import model.adt.IMyMap;
import model.types.IntType;
import model.values.BoolValue;
import model.values.IValue;
import model.values.IntValue;

public class RelationalExpression implements IExpression {
    private IExpression exp1;
    private IExpression exp2;
    private RelationalOperation op;

    public RelationalExpression (IExpression e1, IExpression e2, RelationalOperation operator) {
        this.exp1 = e1;
        this.exp2 = e2;
        this.op = operator;
    }

    public IValue evaluate (IMyMap<String, IValue> symTable) throws ExpressionException {
        var leftExp = this.exp1.evaluate(symTable);
        var rightExp = this.exp2.evaluate(symTable);

        if (! leftExp.getType().equals(new IntType()) || ! rightExp.getType().equals(new IntType())) 
            throw new ExpressionException("The expressions are not of INT type!\n");

        int v1 = ((IntValue) leftExp).getValue();
        int v2 = ((IntValue) rightExp).getValue();

        switch (this.op) {
            case LESS:
                return new BoolValue(v1 < v2);
        
            case LESS_OR_EQUAL:
                return new BoolValue(v1 <= v2);

            case EQUAL:
                return new BoolValue(v1 == v2);

            case DIFFERENT:
                return new BoolValue(v1 != v2);

            case GREATER_OR_EQUAL:
                return new BoolValue(v1 >= v2);

            case GREATER:
                return new BoolValue(v1 > v2);

            default:
                return null;
        }
    }

    @Override
    public IExpression deepCopy() {
        return new RelationalExpression(exp2, exp1, op);
    }

    @Override
    public String toString() {
        return this.exp1.toString() + " " + String.valueOf(this.op) + " " + this.exp2.toString();
    }

}
