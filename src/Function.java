import java.util.Stack;

public class Function
{
    private String name;

    private Variable.Type returnType;

    private int defaultArgumentCount;

    private int nonDefaultArgumentCount;

    private Stack<Variable.Type> defaultTypes;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Variable.Type getReturnType()
    {
        return returnType;
    }

    public void setReturnType(Variable.Type returnType)
    {
        this.returnType = returnType;
    }

    public int getDefaultArgumentCount()
    {
        return  defaultArgumentCount;
    }

    public int getNonDefaultArgumentCount()
    {
        return nonDefaultArgumentCount;
    }

    public Function()
    {
        defaultTypes = new Stack<>();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) return false;

        if (!(obj instanceof Function)) return false;

        if (obj == this) return true;

        Function f = (Function) obj;

        return name.equals(f.name) && nonDefaultArgumentCount < (f.defaultArgumentCount + f.nonDefaultArgumentCount) && f.nonDefaultArgumentCount < (defaultArgumentCount + nonDefaultArgumentCount);
    }

    public String getID()
    {
        StringBuilder ID = new StringBuilder(name + "-" + nonDefaultArgumentCount + ":" + (nonDefaultArgumentCount + defaultArgumentCount));
        for (Variable.Type type : defaultTypes)
        {
            ID.append(" ").append(type.name());
        }
        return ID.toString();
    }

    public void setNonDefaultArgumentCount(int nonDefaultArgumentCount)
    {
        this.nonDefaultArgumentCount = nonDefaultArgumentCount;
    }

    public void setDefaultArgumentCount(int defaultArgumentCount)
    {
        this.defaultArgumentCount = defaultArgumentCount;
    }

    public void addDefaultType(Variable.Type type)
    {
        defaultTypes.push(type);
    }
}
