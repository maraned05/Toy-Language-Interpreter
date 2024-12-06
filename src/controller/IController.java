package controller;

import exceptions.RepoException;
import model.state.ProgramState;

public interface IController {
    //public ProgramState oneStep(ProgramState state) throws EmptyStackException, StatementException, ExpressionException;
    public void allStep() throws RepoException, InterruptedException;
    public void addPrgState(ProgramState state);
}
