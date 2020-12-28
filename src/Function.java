import java.util.ArrayList;

public class Function
{
    private String name;

    private Variable.Type returnType;

    private int position;

    private ArrayList<Argument> argumentList;

    private int defaultArgumentCount;

    private int nonDefaultArgumentCount;

    public Function()
    {
        argumentList = new ArrayList<>();
    }

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

    public int getPosition()
    {
        return position;
    }

    public void setPosition(int position)
    {
        this.position = position;
    }

    public int getDefaultArgumentCount()
    {
        return  defaultArgumentCount;
    }

    public int getNonDefaultArgumentCount()
    {
        return nonDefaultArgumentCount;
    }

    public void addArgument(String name, boolean hasDefault, Variable.Type type)
    {
        argumentList.add(new Argument(name, hasDefault, type));

        if (hasDefault) ++defaultArgumentCount;
        else ++nonDefaultArgumentCount;
    }

    public boolean containsArgument(String name)
    {
        for (Argument argument : argumentList)
        {
            if (argument.name.equals(name)) return true;
        }

        return false;
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

    private class Argument
    {
        private String name;

        private boolean hasDefault;

        private Variable.Type type;

        public Argument(String name, boolean hasDefault, Variable.Type type)
        {
            this.name = name;
            this.hasDefault = hasDefault;
            this.type = type;
        }
    }
}
