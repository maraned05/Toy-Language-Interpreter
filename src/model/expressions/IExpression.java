package model.expressions;
import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import model.adt.IMyHeap;
import model.adt.IMyMap;
import model.types.IType;
import model.values.*;

public interface IExpression {
    IValue evaluate (IMyMap<String, IValue> symYables, IMyHeap<Integer, IValue> heap) throws ExpressionException;
    IType typeCheck (IMyMap<String, IType> typeEnv) throws KeyNotFoundException, ExpressionException;
    IExpression deepCopy();
}
