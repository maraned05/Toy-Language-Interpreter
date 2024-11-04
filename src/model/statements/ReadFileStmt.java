package model.statements;

import java.io.BufferedReader;
import java.io.IOException;

import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import exceptions.StatementException;
import model.expressions.IExpression;
import model.state.ProgramState;
import model.types.IntType;
import model.types.StringType;
import model.values.*;

public class ReadFileStmt implements IStmt{
    private IExpression exp;
    private String varName;

    public ReadFileStmt(IExpression e, String v) {
        this.exp = e;
        this.varName = v;
    }

    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException {
        var table = state.getSymTable();

        try  {
            if (! table.get(varName).getType().equals(new IntType())) {
                throw new StatementException("Variable is not of type INT!\n");
            }
        }
        catch (KeyNotFoundException e) {
            throw new StatementException("Variable name is undefined!");
        }

        IValue res = exp.evaluate(state.getSymTable());
        if (! res.getType().equals(new StringType())) {
            throw new StatementException("Value read is not a string!\n");
        }

        BufferedReader r;
        try {
            r = state.getFileTable().get(varName);
        }
        catch (KeyNotFoundException e) {
            throw new StatementException("Variable name is undefined!");
        }
        

        try {
            String readResult = r.readLine();
            if (readResult == "") {
                readResult = "0";
            }
            int parsedResult = Integer.parseInt(readResult);
            state.getSymTable().insert(this.varName, new IntValue(parsedResult));
            return state;
        }
        catch (IOException e) {
            throw new StatementException("I/O Exception trying to read file " + ((StringValue) res).getValue());
        }
    }

    public IStmt deepCopy() {
        return new ReadFileStmt(exp, varName);
    }
}
