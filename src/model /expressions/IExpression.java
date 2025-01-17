package lab.example.model.expressions;
import lab.example.exceptions.ExpressionException;
import lab.example.exceptions.KeyNotFoundException;
import lab.example.model.adt.IMyHeap;
import lab.example.model.adt.IMyMap;
import lab.example.model.types.IType;
import lab.example.model.values.*;

public interface IExpression {
    IValue evaluate (IMyMap<String, IValue> symYables, IMyHeap<Integer, IValue> heap) throws ExpressionException;
    IType typeCheck (IMyMap<String, IType> typeEnv) throws KeyNotFoundException, ExpressionException;
    IExpression deepCopy();
}
