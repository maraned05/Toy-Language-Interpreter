package lab.example.model.state;
import lab.example.model.adt.*;
import lab.example.model.statements.*;
import lab.example.model.values.IValue;
import lab.example.model.values.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

import java.util.List;
import javafx.util.Pair;

import lab.example.exceptions.EmptyStackException;
import lab.example.exceptions.ExpressionException;
import lab.example.exceptions.StatementException;


public class ProgramState {
    private IMyStack<IStmt> exeStack;
    private IMyStack<IMyMap<String, IValue>> symTables;
    private IMyList<IValue> out;
    private IMyMap<StringValue, BufferedReader> fileTable;
    private IMyHeap<Integer, IValue> heap;
    private IProcTable<String, Pair<List<String>, IStmt>> procTable;

    private int id;
    private static int noOfPrograms = 0;

    public ProgramState(IMyStack<IStmt> stk, IMyStack<IMyMap<String, IValue>> symtbls, IMyList<IValue> out, IStmt prg, IMyMap<StringValue, 
    BufferedReader> fTb, IMyHeap<Integer, IValue> h, IProcTable<String, Pair<List<String>, IStmt>> procTable) {
        this.exeStack = stk;
        this.symTables = symtbls;
        if (this.symTables.isEmpty())
            this.symTables.push(new MyMap<String, IValue> ());
        this.out = out;

        if (prg != null)
            this.exeStack.push(prg);
            
        this.fileTable = fTb;
        this.heap = h;
        this.procTable = procTable;
        
        noOfPrograms++;
        this.id = noOfPrograms;
    }

    public int getId() {
        return this.id;
    }

    public IMyStack<IStmt> getExeStack() {
        return this.exeStack;
    }

    public IMyMap<String, IValue> getCurrentSymTable() {
        try {
            return this.symTables.peek();
        }
        catch (EmptyStackException e) {
            return new MyMap<String, IValue> ();
        }
    }

    public void addSymTable(IMyMap<String, IValue> symtbl) {
        this.symTables.push(symtbl);
    }

    public void removeSymTable() throws EmptyStackException {
        this.symTables.pop();
    }

    public IMyStack<IMyMap<String, IValue>> getSymTables() {
        return this.symTables;
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

    public IProcTable<String, Pair<List<String>, IStmt>> getProcTable() {
        return this.procTable;
    }

    public boolean isNotCompleted() {
        return ! (this.exeStack.isEmpty());
    }

    public ProgramState oneStep() throws EmptyStackException, StatementException, ExpressionException, IOException {
        if (this.exeStack.isEmpty())
            throw new EmptyStackException("The execution stack is empty.");

        IStmt currentStmt = this.exeStack.pop();
        return currentStmt.execute(this);
    }

    @Override
    public String toString() {
        return "Program id: " + String.valueOf(this.id) + "\n" + "Execution Stack: " + this.exeStack.getInversedStack() + "\nSymbols Table: " + this.getCurrentSymTable().toString() 
        + "\nOut: " + this.out.toString() + "\nHeap: " + this.heap.toString() + "\nProcedures Table: " + this.procTable.toString(); 
    }
}
