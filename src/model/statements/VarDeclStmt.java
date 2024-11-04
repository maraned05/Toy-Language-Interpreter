package model.statements;

import exceptions.StatementException;
import model.state.ProgramState;
import model.types.BoolType;
import model.types.IType;
import model.types.IntType;
import model.values.BoolValue;
import model.values.IntValue;

public class VarDeclStmt implements IStmt {
    private String name;
    private IType varType;

    public VarDeclStmt(String name, IType type) {
        this.name = name;
        this.varType = type;
    }

    public ProgramState execute (ProgramState state) throws StatementException {

        if (state.getSymTable().contains(this.name))
            throw new StatementException("Variable already declared!");

        if (this.varType.equals(new IntType())) 
            state.getSymTable().insert(this.name, new IntValue(0));
        
        else if (this.varType.equals(new BoolType()))
            state.getSymTable().insert(this.name, new BoolValue(false));

        return state;
    }

    public IStmt deepCopy() {
        return new VarDeclStmt(name, varType);
    }
}
