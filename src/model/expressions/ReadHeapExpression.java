package model.expressions;

import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import model.adt.IMyHeap;
import model.adt.IMyMap;
import model.types.IType;
import model.types.RefType;
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

    public IType typeCheck (IMyMap<String, IType> typeEnv) throws KeyNotFoundException, ExpressionException {
        IType type = this.exp.typeCheck(typeEnv);
        if (type instanceof RefType) {
            return ((RefType) type).getInnerType();
        }
        else throw new ExpressionException("The expression isn't of reference type!");
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
