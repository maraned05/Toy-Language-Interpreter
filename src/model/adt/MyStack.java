package model.adt;
import java.util.Stack;

import exceptions.EmptyStackException;;

public class MyStack<T> implements IMyStack<T> {
    private Stack<T> stack;

    public MyStack() {
        this.stack = new Stack<T>();
    }

    public T pop() throws EmptyStackException {
        if (this.stack.isEmpty())
            throw new EmptyStackException("The stack is empty!");

        return stack.pop();
    }

    public void push(T elem) {
        this.stack.push(elem);
    }

    public int getSize() {
        return this.stack.size();
    }

    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    Stack<T> getStack() {
        return this.stack;
    }

    void setStack(Stack<T> stack) {
        this.stack = stack;
    }
    
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (T item : stack) {
            str.append(item);
            str.append("\n");
        }
        return "MyStack contains: " + str.toString();
    }
}
