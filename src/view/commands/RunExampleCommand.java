package view.commands;

import controller.*;
import controller.IController;

public class RunExampleCommand extends Command {
    private IController ctr;
    public RunExampleCommand (String k, String desc, Controller c) {
        super(k, desc);
        this.ctr = c;
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
