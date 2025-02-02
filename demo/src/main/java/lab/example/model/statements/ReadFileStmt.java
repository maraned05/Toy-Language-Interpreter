package lab.example.model.statements;

import java.io.BufferedReader;
import java.io.IOException;

import lab.example.exceptions.ExpressionException;
import lab.example.exceptions.KeyNotFoundException;
import lab.example.exceptions.StatementException;
import lab.example.model.adt.IMyMap;
import lab.example.model.expressions.IExpression;
import lab.example.model.state.ProgramState;
import lab.example.model.types.IType;
import lab.example.model.types.IntType;
import lab.example.model.types.StringType;
import lab.example.model.values.*;

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

        IValue res = exp.evaluate(state.getSymTable(), state.getHeap());
        if (! res.getType().equals(new StringType())) {
            throw new StatementException("The read value is not a string!\n");
        }

        BufferedReader r;
        try {
            r = state.getFileTable().get((StringValue) res);
        }
        catch (KeyNotFoundException e) {
            throw new StatementException("Variable name is undefined!");
        }
        

        try {
            String readResult = r.readLine();
            if (readResult == null) {
                readResult = "0";
            }
            int parsedResult = Integer.parseInt(readResult);
            state.getSymTable().insert(this.varName, new IntValue(parsedResult));
        }
        catch (IOException e) {
            throw new StatementException("I/O Exception trying to read file " + ((StringValue) res).getValue());
        }

        return null;
    }

    public IMyMap<String, IType> typeCheck (IMyMap<String, IType> typeEnv) throws KeyNotFoundException, ExpressionException, StatementException {
        IType typeVar = typeEnv.get(this.varName);
        IType typeExp = this.exp.typeCheck(typeEnv);
        if (typeVar.equals(new IntType())) {
            if (typeExp.equals(new StringType()))
                return typeEnv;

            else throw new StatementException("Typechecker Error: The read value is not a string!\n");
        } 
        else throw new StatementException("Typechecker Error: Variable is not of type INT!\n");
    }

    public IStmt deepCopy() {
        return new ReadFileStmt(exp, varName);
    }

    @Override
    public String toString() {
        return "readFile(" + this.exp.toString() + ", " + this.varName + ")";
    }
}
