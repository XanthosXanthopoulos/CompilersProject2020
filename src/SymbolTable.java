import minipython.node.AFunction;

import java.util.HashMap;
import java.util.Stack;

public class SymbolTable
{
    private HashMap<String, Variable> variables;

    private HashMap<String, Function> functions;

    public HashMap<Function, AFunction> functionDef;

    public SymbolTable()
    {
        variables = new HashMap<>();
        functions = new HashMap<>();
        functionDef = new HashMap<>();
    }

    public void addFunction(Function function)
    {
        for (int i = function.getNonDefaultArgumentCount(); i <= function.getDefaultArgumentCount() + function.getNonDefaultArgumentCount(); ++i)
        {
            functions.put(function.getName() + i, function);
        }
    }

    public Function getFunction(String functionName, int argumentNumber)
    {
        return functions.get(functionName + argumentNumber);
    }

    public Function getFunction(String functionName, int nonDefaultArgumentNumber, int defaultArgumentNumber)
    {
        for (int i = nonDefaultArgumentNumber; i <= defaultArgumentNumber + nonDefaultArgumentNumber; ++i)
        {
            if (functions.containsKey(functionName + i)) return functions.get(functionName + i);
        }

        return null;
    }

    public Variable getVariable(String variableName)
    {
        return variables.get(variableName);
    }

    public void addVariable(Variable variable)
    {
        variables.put(variable.getName(), variable);
    }

    public boolean contains(String variableName)
    {
        return variables.containsKey(variableName);
    }

    public boolean contains(String functionName, int argumentNumber)
    {
        return functions.containsKey(functionName + argumentNumber);
    }

    public boolean contains(Function function)
    {
        for (int i = function.getNonDefaultArgumentCount(); i <= function.getDefaultArgumentCount() + function.getNonDefaultArgumentCount(); ++i)
        {
            if (functions.containsKey(function.getName() + i)) return true;
        }

        return false;
    }
}
