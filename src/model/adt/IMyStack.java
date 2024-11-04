package model.adt;
import exceptions.EmptyStackException;

public interface IMyStack<T> {
    T pop() throws EmptyStackException;
    void push(T elem);
    int getSize();
    boolean isEmpty();
}
