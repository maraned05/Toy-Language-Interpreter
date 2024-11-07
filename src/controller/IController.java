package controller;

import exceptions.EmptyStackException;
import exceptions.ExpressionException;
import exceptions.RepoException;
import exceptions.StatementException;
import model.state.ProgramState;

public interface IController {
    public ProgramState oneStep(ProgramState state) throws EmptyStackException, StatementException, ExpressionException;
    public void allStep() throws EmptyStackException, StatementException, ExpressionException, RepoException;
    public void addPrgState(ProgramState state);
}
