package model.state;
import model.adt.*;
import model.statements.*;
import model.values.IValue;
import model.values.StringValue;

import java.io.BufferedReader;


public class ProgramState {
    IMyStack<IStmt> exeStack;
    IMyMap<String, IValue> symTable;
    IMyList<IValue> out;
    IStmt originalProgram;
    IMyMap<StringValue, BufferedReader> fileTable;

    public ProgramState(IMyStack<IStmt> stk, IMyMap<String, IValue> symtbl, IMyList<IValue> out, IStmt prg, IMyMap<StringValue, BufferedReader> fTb) {
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

    public IMyMap<StringValue, BufferedReader> getFileTable() {
        return this.fileTable;
    }

    @Override
    public String toString() {
        return "Execution Stack: " + this.exeStack.toString() + "\nSymbols Table: " + this.symTable.toString() 
        + "\nOut: " + this.out.toString(); 
    }
}
