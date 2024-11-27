package model.expressions;
import exceptions.ExpressionException;
import model.adt.IMyHeap;
import model.adt.IMyMap;
import model.values.*;

public interface IExpression {
    IValue evaluate (IMyMap<String, IValue> symYables, IMyHeap<Integer, IValue> heap) throws ExpressionException;
    IExpression deepCopy();
}
