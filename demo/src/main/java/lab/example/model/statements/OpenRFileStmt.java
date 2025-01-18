package lab.example.model.statements;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import lab.example.exceptions.ExpressionException;
import lab.example.exceptions.KeyNotFoundException;
import lab.example.exceptions.StatementException;
import lab.example.model.adt.IMyMap;
import lab.example.model.expressions.IExpression;
import lab.example.model.state.ProgramState;
import lab.example.model.types.IType;
import lab.example.model.types.StringType;
import lab.example.model.values.IValue;
import lab.example.model.values.StringValue;

public class OpenRFileStmt implements IStmt {
    private IExpression exp;

    public OpenRFileStmt(IExpression e) {
        this.exp = e;
    }

    public ProgramState execute (ProgramState state) throws StatementException, ExpressionException {
        IValue val = this.exp.evaluate(state.getSymTable(), state.getHeap());
        var fileTable = state.getFileTable();
        if (! val.getType().equals(new StringType())) 
            throw new StatementException("The read value is not a string.\n");
        
        StringValue res = (StringValue) val;
        if (fileTable.contains(res)) 
            throw new StatementException("File name already in the table!\n");
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader(res.getValue()));
            fileTable.insert(res, reader);
            return null;
        }
        catch (IOException e) {
            throw new StatementException("I/O Exception!\n");
        }

        //return null;
    }

    public IMyMap<String, IType> typeCheck (IMyMap<String, IType> typeEnv) throws KeyNotFoundException, ExpressionException, StatementException {
        IType type = this.exp.typeCheck(typeEnv);
        if (type.equals(new StringType()))
            return typeEnv;

        else throw new StatementException("Typechecker Error: The read value is not a string.");
    }

    public IStmt deepCopy() {
        return new OpenRFileStmt(exp);
    }

    @Override
    public String toString() {
        return "openFile(" + this.exp.toString() + ")";
    }
}
