package lab.example.view;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.util.Pair;

import lab.example.controller.Controller;
import lab.example.controller.IController;
import lab.example.model.adt.IMyMap;
import lab.example.model.adt.IProcTable;
import lab.example.model.adt.MyHeap;
import lab.example.model.adt.MyList;
import lab.example.model.adt.MyMap;
import lab.example.model.adt.MyStack;
import lab.example.model.adt.ProcTable;
import lab.example.model.expressions.ArithmeticExpression;
import lab.example.model.expressions.ArithmeticOperation;
import lab.example.model.expressions.IExpression;
import lab.example.model.expressions.LogicalExpression;
import lab.example.model.expressions.LogicalOperation;
import lab.example.model.expressions.ReadHeapExpression;
import lab.example.model.expressions.RelationalExpression;
import lab.example.model.expressions.RelationalOperation;
import lab.example.model.expressions.ValueExpression;
import lab.example.model.expressions.VariableExpression;
import lab.example.model.state.ProgramState;
import lab.example.model.statements.AssignStmt;
import lab.example.model.statements.CallProcStmt;
import lab.example.model.statements.CloseRFileStmt;
import lab.example.model.statements.CompStmt;
import lab.example.model.statements.ForkStmt;
import lab.example.model.statements.HeapAllocStmt;
import lab.example.model.statements.IStmt;
import lab.example.model.statements.IfStmt;
import lab.example.model.statements.OpenRFileStmt;
import lab.example.model.statements.PrintStmt;
import lab.example.model.statements.ReadFileStmt;
import lab.example.model.statements.VarDeclStmt;
import lab.example.model.statements.WhileStmt;
import lab.example.model.statements.WriteHeapStmt;
import lab.example.model.types.BoolType;
import lab.example.model.types.IntType;
import lab.example.model.types.RefType;
import lab.example.model.types.StringType;
import lab.example.model.values.BoolValue;
import lab.example.model.values.IValue;
import lab.example.model.values.IntValue;
import lab.example.model.values.StringValue;
import lab.example.repository.Repository;

public class GUIMenu {
    private Map<String, IController> programs;
    private Map<String, IProcTable<String, Pair<List<String>, IStmt>>> procedures;

    public GUIMenu() {
        this.programs = new HashMap<String, IController>();
        this.procedures = new HashMap<String, IProcTable<String, Pair<List<String>, IStmt>>>();
    }

