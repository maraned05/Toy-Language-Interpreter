package repository;
import exceptions.RepoException;
import model.state.ProgramState;

public interface IRepository {
    public void add(ProgramState state);
    public ProgramState getCurrent();
    public void logPrgStateExec() throws RepoException;
}
