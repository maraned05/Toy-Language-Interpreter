package lab.example.view;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lab.example.model.adt.MyHeap;
import lab.example.model.adt.MyList;
import lab.example.model.adt.MyMap;
import lab.example.model.adt.MyStack;
import lab.example.model.expressions.ArithmeticExpression;
import lab.example.model.expressions.ArithmeticOperation;
import lab.example.model.expressions.ReadHeapExpression;
import lab.example.model.expressions.ValueExpression;
import lab.example.model.expressions.VariableExpression;
import lab.example.model.state.ProgramState;
import lab.example.model.statements.AssignStmt;
import lab.example.model.statements.CloseRFileStmt;
import lab.example.model.statements.CompStmt;
import lab.example.model.statements.ForkStmt;
import lab.example.model.statements.HeapAllocStmt;
import lab.example.model.statements.IStmt;
import lab.example.model.statements.OpenRFileStmt;
import lab.example.model.statements.PrintStmt;
import lab.example.model.statements.ReadFileStmt;
import lab.example.model.statements.VarDeclStmt;
import lab.example.model.types.IntType;
import lab.example.model.types.RefType;
import lab.example.model.types.StringType;
import lab.example.model.values.IValue;
import lab.example.model.values.IntValue;
import lab.example.model.values.StringValue;
import lab.example.repository.IRepository;
import lab.example.repository.Repository;

public class GUIMenu {
    private Map<String, IRepository> programs;

    public GUIMenu() {
        this.programs = new HashMap<String, IRepository>();
    }

    public void populateMenu() {
        IStmt statement1 = new CompStmt (new VarDeclStmt("v",new IntType()),
        new CompStmt (new AssignStmt ("v", new ValueExpression(new IntValue(2))), 
        new PrintStmt(new VariableExpression("v"))));

        ProgramState state1 = new ProgramState(new MyStack<IStmt>(), new MyMap<String, IValue>(), 
        new MyList<IValue>(), statement1, new MyMap<StringValue, BufferedReader>(), new MyHeap<Integer, IValue>());

        Repository repo1 = new Repository("log1.txt");
        repo1.add(state1);


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


        IStmt statement3 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), 
        new CompStmt(new HeapAllocStmt(new ValueExpression(new IntValue(20)), "v"), 
        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))), 
        new CompStmt(new HeapAllocStmt(new VariableExpression("v"), "a"), 
        new CompStmt(new ForkStmt(new CompStmt(new HeapAllocStmt(new ValueExpression(new IntValue(30)), "v"), 
        new HeapAllocStmt(new VariableExpression("v"), "a"))), 
        new CompStmt(new HeapAllocStmt(new ValueExpression(new IntValue(50)), "v"), 
        new CompStmt(new HeapAllocStmt(new VariableExpression("v"), "a"), 
        new PrintStmt(new ReadHeapExpression(new VariableExpression("v"))))))))));

        ProgramState state3 = new ProgramState(new MyStack<IStmt>(), new MyMap<String, IValue>(), 
        new MyList<IValue>(), statement3, new MyMap<StringValue, BufferedReader>(), new MyHeap<Integer, IValue>());

        Repository repo3 = new Repository("log3.txt");
        repo3.add(state3);

        IStmt statement4 = new CompStmt(new VarDeclStmt("varf", new StringType()), 
        new CompStmt(new AssignStmt("varf", new ValueExpression(new StringValue("test.txt"))), 
        new CompStmt(new OpenRFileStmt(new VariableExpression("varf")), 
        new CompStmt(new VarDeclStmt("varc", new IntType()), 
        new CompStmt(new ReadFileStmt(new VariableExpression("varf"), "varc"), 
        new CompStmt(new PrintStmt(new VariableExpression("varc")), 
        new CompStmt(new ReadFileStmt(new VariableExpression("varf"), "varc"), 
        new CompStmt(new PrintStmt(new VariableExpression("varc")), 
        new CloseRFileStmt(new VariableExpression("varf"))))))))));

        ProgramState state4 = new ProgramState(new MyStack<IStmt>(), new MyMap<String, IValue>(), 
        new MyList<IValue>(), statement4, new MyMap<StringValue, BufferedReader>(), new MyHeap<Integer, IValue>());

        Repository repo4 = new Repository("log5.txt");
        repo4.add(state4);

        IStmt badstatement = new CompStmt(new VarDeclStmt("v", new IntType()),
        new CompStmt(new HeapAllocStmt(new ValueExpression(new IntValue(20)), "v"), 
        new CompStmt(new ForkStmt(new CompStmt(new HeapAllocStmt(new ValueExpression(new IntValue(30)), "v"), 
        new ForkStmt(new HeapAllocStmt(new ValueExpression(new IntValue(40)), "v")))), 
        new PrintStmt(new ReadHeapExpression(new VariableExpression("v"))))));

        ProgramState stateb = new ProgramState(new MyStack<IStmt>(), new MyMap<String, IValue>(), 
        new MyList<IValue>(), badstatement, new MyMap<StringValue, BufferedReader>(), new MyHeap<Integer, IValue>());

        Repository repob = new Repository("log4.txt");
        repob.add(stateb);

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

        this.addProgram(statement1.toString(), repo1);
        this.addProgram(statement2.toString(), repo2);
        this.addProgram(statement3.toString(), repo3);
        this.addProgram(statement4.toString(), repo4);
        this.addProgram(statement10.toString(), repo10);
        this.addProgram(badstatement.toString(), repob);
    }

    public void addProgram(String desc, IRepository repo) {
        this.programs.put(desc, repo);
    }

    public List<String> getProgramsDesc() {
        List<String> result = new ArrayList<String>();
        for (String desc : this.programs.keySet())
            result.add(desc);

        return result;
    }

    public IRepository getRepository(String desc) {
        return this.programs.get(desc);
    }

}