    public void populateMenuPrograms(IProcTable<String, Pair<List<String>, IStmt>> procTable) {
        List<IExpression> l = new ArrayList<IExpression>();
        List<IExpression> l1 = new ArrayList<IExpression>();
        l.add(new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntValue(10)), ArithmeticOperation.MULTIPLY));
        l.add(new VariableExpression("w"));
        l1.add(new VariableExpression("v"));
        l1.add(new VariableExpression("w"));
        IStmt statement2 = new CompStmt(new VarDeclStmt("v",  new IntType()), 
        new CompStmt(new AssignStmt("v", new ValueExpression(new IntValue(2))), 
        new CompStmt(new VarDeclStmt("w", new IntType()), 
        new CompStmt(new AssignStmt("w", new ValueExpression(new IntValue(5))), 
        new CompStmt(new CallProcStmt("sum", l), 
        new CompStmt(new PrintStmt(new VariableExpression("v")), 
        new ForkStmt(new CompStmt(new CallProcStmt("product", l1), 
        new ForkStmt(new CallProcStmt("sum", l1))))))))));

        ProgramState state2 = new ProgramState(new MyStack<IStmt>(), new MyStack<IMyMap<String, IValue>>(), 
        new MyList<IValue>(), statement2, new MyMap<StringValue, BufferedReader>(), new MyHeap<Integer, IValue>(), procTable);

        Repository repo2 = new Repository("log2.txt");
        repo2.add(state2);
        Controller ctr2 = new Controller(repo2, true);


        IStmt statement3 = new CompStmt (new VarDeclStmt("a",new BoolType()),
        new CompStmt(new VarDeclStmt("v", new IntType()),
        new CompStmt(new AssignStmt("a", new ValueExpression(new BoolValue(true))),
        new CompStmt(new IfStmt(new VariableExpression("a"),new AssignStmt("v",new ValueExpression(new
        IntValue(2))), new AssignStmt("v", new ValueExpression(new IntValue(3)))), new PrintStmt(new
        VariableExpression("v"))))));

        ProgramState state3 = new ProgramState(new MyStack<IStmt>(), new MyStack<IMyMap<String, IValue>>(), 
        new MyList<IValue>(), statement3, new MyMap<StringValue, BufferedReader>(), new MyHeap<Integer, IValue>(), procTable);

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

        ProgramState state4 = new ProgramState(new MyStack<IStmt>(), new MyStack<IMyMap<String, IValue>>(), 
        new MyList<IValue>(), statement4, new MyMap<StringValue, BufferedReader>(), new MyHeap<Integer, IValue>(), procTable);

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

        ProgramState state5 = new ProgramState(new MyStack<IStmt>(), new MyStack<IMyMap<String, IValue>>(), 
        new MyList<IValue>(), statement5, new MyMap<StringValue, BufferedReader>(), new MyHeap<Integer, IValue>(), procTable);

        Repository repo5 = new Repository("log5.txt");
        repo5.add(state5);
        Controller ctr5 = new Controller(repo5, true);


        IStmt statement6 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), 
        new CompStmt(new HeapAllocStmt(new ValueExpression(new IntValue(20)), "v"), 
        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))), 
        new CompStmt(new HeapAllocStmt(new VariableExpression("v"), "a"), 
        new CompStmt(new PrintStmt(new VariableExpression("v")), 
        new PrintStmt(new VariableExpression("a")))))));

        ProgramState state6 = new ProgramState(new MyStack<IStmt>(), new MyStack<IMyMap<String, IValue>>(), 
        new MyList<IValue>(), statement6, new MyMap<StringValue, BufferedReader>(), new MyHeap<Integer, IValue>(), procTable);

        Repository repo6 = new Repository("log6.txt");
        repo6.add(state6);
        Controller ctr6 = new Controller(repo6, true);


        IStmt statement7 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), 
        new CompStmt(new HeapAllocStmt(new ValueExpression(new IntValue(20)), "v"), 
        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))), 
        new CompStmt(new HeapAllocStmt(new VariableExpression("v"), "a"), 
        new CompStmt(new PrintStmt(new ReadHeapExpression(new VariableExpression("v"))), 
        new PrintStmt(new ArithmeticExpression(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a"))), new ValueExpression(new IntValue(5)), ArithmeticOperation.ADD)))))));

        ProgramState state7 = new ProgramState(new MyStack<IStmt>(), new MyStack<IMyMap<String, IValue>>(), 
        new MyList<IValue>(), statement7, new MyMap<StringValue, BufferedReader>(), new MyHeap<Integer, IValue>(), procTable);

        Repository repo7 = new Repository("log7.txt");
        repo7.add(state7);
        Controller ctr7 = new Controller(repo7, true);


        IStmt statement8 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), 
        new CompStmt(new HeapAllocStmt(new ValueExpression(new IntValue(20)), "v"), 
        new CompStmt(new PrintStmt(new ReadHeapExpression(new VariableExpression("v"))),
        new CompStmt(new WriteHeapStmt(new ValueExpression(new BoolValue(true)), "v"), 
        new PrintStmt(new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v")), new ValueExpression(new IntValue(5)), ArithmeticOperation.ADD))))));

        ProgramState state8 = new ProgramState(new MyStack<IStmt>(), new MyStack<IMyMap<String, IValue>>(), 
        new MyList<IValue>(), statement8, new MyMap<StringValue, BufferedReader>(), new MyHeap<Integer, IValue>(), procTable);

        Repository repo8 = new Repository("log8.txt");
        repo8.add(state8);
        Controller ctr8 = new Controller(repo8, true);

        IStmt statement9 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), 
        new CompStmt(new HeapAllocStmt(new ValueExpression(new IntValue(20)), "v"), 
        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
        new CompStmt(new HeapAllocStmt(new VariableExpression("v"), "a"), 
        new CompStmt(new HeapAllocStmt(new ValueExpression(new IntValue(30)), "v"), 
        new PrintStmt(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a")))))))));

        ProgramState state9 = new ProgramState(new MyStack<IStmt>(), new MyStack<IMyMap<String, IValue>>(), 
        new MyList<IValue>(), statement9, new MyMap<StringValue, BufferedReader>(), new MyHeap<Integer, IValue>(), procTable);

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

        ProgramState state10 = new ProgramState(new MyStack<IStmt>(), new MyStack<IMyMap<String, IValue>>(), 
        new MyList<IValue>(), statement10, new MyMap<StringValue, BufferedReader>(), new MyHeap<Integer, IValue>(), procTable);

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

        ProgramState state11 = new ProgramState(new MyStack<IStmt>(), new MyStack<IMyMap<String, IValue>>(), 
        new MyList<IValue>(), statement11, new MyMap<StringValue, BufferedReader>(), new MyHeap<Integer, IValue>(), procTable);

        Repository repo11 = new Repository("log11.txt");
        repo11.add(state11);
        Controller ctr11 = new Controller(repo11, true);

        IStmt statement13 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), 
        new CompStmt(new HeapAllocStmt(new ValueExpression(new IntValue(20)), "v"), 
        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))), 
        new CompStmt(new HeapAllocStmt(new VariableExpression("v"), "a"), 
        new CompStmt(new ForkStmt(new CompStmt(new HeapAllocStmt(new ValueExpression(new IntValue(30)), "v"), 
        new HeapAllocStmt(new VariableExpression("v"), "a"))), 
        new CompStmt(new HeapAllocStmt(new ValueExpression(new IntValue(50)), "v"), 
        new CompStmt(new HeapAllocStmt(new VariableExpression("v"), "a"), 
        new PrintStmt(new ReadHeapExpression(new VariableExpression("v"))))))))));

        ProgramState state13 = new ProgramState(new MyStack<IStmt>(), new MyStack<IMyMap<String, IValue>>(), 
        new MyList<IValue>(), statement13, new MyMap<StringValue, BufferedReader>(), new MyHeap<Integer, IValue>(), procTable);

        Repository repo13 = new Repository("log13.txt");
        repo13.add(state13);
        Controller ctr13 = new Controller(repo13, true);


        IStmt statement14 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), 
        new CompStmt(new HeapAllocStmt(new ValueExpression(new IntValue(20)), "v"), 
        new CompStmt(new ForkStmt(new CompStmt(new WriteHeapStmt(new ValueExpression(new IntValue(30)), "v"), 
        new HeapAllocStmt(new ValueExpression(new IntValue(50)), "v"))), 
        new CompStmt(new ForkStmt(new WriteHeapStmt(new ValueExpression(new IntValue(40)), "v")), 
        new PrintStmt(new ReadHeapExpression(new VariableExpression("v")))))));

        ProgramState state14 = new ProgramState(new MyStack<IStmt>(), new MyStack<IMyMap<String, IValue>>(), 
        new MyList<IValue>(), statement14, new MyMap<StringValue, BufferedReader>(), new MyHeap<Integer, IValue>(), procTable);

        Repository repo14 = new Repository("log14.txt");
        repo14.add(state14);
        Controller ctr14 = new Controller(repo14, true);


        IStmt badstatement = new CompStmt(new VarDeclStmt("v", new IntType()),
        new CompStmt(new HeapAllocStmt(new ValueExpression(new IntValue(20)), "v"), 
        new CompStmt(new ForkStmt(new CompStmt(new HeapAllocStmt(new ValueExpression(new IntValue(30)), "v"), 
        new ForkStmt(new HeapAllocStmt(new ValueExpression(new IntValue(40)), "v")))), 
        new PrintStmt(new ReadHeapExpression(new VariableExpression("v"))))));

        ProgramState stateb = new ProgramState(new MyStack<IStmt>(), new MyStack<IMyMap<String, IValue>>(), 
        new MyList<IValue>(), badstatement, new MyMap<StringValue, BufferedReader>(), new MyHeap<Integer, IValue>(), procTable);

        Repository repob = new Repository("logb.txt");
        repob.add(stateb);
        Controller ctrb = new Controller(repob, true);

        IStmt badstatement1 = new CompStmt(new VarDeclStmt("v", new IntType()),
        new WhileStmt(new ValueExpression(new IntValue(5)), new PrintStmt(new VariableExpression("v"))));

        ProgramState stateb1 = new ProgramState(new MyStack<IStmt>(), new MyStack<IMyMap<String, IValue>>(), 
        new MyList<IValue>(), badstatement1, new MyMap<StringValue, BufferedReader>(), new MyHeap<Integer, IValue>(), procTable);

        Repository repob1 = new Repository("logb1.txt");
        repob1.add(stateb1);
        Controller ctrb1 = new Controller(repob1, true);

        this.addProgram(statement2.toString(), ctr2);
        this.addProgram(statement3.toString(), ctr3);
        this.addProgram(statement4.toString(), ctr4);
        this.addProgram(statement5.toString(), ctr5);
        this.addProgram(statement6.toString(), ctr6);
        this.addProgram(statement7.toString(), ctr7);
        this.addProgram(statement8.toString(), ctr8);
        this.addProgram(statement9.toString(), ctr9);
        this.addProgram(statement10.toString(), ctr10);
        this.addProgram(statement11.toString(), ctr11);
        this.addProgram(statement13.toString(), ctr13);
        this.addProgram(statement14.toString(), ctr14);
        this.addProgram(badstatement.toString(), ctrb);
        this.addProgram(badstatement1.toString(), ctrb1);
    }

    public void populateMenuProcedures() {
        List<String> parameters = new ArrayList<String>();
        parameters.add("a"); parameters.add("b");

        IProcTable<String, Pair<List<String>, IStmt>> procTable1 = new ProcTable<String,Pair<List<String>,IStmt>>();
        IStmt statement1 = new CompStmt(new VarDeclStmt("v", new IntType()), 
        new CompStmt(new AssignStmt("v", new ArithmeticExpression(new VariableExpression("a"), 
        new VariableExpression("b"), ArithmeticOperation.ADD)), new PrintStmt(new VariableExpression("v"))));
        procTable1.set("sum", new Pair<List<String>,IStmt>(parameters, statement1));

        IStmt statement2 = new CompStmt(new VarDeclStmt("v", new IntType()), 
        new CompStmt(new AssignStmt("v", new ArithmeticExpression(new VariableExpression("a"), 
        new VariableExpression("b"), ArithmeticOperation.MULTIPLY)), new PrintStmt(new VariableExpression("v"))));
        procTable1.set("product", new Pair<List<String>,IStmt>(parameters, statement2));

        String procDesc1 = "sum(a, b) v = a + b; print(v); \nproduct(a, b) v = a * b; print(v);";

        this.addProcedure(procDesc1, procTable1);
    }

    public void addProgram(String desc, IController ctr) {
        this.programs.put(desc, ctr);
    }

    public void addProcedure(String desc, IProcTable<String, Pair<List<String>, IStmt>> table) {
        this.procedures.put(desc, table);
    }

    public List<String> getProgramsDesc() {
        List<String> result = new ArrayList<String>();
        for (String desc : this.programs.keySet())
            result.add(desc);

        return result;
    }

    public List<String> getProceduresDesc() {
        List<String> result = new ArrayList<String>();
        for (String desc : this.procedures.keySet())
            result.add(desc);

        return result;
    }

    public IController getController(String desc) {
        return this.programs.get(desc);
    }

    public IProcTable<String, Pair<List<String>, IStmt>> getProcTable(String desc) {
        return this.procedures.get(desc);
    }

}
