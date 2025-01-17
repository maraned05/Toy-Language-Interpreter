package lab.example.model.statements;

import lab.example.exceptions.ExpressionException;
import lab.example.exceptions.KeyNotFoundException;
import lab.example.exceptions.StatementException;
import lab.example.model.adt.IMyMap;
import lab.example.model.state.ProgramState;
import lab.example.model.types.BoolType;
import lab.example.model.types.IType;
import lab.example.model.types.IntType;
import lab.example.model.types.RefType;
import lab.example.model.types.StringType;


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

    public IMyMap<String, IType> typeCheck (IMyMap<String, IType> typeEnv) throws KeyNotFoundException, ExpressionException, StatementException {
        typeEnv.insert(this.name, this.varType);
        return typeEnv;
    }

    @Override
    public String toString() {
        return this.varType.toString() + " " + this.name;
    }

    public IStmt deepCopy() {
        return new VarDeclStmt(name, varType);
    }
}
