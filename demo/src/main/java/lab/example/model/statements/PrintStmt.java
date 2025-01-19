package lab.example.model.statements;

import lab.example.exceptions.ExpressionException;
import lab.example.exceptions.KeyNotFoundException;
import lab.example.exceptions.StatementException;
import lab.example.model.adt.IMyMap;
import lab.example.model.expressions.IExpression;
import lab.example.model.state.ProgramState;
import lab.example.model.types.IType;

public class PrintStmt implements IStmt {
    private IExpression expr;

    public PrintStmt (IExpression e) {
        this.expr = e;
    }

    public ProgramState execute(ProgramState state) throws ExpressionException {
      var res = expr.evaluate(state.getCurrentSymTable(), state.getHeap());
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


