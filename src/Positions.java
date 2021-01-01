import minipython.analysis.DepthFirstAdapter;
import minipython.analysis.ReversedDepthFirstAdapter;
import minipython.node.*;

import java.util.HashMap;

public class Positions extends ReversedDepthFirstAdapter
{
    private int line;
    private int column;

    private HashMap<Node, Integer> lines;
    private HashMap<Node, Integer> columns;

    public Positions()
    {
        lines = new HashMap<>();
        columns = new HashMap<>();

        line = -1;
        column = -1;
    }

    @Override
    public void defaultOut(Node node)
    {
        lines.put(node, line);
        columns.put(node, column);
    }

    @Override
    public void defaultCase(Node node)
    {
        if (node instanceof Token)
        {
            line = ((Token) node).getLine();
            column = ((Token) node).getPos();
        }
    }

    public int getLine(Node node)
    {
        return lines.get(node);
    }

    public int getColumn(Node node)
    {
        return columns.get(node);
    }
}
