import minipython.analysis.*;
import minipython.node.*;

import java.util.Stack;

public class SecondAdapter extends DepthFirstAdapter
{
    private static class Trace
    {
        private String function;
        private int line;
        private int position;

        public Trace(String function, int line, int position)
        {
            this.function = function;
            this.line = line;
            this.position = position;
        }

        @Override
        public String toString()
        {
            return "Called from " + function + " at [" + line + ":" + position + "]";
        }
    }
    private Positions positions;
    private Stack<AFunction> currentFunctionDef;
    private HierarchicalSymbolTable hierarchicalSymbolTable;
    private String scopeID;
    private Stack<String> scopeStack;
    private Stack<Trace> stacktrace;

    private int errors;

    private Variable.Type common(Variable.Type lType, Variable.Type rType)
    {
        if (lType == Variable.Type.NA) return rType;

        return lType;
    }

    private void printStacktrace()
    {
        if (stacktrace.size() == 0) return;

        System.err.println("\tStack Trace: " + stacktrace.get(stacktrace.size() - 1).toString());
        for (int i = stacktrace.size() - 2; i >= 0; --i)
        {
            System.err.println("\t\t\t\t " + stacktrace.get(i).toString());
        }
    }

    public SecondAdapter(int errors, Positions positions, HierarchicalSymbolTable hierarchicalSymbolTable)
    {
        this.hierarchicalSymbolTable = hierarchicalSymbolTable;
        this.errors = errors;
        this.positions = positions;
        currentFunctionDef = new Stack<>();
        scopeStack = new Stack<>();
        stacktrace = new Stack<>();
        scopeID = "global";
    }

    @Override
    public void outAPowExpression(APowExpression node)
    {
        Variable.Type lType = (Variable.Type) getOut(node.getFirst());
        Variable.Type rType = (Variable.Type) getOut(node.getSecond());

        if ((lType != Variable.Type.INTEGER && lType != Variable.Type.NA) || (rType != Variable.Type.INTEGER && rType != Variable.Type.NA))
        {
            ++errors;
            System.err.println("Error " + errors + ": Incompatible data types for power operator at [" + positions.getLine(node) + ":" + positions.getColumn(node) + "]. Types found " + lType.name() + " and " + rType.name());
            printStacktrace();
            setOut(node, Variable.Type.NA);
        }
        else
        {
            setOut(node, common(lType, rType));
        }
    }

    @Override
    public void outAMultExpression(AMultExpression node)
    {
        Variable.Type lType = (Variable.Type) getOut(node.getFirst());
        Variable.Type rType = (Variable.Type) getOut(node.getSecond());

        if ((lType != Variable.Type.INTEGER && lType != Variable.Type.NA) || (rType != Variable.Type.INTEGER && rType != Variable.Type.NA))
        {
            ++errors;
            System.err.println("Error " + errors + ": Incompatible data types for multiplication operator at [" + positions.getLine(node) + ":" + positions.getColumn(node) + "]. Types found " + lType.name() + " and " + rType.name());
            printStacktrace();
            setOut(node, Variable.Type.NA);
        }
        else
        {
            setOut(node, common(lType, rType));
        }
    }

    @Override
    public void outADivExpression(ADivExpression node)
    {
        Variable.Type lType = (Variable.Type) getOut(node.getFirst());
        Variable.Type rType = (Variable.Type) getOut(node.getSecond());

        if ((lType != Variable.Type.INTEGER && lType != Variable.Type.NA) || (rType != Variable.Type.INTEGER && rType != Variable.Type.NA))
        {
            ++errors;
            System.err.println("Error " + errors + ": Incompatible data types for division operator at [" + positions.getLine(node) + ":" + positions.getColumn(node) + "]. Types found " + lType.name() + " and " + rType.name());
            printStacktrace();
            setOut(node, Variable.Type.NA);
        }
        else
        {
            setOut(node, common(lType, rType));
        }
    }

    @Override
    public void outAModExpression(AModExpression node)
    {
        Variable.Type lType = (Variable.Type) getOut(node.getFirst());
        Variable.Type rType = (Variable.Type) getOut(node.getSecond());

        if ((lType != Variable.Type.INTEGER && lType != Variable.Type.NA) || (rType != Variable.Type.INTEGER && rType != Variable.Type.NA))
        {
            ++errors;
            System.err.println("Error " + errors + ": Incompatible data types for modulo operator at [" + positions.getLine(node) + ":" + positions.getColumn(node) + "]. Types found " + lType.name() + " and " + rType.name());
            printStacktrace();
            setOut(node, Variable.Type.NA);
        }
        else
        {
            setOut(node, common(lType, rType));
        }
    }

