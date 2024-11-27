package model.expressions;

import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import model.adt.IMyHeap;
import model.adt.IMyMap;
import model.values.IValue;
import model.values.RefValue;

public class ReadHeapExpression implements IExpression{
    private IExpression exp;

    public ReadHeapExpression(IExpression e) {
        this.exp = e;
    }

    public IValue evaluate(IMyMap<String, IValue> symYables, IMyHeap<Integer, IValue> heap) throws ExpressionException {
        IValue expVal = exp.evaluate(symYables, heap);
        if (! (expVal instanceof RefValue))
            throw new ExpressionException("The value isn't of reference type.");

        try {
            return heap.get(((RefValue)expVal).getAddress());
        }
        catch (KeyNotFoundException e) {
            throw new ExpressionException("Invalid variable.");
        }
    }

    @Override
    public String toString() {
        return "rH(" + this.exp.toString() + ")";
    }

    @Override
    public IExpression deepCopy() {
        return new ReadHeapExpression(this.exp);
    }
}
