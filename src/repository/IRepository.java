package repository;
import exceptions.RepoException;
import model.state.ProgramState;
import java.util.List;

public interface IRepository {
    void add(ProgramState state);
    ProgramState getCurrent();
    void logPrgStateExec() throws RepoException;
    List<ProgramState> getPrgList();
    void setPrgList(List<ProgramState> l);
}