    @Override
    public void outAMinusExpression(AMinusExpression node)
    {
        Variable.Type lType = (Variable.Type) getOut(node.getFirst());
        Variable.Type rType = (Variable.Type) getOut(node.getSecond());

        if ((lType != Variable.Type.INTEGER && lType != Variable.Type.NA) || (rType != Variable.Type.INTEGER && rType != Variable.Type.NA))
        {
            ++errors;
            System.err.println("Error " + errors + ": Incompatible data types subtraction operator at [" + positions.getLine(node) + ":" + positions.getColumn(node) + "]. Types found " + lType.name() + " and " + rType.name());
            printStacktrace();
            setOut(node, Variable.Type.NA);
        }
        else
        {
            setOut(node, common(lType, rType));
        }
    }

    @Override
    public void outAAddExpression(AAddExpression node)
    {
        Variable.Type lType = (Variable.Type) getOut(node.getFirst());
        Variable.Type rType = (Variable.Type) getOut(node.getSecond());


        if ((lType == rType && lType != Variable.Type.NONE) ||
            (lType != Variable.Type.NONE && rType == Variable.Type.NA) ||
            (lType == Variable.Type.NA && rType != Variable.Type.NONE))
        {
            setOut(node, common(lType, rType));
        }
        else
        {
            ++errors;
            System.err.println("Error " + errors + ": Incompatible data types for addition operator at [" + positions.getLine(node) + ":" + positions.getColumn(node) + "]. Types found " + lType.name() + " and " + rType.name());
            printStacktrace();
            setOut(node, Variable.Type.NA);
        }
    }

    @Override
    public void outANumberValue(ANumberValue node)
    {
        setOut(node, Variable.Type.INTEGER);
    }

    @Override
    public void outAStringValue(AStringValue node)
    {
        setOut(node, Variable.Type.STRING);
    }

    @Override
    public void outANoneValue(ANoneValue node)
    {
        setOut(node, Variable.Type.NONE);
    }

    @Override
    public void outAFunctionValue(AFunctionValue node)
    {
        setOut(node, Variable.Type.NA);
    }

    @Override
    public void outAValueExpression(AValueExpression node)
    {
        setOut(node, getOut(node.getValue()));
    }

    @Override
    public void outAParExpression(AParExpression node)
    {
        setOut(node, getOut(node.getExpression()));
    }

    @Override
    public void outAArrayExpression(AArrayExpression node)
    {
        //TODO: Check for homogeneous or heterogeneous array
        setOut(node, Variable.Type.ARRAY);
    }

    @Override
    public void outAArrayAccessExpression(AArrayAccessExpression node)
    {
        Variable.Type indexType = (Variable.Type) getOut(node.getExpression());
        int line = node.getIdentifier().getLine();
        int position = node.getIdentifier().getPos();

        if (indexType == Variable.Type.NONE || indexType == Variable.Type.ARRAY)
        {
            ++errors;
            System.err.println("Error " + errors + ": Invalid array index type at [" + line + ":" + position + "]. Found index type is '"+ indexType.name() +"'.");
            printStacktrace();
        }
        setOut(node, Variable.Type.NA);
    }

    @Override
    public void outAMaxExpression(AMaxExpression node)
    {
        Variable.Type type = Variable.Type.NA;

        for (Object object : node.getValue())
        {
            Variable.Type argType = (Variable.Type) getOut((Node)object);

            if (argType == Variable.Type.NONE)
            {
                ++errors;
                System.err.println("Error " + errors + ": Invalid data type at [" + positions.getLine((Node)object) + ":" + positions.getColumn((Node)object) + "]. Found type is '"+ argType.name() +"'.");
                printStacktrace();
            }
            else if ((type == Variable.Type.INTEGER && argType == Variable.Type.STRING) ||
                     (type == Variable.Type.STRING && argType == Variable.Type.INTEGER))
            {
                ++errors;
                System.err.println("Error " + errors + ": Invalid data type at [" + positions.getLine((Node)object) + ":" + positions.getColumn((Node)object) + "]. Found type is '"+ argType.name() +"', expected '" + type.name() + "'.");
                printStacktrace();
            }
            else
            {
                type = common(type, argType);
            }
        }

        setOut(node, type);
    }

