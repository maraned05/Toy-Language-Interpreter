package model.statements;

import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import exceptions.StatementException;
import model.adt.IMyMap;
import model.expressions.IExpression;
import model.state.ProgramState;
import model.types.IType;

public class PrintStmt implements IStmt {
    private IExpression expr;

    public PrintStmt (IExpression e) {
        this.expr = e;
    }

    public ProgramState execute(ProgramState state) throws ExpressionException {
      var res = expr.evaluate(state.getSymTable(), state.getHeap());
      state.getOut().add(res);
      
      return null;
    }

    public IMyMap<String, IType> typeCheck (IMyMap<String, IType> typeEnv) throws KeyNotFoundException, ExpressionException, StatementException {
        this.expr.typeCheck(typeEnv);
        return typeEnv;
    }
 
    @Override
    public String toString() {
      return  "print("+expr.toString()+")";
    }

    public IStmt deepCopy() {
        return new PrintStmt(expr);
    }
}


