package model.statements;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.state.*;

public interface IStmt {
    public ProgramState execute (ProgramState state) throws StatementException, ExpressionException;
    public IStmt deepCopy();
}