    @Override
    public void outAMinExpression(AMinExpression node)
    {
        Variable.Type type = Variable.Type.NA;

        for (Object object : node.getValue())
        {
            Variable.Type argType = (Variable.Type) getOut((Node)object);

            if (argType == Variable.Type.NONE)
            {
                ++errors;
                System.err.println("Error " + errors + ": Invalid data type at [" + positions.getLine((Node)object) + ":" + positions.getColumn((Node)object) + "]. Found index type is '"+ argType.name() +"'.");
                printStacktrace();
            }
            else if ((type == Variable.Type.INTEGER && argType == Variable.Type.STRING) ||
                    (type == Variable.Type.STRING && argType == Variable.Type.INTEGER))
            {
                ++errors;
                System.err.println("Error " + errors + ": Invalid data type at [" + positions.getLine((Node)object) + ":" + positions.getColumn((Node)object) + "]. Found index type is '"+ argType.name() +"', expected '" + type.name() + "'.");
                printStacktrace();
            }
            else
            {
                type = common(type, argType);
            }
        }

        setOut(node, type);
    }

    @Override
    public void outAIdExpression(AIdExpression node)
    {
        String name = node.getIdentifier().getText().trim();
        int line = node.getIdentifier().getLine();
        int position = node.getIdentifier().getPos();

        if (hierarchicalSymbolTable.containsVariable(name, scopeID) && hierarchicalSymbolTable.getVariable(name, scopeID).getState() == Variable.State.DECLARED)
        {
            Variable variable = hierarchicalSymbolTable.getVariable(name, scopeID);

            setOut(node, variable.getType());
        }
        else
        {
            ++errors;
            System.err.println("Error " + errors + ": Variable " + name + " at [" + line + ":" + position + "] is undefined.");
            printStacktrace();
            setOut(node, Variable.Type.NA);
        }
    }

    @Override
    public void outAFuncCallExpression(AFuncCallExpression node)
    {
        PFunctionCall functionCall = node.getFunctionCall();

        if (functionCall instanceof AFunctionCall)
        {
            String functionName = ((AFunctionCall) functionCall).getIdentifier().getText().trim();
            int argumentNumber = ((AFunctionCall) functionCall).getExpression().size();
            int line = ((AFunctionCall) functionCall).getIdentifier().getLine();
            int position =((AFunctionCall) functionCall).getIdentifier().getPos();

            if (hierarchicalSymbolTable.containsFunction(functionName, argumentNumber))
            {
                Function function = hierarchicalSymbolTable.getFunction(functionName, argumentNumber);
                String functionScope = function.getID();
                AFunction functionNode = hierarchicalSymbolTable.functionDef.get(function);

                for (int i = 0; i < argumentNumber; ++i)
                {
                    Object object = functionNode.getArgument().get(i);

                    if (object instanceof AArgument)
                    {
                        String variableName = ((AArgument) object).getIdentifier().getText().trim();
                        Variable.Type type = (Variable.Type) getOut((Node)((AFunctionCall) functionCall).getExpression().get(i));

                        hierarchicalSymbolTable.getVariable(variableName, functionScope).setType(type);
                    }
                }

                stacktrace.push(new Trace(functionName, positions.getLine(node), positions.getColumn(node)));
                caseAFunction(functionNode);
                stacktrace.pop();
                setOut(node, getOut(functionNode));
            }
            else
            {
                ++errors;
                System.err.println("Error " + errors + ": Function " + functionName + " at [" + line + ":" + position + "] is undefined.");
                printStacktrace();
                setOut(node, Variable.Type.NA);
            }
        }
    }

    @Override
    public void outACompareComparison(ACompareComparison node)
    {
        Variable.Type lType = (Variable.Type) getOut(node.getFirst());
        Variable.Type rType = (Variable.Type) getOut(node.getSecond());

        if ((lType == rType && lType != Variable.Type.NONE) ||
            (lType != Variable.Type.NONE && rType == Variable.Type.NA) ||
            (lType == Variable.Type.NA && rType != Variable.Type.NONE))
        {
            setOut(node, common(lType, rType));
        }
        else
        {
            ++errors;
            System.err.println("Error " + errors + ": Incompatible data types for comparison at [" + positions.getLine(node) + ":" + positions.getColumn(node) + "]. Types found " + lType.name() + " and " + rType.name());
            printStacktrace();
            setOut(node, Variable.Type.NA);
        }
    }

