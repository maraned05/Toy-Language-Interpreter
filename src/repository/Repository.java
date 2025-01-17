package lab.example.repository;

import java.util.ArrayList;
import java.util.List;
import lab.example.exceptions.RepoException;
import lab.example.model.state.ProgramState;
import java.io.*;

public class Repository implements IRepository {
    private List<ProgramState> states;
    private String fileName;

    public Repository(String file) {
        this.states = new ArrayList<ProgramState>();
        this.fileName = file;
    } 

    public void add(ProgramState state) {
        this.states.add(state);
    }

    public void logPrgStateExec(ProgramState state) throws RepoException{
        try {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
            writer.println(state + "\n");
            writer.close();
        }
        catch (IOException exc) {
            throw new RepoException("Cannot write in the file!\n");
        }
    }

    public ProgramState getProgramState(int id) throws RepoException{
        for (ProgramState state : this.states) {
            if (state.getId() == id)
                return state;
        }
        
        throw new RepoException("Invalid Program State Id!");
    }

    public List<ProgramState> getPrgList() {
        return this.states;
    }

    public void setPrgList(List<ProgramState> l) {
        this.states = l;
    }
}
