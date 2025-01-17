package lab.example.model.statements;

import lab.example.model.adt.IMyMap;
import lab.example.model.expressions.IExpression;
import lab.example.model.state.ProgramState;
import lab.example.model.types.BoolType;
import lab.example.model.types.IType;
import lab.example.model.values.*;
import lab.example.exceptions.ExpressionException;
import lab.example.exceptions.KeyNotFoundException;
import lab.example.exceptions.StatementException;

public class IfStmt implements IStmt{
    private IExpression exp;
    private IStmt thenS;
    private IStmt elseS;

    public IfStmt (IExpression e, IStmt t, IStmt el) {
        this.exp = e;
        this.thenS = t;
        this.elseS = el;
    }

    public IMyMap<String, IType> typeCheck (IMyMap<String, IType> typeEnv) throws KeyNotFoundException, ExpressionException, StatementException {
        IType type = this.exp.typeCheck(typeEnv);
        if (type.equals(new BoolType())) {
            this.thenS.typeCheck(typeEnv.deepCopy());
            this.elseS.typeCheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else throw new ExpressionException("The IF condition is not of type bool.");
    }

    @Override
    public String toString() {
        return "(IF(" + this.exp.toString() + ") THEN(" + this.thenS.toString() + ") ELSE(" + this.elseS.toString() + "))";
    }

    public ProgramState execute (ProgramState state) throws StatementException, ExpressionException{
        IValue expVal = this.exp.evaluate(state.getSymTable(), state.getHeap());
        if (!(expVal.getType() instanceof BoolType)) {
            throw new StatementException(expVal.toString() + " is not a boolean");
        }
        if (((BoolValue)expVal).getValue()) {
            state.getExeStack().push(thenS);
        } else {
            state.getExeStack().push(elseS);
        }

        return null;    
    }

    public IStmt deepCopy() {
        return new IfStmt(exp, thenS, elseS);
    }
}
