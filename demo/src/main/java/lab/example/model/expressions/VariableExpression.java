package lab.example.model.expressions;

import lab.example.exceptions.ExpressionException;
import lab.example.exceptions.KeyNotFoundException;
import lab.example.model.adt.IMyHeap;
import lab.example.model.adt.IMyMap;
import lab.example.model.types.IType;
import lab.example.model.values.IValue;

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

    public IType typeCheck(IMyMap<String, IType> typeEnv) throws KeyNotFoundException {
        return typeEnv.get(variable);
    }

    public IExpression deepCopy() {
        return new VariableExpression(variable);
    }

    @Override
    public String toString() {
        return this.variable;
    }
}
