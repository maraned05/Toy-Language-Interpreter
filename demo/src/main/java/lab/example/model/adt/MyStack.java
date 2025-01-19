package lab.example.model.adt;
import java.util.Stack;
import java.util.stream.Collectors;

import lab.example.exceptions.EmptyStackException;;

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

    public T peek() throws EmptyStackException {
        if (this.stack.isEmpty())
            throw new EmptyStackException("The stack is empty!");

        return this.stack.peek();
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

    public Stack<T> getStack() {
        return this.stack;
    }

    public String getInversedStack() {
    StringBuilder elemsInString = new StringBuilder();
        Stack<T> copyElementsReverse = new Stack<T>();
        T topOfStack;
        while (!this.stack.empty()) {
            topOfStack = this.stack.pop();
            copyElementsReverse.push(topOfStack);
            elemsInString.append(topOfStack.toString());
            elemsInString.append(", ");
        }
        String elemsInStringAsString = elemsInString.toString();
        if (elemsInStringAsString.length() > 2) {
            elemsInStringAsString = elemsInStringAsString.substring(0, elemsInStringAsString.length() - 2);
        }   
        while (!copyElementsReverse.empty()) {
            topOfStack = copyElementsReverse.pop();
            this.stack.push(topOfStack);
        }
        return elemsInStringAsString;
    }


    public void setStack(Stack<T> stack) {
        this.stack = stack;
    }

    public MyStack<T> deepCopy() {
        MyStack<T> newStack = new MyStack<T> ();
        newStack.getStack().addAll(this.stack.stream().collect(Collectors.toList()));
        return newStack;
    }
    
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (T item : stack) {
            str.append(item);
            str.append(" ");
        }
        return str.toString();
    }
}
