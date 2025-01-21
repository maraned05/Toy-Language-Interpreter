package lab.example.model.statements;

import lab.example.exceptions.ExpressionException;
import lab.example.exceptions.KeyNotFoundException;
import lab.example.exceptions.StatementException;
import lab.example.model.adt.IMyMap;
import lab.example.model.expressions.IExpression;
import lab.example.model.state.ProgramState;
import lab.example.model.types.BoolType;
import lab.example.model.types.IType;

public class RepeatUntilStmt implements IStmt {
    private IStmt stmt;
    private IExpression exp;

    public RepeatUntilStmt (IStmt s, IExpression e) {
        this.stmt = s; this.exp = e;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException {
        // to inverse the val of exp
        IStmt newStmt = new CompStmt(this.stmt, new WhileNotStmt(this.exp, stmt));
        state.getExeStack().push(newStmt);
        return null;
    }

    @Override 
    public IMyMap<String, IType> typeCheck(IMyMap<String, IType> typeEnv) throws StatementException, ExpressionException, KeyNotFoundException {
        IType type = this.exp.typeCheck(typeEnv);
        if (! type.equals(new BoolType()))
            throw new ExpressionException("Type checker: The expression is not of type bool!");
        
        this.stmt.typeCheck(typeEnv);
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new RepeatUntilStmt(stmt, exp);
    }
    
    @Override
    public String toString() {
        return "repeat(" + this.stmt.toString() + ")" + " until " + this.exp;
    }
    
}
