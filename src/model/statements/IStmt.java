package model.statements;
import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import exceptions.StatementException;
import model.adt.IMyMap;
import model.state.*;
import model.types.IType;

public interface IStmt {
    public ProgramState execute (ProgramState state) throws StatementException, ExpressionException;
    IMyMap<String, IType> typeCheck (IMyMap<String, IType> typeEnv) throws KeyNotFoundException, ExpressionException, StatementException;
    public IStmt deepCopy();
}
