package lab.example.model.state;
import lab.example.model.adt.*;
import lab.example.model.statements.*;
import lab.example.model.values.IValue;
import lab.example.model.values.StringValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import lab.example.exceptions.EmptyStackException;
import lab.example.exceptions.ExpressionException;
import lab.example.exceptions.StatementException;


public class ProgramState {
    private IMyStack<IStmt> exeStack;
    private IMyMap<String, IValue> symTable;
    private IMyList<IValue> out;
    private IMyMap<StringValue, BufferedReader> fileTable;
    private IMyHeap<Integer, IValue> heap;
    private ILockTable lockTable;
    public static ReadWriteLock lock = new ReentrantReadWriteLock();
    public static Lock lock1 = new ReentrantLock();

    private int id;
    private static int noOfPrograms = 0;

    public ProgramState(IMyStack<IStmt> stk, IMyMap<String, IValue> symtbl, IMyList<IValue> out, IStmt prg, IMyMap<StringValue, BufferedReader> fTb, 
    IMyHeap<Integer, IValue> h, ILockTable lockTable) {
        this.exeStack = stk;
        this.symTable = symtbl;
        this.out = out;

        if (prg != null)
            this.exeStack.push(prg);
            
        this.fileTable = fTb;
        this.heap = h;
        this.lockTable = lockTable;

        lock1.lock();
        noOfPrograms++;
        lock1.unlock();
        this.id = noOfPrograms;
    }

    public int getId() {
        return this.id;
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

    public ILockTable getLockTable() {
        return this.lockTable;
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
        return "Program id: " + String.valueOf(this.id) + "\n" + "Execution Stack: " + this.exeStack.getInversedStack() + "\nSymbols Table: " + this.symTable.toString() 
        + "\nOut: " + this.out.toString() + "\nHeap: " + this.heap.toString() + "\nLock Table: " + this.lockTable.toString(); 
    }
}
