package lab.example.model.statements;

import lab.example.exceptions.ExpressionException;
import lab.example.exceptions.KeyNotFoundException;
import lab.example.exceptions.StatementException;
import lab.example.model.adt.IMyMap;
import lab.example.model.state.*;
import lab.example.model.types.IType;

public interface IStmt {
    public ProgramState execute (ProgramState state) throws StatementException, ExpressionException;
    IMyMap<String, IType> typeCheck (IMyMap<String, IType> typeEnv) throws KeyNotFoundException, ExpressionException, StatementException;
    public IStmt deepCopy();
}
