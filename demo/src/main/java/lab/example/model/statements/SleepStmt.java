package lab.example.model.statements;

import lab.example.exceptions.ExpressionException;
import lab.example.exceptions.StatementException;
import lab.example.model.adt.IMyMap;
import lab.example.model.state.ProgramState;
import lab.example.model.types.IType;

public class SleepStmt implements IStmt {
    private int number;

    public SleepStmt (int n) {
        this.number = n;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException {
        if (this.number > 0)
            state.getExeStack().push(new SleepStmt(number - 1));

        return null; 
    }

    @Override
    public IMyMap<String, IType> typeCheck(IMyMap<String, IType> typeEnv) {
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new SleepStmt(number);
    }
    
    @Override
    public String toString() {
        return "sleep(" + this.number + ")";
    }
}
