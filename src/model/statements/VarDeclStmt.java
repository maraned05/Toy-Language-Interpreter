package model.statements;

import exceptions.StatementException;
import model.state.ProgramState;
import model.types.BoolType;
import model.types.IType;
import model.types.IntType;
import model.types.RefType;
import model.types.StringType;


public class VarDeclStmt implements IStmt {
    private String name;
    private IType varType;

    public VarDeclStmt(String name, IType type) {
        this.name = name;
        this.varType = type;
    }

    private static RefType createRef(RefType type) {
        if (! (type.getInnerType() instanceof RefType))
            return new RefType(type.getInnerType());

        RefType res = createRef(((RefType)type.getInnerType()));
        return new RefType(res);
    }

    public ProgramState execute (ProgramState state) throws StatementException {

        if (state.getSymTable().contains(this.name))
            throw new StatementException("Variable already declared!");

        if (this.varType.equals(new IntType())) 
            state.getSymTable().insert(this.name, new IntType().defaultValue());
        
        else if (this.varType.equals(new BoolType()))
            state.getSymTable().insert(this.name, new BoolType().defaultValue());

        else if (this.varType.equals(new StringType()))
            state.getSymTable().insert(this.name, new StringType().defaultValue());

        else if (this.varType instanceof RefType) {
            state.getSymTable().insert(this.name, createRef(((RefType)this.varType)).defaultValue());    
        }

        return null;
    }

    @Override
    public String toString() {
        return this.varType.toString() + " " + this.name;
    }

    public IStmt deepCopy() {
        return new VarDeclStmt(name, varType);
    }
}
