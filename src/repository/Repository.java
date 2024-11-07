package repository;

import java.util.ArrayList;
import java.util.List;
import exceptions.RepoException;
import model.state.ProgramState;
import java.io.*;

public class Repository implements IRepository {
    private List<ProgramState> states;
    private int currentState;
    private String fileName;

    public Repository(String file) {
        this.states = new ArrayList<ProgramState>();
        this.currentState = 0;
        this.fileName = file;
    } 

    public void add(ProgramState state) {
        this.states.add(state);
    }

    public ProgramState getCurrent() {
        return this.states.get(this.currentState);
    }

    public void logPrgStateExec() throws RepoException{
        try {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
            writer.println(getCurrent() + "\n");
            writer.close();
        }
        catch (IOException exc) {
            throw new RepoException("Cannot write in the file!\n");
        }
    }
}
