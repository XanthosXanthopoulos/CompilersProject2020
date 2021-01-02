import java.util.Stack;

public class Variable
{
    private Stack<Type> type;

    private Type defaultType;

    private State state;

    private String name;

    private int position;

    public Variable(Type type, String name, int position)
    {
        this.type = new Stack<>();

        this.defaultType = type;
        this.name = name;
        this.position = position;

        this.type.push(Type.NA);
    }

    public Variable()
    {
        this(Type.NA, "", -1);
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

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getPosition()
    {
        return position;
    }

    public void setPosition(int position)
    {
        this.position = position;
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

    public void setDefaultType(Type defaultType)
    {
        this.defaultType = defaultType;
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
