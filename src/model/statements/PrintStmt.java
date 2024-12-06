package model.statements;

import exceptions.ExpressionException;
import model.expressions.IExpression;
import model.state.ProgramState;

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

    @Override
    public String toString() {
      return  "print("+expr.toString()+")";
    }

    public IStmt deepCopy() {
        return new PrintStmt(expr);
    }
}


