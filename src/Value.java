import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;

public class Value<T>
{
    private Stack<T> value;

    public boolean isNA()
    {
        return isNA;
    }

    private boolean isNA;

    public Value(T value, boolean isNA)
    {
        this.value = new Stack<>();

        if (value instanceof Collection)
        {
            ArrayList<?> list = new ArrayList<>();

            list.addAll((Collection)value);
            this.value.push((T)list);
        }
        else
        {
            this.value.push(value);
        }

        this.isNA = isNA;
    }

    public Value(boolean isNA)
    {
        this.value = new Stack<>();
        value.push(null);
        this.isNA = isNA;
    }

    public static <E, T> Value<?> Add(Value<E> lhs, Value<T> rhs)
    {
        if (!lhs.isNA && !rhs.isNA)
        {

            E lhs_val = lhs.value.peek();
            T rhs_val = rhs.value.peek();

            if (lhs_val instanceof Integer && rhs_val instanceof Integer)
            {
                return new Value<>((int)lhs_val + (int)rhs_val, false);
            }
            else if (lhs_val instanceof String && rhs_val instanceof String)
            {
                return new Value<>(String.valueOf(lhs_val) + rhs_val, false);
            }
            else if (lhs_val instanceof Collection<?> && rhs_val instanceof Collection<?>)
            {
                ArrayList<?> list = new ArrayList<>();
                list.addAll((Collection) lhs_val);
                list.addAll((Collection) rhs_val);

                return new Value<>(list, false);
            }
        }

        return new Value<>(true);
    }

    public static <E, T> Value<Integer> Subtract(Value<E> lhs, Value<T> rhs)
    {
        if (!lhs.isNA && !rhs.isNA)
        {
            E lhs_val = lhs.value.peek();
            T rhs_val = rhs.value.peek();

            if (lhs_val instanceof Integer && rhs_val instanceof Integer)
            {
                return new Value<>((int)lhs_val - (int)rhs_val, false);
            }
        }

        return new Value<>(true);
    }

    public static <E, T> Value<Integer> Multiply(Value<E> lhs, Value<T> rhs)
    {
        if (!lhs.isNA && !rhs.isNA)
        {
            E lhs_val = lhs.value.peek();
            T rhs_val = rhs.value.peek();

            if (lhs_val instanceof Integer && rhs_val instanceof Integer)
            {
                return new Value<>((int)lhs_val * (int)rhs_val, false);
            }
        }

        return new Value<>(true);
    }

    public static <E, T> Value<Integer> Divide(Value<E> lhs, Value<T> rhs)
    {
        if (!lhs.isNA && !rhs.isNA)
        {
            E lhs_val = lhs.value.peek();
            T rhs_val = rhs.value.peek();

            if (lhs_val instanceof Integer && rhs_val instanceof Integer)
            {
                return new Value<>((int) lhs_val / (int) rhs_val, false);
            }
        }

        return new Value<>(true);
    }

    public static <E, T> Value<Integer> Modulo(Value<E> lhs, Value<T> rhs)
    {
        if (!lhs.isNA && !rhs.isNA)
        {
            E lhs_val = lhs.value.peek();
            T rhs_val = rhs.value.peek();

            if (lhs_val instanceof Integer && rhs_val instanceof Integer)
            {
                return new Value<>((int) lhs_val % (int) rhs_val, false);
            }
        }

        return new Value<>(true);
    }

    public static <E, T> Value<Integer> Power(Value<E> lhs, Value<T> rhs)
    {
        if (!lhs.isNA && !rhs.isNA)
        {
            E lhs_val = lhs.value.peek();
            T rhs_val = rhs.value.peek();

            if (lhs_val instanceof Integer && rhs_val instanceof Integer)
            {
                return new Value<>((int) Math.pow((int)lhs_val, (int)rhs_val), false);
            }
        }

        return new Value<>(true);
    }

    public static <E, T> Value<?> Max(Value<E> lhs, Value<T> rhs)
    {
        if (!lhs.isNA && !rhs.isNA)
        {
            E lhs_val = lhs.value.peek();
            T rhs_val = rhs.value.peek();

            if (lhs_val instanceof Integer && rhs_val instanceof Integer)
            {
                return new Value<>(Math.max((int)lhs_val, (int)rhs_val), false);
            }
            else if (lhs_val instanceof String && rhs_val instanceof String)
            {
                return new Value<>(((String) lhs_val).compareTo((String)rhs_val) > 0 ? lhs_val : rhs_val, false);
            }
        }

        return new Value<>(true);
    }

    public static <E, T> Value<?> Min(Value<E> lhs, Value<T> rhs)
    {
        if (!lhs.isNA && !rhs.isNA)
        {
            E lhs_val = lhs.value.peek();
            T rhs_val = rhs.value.peek();

            if (lhs_val instanceof Integer && rhs_val instanceof Integer)
            {
                return new Value<>(Math.min((int)lhs_val, (int)rhs_val), false);
            }
            else if (lhs_val instanceof String && rhs_val instanceof String)
            {
                return new Value<>(((String) lhs_val).compareTo((String)rhs_val) < 0 ? lhs_val : rhs_val, false);
            }
        }

        return new Value<>(true);
    }

    public static <E, T> boolean Compare(Value<E> lhs, Value<T> rhs, ComparisonOperation operation)
    {
        if (!lhs.isNA && !rhs.isNA)
        {
            E lhs_val = lhs.value.peek();
            T rhs_val = rhs.value.peek();

            int result = 0;

            if (lhs_val instanceof Integer && rhs_val instanceof Integer)
            {
                result = ((Integer) lhs_val).compareTo((Integer)rhs_val);
            }
            else if (lhs_val instanceof String && rhs_val instanceof String)
            {
                result = ((String) lhs_val).compareTo((String)rhs_val);
            }
            else if (lhs_val instanceof ArrayList<?> && rhs_val instanceof  ArrayList<?>)
            {
                int min = Math.min(((ArrayList<?>) lhs_val).size(), ((ArrayList<?>) rhs_val).size());

                for (int i = 0; i < min && result == 0; ++i)
                {
                    result = Compare(((ArrayList<Value>) lhs_val).get(i), ((ArrayList<Value>) rhs_val).get(i), operation) ? 1 : 0;
                }

                if (result == 0)
                {
                    result = Compare(new Value<>(((ArrayList<?>) lhs_val).size(), false), new Value<>(((ArrayList<?>) rhs_val).size(), false), operation) ? 1 : 0;
                }

                return result == 1;
            }

            switch (operation)
            {
                case EQUAL:
                    return result == 0;
                case GREATER:
                    return result > 0;
                case GREATER_EQUAL:
                    return result >= 0;
                case LESS:
                    return result < 0;
                case LESS_EQUAL:
                    return result <= 0;
                case NON_EQUAL:
                    return result != 0;
            }
        }

        return false;
    }

    public T getValue()
    {
        return value.peek();
    }


    @Override
    public String toString()
    {
        if (value.peek() instanceof ArrayList)
        {
            StringBuilder output = new StringBuilder("[");
            for (Object object : (ArrayList<?>)value.peek())
            {
                output.append(object).append(", ");
            }
            output = new StringBuilder(output.substring(0, output.length() - 2) + "]");
            return output.toString();
        }

        return String.valueOf(value);
    }

    public enum ComparisonOperation
    {
        EQUAL,
        GREATER,
        GREATER_EQUAL,
        LESS,
        LESS_EQUAL,
        NON_EQUAL
    }
}
