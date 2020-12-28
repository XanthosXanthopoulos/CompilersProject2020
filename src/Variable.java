public class Variable
{
    private Type type;

    private String name;

    private int position;

    public Variable(Type type, String name, int position)
    {
        this.type = type;
        this.name = name;
        this.position = position;
    }

    public Variable()
    {
        this(Type.NA, "", -1);
    }

    public Type getType()
    {
        return type;
    }

    public void setType(Type type)
    {
        this.type = type;
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

    public enum Type
    {
        STRING,
        INTEGER,
        NONE,
        ARRAY,
        NA
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
