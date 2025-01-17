package lab.example.view.commands;

import lab.example.controller.*;

public class RunExampleCommand extends Command {
    private IController ctr;

    public RunExampleCommand (String k, String desc, Controller c) {
        super(k, desc);
        this.ctr = c;
    }

    public IController getController () {
        return this.ctr;
    }

    @Override
    public void execute() {
        try {
            this.ctr.allStep();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
