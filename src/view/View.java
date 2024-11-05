package view;

import java.util.Scanner;

import controller.*;
import model.adt.MyList;
import model.adt.MyMap;
import model.adt.MyStack;
import model.expressions.ArithmeticExpression;
import model.expressions.ArithmeticOperation;
import model.expressions.ValueExpression;
import model.expressions.VariableExpression;
import model.state.ProgramState;
import model.statements.AssignStmt;
import model.statements.CompStmt;
import model.statements.IStmt;
import model.statements.IfStmt;
import model.statements.NoStmt;
import model.statements.PrintStmt;
import model.statements.VarDeclStmt;
import model.types.BoolType;
import model.types.IntType;
import model.values.BoolValue;
import model.values.IValue;
import model.values.IntValue;

import java.io.BufferedReader;

public class View {
    private IController controller;

    public View (Controller c) {
        this.controller = c;
    }
    
    public void executeProgram() {
        System.out.println("Please select one of the programs: \n1. int v; v = 2; print(v);" +
        "\n2. int a; int b; a = 2+3*5; b = a+1; print(b);\n3. bool a; int v; a = true; (if a then v = 2 else v = 3); print(v)\n" + 
        "4. int v; c = 2; print(c);");
        System.out.println("Your option: ");
        Scanner scanner = new Scanner(System.in);
        int opt = scanner.nextInt();
        scanner.close();
        IStmt statement;
        switch (opt) {
            case 1:
                statement = new CompStmt (new VarDeclStmt("v",new IntType()),
                new CompStmt (new AssignStmt ("v", new ValueExpression(new IntValue(2))), 
                new PrintStmt(new VariableExpression("v"))));
                break;
            
            case 2:
                statement = new CompStmt (new VarDeclStmt("a",new IntType()),
                new CompStmt (new VarDeclStmt("b",new IntType()),
                new CompStmt (new AssignStmt("a", new ArithmeticExpression (new ValueExpression(new IntValue(2)),
                new ArithmeticExpression (new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5)), ArithmeticOperation.MULTIPLY),
                ArithmeticOperation.ADD)), new CompStmt(new AssignStmt("b",new ArithmeticExpression(new VariableExpression("a"), new ValueExpression(new
                IntValue(1)), ArithmeticOperation.ADD)), new PrintStmt(new VariableExpression("b")))))
                );
                break;

            case 3:
                statement = new CompStmt (new VarDeclStmt("a",new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("a", new ValueExpression(new BoolValue(true))),
                new CompStmt(new IfStmt(new VariableExpression("a"),new AssignStmt("v",new ValueExpression(new
                IntValue(2))), new AssignStmt("v", new ValueExpression(new IntValue(3)))), new PrintStmt(new
                VariableExpression("v"))))));
                break;

            case 4:
                statement = new CompStmt (new VarDeclStmt("v",new IntType()),
                new CompStmt (new AssignStmt ("c", new ValueExpression(new IntValue(2))), 
                new PrintStmt(new VariableExpression("c"))));
                break;

        
            default:
                statement = new NoStmt();
                break;
        }
        MyStack<IStmt> exeStack = new MyStack<IStmt>();
        MyMap<String, IValue> symTable = new MyMap<String, IValue>();
        MyList<IValue> outList = new MyList<IValue>();
        MyMap<String, BufferedReader> fTb = new MyMap<String, BufferedReader>();
        ProgramState firstState = new ProgramState(exeStack, symTable, outList, statement, fTb);

        this.controller.addPrgState(firstState);
        try {
            this.controller.allStep();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
