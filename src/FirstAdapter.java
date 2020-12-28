import minipython.node.*;
import minipython.analysis.*;

public class FirstAdapter extends DepthFirstAdapter
{
    private SymbolTable symbolTable;

    private int errors;

    public FirstAdapter(SymbolTable symbolTable)
    {
        this.symbolTable = symbolTable;
        errors = 0;
    }

    @Override
    public void inAAssignStatement(AAssignStatement node)
    {
        TIdentifier identifier = node.getIdentifier();

        String variableName = identifier.getText().trim();

        Variable variable;
        if (symbolTable.contains(variableName))
        {
            variable = symbolTable.getVariable(variableName);
        }
        else
        {
            variable = new Variable(Variable.Type.NA, variableName, identifier.getPos());
            symbolTable.addVariable(variable);
        }

        PExpression expression = node.getExpression();

        if (expression instanceof AValueExpression)
        {
            PValue value = ((AValueExpression) expression).getValue();

            if (value instanceof AStringValue)
            {
                variable.setType(Variable.Type.STRING);
            }
            else if (value instanceof ANumberValue)
            {
                variable.setType(Variable.Type.INTEGER);
            }
            else if (value instanceof ANoneValue)
            {
                variable.setType(Variable.Type.NONE);
            }
            else if (value instanceof AFunctionValue)
            {
                PFunctionCall functionCall = ((AFunctionValue) value).getFunctionCall();

                if (functionCall instanceof AFunctionCall)
                {
                    String functionName = ((AFunctionCall) functionCall).getIdentifier().getText().trim();
                    int arguments = ((AFunctionCall) functionCall).getExpression().size();

                    if (symbolTable.contains(functionName, arguments))
                    {
                        variable.setType(symbolTable.getFunction(functionName, arguments).getReturnType());
                    }
                }
            }
        }
        else if (expression instanceof AArrayExpression)
        {
            variable.setType(Variable.Type.ARRAY);
        }
        else if (expression instanceof AIdExpression)
        {
            if (symbolTable.contains(((AIdExpression) expression).getIdentifier().getText().trim()))
            {
                variable.setType(symbolTable.getVariable(((AIdExpression) expression).getIdentifier().getText().trim()).getType());
            }
        }
    }


    @Override
    public void inAFunction(AFunction node)
    {
        Function function = new Function();

        TIdentifier identifier = node.getIdentifier();

        function.setName(identifier.getText().trim());
        function.setPosition(identifier.getPos());
        function.setReturnType(Variable.Type.NONE);

        for (Object objectArgument : node.getArgument())
        {
            if (!(objectArgument instanceof AArgument)) break;
            AArgument argument = (AArgument)objectArgument;

            String argumentName = argument.getIdentifier().getText().trim();

            if (function.containsArgument(argumentName))
            {
                ++errors;
                System.err.println("Error " + errors + ": Function argument '" + argumentName + "' at line:" + function.getPosition() + " has already been declared.");
            }

            if (argument.getValue().size() == 0)
            {
                function.addArgument(argumentName, false, Variable.Type.NA);
            }
            else
            {
                PValue value = (PValue)argument.getValue().get(0);

                if (value instanceof ANumberValue)
                {
                    function.addArgument(argumentName, true, Variable.Type.INTEGER);
                }
                else if (value instanceof AStringValue)
                {
                    function.addArgument(argumentName, true, Variable.Type.STRING);
                }
                else if (value instanceof ANoneValue)
                {
                    function.addArgument(argumentName, true, Variable.Type.NONE);
                }
                else
                {
                    function.addArgument(argumentName, true, Variable.Type.NA); //TODO: Possible change of NA Type
                }
            }
        }

        if (!symbolTable.contains(function))
        {
            symbolTable.addFunction(function);
        }
        else
        {
            ++errors;
            System.err.println("Error " + errors + ": Function '" + function.getName() + "' at line:" + function.getPosition() + " has already been declared at line " + symbolTable.getFunction(function.getName(), function.getDefaultArgumentCount(), function.getNonDefaultArgumentCount()).getPosition());
        }
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

        Function function = symbolTable.getFunction(functionName, arguments);

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

                    if (symbolTable.contains(functionCallName, argumentNumber))
                    {
                        function.setReturnType(symbolTable.getFunction(functionName, argumentNumber).getReturnType());
                    }
                }
            }
        }
        else if (expression instanceof AIdExpression)
        {
            if (symbolTable.contains(((AIdExpression) expression).getIdentifier().getText().trim()))
            {
                function.setReturnType(symbolTable.getVariable(((AIdExpression) expression).getIdentifier().getText().trim()).getType());
            }
        }
        else if (expression instanceof AFuncCallExpression)
        {
            if (((AFuncCallExpression) expression).getFunctionCall() instanceof  AFunctionCall)
            {
                String functionCallName = ((AFunctionCall) ((AFuncCallExpression) expression).getFunctionCall()).getIdentifier().getText().trim();
                int argumentNumber = ((AFunctionCall) ((AFuncCallExpression) expression).getFunctionCall()).getExpression().size();

                if (symbolTable.contains(functionCallName, argumentNumber))
                {
                    function.setReturnType(symbolTable.getFunction(functionName, argumentNumber).getReturnType());
                }
            }
        }
    }
}
