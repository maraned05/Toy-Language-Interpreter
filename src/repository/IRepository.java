package repository;
import exceptions.RepoException;
import model.state.ProgramState;
import java.util.List;

public interface IRepository {
    void add(ProgramState state);
    void logPrgStateExec(ProgramState state) throws RepoException;
    List<ProgramState> getPrgList();
    void setPrgList(List<ProgramState> l);
}
