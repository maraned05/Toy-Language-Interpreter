package lab.example.model.statements;

import lab.example.exceptions.EmptyStackException;
import lab.example.exceptions.ExpressionException;
import lab.example.exceptions.StatementException;
import lab.example.model.adt.IMyMap;
import lab.example.model.state.ProgramState;
import lab.example.model.types.IType;

public class ReturnStmt implements IStmt {

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException {
        try {
            state.removeSymTable();
            return null;
        }
        catch (EmptyStackException e) {
            throw new StatementException("The SymTables Stack is empty!");
        }
    }

    @Override
    public IMyMap<String, IType> typeCheck(IMyMap<String, IType> typeEnv) {
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new ReturnStmt();
    }
    
    @Override
    public String toString() {
        return "return";
    }
}
