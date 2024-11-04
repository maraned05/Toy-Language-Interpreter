package model.state;
import model.adt.*;
import model.statements.*;
import model.values.IValue;
import java.io.BufferedReader;


public class ProgramState {
    IMyStack<IStmt> exeStack;
    IMyMap<String, IValue> symTable;
    IMyList<IValue> out;
    IStmt originalProgram;
    IMyMap<String, BufferedReader> fileTable;

    ProgramState(IMyStack<IStmt> stk, IMyMap<String, IValue> symtbl, IMyList<IValue> out, IStmt prg, IMyMap<String, BufferedReader> fTb) {
        this.exeStack = stk;
        this.symTable = symtbl;
        this.out = out;
        this.originalProgram = prg;
        this.exeStack.push(prg);
        this.fileTable = fTb;
    }

    public IMyStack<IStmt> getExeStack() {
        return this.exeStack;
    }

    public IMyMap<String, IValue> getSymTable() {
        return this.symTable;
    }

    public IMyList<IValue> getOut() {
        return this.out;
    }

    public IMyMap<String, BufferedReader> getFileTable() {
        return this.fileTable;
    }
}
