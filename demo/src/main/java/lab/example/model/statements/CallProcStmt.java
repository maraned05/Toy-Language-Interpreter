package lab.example.model.statements;

import lab.example.exceptions.ExpressionException;
import lab.example.exceptions.KeyNotFoundException;
import lab.example.exceptions.StatementException;
import lab.example.model.adt.IMyMap;
import lab.example.model.adt.MyMap;
import lab.example.model.state.ProgramState;
import lab.example.model.types.IType;
import lab.example.model.values.IValue;
import lab.example.model.expressions.IExpression;

import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;

public class CallProcStmt implements IStmt{
    private String procName;
    private List<IExpression> exp;

    public CallProcStmt(String name, List<IExpression> exp) {
        this.procName = name;
        this.exp = new ArrayList<IExpression>();
        this.exp.addAll(exp);
    }
 
    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException {
        try {
            Pair<List<String>, IStmt> procedureStats = state.getProcTable().get(this.procName);
            List<String> parameters = procedureStats.getKey();

            List<IValue> expressionValues = new ArrayList<IValue>();
            for (IExpression e : this.exp) {
                expressionValues.add(e.evaluate(state.getCurrentSymTable(), state.getHeap()));
            }
            IMyMap<String, IValue> newSymTable = new MyMap<String, IValue>();
            //newSymTable = state.getCurrentSymTable().deepCopy();

            if (expressionValues.size() != parameters.size())
                throw new StatementException("The number of arguments given doesn't match the number of parameters!");

            for (int i = 0; i < parameters.size(); i++) {
                newSymTable.insert(parameters.get(i), expressionValues.get(i));
            }

            state.addSymTable(newSymTable);
            state.getExeStack().push(new ReturnStmt());
            state.getExeStack().push(procedureStats.getValue());
            return null;

        }
        catch (KeyNotFoundException e) {
            throw new StatementException("Procedure name not found!");
        }
        
    }

    @Override
    public IMyMap<String, IType> typeCheck(IMyMap<String, IType> typeEnv) {
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new CallProcStmt(procName, exp);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(procName + "(");
        for (int i = 0; i < this.exp.size(); i++) {
            if (i == this.exp.size() - 1)
                str.append(this.exp.get(i).toString());
            else 
                str.append(this.exp.get(i).toString() + ", ");
        }
        str.append(")");
        return str.toString();
    }
    
}
