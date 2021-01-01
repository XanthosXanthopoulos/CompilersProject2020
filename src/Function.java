import java.util.HashMap;

public class Function
{
    private String name;

    private Variable.Type returnType;

    private int position;

    private HashMap<String, Argument> argumentList;

    private int defaultArgumentCount;

    private int nonDefaultArgumentCount;

    private boolean hasDefinition;

    public Function()
    {
        argumentList = new HashMap<>();
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
        argumentList.put(name, new Argument(name, hasDefault, Variable.Type.NA, type));

        if (hasDefault) ++defaultArgumentCount;
        else ++nonDefaultArgumentCount;
    }

    public boolean containsArgument(String name)
    {
        return argumentList.containsKey(name);
    }

    public Argument getArgument(String name)
    {
        return argumentList.get(name);
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

    public boolean isHasDefinition()
    {
        return hasDefinition;
    }

    public void setHasDefinition(boolean hasDefinition)
    {
        this.hasDefinition = hasDefinition;
    }

    public String getID()
    {
        return name + "-" + nonDefaultArgumentCount + ":" + (nonDefaultArgumentCount + defaultArgumentCount);
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

        public Variable.Type getType()
        {
            return type;
        }

        public void setType(Variable.Type type)
        {
            this.type = type;
        }

        public boolean isHasDefault()
        {
            return hasDefault;
        }

        public void setHasDefault(boolean hasDefault)
        {
            this.hasDefault = hasDefault;
        }

        public Variable.Type getDefaultType()
        {
            return defaultType;
        }

        public void setDefaultType(Variable.Type defaultType)
        {
            this.defaultType = defaultType;
        }
    }
}
