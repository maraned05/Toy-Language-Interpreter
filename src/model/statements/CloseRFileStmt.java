package model.statements;

import java.io.BufferedReader;
import java.io.IOException;

import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import exceptions.StatementException;
import model.expressions.IExpression;
import model.state.ProgramState;
import model.types.StringType;
import model.values.IValue;
import model.values.StringValue;

public class CloseRFileStmt implements IStmt {
    private IExpression exp;

    public CloseRFileStmt (IExpression e) {
        this.exp = e; 
    }

    public ProgramState execute (ProgramState state) throws StatementException, ExpressionException {
        IValue val = this.exp.evaluate(state.getSymTable(), state.getHeap());
        if (! val.getType().equals(new StringType())) 
            throw new StatementException("Value read is not a string!\n");

        StringValue valString = (StringValue) val;
        var fileTable = state.getFileTable();
        BufferedReader r;
        try {
            r = fileTable.get(valString);   
        }   
        catch (KeyNotFoundException e) {
            throw new StatementException("File name doesn't exist in the table!\n");
        }

        try {
            r.close();
        }
        catch (IOException e) {
            throw new StatementException("I/O Exception trying to close file " + (valString).getValue());
        }

        try {
            fileTable.remove(valString);
        }
        catch (KeyNotFoundException e) {
            throw new StatementException("File name doesn't exist in the table!\n");
        }

        return state;
    }

    public IStmt deepCopy() {
        return new CloseRFileStmt(exp);
    }

    @Override
    public String toString() {
        return "closeFile(" + this.exp.toString() + ")";
    }
}
