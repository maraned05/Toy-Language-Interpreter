package lab.example.repository;
import lab.example.exceptions.RepoException;
import lab.example.model.state.ProgramState;
import java.util.List;

public interface IRepository {
    void add(ProgramState state);
    void logPrgStateExec(ProgramState state) throws RepoException;
    List<ProgramState> getPrgList();
    void setPrgList(List<ProgramState> l);
    ProgramState getProgramState(int id) throws RepoException;
}
