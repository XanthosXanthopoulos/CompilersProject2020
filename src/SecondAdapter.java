import minipython.analysis.*;
import minipython.node.*;

import java.util.*;

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
    private Stack<String> iterationCounter;
    private HashMap<Node, Value<?>> values;

    private boolean parseDefinition = true;
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
        iterationCounter = new Stack<>();
        iterationCounter.push("");
        values = new HashMap<>();
        scopeID = "global";
    }

    @Override
    public void outAPowExpression(APowExpression node) //Checked
    {
        Variable.Type lType = (Variable.Type) getOut(node.getFirst());
        Variable.Type rType = (Variable.Type) getOut(node.getSecond());

        if ((lType != Variable.Type.INTEGER && lType != Variable.Type.NA) || (rType != Variable.Type.INTEGER && rType != Variable.Type.NA))
        {
            ++errors;
            System.err.println("Error " + errors + ": Incompatible data types for power operator at [" + positions.getLine(node) + ":" + positions.getColumn(node) + "]. Types found '" + lType.name() + "' and '" + rType.name() + "'." + iterationCounter.peek());
            printStacktrace();

            values.put(node, new Value<>(true));
            setOut(node, Variable.Type.NA);
        }
        else
        {
            Value<?> lhs = values.get(node.getFirst());
            Value<?> rhs = values.get(node.getSecond());

            values.put(node, Value.Power(lhs, rhs));
            setOut(node, common(lType, rType));
        }
    }

    @Override
    public void outAMultExpression(AMultExpression node) //Checked
    {
        Variable.Type lType = (Variable.Type) getOut(node.getFirst());
        Variable.Type rType = (Variable.Type) getOut(node.getSecond());

        if ((lType != Variable.Type.INTEGER && lType != Variable.Type.NA) || (rType != Variable.Type.INTEGER && rType != Variable.Type.NA))
        {
            ++errors;
            System.err.println("Error " + errors + ": Incompatible data types for multiplication operator at [" + positions.getLine(node) + ":" + positions.getColumn(node) + "]. Types found '" + lType.name() + "' and '" + rType.name() + "'." + iterationCounter.peek());
            printStacktrace();

            values.put(node, new Value<>(true));
            setOut(node, Variable.Type.NA);
        }
        else
        {
            Value<?> lhs = values.get(node.getFirst());
            Value<?> rhs = values.get(node.getSecond());

            values.put(node, Value.Multiply(lhs, rhs));
            setOut(node, common(lType, rType));
        }
    }

    @Override
    public void outADivExpression(ADivExpression node) //Checked
    {
        Variable.Type lType = (Variable.Type) getOut(node.getFirst());
        Variable.Type rType = (Variable.Type) getOut(node.getSecond());

        if ((lType != Variable.Type.INTEGER && lType != Variable.Type.NA) || (rType != Variable.Type.INTEGER && rType != Variable.Type.NA))
        {
            ++errors;
            System.err.println("Error " + errors + ": Incompatible data types for division operator at [" + positions.getLine(node) + ":" + positions.getColumn(node) + "]. Types found '" + lType.name() + "' and '" + rType.name() + "'." + iterationCounter.peek());
            printStacktrace();

            values.put(node, new Value<>(true));
            setOut(node, Variable.Type.NA);
        }
        else
        {
            Value<?> lhs = values.get(node.getFirst());
            Value<?> rhs = values.get(node.getSecond());

            values.put(node, Value.Divide(lhs, rhs));
            setOut(node, common(lType, rType));
        }
    }

    @Override
    public void outAModExpression(AModExpression node) //Checked
    {
        Variable.Type lType = (Variable.Type) getOut(node.getFirst());
        Variable.Type rType = (Variable.Type) getOut(node.getSecond());

        if ((lType != Variable.Type.INTEGER && lType != Variable.Type.NA) || (rType != Variable.Type.INTEGER && rType != Variable.Type.NA))
        {
            ++errors;
            System.err.println("Error " + errors + ": Incompatible data types for modulo operator at [" + positions.getLine(node) + ":" + positions.getColumn(node) + "]. Types found '" + lType.name() + "' and '" + rType.name() + "'." + iterationCounter.peek());
            printStacktrace();

            values.put(node, new Value<>(true));
            setOut(node, Variable.Type.NA);
        }
        else
        {
            Value<?> lhs = values.get(node.getFirst());
            Value<?> rhs = values.get(node.getSecond());

            values.put(node, Value.Modulo(lhs, rhs));
            setOut(node, common(lType, rType));
        }
    }

    @Override
    public void outAMinusExpression(AMinusExpression node) //Checked
    {
        Variable.Type lType = (Variable.Type) getOut(node.getFirst());
        Variable.Type rType = (Variable.Type) getOut(node.getSecond());

        if ((lType != Variable.Type.INTEGER && lType != Variable.Type.NA) || (rType != Variable.Type.INTEGER && rType != Variable.Type.NA))
        {
            ++errors;
            System.err.println("Error " + errors + ": Incompatible data types subtraction operator at [" + positions.getLine(node) + ":" + positions.getColumn(node) + "]. Types found '" + lType.name() + "' and '" + rType.name() + "'." + iterationCounter.peek());
            printStacktrace();

            values.put(node, new Value<>(true));
            setOut(node, Variable.Type.NA);
        }
        else
        {
            Value<?> lhs = values.get(node.getFirst());
            Value<?> rhs = values.get(node.getSecond());

            values.put(node, Value.Subtract(lhs, rhs));
            setOut(node, common(lType, rType));
        }
    }

    @Override
    public void outAAddExpression(AAddExpression node) //Checked
    {
        Variable.Type lType = (Variable.Type) getOut(node.getFirst());
        Variable.Type rType = (Variable.Type) getOut(node.getSecond());


        if ((lType == rType && lType != Variable.Type.NONE) ||
                (lType != Variable.Type.NONE && rType == Variable.Type.NA) ||
                (lType == Variable.Type.NA && rType != Variable.Type.NONE))
        {
            Value<?> lhs = values.get(node.getFirst());
            Value<?> rhs = values.get(node.getSecond());

            values.put(node, Value.Add(lhs, rhs));
            setOut(node, common(lType, rType));
        }
        else
        {
            ++errors;
            System.err.println("Error " + errors + ": Incompatible data types for addition operator at [" + positions.getLine(node) + ":" + positions.getColumn(node) + "]. Types found '" + lType.name() + "' and '" + rType.name() + "'." + iterationCounter.peek());
            printStacktrace();

            values.put(node, new Value<>(true));
            setOut(node, Variable.Type.NA);
        }
    }

    @Override
    public void outANumberValue(ANumberValue node) //Checked
    {
        if (node.getMinus() == null)
        {
            values.put(node, new Value<>(Integer.parseInt(node.getNumber().getText().trim()), false));
        }
        else
        {
            values.put(node, new Value<>(Integer.parseInt(node.getMinus().getText().trim() + node.getNumber().getText().trim()), false));
        }
        setOut(node, Variable.Type.INTEGER);
    }

    @Override
    public void outAStringValue(AStringValue node) //Checked
    {
        values.put(node, new Value<>(node.getString().getText().trim().substring(1, node.getString().getText().trim().length() - 1), false));
        setOut(node, Variable.Type.STRING);
    }

    @Override
    public void outANoneValue(ANoneValue node) //Checked
    {
        setOut(node, Variable.Type.NONE);
    }

    @Override
    public void outAFunctionValue(AFunctionValue node) //Checked
    {
        values.put(node, new Value<>(true));
        setOut(node, Variable.Type.NA);
    }

    @Override
    public void outAValueExpression(AValueExpression node) //Checked
    {
        Value<?> value = values.get(node.getValue());
        if (value != null)
        {
            values.put(node, new Value<>(value.getValue(), value.isNA()));
        }
        setOut(node, getOut(node.getValue()));
    }

    @Override
    public void outAParExpression(AParExpression node) //Checked
    {
        Value<?> value = values.get(node.getExpression());
        if (value != null)
        {
            values.put(node, new Value<>(value.getValue(), value.isNA()));
        }
        setOut(node, getOut(node.getExpression()));
    }

    @Override
    public void outAArrayExpression(AArrayExpression node) //Checked
    {
        ArrayList<Value<?>> list = new ArrayList<>();
        for (Object object : node.getExpression())
        {
            if (object instanceof PExpression)
            {
                Value<?> value = values.get(object);

                if (value != null)
                {
                    list.add(new Value<>(value.getValue(), value.isNA()));
                }
                else
                {
                    list.add(null);
                }
            }
        }

        values.put(node, new Value<>(list, false));
        setOut(node, Variable.Type.ARRAY);
    }

    @Override
    public void outAArrayAccessExpression(AArrayAccessExpression node)
    {
        String name = node.getIdentifier().getText().trim();
        Variable.Type indexType = (Variable.Type) getOut(node.getExpression());

        if (!hierarchicalSymbolTable.containsVariable(name, scopeID) || hierarchicalSymbolTable.getVariable(name, scopeID).getState() != Variable.State.DECLARED)
        {
            ++errors;
            System.err.println("Error " + errors + ": Variable " + name + " at [" + node.getIdentifier().getLine() + ":" + node.getIdentifier().getPos() + "] is undefined." + iterationCounter.peek());
            printStacktrace();
        }

        if (indexType == Variable.Type.NONE || indexType == Variable.Type.ARRAY || indexType == Variable.Type.STRING)
        {
            ++errors;
            System.err.println("Error " + errors + ": Invalid array index type at [" + positions.getLine(node.getExpression()) + ":" + positions.getColumn(node.getExpression()) + "]. Found index type is '"+ indexType.name() +"'." + iterationCounter.peek());
            printStacktrace();

            values.put(node, new Value<>(true));
            setOut(node, Variable.Type.NA);
        }
        else
        {
            if (!hierarchicalSymbolTable.containsVariable(name, scopeID) || hierarchicalSymbolTable.getVariable(name, scopeID).getState() != Variable.State.DECLARED || indexType == Variable.Type.NA)
            {
                values.put(node, new Value<>(true));
                setOut(node, Variable.Type.NA);
            }
            else
            {
                Variable container = hierarchicalSymbolTable.getVariable(name, scopeID);
                Variable.Type containerType = container.getType();
                int indexValue = (Integer)values.get(node.getExpression()).getValue();

                if (containerType == Variable.Type.STRING)
                {
                    String value = (String)container.getValue().getValue();

                    if (indexValue >= value.length() || indexValue < 0)
                    {
                        ++errors;
                        System.err.println("Error " + errors + ": Index out of range at [" + node.getIdentifier().getLine() + ":" + node.getIdentifier().getPos() + "]." + iterationCounter.peek());
                        printStacktrace();

                        values.put(node, new Value<>(true));
                        setOut(node, Variable.Type.NA);
                    }
                    else
                    {
                        values.put(node, new Value<>(value.substring(indexValue, indexValue + 1), false));
                        setOut(node, Variable.Type.STRING);
                    }
                }
                else if (containerType == Variable.Type.ARRAY)
                {
                    ArrayList<Value<?>> value = (ArrayList<Value<?>>)container.getValue().getValue();

                    if (indexValue >= value.size() || indexValue < 0)
                    {
                        ++errors;
                        System.err.println("Error " + errors + ": Index out of range at [" + node.getIdentifier().getLine() + ":" + node.getIdentifier().getPos() + "]." + iterationCounter.peek());
                        printStacktrace();

                        values.put(node, new Value<>(true));
                        setOut(node, Variable.Type.NA);
                    }
                    else
                    {
                        if (value.get(indexValue) == null)
                        {
                            setOut(node, Variable.Type.NONE);
                        }
                        else
                        {
                            if (value.get(indexValue).getValue() instanceof Integer)
                            {
                                values.put(node, new Value<>(value.get(indexValue).getValue(), false));
                                setOut(node, Variable.Type.INTEGER);
                            }
                            else if (value.get(indexValue).getValue() instanceof String)
                            {
                                values.put(node, new Value<>(value.get(indexValue).getValue(), false));
                                setOut(node, Variable.Type.STRING);
                            }
                            else if (value.get(indexValue).getValue() instanceof Collection)
                            {
                                values.put(node, new Value<>(value.get(indexValue).getValue(), false));
                                setOut(node, Variable.Type.ARRAY);
                            }
                            else
                            {
                                values.put(node, new Value<>(true));
                                setOut(node, Variable.Type.NA);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void outAMaxExpression(AMaxExpression node) //Checked
    {
        Variable.Type type = Variable.Type.NA;
        Value<?> max = null;

        for (Object object : node.getValue())
        {
            Variable.Type argType = (Variable.Type) getOut((Node)object);

            if (argType == Variable.Type.NONE)
            {
                ++errors;
                System.err.println("Error " + errors + ": Invalid data type at [" + positions.getLine((Node)object) + ":" + positions.getColumn((Node)object) + "]. Found type is '"+ argType.name() +"'." + iterationCounter.peek());
                printStacktrace();
            }
            else if ((type == Variable.Type.INTEGER && argType == Variable.Type.STRING) ||
                     (type == Variable.Type.STRING && argType == Variable.Type.INTEGER))
            {
                ++errors;
                System.err.println("Error " + errors + ": Invalid data type at [" + positions.getLine((Node)object) + ":" + positions.getColumn((Node)object) + "]. Found type is '"+ argType.name() +"', expected '" + type.name() + "'." + iterationCounter.peek());
                printStacktrace();
            }
            else
            {
                if (max == null)
                {
                    max = values.get(object);
                }
                else
                {
                    max = Value.Max(max, values.get(object));
                }
                type = common(type, argType);
            }
        }

        if (max == null) max = new Value<>(true);

        values.put(node, max);
        setOut(node, type);
    }

    @Override
    public void outAMinExpression(AMinExpression node) //Checked
    {
        Variable.Type type = Variable.Type.NA;
        Value<?> min = null;

        for (Object object : node.getValue())
        {
            Variable.Type argType = (Variable.Type) getOut((Node)object);

            if (argType == Variable.Type.NONE)
            {
                ++errors;
                System.err.println("Error " + errors + ": Invalid data type at [" + positions.getLine((Node)object) + ":" + positions.getColumn((Node)object) + "]. Found index type is '"+ argType.name() +"'." + iterationCounter.peek());
                printStacktrace();
            }
            else if ((type == Variable.Type.INTEGER && argType == Variable.Type.STRING) ||
                    (type == Variable.Type.STRING && argType == Variable.Type.INTEGER))
            {
                ++errors;
                System.err.println("Error " + errors + ": Invalid data type at [" + positions.getLine((Node)object) + ":" + positions.getColumn((Node)object) + "]. Found index type is '"+ argType.name() +"', expected '" + type.name() + "'." + iterationCounter.peek());
                printStacktrace();
            }
            else
            {
                if (min == null)
                {
                    min = values.get(object);
                }
                else
                {
                    Value<?> val = values.get(object);
                    min = Value.Min(min, values.get(object));
                }
                type = common(type, argType);
            }
        }

        if (min == null) min = new Value<>(true);
        values.put(node, min);
        setOut(node, type);
    }

    @Override
    public void outAIdExpression(AIdExpression node) //Checked
    {
        String name = node.getIdentifier().getText().trim();

        if (hierarchicalSymbolTable.containsVariable(name, scopeID) && hierarchicalSymbolTable.getVariable(name, scopeID).getState() == Variable.State.DECLARED)
        {
            Variable variable = hierarchicalSymbolTable.getVariable(name, scopeID);
            Value<?> value = variable.getValue();

            if (value != null)
            {
                values.put(node, new Value<>(value.getValue(), value.isNA()));
            }

            setOut(node, variable.getType());
        }
        else
        {
            ++errors;
            System.err.println("Error " + errors + ": Variable " + name + " at [" + node.getIdentifier().getLine() + ":" + node.getIdentifier().getPos() + "] is undefined." + iterationCounter.peek());
            printStacktrace();

            values.put(node, new Value<>(true));
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
                if (hierarchicalSymbolTable.isAmbiguous(functionName, argumentNumber))
                {
                    ++errors;
                    System.err.println("Error " + errors + ": Function call " + functionName + " at [" + line + ":" + position + "] is ambiguous." + iterationCounter.peek());
                    printStacktrace();

                    values.put(node, new Value<>(true));
                    setOut(node, Variable.Type.NA);
                }
                else
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
                            Variable.Type type = (Variable.Type) getOut((Node) ((AFunctionCall) functionCall).getExpression().get(i));
                            Value<?> value = values.get(((AFunctionCall) functionCall).getExpression().get(i));

                            if (value != null)
                            {
                                hierarchicalSymbolTable.getVariable(variableName, functionScope).addValue(new Value<>(value.getValue(), value.isNA()));
                            }
                            else
                            {
                                hierarchicalSymbolTable.getVariable(variableName, functionScope).addValue(null);
                            }

                            hierarchicalSymbolTable.getVariable(variableName, functionScope).addType(type);
                        }
                    }

                    for (int i = argumentNumber; i < function.getDefaultArgumentCount() + function.getNonDefaultArgumentCount(); ++i)
                    {
                        Object object = functionNode.getArgument().get(i);

                        if (object instanceof AArgument)
                        {
                            String variableName = ((AArgument) object).getIdentifier().getText().trim();

                            Variable variable = hierarchicalSymbolTable.getVariable(variableName, functionScope);

                            variable.addValue(variable.getDefaultValue());
                            variable.addType(variable.getDefaultType());
                        }
                    }

                    if (stacktrace.size() < 20)
                    {
                        stacktrace.push(new Trace(functionName, positions.getLine(node), positions.getColumn(node)));
                        caseAFunction(functionNode);
                        stacktrace.pop();

                        Value<?> value = values.get(functionNode);

                        if (value != null)
                        {
                            values.put(node, new Value<>(value.getValue(), value.isNA()));
                        }

                        setOut(node, getOut(functionNode));
                    }
                    else
                    {
                        values.put(node, new Value<>(true));
                        setOut(node, Variable.Type.NA);
                    }

                    for (int i = 0; i < function.getDefaultArgumentCount() + function.getNonDefaultArgumentCount(); ++i)
                    {
                        Object object = functionNode.getArgument().get(i);

                        if (object instanceof AArgument)
                        {
                            String variableName = ((AArgument) object).getIdentifier().getText().trim();

                            hierarchicalSymbolTable.getVariable(variableName, functionScope).removeType();
                            hierarchicalSymbolTable.getVariable(variableName, functionScope).removeValue();
                        }
                    }
                }
            }
            else
            {
                ++errors;
                System.err.println("Error " + errors + ": Function " + functionName + " at [" + line + ":" + position + "] is undefined." + iterationCounter.peek());
                printStacktrace();

                values.put(node, new Value<>(true));
                setOut(node, Variable.Type.NA);
            }
        }
    }

    @Override
    public void outACompareComparison(ACompareComparison node) //Checked
    {
        Variable.Type lType = (Variable.Type) getOut(node.getFirst());
        Variable.Type rType = (Variable.Type) getOut(node.getSecond());

        Value.ComparisonOperation operation = null;
        if (node.getComparisonSymbol() instanceof AEqComparisonSymbol) operation = Value.ComparisonOperation.EQUAL;
        else if (node.getComparisonSymbol() instanceof AGrtEqComparisonSymbol) operation = Value.ComparisonOperation.GREATER_EQUAL;
        else if (node.getComparisonSymbol() instanceof AGrtComparisonSymbol) operation = Value.ComparisonOperation.GREATER;
        else if (node.getComparisonSymbol() instanceof ALessEqComparisonSymbol) operation = Value.ComparisonOperation.LESS_EQUAL;
        else if (node.getComparisonSymbol() instanceof ALessComparisonSymbol) operation = Value.ComparisonOperation.LESS;
        else if (node.getComparisonSymbol() instanceof ANEqComparisonSymbol) operation = Value.ComparisonOperation.NON_EQUAL;

        Value<?> lhs = values.get(node.getFirst());
        Value<?> rhs = values.get(node.getSecond());

        if(operation == Value.ComparisonOperation.EQUAL || operation == Value.ComparisonOperation.NON_EQUAL)
        {
            Value<Boolean> result = Value.Compare(lhs, rhs, operation);

            if (result == null)
            {
                setOut(node, false);
            }
            else
            {
                setOut(node, result.getValue());
            }
        }
        else
        {
            if ((lType == rType && lType != Variable.Type.NONE) ||
                    (lType != Variable.Type.NONE && rType == Variable.Type.NA) ||
                    (lType == Variable.Type.NA && rType != Variable.Type.NONE))
            {
                Value<Boolean> result = Value.Compare(lhs, rhs, operation);

                if (result == null)
                {
                    ++errors;
                    System.err.println("Error " + errors + ": Incompatible array data types for comparison at [" + positions.getLine(node) + ":" + positions.getColumn(node) + "]." + iterationCounter.peek());
                    printStacktrace();

                    setOut(node, false);
                }
                else
                {
                    setOut(node, result.getValue());
                }
            }
            else
            {
                ++errors;
                System.err.println("Error " + errors + ": Incompatible data types for comparison at [" + positions.getLine(node) + ":" + positions.getColumn(node) + "]. Types found " + lType.name() + " and " + rType.name() + "." + iterationCounter.peek());
                printStacktrace();

                setOut(node, false);
            }
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
                if (hierarchicalSymbolTable.isAmbiguous(functionName, argumentNumber))
                {
                    ++errors;
                    System.err.println("Error " + errors + ": Function call " + functionName + " at [" + line + ":" + position + "] is ambiguous." + iterationCounter.peek());
                    printStacktrace();

                    values.put(node, new Value<>(true));
                    setOut(node, Variable.Type.NA);
                }
                else
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
                            Variable.Type type = (Variable.Type) getOut((Node) ((AFunctionCall) functionCall).getExpression().get(i));
                            Value<?> value = values.get(((AFunctionCall) functionCall).getExpression().get(i));

                            if (value != null)
                            {
                                hierarchicalSymbolTable.getVariable(variableName, functionScope).addValue(new Value<>(value.getValue(), value.isNA()));
                            }
                            else
                            {
                                hierarchicalSymbolTable.getVariable(variableName, functionScope).addValue(null);
                            }

                            hierarchicalSymbolTable.getVariable(variableName, functionScope).addType(type);
                        }
                    }

                    for (int i = argumentNumber; i < function.getDefaultArgumentCount() + function.getNonDefaultArgumentCount(); ++i)
                    {
                        Object object = functionNode.getArgument().get(i);

                        if (object instanceof AArgument)
                        {
                            String variableName = ((AArgument) object).getIdentifier().getText().trim();

                            Variable variable = hierarchicalSymbolTable.getVariable(variableName, functionScope);

                            variable.addValue(variable.getDefaultValue());
                            variable.addType(variable.getDefaultType());
                        }
                    }

                    if (stacktrace.size() < 20)
                    {
                        stacktrace.push(new Trace(functionName, positions.getLine(node), positions.getColumn(node)));
                        caseAFunction(functionNode);
                        stacktrace.pop();

                        Value<?> value = values.get(functionNode);

                        if (value != null)
                        {
                            values.put(node, new Value<>(value.getValue(), value.isNA()));
                        }

                        setOut(node, getOut(functionNode));
                    }
                    else
                    {
                        setOut(node, Variable.Type.NA);
                    }

                    for (int i = 0; i < function.getDefaultArgumentCount() + function.getNonDefaultArgumentCount(); ++i)
                    {
                        Object object = functionNode.getArgument().get(i);

                        if (object instanceof AArgument)
                        {
                            String variableName = ((AArgument) object).getIdentifier().getText().trim();

                            hierarchicalSymbolTable.getVariable(variableName, functionScope).removeValue();
                            hierarchicalSymbolTable.getVariable(variableName, functionScope).removeType();
                        }
                    }
                }
            }
            else
            {
                ++errors;
                System.err.println("Error " + errors + ": Function " + functionName + " at [" + line + ":" + position + "] is undefined." + iterationCounter.peek());
                printStacktrace();

                values.put(node, new Value<>(true));
                setOut(node, Variable.Type.NA);
            }
        }
    }

    @Override
    public void outATypeExpression(ATypeExpression node) //Checked
    {
        setOut(node, Variable.Type.NONE);
    }

    @Override
    public void outAOpenExpression(AOpenExpression node) //Checked
    {
        setOut(node, Variable.Type.NONE);
    }

    @Override
    public void outAAssignStatement(AAssignStatement node) //Checked TODO: Need revision - Deep copy implementation needed
    {
        String name = node.getIdentifier().getText().trim();

        if (node.getOperationAssign() instanceof AAssignOperationAssign)
        {
            Variable variable = hierarchicalSymbolTable.getVariable(name, scopeID);
            Value<?> value = values.get(node.getExpression());

            variable.setType((Variable.Type) getOut(node.getExpression()));
            if (value != null)
            {
                variable.setValue(new Value<>(value.getValue(), value.isNA()));
            }
            variable.setState(Variable.State.DECLARED);
        }
        else
        {
            if (hierarchicalSymbolTable.containsVariable(name, scopeID) && hierarchicalSymbolTable.getVariable(name, scopeID).getState() == Variable.State.DECLARED)
            {
                Variable variable = hierarchicalSymbolTable.getVariable(name, scopeID);
                Variable.Type expressionType = (Variable.Type) getOut(node.getExpression());

                if (variable.getType() == Variable.Type.INTEGER)
                {
                    if (expressionType == Variable.Type.INTEGER)
                    {
                        Value<?> variableValue = variable.getValue();
                        Value<?> expressionValue = values.get(node.getExpression());

                        if (node.getOperationAssign() instanceof AMinusAssignOperationAssign)
                        {
                            variable.setValue(Value.Subtract(variableValue, expressionValue));
                        }
                        else
                        {
                            variable.setValue(Value.Divide(variableValue, expressionValue));
                        }
                    }
                    else if (expressionType == Variable.Type.NA)
                    {
                        variable.setValue(new Value<>(true));
                    }
                    else
                    {
                        ++errors;
                        System.err.println("Error " + errors + ": Invalid expression type at [" + positions.getLine(node.getExpression()) + ":" + positions.getColumn(node.getExpression()) + "]. Found '" + expressionType.name() + "', expected 'INTEGER'." + iterationCounter.peek());
                        printStacktrace();

                        variable.setValue(new Value<>(true));
                    }
                }
                else if (variable.getType() == Variable.Type.NA)
                {
                    if (expressionType != Variable.Type.NA && expressionType != Variable.Type.INTEGER)
                    {
                        ++errors;
                        System.err.println("Error " + errors + ": Invalid expression type at [" + positions.getLine(node.getExpression()) + ":" + positions.getColumn(node.getExpression()) + "]. Found '" + expressionType.name() + "', expected 'INTEGER'." + iterationCounter.peek());
                        printStacktrace();
                    }

                    variable.setValue(new Value<>(true));
                }
                else
                {
                    ++errors;
                    System.err.println("Error " + errors + ": Invalid variable type at [" + node.getIdentifier().getLine() + ":" + node.getIdentifier().getPos() + "]. Found '" + variable.getType().name() + "', expected 'INTEGER'." + iterationCounter.peek());
                    printStacktrace();

                    variable.setValue(new Value<>(true));
                }
            }
            else
            {
                ++errors;
                System.err.println("Error " + errors + ": Variable " + name + " at [" + node.getIdentifier().getLine() + ":" + node.getIdentifier().getPos() + "] is undefined." + iterationCounter.peek());
                printStacktrace();
            }
        }
    }

    @Override
    public void outAArrayAssignStatement(AArrayAssignStatement node) //Checked
    {
        String name = node.getIdentifier().getText().trim();

        if (hierarchicalSymbolTable.containsVariable(name, scopeID) && hierarchicalSymbolTable.getVariable(name, scopeID).getState() == Variable.State.DECLARED)
        {
            Variable variable = hierarchicalSymbolTable.getVariable(name, scopeID);
            Variable.Type indexType = (Variable.Type) getOut(node.getFirst());
            Variable.Type valueType = (Variable.Type) getOut(node.getSecond());

            Value<?> value = values.get(node.getSecond());

            if (variable.getType() == Variable.Type.ARRAY)
            {
                ArrayList<Value<?>> list = (ArrayList<Value<?>>)variable.getValue().getValue();

                if (indexType == Variable.Type.INTEGER)
                {
                    int index = (Integer)values.get(node.getFirst()).getValue();

                    if (index >= list.size() || index < 0)
                    {
                        ++errors;
                        System.err.println("Error " + errors + ": Index out of range at [" + node.getIdentifier().getLine() + ":" + node.getIdentifier().getPos() + "]." + iterationCounter.peek());
                        printStacktrace();
                    }
                    else
                    {
                        if (value != null)
                        {
                            list.set(index, new Value<>(value.getValue(), value.isNA()));
                        }
                        else
                        {
                            list.set(index, null);
                        }
                    }
                }
                else if (indexType == Variable.Type.NA)
                {
                    if (value != null)
                    {
                        for (int i = 0; i < list.size(); ++i)
                        {
                            list.set(i, new Value<>(value.getValue(), value.isNA()));
                        }
                    }
                    else
                    {
                        for (int i = 0; i < list.size(); ++i)
                        {
                            list.set(i, null);
                        }
                    }
                }
                else
                {
                    ++errors;
                    System.err.println("Error " + errors + ": Invalid array index type at [" + positions.getLine(node.getFirst()) + ":" + positions.getColumn(node.getFirst()) + "]. Found '"+ indexType.name() +"', expected 'INTEGER' or 'STRING'." + iterationCounter.peek());
                    printStacktrace();
                }
            }
            else if (variable.getType() == Variable.Type.STRING)
            {
                String word = (String)variable.getValue().getValue();

                if (indexType == Variable.Type.INTEGER)
                {
                    int index = (Integer)values.get(node.getFirst()).getValue();

                    if (index >= word.length() || index < 0)
                    {
                        ++errors;
                        System.err.println("Error " + errors + ": Index out of range at [" + node.getIdentifier().getLine() + ":" + node.getIdentifier().getPos() + "]." + iterationCounter.peek());
                        printStacktrace();
                    }
                    else
                    {
                        if (valueType == Variable.Type.STRING)
                        {
                            String letter = (String)values.get(node.getSecond()).getValue();

                            if (letter.length() == 1)
                            {
                                word = word.substring(0, index) + letter + word.substring(index + 1);
                                variable.setValue(new Value<>(word, false));
                            }
                            else
                            {
                                ++errors;
                                System.err.println("Error " + errors + ": Expected string length at [" + positions.getLine(node.getSecond()) + ":" + positions.getColumn(node.getSecond()) + "] is 1. String length found " + letter.length() + "." + iterationCounter.peek());
                                printStacktrace();
                            }
                        }
                        else if (valueType == Variable.Type.NA)
                        {
                            variable.setValue(new Value<>(true));
                        }
                    }
                }
                else if (indexType == Variable.Type.NA)
                {
                    if (valueType == Variable.Type.STRING)
                    {
                        String letter = (String)values.get(node.getSecond()).getValue();

                        if (letter.length() == 1)
                        {
                            variable.setValue(new Value<>(letter.repeat(word.length()), false));
                        }
                        else
                        {
                            ++errors;
                            System.err.println("Error " + errors + ": Expected string length at [" + positions.getLine(node.getSecond()) + ":" + positions.getColumn(node.getSecond()) + "] is 1. String length found " + letter.length() + "." + iterationCounter.peek());
                            printStacktrace();
                        }
                    }
                    else if (valueType == Variable.Type.NA)
                    {
                        variable.setValue(new Value<>(true));
                    }
                }
                else
                {
                    ++errors;
                    System.err.println("Error " + errors + ": Invalid array index type at [" + positions.getLine(node.getFirst()) + ":" + positions.getColumn(node.getFirst()) + "]. Found '" + indexType.name() +"', expected 'INTEGER'." + iterationCounter.peek());
                    printStacktrace();
                }

                if (valueType != Variable.Type.STRING && valueType != Variable.Type.NA)
                {
                    ++errors;
                    System.err.println("Error " + errors + ": Invalid expression type at [" + positions.getLine(node.getSecond()) + ":" + positions.getColumn(node.getSecond()) + "]. Found '" + valueType.name() +"', expected 'STRING'." + iterationCounter.peek());
                    printStacktrace();
                }
            }
            else
            {
                ++errors;
                System.err.println("Error " + errors + ": Invalid variable type at [" + node.getIdentifier().getLine() + ":" + node.getIdentifier().getPos() + "]. Found '" + variable.getType().name() + "', expected 'ARRAY' or 'STRING'." + iterationCounter.peek());
                printStacktrace();
            }
        }
        else
        {
            ++errors;
            System.err.println("Error " + errors + ": Variable " + name + " at [" + node.getIdentifier().getLine() + ":" + node.getIdentifier().getPos() + "] is undefined." + iterationCounter.peek());
            printStacktrace();
        }
    }

    @Override
    public void inAFunction(AFunction node)
    {
        currentFunctionDef.push(node);

        String name = node.getIdentifier().getText().trim();
        int argumentNumber = node.getArgument().size();

        Function function = null;

        if (hierarchicalSymbolTable.isAmbiguous(name, argumentNumber))
        {
            for (Map.Entry<Function, AFunction> entry : hierarchicalSymbolTable.functionDef.entrySet())
            {
                if (entry.getValue().equals(node))
                {
                    function = entry.getKey();
                    break;
                }
            }
        }
        else
        {
            function = hierarchicalSymbolTable.getFunction(name, argumentNumber);
        }

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
    public void outAFunction(AFunction node) //Checked
    {
        String name = node.getIdentifier().getText().trim();
        int argumentNumber = node.getArgument().size();

        Function function = hierarchicalSymbolTable.getFunction(name, argumentNumber);
        scopeID = function.getID();

        hierarchicalSymbolTable.resetScope(scopeID);

        currentFunctionDef.pop();
        scopeID = scopeStack.pop();
    }

    @Override
    public void outAReturnStatement(AReturnStatement node)
    {
        if (!currentFunctionDef.empty())
        {
            Value<?> value = values.get(node.getExpression());

            if (value != null)
            {
                values.put(currentFunctionDef.peek(), new Value<>(values.get(node.getExpression()).getValue(), values.get(node.getExpression()).isNA()));
            }

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
            else if (containerType == Variable.Type.NA)
            {
                iterator.setType(Variable.Type.NA);
            }
            else
            {
                ++errors;
                System.err.println("Error " + errors + ": Invalid variable type at [" + node.getSecond().getLine() + ":" + node.getSecond().getPos() + "]. Found type '" + containerType.name() + "'." + iterationCounter.peek());
                printStacktrace();
                iterator.setType(Variable.Type.NA);
            }
        }
        else
        {
            ++errors;
            System.err.println("Error " + errors + ": Variable " + containerName + " at [" + node.getSecond().getLine() + ":" + node.getSecond().getPos() + "] is undefined." + iterationCounter.peek());
            printStacktrace();
            iterator.setType(Variable.Type.NA);
        }
    }

    @Override
    public void outANotComparison(ANotComparison node) //Checked
    {
        setOut(node, !(boolean)getOut(node.getComparison()));
    }

    @Override
    public void outALogOpComparison(ALogOpComparison node) //Checked
    {
        if (node.getOperationLogical() instanceof AAndOperationLogical)
        {
            setOut(node, (boolean)getOut(node.getFirst()) && (boolean)getOut(node.getSecond()));
        }
        else
        {
            setOut(node, (boolean)getOut(node.getFirst()) || (boolean)getOut(node.getSecond()));
        }
    }

    @Override
    public void outATrueComparison(ATrueComparison node) //Checked
    {
        setOut(node, true);
    }

    @Override
    public void outAFalseComparison(AFalseComparison node) //Checked
    {
        setOut(node, false);
    }

    @Override
    public void caseAIfStatement(AIfStatement node) //Checked
    {
        inAIfStatement(node);
        if(node.getComparison() != null)
        {
            node.getComparison().apply(this);
        }
        if(node.getStatement() != null && (boolean) getOut(node.getComparison()))
        {
            node.getStatement().apply(this);
        }
        outAIfStatement(node);
    }

    @Override
    public void caseAWhileStatement(AWhileStatement node) //Checked
    {
        inAWhileStatement(node);
        if(node.getComparison() != null)
        {
            node.getComparison().apply(this);
        }

        if(node.getStatement() != null)
        {
            for (int i = 0; i < 10 && (boolean)getOut(node.getComparison()); ++i)
            {
                iterationCounter.push(" @ iteration " + (i + 1) + (i == 9 ? " ~ max search depth" : ""));
                node.getStatement().apply(this);
                iterationCounter.pop();

                if(node.getComparison() != null)
                {
                    node.getComparison().apply(this);
                }
            }
        }
        outAWhileStatement(node);
    }

    @Override
    public void caseAForStatement(AForStatement node)
    {
        inAForStatement(node);
        if(node.getFirst() != null)
        {
            node.getFirst().apply(this);
        }

        Variable.Type containerType = Variable.Type.NONE;

        if(node.getSecond() != null)
        {
            node.getSecond().apply(this);

            if (hierarchicalSymbolTable.containsVariable(node.getSecond().getText().trim(), scopeID))
            {
                containerType = hierarchicalSymbolTable.getVariable(node.getSecond().getText().trim(), scopeID).getType();
            }
        }

        if(node.getStatement() != null)
        {
            if (containerType == Variable.Type.STRING)
            {
                String value = (String) hierarchicalSymbolTable.getVariable(node.getSecond().getText().trim(), scopeID).getValue().getValue();
                for (int i = 0; i < value.length(); ++i)
                {
                    values.put(node.getFirst(), new Value<>(value.substring(i, i + 1), false));
                    iterationCounter.push(" @ iteration " + (i + 1));
                    node.getStatement().apply(this);
                    iterationCounter.pop();
                }
            }
            else if (containerType == Variable.Type.ARRAY)
            {
                ArrayList<Value<?>> value = (ArrayList<Value<?>>) hierarchicalSymbolTable.getVariable(node.getSecond().getText().trim(), scopeID).getValue().getValue();
                int i = 0;
                for (Value<?> item : value)
                {
                    ++i;
                    if (item == null)
                    {
                        hierarchicalSymbolTable.getVariable(node.getFirst().getText().trim(), scopeID).setType(Variable.Type.NONE);
                    }
                    else
                    {
                        if (item.getValue() instanceof Integer)
                        {
                            hierarchicalSymbolTable.getVariable(node.getFirst().getText().trim(), scopeID).setType(Variable.Type.INTEGER);
                        }
                        else if (item.getValue() instanceof String)
                        {
                            hierarchicalSymbolTable.getVariable(node.getFirst().getText().trim(), scopeID).setType(Variable.Type.STRING);
                        }
                        else if (item.getValue() instanceof Collection)
                        {
                            hierarchicalSymbolTable.getVariable(node.getFirst().getText().trim(), scopeID).setType(Variable.Type.ARRAY);
                        }
                        else
                        {
                            hierarchicalSymbolTable.getVariable(node.getFirst().getText().trim(), scopeID).setType(Variable.Type.NA);
                        }

                        hierarchicalSymbolTable.getVariable(node.getFirst().getText().trim(), scopeID).setValue(new Value<>(item.getValue(), false));
                    }

                    iterationCounter.push(" @ iteration " + i);
                    node.getStatement().apply(this);
                    iterationCounter.pop();
                }
            }
            else if (containerType == Variable.Type.NA)
            {
                hierarchicalSymbolTable.getVariable(node.getFirst().getText().trim(), scopeID).setType(Variable.Type.NA);
                hierarchicalSymbolTable.getVariable(node.getFirst().getText().trim(), scopeID).setValue(new Value<>(true));
                node.getStatement().apply(this);
            }
        }

        outAForStatement(node);
    }
}
