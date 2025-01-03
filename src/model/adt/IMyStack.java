package model.adt;

import exceptions.EmptyStackException;

public interface IMyStack<T> {
    T peek() throws EmptyStackException;
    T pop() throws EmptyStackException;
    void push(T elem);
    int getSize();
    boolean isEmpty();
    String getInversedStack();
}
