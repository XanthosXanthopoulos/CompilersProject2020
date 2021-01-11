import minipython.node.AFunction;

import java.util.ArrayList;
import java.util.HashMap;

public class HierarchicalSymbolTable
{
    private HashMap<String, ArrayList<Function>> functions;
    private HashMap<String, Scope> scopes;
    public HashMap<Function, AFunction> functionDef;

    public HierarchicalSymbolTable()
    {
        functions = new HashMap<>();
        scopes = new HashMap<>();
        functionDef = new HashMap<>();

        Scope currentScope = new Scope(null);
        currentScope.parent = currentScope;

        scopes.put("global", currentScope);
    }

    public Scope getScope(String scopeID)
    {
        return scopes.get(scopeID);
    }

    public void resetScope(String scopeID)
    {
        for (Variable variable : scopes.get(scopeID).variables.values())
        {
            variable.setState(Variable.State.UNDECLARED);
        }
    }

    public void addScope(String scopeID, String parentID)
    {
        if (!scopes.containsKey(scopeID))
        {
            Scope scope = new Scope(scopes.get(parentID));
            scopes.put(scopeID, scope);
        }
    }

    public boolean containsFunction(String functionID, int argumentNumber)
    {
        return functions.containsKey(functionID + argumentNumber);
    }

    public boolean containsFunction(Function function)
    {
        for (int i = function.getNonDefaultArgumentCount(); i <= function.getDefaultArgumentCount() + function.getNonDefaultArgumentCount(); ++i)
        {
            if (functions.containsKey(function.getName() + i)) return true;
        }

        return false;
    }

    public boolean isAmbiguous(String functionID, int argumentNumber)
    {
        ArrayList<Function> list = functions.get(functionID + argumentNumber);

        if (list != null)
        {
            return list.size() > 1;
        }

        return false;
    }

    public Function getFunction(String functionID, int argumentNumber)
    {
        return functions.get(functionID + argumentNumber).get(0);
    }

    public Function getFunction(String functionName, int nonDefaultArgumentNumber, int defaultArgumentNumber)
    {
        for (int i = nonDefaultArgumentNumber; i <= defaultArgumentNumber + nonDefaultArgumentNumber; ++i)
        {
            if (functions.containsKey(functionName + i)) return functions.get(functionName + i).get(0);
        }

        return null;
    }

    public void addFunction(Function function)
    {
        for (int i = function.getNonDefaultArgumentCount(); i <= function.getDefaultArgumentCount() + function.getNonDefaultArgumentCount(); ++i)
        {
            if (functions.containsKey(function.getName() + i))
            {
                functions.get(function.getName() + i).add(function);
            }
            else
            {
                ArrayList<Function> list = new ArrayList<>();
                list.add(function);
                functions.put(function.getName() + i, list);
            }
        }
    }

    public boolean containsVariable(String variableID, String scopeID)
    {
        Scope searchScope = scopes.get(scopeID);

        do
        {
            if (searchScope.variables.containsKey(variableID))
            {
                return true;
            }
            else
            {
                searchScope = searchScope.parent;
            }
        }
        while (searchScope.parent != searchScope);

        return false;
    }

    public Variable getVariable(String variableID, String scopeID)
    {
        Scope searchScope = scopes.get(scopeID);

        do
        {
            if (searchScope.variables.containsKey(variableID))
            {
                return searchScope.variables.get(variableID);
            }
            else
            {
                searchScope = searchScope.parent;
            }
        }
        while (searchScope.parent != searchScope);

        return null;
    }

    public void addVariable(Variable variable, String scopeID)
    {
        scopes.get(scopeID).variables.put(variable.getName(), variable);
    }

    public static class Scope
    {
        protected HashMap<String, Variable> variables;
        protected HashMap<String, Scope> children;
        protected Scope parent;

        public Scope(Scope parent)
        {
            this.parent = parent;

            variables = new HashMap<>();
            children = new HashMap<>();
        }

        public HashMap<String, Variable> getVariables()
        {
            return variables;
        }
    }
}
