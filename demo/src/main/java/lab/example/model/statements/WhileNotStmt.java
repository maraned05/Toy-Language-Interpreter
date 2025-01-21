package lab.example.model.statements;

import lab.example.exceptions.ExpressionException;
import lab.example.exceptions.KeyNotFoundException;
import lab.example.exceptions.StatementException;
import lab.example.model.adt.IMyMap;
import lab.example.model.expressions.IExpression;
import lab.example.model.state.ProgramState;
import lab.example.model.types.BoolType;
import lab.example.model.types.IType;
import lab.example.model.values.BoolValue;
import lab.example.model.values.IValue;

public class WhileNotStmt implements IStmt{
    private IExpression exp;
    private IStmt statement;

    public WhileNotStmt(IExpression e, IStmt s) {
        this.exp = e;
        this.statement = s;
    }

    public ProgramState execute (ProgramState state) throws StatementException, ExpressionException {
        IValue res = this.exp.evaluate(state.getSymTable(), state.getHeap());
        if (! (res.getType().equals(new BoolType())))   
            throw new ExpressionException("Expression isn't of bool type.");

        if (! ((BoolValue)res).getValue()) {
            state.getExeStack().push(this);
            state.getExeStack().push(statement);
            
        }

        return null;
    }

    public IMyMap<String, IType> typeCheck (IMyMap<String, IType> typeEnv) throws KeyNotFoundException, ExpressionException, StatementException {
        IType type = this.exp.typeCheck(typeEnv);
        if (type.equals(new BoolType())) {
            this.statement.typeCheck(typeEnv);
            return typeEnv;
        }
        else throw new ExpressionException("Typechecker Error: The WHILE condition isn't of bool type.");
    }

    @Override
    public IStmt deepCopy() {
        return new WhileStmt(exp, statement);
    }

    @Override
    public String toString() {
        return "while not (" + this.exp.toString() + ") do " + this.statement.toString();
    }
}
