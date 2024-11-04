package model.expressions;
import exceptions.ExpressionException;
import model.adt.IMyMap;
import model.values.*;

public interface IExpression {
    IValue evaluate (IMyMap<String, IValue> symYables) throws ExpressionException;
    IExpression deepCopy();
}
