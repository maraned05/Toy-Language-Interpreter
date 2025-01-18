package lab.example.model.expressions;

import lab.example.exceptions.ExpressionException;
import lab.example.exceptions.KeyNotFoundException;
import lab.example.model.adt.IMyHeap;
import lab.example.model.adt.IMyMap;
import lab.example.model.types.IType;
import lab.example.model.types.RefType;
import lab.example.model.values.IValue;
import lab.example.model.values.RefValue;

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
        else throw new ExpressionException("Typechecker Error: The expression isn't of reference type!");
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
