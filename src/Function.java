import java.util.HashMap;

public class Function
{
    private String name;

    private Variable.Type returnType;

    private int defaultArgumentCount;

    private int nonDefaultArgumentCount;

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
        return name + "-" + nonDefaultArgumentCount + ":" + (nonDefaultArgumentCount + defaultArgumentCount);
    }

    public void setNonDefaultArgumentCount(int nonDefaultArgumentCount)
    {
        this.nonDefaultArgumentCount = nonDefaultArgumentCount;
    }

    public void setDefaultArgumentCount(int defaultArgumentCount)
    {
        this.defaultArgumentCount = defaultArgumentCount;
    }

    public static class Argument
    {
        private String name;

        private boolean hasDefault;

        private Variable.Type type;

        private Variable.Type defaultType;

        public Argument(String name, boolean hasDefault, Variable.Type type, Variable.Type defaultType)
        {
            this.name = name;
            this.hasDefault = hasDefault;
            this.type = type;
        }
    }
}
