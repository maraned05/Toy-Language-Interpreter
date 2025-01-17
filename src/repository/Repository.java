package repository;

import java.util.ArrayList;
import java.util.List;
import exceptions.RepoException;
import model.state.ProgramState;
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

    public List<ProgramState> getPrgList() {
        return this.states;
    }

    public void setPrgList(List<ProgramState> l) {
        this.states = l;
    }
}
