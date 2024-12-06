import java.io.BufferedReader;

import controller.*;
import model.adt.MyHeap;
import model.adt.MyList;
import model.adt.MyMap;
import model.adt.MyStack;
// import model.expressions.ArithmeticExpression;
// import model.expressions.ArithmeticOperation;
// import model.expressions.LogicalExpression;
// import model.expressions.LogicalOperation;
import model.expressions.ReadHeapExpression;
// import model.expressions.RelationalExpression;
// import model.expressions.RelationalOperation;
import model.expressions.ValueExpression;
import model.expressions.VariableExpression;
import model.state.ProgramState;
import model.statements.AssignStmt;
// import model.statements.CloseRFileStmt;
import model.statements.CompStmt;
import model.statements.ForkStmt;
import model.statements.HeapAllocStmt;
// import model.statements.HeapAllocStmt;
import model.statements.IStmt;
// import model.statements.IfStmt;
// import model.statements.OpenRFileStmt;
import model.statements.PrintStmt;
// import model.statements.ReadFileStmt;
import model.statements.VarDeclStmt;
// import model.statements.WhileStmt;
import model.statements.WriteHeapStmt;
// import model.types.BoolType;
import model.types.IntType;
import model.types.RefType;
// import model.types.StringType;
// import model.values.BoolValue;
import model.values.IValue;
import model.values.IntValue;
import model.values.StringValue;
import repository.Repository;
import view.TextMenu;
// import view.commands.ExitCommand;
import view.commands.RunExampleCommand;

