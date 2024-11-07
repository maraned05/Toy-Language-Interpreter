import java.io.BufferedReader;

import controller.*;
import model.adt.MyList;
import model.adt.MyMap;
import model.adt.MyStack;
import model.expressions.ArithmeticExpression;
import model.expressions.ArithmeticOperation;
import model.expressions.RelationalExpression;
import model.expressions.RelationalOperation;
import model.expressions.ValueExpression;
import model.expressions.VariableExpression;
import model.state.ProgramState;
import model.statements.AssignStmt;
import model.statements.CloseRFileStmt;
import model.statements.CompStmt;
import model.statements.IStmt;
import model.statements.IfStmt;
import model.statements.OpenRFileStmt;
import model.statements.PrintStmt;
import model.statements.ReadFileStmt;
import model.statements.VarDeclStmt;
import model.types.BoolType;
import model.types.IntType;
import model.types.StringType;
import model.values.BoolValue;
import model.values.IValue;
import model.values.IntValue;
import model.values.StringValue;
import repository.Repository;
import view.TextMenu;
import view.commands.ExitCommand;
import view.commands.RunExampleCommand;

public class App {
    public static void main(String[] args) throws Exception {
        IStmt statement1 = new CompStmt (new VarDeclStmt("v",new IntType()),
        new CompStmt (new AssignStmt ("v", new ValueExpression(new IntValue(2))), 
        new PrintStmt(new VariableExpression("v"))));

        ProgramState state1 = new ProgramState(new MyStack<IStmt>(), new MyMap<String, IValue>(), 
        new MyList<IValue>(), statement1, new MyMap<StringValue, BufferedReader>());

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
        new MyList<IValue>(), statement2, new MyMap<StringValue, BufferedReader>());

        Repository repo2 = new Repository("log2.txt");
        repo2.add(state2);
        Controller ctr2 = new Controller(repo2, true);


        IStmt statement3 = new CompStmt (new VarDeclStmt("a",new BoolType()),
        new CompStmt(new VarDeclStmt("v", new IntType()),
        new CompStmt(new AssignStmt("a", new ValueExpression(new BoolValue(true))),
        new CompStmt(new IfStmt(new VariableExpression("a"),new AssignStmt("v",new ValueExpression(new
        IntValue(2))), new AssignStmt("v", new ValueExpression(new IntValue(3)))), new PrintStmt(new
        VariableExpression("v"))))));

        ProgramState state3 = new ProgramState(new MyStack<IStmt>(), new MyMap<String, IValue>(), 
        new MyList<IValue>(), statement3, new MyMap<StringValue, BufferedReader>());

        Repository repo3 = new Repository("log3.txt");
        repo3.add(state3);
        Controller ctr3 = new Controller(repo3, true);

        
        IStmt statement4 = new CompStmt(new VarDeclStmt("varf", new StringType()), 
        new CompStmt(new AssignStmt("varf", new ValueExpression(new StringValue("test.in"))), 
        new CompStmt(new OpenRFileStmt(new VariableExpression("varf")), 
        new CompStmt(new VarDeclStmt("varc", new IntType()), 
        new CompStmt(new ReadFileStmt(new VariableExpression("varf"), "varc"), 
        new CompStmt(new PrintStmt(new VariableExpression("varc")), 
        new CompStmt(new ReadFileStmt(new VariableExpression("varf"), "varc"), 
        new CompStmt(new PrintStmt(new VariableExpression("varc")), 
        new CloseRFileStmt(new VariableExpression("varf"))))))))));

        ProgramState state4 = new ProgramState(new MyStack<IStmt>(), new MyMap<String, IValue>(), 
        new MyList<IValue>(), statement4, new MyMap<StringValue, BufferedReader>());

        Repository repo4 = new Repository("log4.txt");
        repo4.add(state4);
        Controller ctr4 = new Controller(repo4, true);


        // int a; int b; int c; a = 3; b = 4; if (a <= b) c = 1 else c = 0;
        IStmt statement5 = new CompStmt(new VarDeclStmt("a", new IntType()), 
        new CompStmt(new VarDeclStmt("b", new IntType()), 
        new CompStmt(new VarDeclStmt("c", new IntType()), 
        new CompStmt(new AssignStmt("a", new ValueExpression(new IntValue(3))), 
        new CompStmt(new AssignStmt("b", new ValueExpression(new IntValue(4))), 
        new IfStmt(new RelationalExpression(new VariableExpression("a"), new VariableExpression("b"), RelationalOperation.GREATER), 
        new AssignStmt("c", new ValueExpression(new IntValue(1))), 
        new AssignStmt("c", new ValueExpression(new IntValue(0)))))))));

        ProgramState state5 = new ProgramState(new MyStack<IStmt>(), new MyMap<String, IValue>(), 
        new MyList<IValue>(), statement5, new MyMap<StringValue, BufferedReader>());

        Repository repo5 = new Repository("log5.txt");
        repo5.add(state5);
        Controller ctr5 = new Controller(repo5, true);


        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExampleCommand("1", statement1.toString(), ctr1));
        menu.addCommand(new RunExampleCommand("2", statement2.toString(), ctr2));
        menu.addCommand(new RunExampleCommand("3", statement3.toString(), ctr3));
        menu.addCommand(new RunExampleCommand("4", statement4.toString(), ctr4));
        menu.addCommand(new RunExampleCommand("5", statement5.toString(), ctr5));
        menu.show();
    }
}
