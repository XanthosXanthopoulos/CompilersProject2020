import java.util.Stack;

public class Variable
{
    private Stack<Type> type;

    private Stack<Value<?>> value;

    private Type defaultType;

    private Value<?> defaultValue;

    private State state;

    private String name;

    private int position;

    public Variable(Type type, Value<?> value, String name, int position)
    {
        this.type = new Stack<>();
        this.value = new Stack<>();

        this.defaultType = type;
        this.defaultValue = value;
        this.name = name;
        this.position = position;

        this.type.push(type);
        this.value.push(value);
    }

    public Type getType()
    {
        return type.peek();
    }

    public void setType(Type type)
    {
        if (!this.type.empty())
        {
            this.type.pop();
        }

        this.type.push(type);
    }

    public void addType(Type type)
    {
        this.type.push(type);
    }

    public void removeType()
    {
        this.type.pop();
    }

    public Value<?> getValue()
    {
        return value.peek();
    }

    public void setValue(Value<?> value)
    {
        if (!this.value.isEmpty())
        {
            this.value.pop();
        }

        this.value.push(value);
    }

    public void addValue(Value<?> value)
    {
        this.value.push(value);
    }

    public void removeValue()
    {
        value.pop();
    }

    public String getName()
    {
        return name;
    }

    public State getState()
    {
        return state;
    }

    public void setState(State state)
    {
        this.state = state;
    }

    public Type getDefaultType()
    {
        return defaultType;
    }

    public Value<?> getDefaultValue()
    {
        return defaultValue;
    }

    public enum Type
    {
        STRING,
        INTEGER,
        NONE,
        ARRAY,
        NA
    }

    public enum State
    {
        DECLARED,
        UNDECLARED
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) return false;

        if (!(obj instanceof Variable))

        if (obj == this) return true;

        Variable v = (Variable)obj;

        return name.equals(v.name) && type.equals(v.type) && position == v.position;
    }
}
