/* This file was generated by SableCC (http://www.sablecc.org/). */

package minipython.node;

import java.util.*;
import minipython.analysis.*;

public final class APrintStatement extends PStatement
{
    private final LinkedList _expression_ = new TypedLinkedList(new Expression_Cast());

    public APrintStatement()
    {
    }

    public APrintStatement(
        List _expression_)
    {
        {
            this._expression_.clear();
            this._expression_.addAll(_expression_);
        }

    }
    public Object clone()
    {
        return new APrintStatement(
            cloneList(_expression_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAPrintStatement(this);
    }

    public LinkedList getExpression()
    {
        return _expression_;
    }

    public void setExpression(List list)
    {
        _expression_.clear();
        _expression_.addAll(list);
    }

    public String toString()
    {
        return ""
            + toString(_expression_);
    }

    void removeChild(Node child)
    {
        if(_expression_.remove(child))
        {
            return;
        }

    }

    void replaceChild(Node oldChild, Node newChild)
    {
        for(ListIterator i = _expression_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set(newChild);
                    oldChild.parent(null);
                    return;
                }

                i.remove();
                oldChild.parent(null);
                return;
            }
        }

    }

    private class Expression_Cast implements Cast
    {
        public Object cast(Object o)
        {
            PExpression node = (PExpression) o;

            if((node.parent() != null) &&
                (node.parent() != APrintStatement.this))
            {
                node.parent().removeChild(node);
            }

            if((node.parent() == null) ||
                (node.parent() != APrintStatement.this))
            {
                node.parent(APrintStatement.this);
            }

            return node;
        }
    }
}
