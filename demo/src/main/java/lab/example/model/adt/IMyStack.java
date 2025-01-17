package lab.example.model.adt;

import java.util.Stack;

import lab.example.exceptions.EmptyStackException;

public interface IMyStack<T> {
    T peek() throws EmptyStackException;
    T pop() throws EmptyStackException;
    Stack<T> getStack();
    void push(T elem);
    int getSize();
    boolean isEmpty();
    String getInversedStack();
}