    @Override
    public void outAFunctionStatement(AFunctionStatement node)
    {
        PFunctionCall functionCall = node.getFunctionCall();

        if (functionCall instanceof AFunctionCall)
        {
            String functionName = ((AFunctionCall) functionCall).getIdentifier().getText().trim();
            int argumentNumber = ((AFunctionCall) functionCall).getExpression().size();
            int line = ((AFunctionCall) functionCall).getIdentifier().getLine();
            int position =((AFunctionCall) functionCall).getIdentifier().getPos();

            if (hierarchicalSymbolTable.containsFunction(functionName, argumentNumber))
            {
                Function function = hierarchicalSymbolTable.getFunction(functionName, argumentNumber);
                String functionScope = function.getID();
                AFunction functionNode = hierarchicalSymbolTable.functionDef.get(function);

                for (int i = 0; i < argumentNumber; ++i)
                {
                    Object object = functionNode.getArgument().get(i);

                    if (object instanceof AArgument)
                    {
                        String variableName = ((AArgument) object).getIdentifier().getText().trim();
                        Variable.Type type = (Variable.Type) getOut((Node)((AFunctionCall) functionCall).getExpression().get(i));

                        hierarchicalSymbolTable.getVariable(variableName, functionScope).setType(type);
                    }
                }

                stacktrace.push(new Trace(functionName, positions.getLine(node), positions.getColumn(node)));
                caseAFunction(functionNode);
                stacktrace.pop();
                setOut(node, getOut(functionNode));
            }
            else
            {
                ++errors;
                System.err.println("Error " + errors + ": Function " + functionName + " at [" + line + ":" + position + "] is undefined.");
                printStacktrace();
                setOut(node, Variable.Type.NA);
            }
        }
    }

    @Override
    public void outATypeExpression(ATypeExpression node)
    {
        setOut(node, Variable.Type.NONE);
    }

    @Override
    public void outAOpenExpression(AOpenExpression node)
    {
        setOut(node, Variable.Type.NONE);
    }

    @Override
    public void outAAssignStatement(AAssignStatement node)
    {
        String name = node.getIdentifier().getText().trim();

        if (node.getOperationAssign() instanceof AAssignOperationAssign)
        {
            Variable variable = hierarchicalSymbolTable.getVariable(name, scopeID);
            variable.setType((Variable.Type) getOut(node.getExpression()));
            variable.setState(Variable.State.DECLARED);
        }
        else
        {
            if (hierarchicalSymbolTable.containsVariable(name, scopeID) && hierarchicalSymbolTable.getVariable(name, scopeID).getState() == Variable.State.DECLARED)
            {
                Variable variable = hierarchicalSymbolTable.getVariable(name, scopeID);

                if (variable.getType() == Variable.Type.INTEGER || variable.getType() == Variable.Type.NA)
                {
                    Variable.Type expressionType = (Variable.Type) getOut(node.getExpression());

                    if (expressionType != Variable.Type.INTEGER && expressionType != Variable.Type.NA)
                    {
                        ++errors;
                        System.err.println("Error " + errors + ": Invalid expression type at [" + positions.getLine(node.getExpression()) + ":" + positions.getColumn(node.getExpression()) + "]. Found '" + expressionType.name() + "', expected 'INTEGER'.");
                        printStacktrace();
                    }
                }
                else
                {
                    ++errors;
                    System.err.println("Error " + errors + ": Invalid variable type at [" + node.getIdentifier().getLine() + ":" + node.getIdentifier().getPos() + "]. Found '" + variable.getType().name() + "', expected 'INTEGER'.");
                    printStacktrace();
                }
            }
            else
            {
                ++errors;
                System.err.println("Error " + errors + ": Variable " + name + " at [" + node.getIdentifier().getLine() + ":" + node.getIdentifier().getPos() + "] is undefined.");
                printStacktrace();
            }
        }
    }

