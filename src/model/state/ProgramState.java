package model.state;
import model.adt.*;
import model.statements.*;
import model.values.IValue;
import model.values.StringValue;

import java.io.BufferedReader;

import exceptions.EmptyStackException;
import exceptions.ExpressionException;
import exceptions.StatementException;


public class ProgramState {
    private IMyStack<IStmt> exeStack;
    private IMyMap<String, IValue> symTable;
    private IMyList<IValue> out;
    private IMyMap<StringValue, BufferedReader> fileTable;
    private IMyHeap<Integer, IValue> heap;

    private int id;
    private static int noOfPrograms = 0;

    public ProgramState(IMyStack<IStmt> stk, IMyMap<String, IValue> symtbl, IMyList<IValue> out, IStmt prg, IMyMap<StringValue, BufferedReader> fTb, IMyHeap<Integer, IValue> h) {
        this.exeStack = stk;
        this.symTable = symtbl;
        this.out = out;

        if (prg != null)
            this.exeStack.push(prg);
            
        this.fileTable = fTb;
        this.heap = h;

        noOfPrograms++;
        this.id = noOfPrograms;
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

    public IMyHeap<Integer, IValue> getHeap() {
        return this.heap;
    }

    public boolean isNotCompleted() {
        return ! (this.exeStack.isEmpty());
    }

    public ProgramState oneStep() throws EmptyStackException, StatementException, ExpressionException {
        if (this.exeStack.isEmpty())
            throw new EmptyStackException("The execution stack is empty.");

        IStmt currentStmt = this.exeStack.pop();
        return currentStmt.execute(this);
    }

    @Override
    public String toString() {
        return "Program id: " + String.valueOf(this.id) + "\n" + "Execution Stack: " + this.exeStack.getInversedStack() + "\nSymbols Table: " + this.symTable.toString() 
        + "\nOut: " + this.out.toString() + "\nHeap: " + this.heap.toString(); 
    }
}
