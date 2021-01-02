import minipython.node.*;
import minipython.analysis.*;

import java.util.ArrayList;
import java.util.HashMap;

public class FirstAdapter extends DepthFirstAdapter
{
    private Positions positions;
    private HierarchicalSymbolTable hierarchicalSymbolTable;
    private String scopeID;

    private int errors;

    public FirstAdapter(Positions positions, HierarchicalSymbolTable hierarchicalSymbolTable)
    {
        this.positions = positions;
        this.hierarchicalSymbolTable = hierarchicalSymbolTable;
        scopeID = "global";
        errors = 0;
    }

    @Override
    public void inAAssignStatement(AAssignStatement node)
    {
        TIdentifier identifier = node.getIdentifier();

        String variableName = identifier.getText().trim();

        if (!hierarchicalSymbolTable.containsVariable(variableName, scopeID))
        {
            Variable variable = new Variable(Variable.Type.NA, variableName, identifier.getLine());
            hierarchicalSymbolTable.addVariable(variable, scopeID);
        }
    }


    @Override
    public void inAFunction(AFunction node)
    {
        Function function = new Function();

        TIdentifier identifier = node.getIdentifier();

        function.setName(identifier.getText().trim());
        function.setReturnType(Variable.Type.NONE);

        HashMap<String, Variable> arguments = new HashMap<>();
        int defaultArguments = 0;
        int nonDefaultArguments = 0;


        for (Object objectArgument : node.getArgument())
        {
            if (!(objectArgument instanceof AArgument)) break;

            AArgument argument = (AArgument)objectArgument;

            String argumentName = argument.getIdentifier().getText().trim();

            if (arguments.containsKey(argumentName))
            {
                ++errors;
                System.err.println("Error " + errors + ": Function argument '" + argumentName + "' at [" + positions.getLine(argument) + ":" + positions.getColumn(argument) + " has already been declared.");
            }

            if (argument.getValue().size() == 0)
            {
                arguments.put(argumentName, new Variable(Variable.Type.NA, argumentName, argument.getIdentifier().getLine()));
                ++nonDefaultArguments;
            }
            else
            {
                PValue value = (PValue)argument.getValue().get(0);
                ++defaultArguments;

                if (value instanceof ANumberValue)
                {
                    arguments.put(argumentName, new Variable(Variable.Type.INTEGER, argumentName, argument.getIdentifier().getLine()));
                }
                else if (value instanceof AStringValue)
                {
                    arguments.put(argumentName, new Variable(Variable.Type.STRING, argumentName, argument.getIdentifier().getLine()));
                }
                else if (value instanceof ANoneValue)
                {
                    arguments.put(argumentName, new Variable(Variable.Type.NONE, argumentName, argument.getIdentifier().getLine()));
                }
                else
                {
                    arguments.put(argumentName, new Variable(Variable.Type.NA, argumentName, argument.getIdentifier().getLine()));
                }
            }
        }

        function.setNonDefaultArgumentCount(nonDefaultArguments);
        function.setDefaultArgumentCount(defaultArguments);

        if (!hierarchicalSymbolTable.containsFunction(function))
        {
            hierarchicalSymbolTable.addFunction(function);
            hierarchicalSymbolTable.addScope(function.getID(), scopeID);
            scopeID = function.getID();

            for (Variable variable : arguments.values())
            {
                hierarchicalSymbolTable.addVariable(variable, scopeID);
            }

            hierarchicalSymbolTable.functionDef.put(function, node);
        }
        else
        {
            Node functionDefinition = hierarchicalSymbolTable.functionDef.get(hierarchicalSymbolTable.getFunction(function.getName(), function.getDefaultArgumentCount(), function.getNonDefaultArgumentCount()));

            ++errors;
            System.err.println("Error " + errors + ": Function '" + function.getName() + "' at [" + positions.getLine(node) + ":" + positions.getColumn(node) + "] has already been declared at [" + positions.getLine(functionDefinition) + ":" + positions.getColumn(functionDefinition) + "].");
        }
    }

    @Override
    public void outAFunction(AFunction node)
    {
        scopeID = "global";
    }

    @Override
    public void inAReturnStatement(AReturnStatement node)
    {
        Node parent = node.parent();

        while (!(parent instanceof AFunction))
        {
            if (parent instanceof AGoal)
            {
                ++errors;
                System.err.println("Error " + errors + ": Return statement outside of function at line '" + "LINE_NUMBER");
                return;
            }

            parent = parent.parent();
        }

        String functionName = ((AFunction) parent).getIdentifier().getText().trim();
        int arguments = ((AFunction) parent).getArgument().size();

        Function function = hierarchicalSymbolTable.getFunction(functionName, arguments);

        PExpression expression = node.getExpression();

        if (expression instanceof AValueExpression)
        {
            PValue value = ((AValueExpression) expression).getValue();

            if (value instanceof ANumberValue)
            {
                function.setReturnType(Variable.Type.INTEGER);
            }
            else if (value instanceof AStringValue)
            {
                function.setReturnType(Variable.Type.STRING);
            }
            else if (value instanceof ANoneValue)
            {
                function.setReturnType(Variable.Type.NONE);
            }
            else if (value instanceof AFunctionValue)
            {
                if (((AFunctionValue) value).getFunctionCall() instanceof  AFunctionCall)
                {
                    String functionCallName = ((AFunctionCall) ((AFunctionValue) value).getFunctionCall()).getIdentifier().getText().trim();
                    int argumentNumber = ((AFunctionCall) ((AFunctionValue) value).getFunctionCall()).getExpression().size();

                    if (hierarchicalSymbolTable.containsFunction(functionCallName, argumentNumber))
                    {
                        function.setReturnType(hierarchicalSymbolTable.getFunction(functionName, argumentNumber).getReturnType());
                    }
                }
            }
        }
        else if (expression instanceof AIdExpression)
        {
            if (hierarchicalSymbolTable.containsVariable(((AIdExpression) expression).getIdentifier().getText().trim(), scopeID))
            {
                function.setReturnType(hierarchicalSymbolTable.getVariable(((AIdExpression) expression).getIdentifier().getText().trim(), scopeID).getType());
            }
        }
        else if (expression instanceof AFuncCallExpression)
        {
            if (((AFuncCallExpression) expression).getFunctionCall() instanceof  AFunctionCall)
            {
                String functionCallName = ((AFunctionCall) ((AFuncCallExpression) expression).getFunctionCall()).getIdentifier().getText().trim();
                int argumentNumber = ((AFunctionCall) ((AFuncCallExpression) expression).getFunctionCall()).getExpression().size();

                if (hierarchicalSymbolTable.containsFunction(functionCallName, argumentNumber))
                {
                    function.setReturnType(hierarchicalSymbolTable.getFunction(functionName, argumentNumber).getReturnType());
                }
            }
        }
    }

    @Override
    public void inAForStatement(AForStatement node)
    {
        String name = node.getFirst().getText().trim();
        if (!hierarchicalSymbolTable.containsVariable(name, scopeID))
        {
            Variable variable = new Variable(Variable.Type.NA, name, positions.getLine(node));
            hierarchicalSymbolTable.addVariable(variable, scopeID);
        }
    }

    public int getErrors()
    {
        return errors;
    }
}
