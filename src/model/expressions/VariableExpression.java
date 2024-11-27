package model.expressions;

import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import model.adt.IMyHeap;
import model.adt.IMyMap;
import model.values.IValue;

public class VariableExpression implements IExpression{
    private String variable;

    public VariableExpression(String var) {
        this.variable = var;
    }

    public IValue evaluate (IMyMap<String, IValue> symbTbl, IMyHeap<Integer, IValue> heap) throws ExpressionException {
        try {
            return symbTbl.get(this.variable);
        }
        catch (KeyNotFoundException e) {
            throw new ExpressionException("Invalid variable!");
        }
    }

    public IExpression deepCopy() {
        return new VariableExpression(variable);
    }

    @Override
    public String toString() {
        return this.variable;
    }
}
