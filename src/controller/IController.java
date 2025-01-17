package lab.example.controller;

import lab.example.exceptions.RepoException;
import lab.example.model.state.ProgramState;

public interface IController {
    //public ProgramState oneStep(ProgramState state) throws EmptyStackException, StatementException, ExpressionException;
    public void allStep() throws Exception, RepoException, InterruptedException;
    public void addPrgState(ProgramState state);
}