    @Override
    public void outAArrayAssignStatement(AArrayAssignStatement node)
    {
        String name = node.getIdentifier().getText().trim();

        if (hierarchicalSymbolTable.containsVariable(name, scopeID) && hierarchicalSymbolTable.getVariable(name, scopeID).getState() == Variable.State.DECLARED)
        {
            Variable variable = hierarchicalSymbolTable.getVariable(name, scopeID);
            Variable.Type indexType = (Variable.Type) getOut(node.getFirst());
            Variable.Type valueType = (Variable.Type) getOut(node.getSecond());

            if (variable.getType() == Variable.Type.ARRAY)
            {
                if (indexType != Variable.Type.INTEGER && indexType != Variable.Type.STRING)
                {
                    ++errors;
                    System.err.println("Error " + errors + ": Invalid array index type at [" + positions.getLine(node.getFirst()) + ":" + positions.getColumn(node.getFirst()) + "]. Found '"+ indexType.name() +"', expected 'INTEGER' or 'STRING'.");
                    printStacktrace();
                }
            }
            else if (variable.getType() == Variable.Type.STRING)
            {
                if (indexType != Variable.Type.INTEGER)
                {
                    ++errors;
                    System.err.println("Error " + errors + ": Invalid array index type at [" + positions.getLine(node.getFirst()) + ":" + positions.getColumn(node.getFirst()) + "]. Found '" + indexType.name() +"', expected 'INTEGER'.");
                    printStacktrace();
                }

                if (valueType !=  Variable.Type.STRING)
                {
                    ++errors;
                    System.err.println("Error " + errors + ": Invalid expression type at [" + positions.getLine(node.getSecond()) + ":" + positions.getColumn(node.getSecond()) + "]. Found '" + valueType.name() +"', expected 'STRING'.");
                    printStacktrace();
                }
            }
            else
            {
                ++errors;
                System.err.println("Error " + errors + ": Invalid variable type at [" + node.getIdentifier().getLine() + ":" + node.getIdentifier().getPos() + "]. Found '" + variable.getType().name() + "', expected 'ARRAY' or 'STRING'.");
                printStacktrace();
            }
        }
        else
        {
            ++errors;
            System.err.println("Error " + errors + ": Variable " + name + " at [" + node.getIdentifier().getLine() + ":" + node.getIdentifier().getPos() + "] is undefined.");
            printStacktrace();
        }
    }

    @Override
    public void inAFunction(AFunction node)
    {
        currentFunctionDef.push(node);

        String name = node.getIdentifier().getText().trim();
        int argumentNumber = node.getArgument().size();

        Function function = hierarchicalSymbolTable.getFunction(name, argumentNumber);

        scopeStack.push(scopeID);
        scopeID = function.getID();

        for (Object object : node.getArgument())
        {
            if (object instanceof AArgument)
            {
                String argumentName = ((AArgument) object).getIdentifier().getText().trim();

                Variable argument = hierarchicalSymbolTable.getVariable(argumentName, scopeID);
                argument.setState(Variable.State.DECLARED);
            }
        }

        setOut(node, Variable.Type.NONE);
    }

    @Override
    public void outAFunction(AFunction node)
    {
        currentFunctionDef.pop();
        scopeID = scopeStack.pop();
    }

    @Override
    public void outAReturnStatement(AReturnStatement node)
    {
        if (!currentFunctionDef.empty())
        {
            setOut(currentFunctionDef.peek(), getOut(node.getExpression()));
        }
    }

    @Override
    public void inAForStatement(AForStatement node)
    {
        String iteratorName = node.getFirst().getText().trim();
        String containerName = node.getSecond().getText().trim();

        Variable iterator = hierarchicalSymbolTable.getVariable(iteratorName, scopeID);
        iterator.setState(Variable.State.DECLARED);

        if (hierarchicalSymbolTable.containsVariable(containerName, scopeID) && hierarchicalSymbolTable.getVariable(containerName, scopeID).getState() == Variable.State.DECLARED)
        {
            Variable.Type containerType = hierarchicalSymbolTable.getVariable(containerName, scopeID).getType();

            if (containerType == Variable.Type.ARRAY)
            {
                iterator.setType(Variable.Type.NA);
            }
            else if (containerType == Variable.Type.STRING)
            {
                iterator.setType(Variable.Type.STRING);
            }
            else
            {
                ++errors;
                System.err.println("Error " + errors + ": Invalid variable type at [" + node.getSecond().getLine() + ":" + node.getSecond().getPos() + "]. Found type '" + containerType.name() + "'.");
                printStacktrace();
                iterator.setType(Variable.Type.NA);
            }
        }
        else
        {
            ++errors;
            System.err.println("Error " + errors + ": Variable " + containerName + " at [" + node.getSecond().getLine() + ":" + node.getSecond().getPos() + "] is undefined.");
            printStacktrace();
            iterator.setType(Variable.Type.NA);
        }
    }
}