public class App {
    public static void main(String[] args) throws Exception {
        /*
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


        IStmt statement3 = new CompStmt (new VarDeclStmt("a",new BoolType()),
        new CompStmt(new VarDeclStmt("v", new IntType()),
        new CompStmt(new AssignStmt("a", new ValueExpression(new BoolValue(true))),
        new CompStmt(new IfStmt(new VariableExpression("a"),new AssignStmt("v",new ValueExpression(new
        IntValue(2))), new AssignStmt("v", new ValueExpression(new IntValue(3)))), new PrintStmt(new
        VariableExpression("v"))))));

        ProgramState state3 = new ProgramState(new MyStack<IStmt>(), new MyMap<String, IValue>(), 
        new MyList<IValue>(), statement3, new MyMap<StringValue, BufferedReader>(), new MyHeap<Integer, IValue>());

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
        new MyList<IValue>(), statement4, new MyMap<StringValue, BufferedReader>(), new MyHeap<Integer, IValue>());

        Repository repo4 = new Repository("log4.txt");
        repo4.add(state4);
        Controller ctr4 = new Controller(repo4, true);


        IStmt statement5 = new CompStmt(new VarDeclStmt("a", new IntType()), 
        new CompStmt(new VarDeclStmt("b", new IntType()), 
        new CompStmt(new VarDeclStmt("c", new IntType()), 
        new CompStmt(new AssignStmt("a", new ValueExpression(new IntValue(3))), 
        new CompStmt(new AssignStmt("b", new ValueExpression(new IntValue(4))), 
        new IfStmt(new RelationalExpression(new VariableExpression("a"), new VariableExpression("b"), RelationalOperation.GREATER), 
        new AssignStmt("c", new ValueExpression(new IntValue(1))), 
        new AssignStmt("c", new ValueExpression(new IntValue(-1)))))))));

        ProgramState state5 = new ProgramState(new MyStack<IStmt>(), new MyMap<String, IValue>(), 
        new MyList<IValue>(), statement5, new MyMap<StringValue, BufferedReader>(), new MyHeap<Integer, IValue>());

        Repository repo5 = new Repository("log5.txt");
        repo5.add(state5);
        Controller ctr5 = new Controller(repo5, true);


        IStmt statement6 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), 
        new CompStmt(new HeapAllocStmt(new ValueExpression(new IntValue(20)), "v"), 
        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))), 
        new CompStmt(new HeapAllocStmt(new VariableExpression("v"), "a"), 
        new CompStmt(new PrintStmt(new VariableExpression("v")), 
        new PrintStmt(new VariableExpression("a")))))));

        ProgramState state6 = new ProgramState(new MyStack<IStmt>(), new MyMap<String, IValue>(), 
        new MyList<IValue>(), statement6, new MyMap<StringValue, BufferedReader>(), new MyHeap<Integer, IValue>());

        Repository repo6 = new Repository("log6.txt");
        repo6.add(state6);
        Controller ctr6 = new Controller(repo6, true);


        IStmt statement7 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), 
        new CompStmt(new HeapAllocStmt(new ValueExpression(new IntValue(20)), "v"), 
        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))), 
        new CompStmt(new HeapAllocStmt(new VariableExpression("v"), "a"), 
        new CompStmt(new PrintStmt(new ReadHeapExpression(new VariableExpression("v"))), 
        new PrintStmt(new ArithmeticExpression(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a"))), new ValueExpression(new IntValue(5)), ArithmeticOperation.ADD)))))));

        ProgramState state7 = new ProgramState(new MyStack<IStmt>(), new MyMap<String, IValue>(), 
        new MyList<IValue>(), statement7, new MyMap<StringValue, BufferedReader>(), new MyHeap<Integer, IValue>());

        Repository repo7 = new Repository("log7.txt");
        repo7.add(state7);
        Controller ctr7 = new Controller(repo7, true);


        IStmt statement8 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), 
        new CompStmt(new HeapAllocStmt(new ValueExpression(new IntValue(20)), "v"), 
        new CompStmt(new PrintStmt(new ReadHeapExpression(new VariableExpression("v"))),
        new CompStmt(new WriteHeapStmt(new ValueExpression(new BoolValue(true)), "v"), 
        new PrintStmt(new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v")), new ValueExpression(new IntValue(5)), ArithmeticOperation.ADD))))));

        ProgramState state8 = new ProgramState(new MyStack<IStmt>(), new MyMap<String, IValue>(), 
        new MyList<IValue>(), statement8, new MyMap<StringValue, BufferedReader>(), new MyHeap<Integer, IValue>());

        Repository repo8 = new Repository("log8.txt");
        repo8.add(state8);
        Controller ctr8 = new Controller(repo8, true);

        IStmt statement9 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), 
        new CompStmt(new HeapAllocStmt(new ValueExpression(new IntValue(20)), "v"), 
        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
        new CompStmt(new HeapAllocStmt(new VariableExpression("v"), "a"), 
        new CompStmt(new HeapAllocStmt(new ValueExpression(new IntValue(30)), "v"), 
        new PrintStmt(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a")))))))));

        ProgramState state9 = new ProgramState(new MyStack<IStmt>(), new MyMap<String, IValue>(), 
        new MyList<IValue>(), statement9, new MyMap<StringValue, BufferedReader>(), new MyHeap<Integer, IValue>());

        Repository repo9 = new Repository("log9.txt");
        repo9.add(state9);
        Controller ctr9 = new Controller(repo9, true);

        IStmt statement10 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), 
        new CompStmt(new HeapAllocStmt(new ValueExpression(new IntValue(20)), "v"), 
        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
        new CompStmt(new HeapAllocStmt(new VariableExpression("v"), "a"), 
        new CompStmt(new HeapAllocStmt(new ValueExpression(new IntValue(30)), "v"), 
        new CompStmt(new HeapAllocStmt(new VariableExpression("v"), "a"), 
        new PrintStmt(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a"))))))))));

        ProgramState state10 = new ProgramState(new MyStack<IStmt>(), new MyMap<String, IValue>(), 
        new MyList<IValue>(), statement10, new MyMap<StringValue, BufferedReader>(), new MyHeap<Integer, IValue>());

        Repository repo10 = new Repository("log10.txt");
        repo10.add(state10);
        Controller ctr10 = new Controller(repo10, true);

        IStmt statement11 = new CompStmt(new VarDeclStmt("v", new IntType()), 
        new CompStmt(new AssignStmt("v", new ValueExpression(new IntValue(2))), 
        new CompStmt(new VarDeclStmt("b", new IntType()), 
        new CompStmt(new AssignStmt("b", new ValueExpression(new IntValue(1))), 
        new WhileStmt(new LogicalExpression(new RelationalExpression(new VariableExpression("v"), 
        new ValueExpression(new IntValue(0)), RelationalOperation.GREATER), 
        new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(10)), RelationalOperation.LESS), LogicalOperation.AND), 
        new CompStmt(new AssignStmt("v", new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntValue(1)), ArithmeticOperation.SUBTRACT)), 
        new AssignStmt("b", new ArithmeticExpression(new VariableExpression("b"), new ValueExpression(new IntValue(2)), ArithmeticOperation.MULTIPLY))))))));

        ProgramState state11 = new ProgramState(new MyStack<IStmt>(), new MyMap<String, IValue>(), 
        new MyList<IValue>(), statement11, new MyMap<StringValue, BufferedReader>(), new MyHeap<Integer, IValue>());

        Repository repo11 = new Repository("log11.txt");
        repo11.add(state11);
        Controller ctr11 = new Controller(repo11, true);
        */


        IStmt statement12 = new CompStmt(new VarDeclStmt("v", new IntType()), 
        new CompStmt(new VarDeclStmt("a", new RefType(new IntType())), 
        new CompStmt(new AssignStmt("v", new ValueExpression(new IntValue(10))), 
        new CompStmt(new HeapAllocStmt(new ValueExpression(new IntValue(22)), "a"),
        new CompStmt(new ForkStmt(new CompStmt(new WriteHeapStmt(new ValueExpression(new IntValue(30)), "a"), 
        new CompStmt(new AssignStmt("v", new ValueExpression(new IntValue(32))), 
        new CompStmt(new PrintStmt(new VariableExpression("v")), new PrintStmt(new ReadHeapExpression(new VariableExpression("a"))))))), 
        new CompStmt(new PrintStmt(new VariableExpression("v")), 
        new PrintStmt(new ReadHeapExpression(new VariableExpression("a")))))))));

        ProgramState state12 = new ProgramState(new MyStack<IStmt>(), new MyMap<String, IValue>(), 
        new MyList<IValue>(), statement12, new MyMap<StringValue, BufferedReader>(), new MyHeap<Integer, IValue>());

        Repository repo12 = new Repository("log12.txt");
        repo12.add(state12);
        Controller ctr12 = new Controller(repo12, true);


        IStmt statement13 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), 
        new CompStmt(new HeapAllocStmt(new ValueExpression(new IntValue(20)), "v"), 
        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))), 
        new CompStmt(new HeapAllocStmt(new VariableExpression("v"), "a"), 
        new CompStmt(new ForkStmt(new CompStmt(new HeapAllocStmt(new ValueExpression(new IntValue(30)), "v"), 
        new HeapAllocStmt(new VariableExpression("v"), "a"))), 
        new HeapAllocStmt(new ValueExpression(new IntValue(50)), "v"))))));

        ProgramState state13 = new ProgramState(new MyStack<IStmt>(), new MyMap<String, IValue>(), 
        new MyList<IValue>(), statement13, new MyMap<StringValue, BufferedReader>(), new MyHeap<Integer, IValue>());

        Repository repo13 = new Repository("log13.txt");
        repo13.add(state13);
        Controller ctr13 = new Controller(repo13, true);

        TextMenu menu = new TextMenu();
        /* 
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExampleCommand("1", statement1.toString(), ctr1));
        menu.addCommand(new RunExampleCommand("2", statement2.toString(), ctr2));
        menu.addCommand(new RunExampleCommand("3", statement3.toString(), ctr3));
        menu.addCommand(new RunExampleCommand("4", statement4.toString(), ctr4));
        menu.addCommand(new RunExampleCommand("5", statement5.toString(), ctr5));
        menu.addCommand(new RunExampleCommand("6", statement6.toString(), ctr6));
        menu.addCommand(new RunExampleCommand("7", statement7.toString(), ctr7));
        menu.addCommand(new RunExampleCommand("8", statement8.toString(), ctr8)); 
        menu.addCommand(new RunExampleCommand("9", statement9.toString(), ctr9)); 
        menu.addCommand(new RunExampleCommand("10", statement10.toString(), ctr10)); 
        menu.addCommand(new RunExampleCommand("11", statement11.toString(), ctr11));
        */
        menu.addCommand(new RunExampleCommand("12", statement12.toString(), ctr12));
        menu.addCommand(new RunExampleCommand("13", statement13.toString(), ctr13));
        menu.show();
    }
}
