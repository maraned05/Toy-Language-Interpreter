package lab.example.view;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import lab.example.controller.Controller;
import lab.example.model.adt.MyHeap;
import lab.example.model.adt.MyList;
import lab.example.model.adt.MyMap;
import lab.example.model.adt.MyStack;
import lab.example.model.expressions.ArithmeticExpression;
import lab.example.model.expressions.ArithmeticOperation;
import lab.example.model.expressions.ValueExpression;
import lab.example.model.expressions.VariableExpression;
import lab.example.model.state.ProgramState;
import lab.example.model.statements.AssignStmt;
import lab.example.model.statements.CompStmt;
import lab.example.model.statements.IStmt;
import lab.example.model.statements.PrintStmt;
import lab.example.model.statements.VarDeclStmt;
import lab.example.model.types.IntType;
import lab.example.model.values.IValue;
import lab.example.model.values.IntValue;
import lab.example.model.values.StringValue;
import lab.example.repository.Repository;
import lab.example.view.commands.Command;
import lab.example.view.commands.ExitCommand;
import lab.example.view.commands.RunExampleCommand;

public class TextMenu {
    private Map<String, Command> commands;
    
    public TextMenu() {
        this.commands = new HashMap<String, Command>();
    }

    public void populateMenu() {
        IStmt statement1 = new CompStmt (new VarDeclStmt("v",new IntType()),
        new CompStmt (new AssignStmt ("v", new ValueExpression(new IntValue(2))), 
        new PrintStmt(new VariableExpression("v"))));

        ProgramState state1 = new ProgramState(new MyStack<IStmt>(), new MyMap<String, IValue>(), 
        new MyList<IValue>(), statement1, new MyMap<StringValue, BufferedReader>(), new MyHeap<Integer, IValue>());

        Repository repo1 = new Repository("log1.txt");
        repo1.add(state1);
        Controller ctr1 = new Controller(repo1, true);


        IStmt statement2 = new CompStmt (new VarDeclStmt("a",new IntType()),
        new CompStmt (new VarDeclStmt("b",new IntType()),
        new CompStmt (new AssignStmt("a", new ArithmeticExpression (new ValueExpression(new IntValue(2)),
        new ArithmeticExpression (new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5)), ArithmeticOperation.MULTIPLY),
        ArithmeticOperation.ADD)), new CompStmt(new AssignStmt("b",new ArithmeticExpression(new VariableExpression("a"), new ValueExpression(new
        IntValue(1)), ArithmeticOperation.ADD)), new PrintStmt(new VariableExpression("b")))))
        );

        ProgramState state2 = new ProgramState(new MyStack<IStmt>(), new MyMap<String, IValue>(), 
        new MyList<IValue>(), statement2, new MyMap<StringValue, BufferedReader>(), new MyHeap<Integer, IValue>());

        Repository repo2 = new Repository("log2.txt");
        repo2.add(state2);
        Controller ctr2 = new Controller(repo2, true);

        this.addCommand(new RunExampleCommand("1", statement1.toString(), ctr1));
        this.addCommand(new RunExampleCommand("2", statement2.toString(), ctr2));
        this.addCommand(new ExitCommand("3", "Exit the application."));
    }

    public void addCommand(Command c) {
        this.commands.put(c.getKey(), c);
    }

    public Command getCommand(String key) {
        return this.commands.get(key);
    }

    public List<String> getCommandsDesc() {
        ArrayList<String> result = new ArrayList<String>();

        for (Command c : this.commands.values())
            result.add(c.getKey() + ". " + c.getDescription());

        return result;
    }

    private void printMenu() {
        for (Command c : this.commands.values()) {
            String line = String.format("%4s: %s", c.getKey(), c.getDescription());
            System.out.println(line);
        }
    }

    public void show() {
        Scanner scanner = new Scanner(System.in);
        this.printMenu();
        System.out.println("Input the option: ");
        String key = scanner.nextLine();
        scanner.close();
        Command c = this.commands.get(key);
        if (c == null) 
            System.out.println("Invalid Option!\n");
            
        else c.execute();
    }
}
