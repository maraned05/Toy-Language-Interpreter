package model.expressions;

import model.adt.IMyMap;
import model.values.IValue;

public class ValueExpression implements IExpression {
    private IValue value;

    public ValueExpression(IValue val) {
        this.value = val;
    }

    public IValue evaluate(IMyMap<String, IValue> symbTbl) {
        return this.value;
    }

    public IExpression deepCopy() {
        return new ValueExpression(value);
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}
