package lab.example.model.expressions;

import lab.example.model.adt.IMyHeap;
import lab.example.model.adt.IMyMap;
import lab.example.model.types.IType;
import lab.example.model.values.IValue;

public class ValueExpression implements IExpression {
    private IValue value;

    public ValueExpression(IValue val) {
        this.value = val;
    }

    public IValue evaluate(IMyMap<String, IValue> symbTbl, IMyHeap<Integer, IValue> heap) {
        return this.value;
    }

    public IType typeCheck(IMyMap<String, IType> typeEnv) {
        return this.value.getType();
    }
    
    public IExpression deepCopy() {
        return new ValueExpression(value);
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}
