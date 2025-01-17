package lab.example.model.expressions;

import lab.example.exceptions.ExpressionException;
import lab.example.exceptions.KeyNotFoundException;
import lab.example.model.adt.IMyHeap;
import lab.example.model.adt.IMyMap;
import lab.example.model.types.BoolType;
import lab.example.model.types.IType;
import lab.example.model.types.IntType;
import lab.example.model.values.BoolValue;
import lab.example.model.values.IValue;
import lab.example.model.values.IntValue;

public class RelationalExpression implements IExpression {
    private IExpression exp1;
    private IExpression exp2;
    private RelationalOperation op;

    public RelationalExpression (IExpression e1, IExpression e2, RelationalOperation operator) {
        this.exp1 = e1;
        this.exp2 = e2;
        this.op = operator;
    }

    public IValue evaluate (IMyMap<String, IValue> symTable, IMyHeap<Integer, IValue> heap) throws ExpressionException {
        var leftExp = this.exp1.evaluate(symTable, heap);
        var rightExp = this.exp2.evaluate(symTable, heap);

        if (! leftExp.getType().equals(new IntType()) || ! rightExp.getType().equals(new IntType())) 
            throw new ExpressionException("The expressions are not of INT type!\n");

        int v1 = ((IntValue) leftExp).getValue();
        int v2 = ((IntValue) rightExp).getValue();

        switch (this.op) {
            case LESS:
                return new BoolValue(v1 < v2);
        
            case LESS_OR_EQUAL:
                return new BoolValue(v1 <= v2);

            case EQUAL:
                return new BoolValue(v1 == v2);

            case DIFFERENT:
                return new BoolValue(v1 != v2);

            case GREATER_OR_EQUAL:
                return new BoolValue(v1 >= v2);

            case GREATER:
                return new BoolValue(v1 > v2);

            default:
                return null;
        }
    }

    public IType typeCheck(IMyMap<String, IType> typeEnv) throws KeyNotFoundException, ExpressionException {
        IType type1, type2;
        type1 = this.exp1.typeCheck(typeEnv);
        type2 = this.exp2.typeCheck(typeEnv);
        if (type1.equals(new IntType())) {
            if (type2.equals(new IntType()))
                return new BoolType();

            else throw new ExpressionException("Second operand is not an integer!");
        }
        else throw new ExpressionException("First operand is not an integer!");
    }

    @Override
    public IExpression deepCopy() {
        return new RelationalExpression(exp2, exp1, op);
    }

    @Override
    public String toString() {
        return this.exp1.toString() + " " + String.valueOf(this.op) + " " + this.exp2.toString();
    }

}
