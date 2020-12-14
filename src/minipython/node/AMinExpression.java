/* This file was generated by SableCC (http://www.sablecc.org/). */

package minipython.node;

import java.util.*;
import minipython.analysis.*;

public final class AMinExpression extends PExpression
{
    private final LinkedList _value_ = new TypedLinkedList(new Value_Cast());

    public AMinExpression()
    {
    }

    public AMinExpression(
        List _value_)
    {
        {
            this._value_.clear();
            this._value_.addAll(_value_);
        }

    }
    public Object clone()
    {
        return new AMinExpression(
            cloneList(_value_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAMinExpression(this);
    }

    public LinkedList getValue()
    {
        return _value_;
    }

    public void setValue(List list)
    {
        _value_.clear();
        _value_.addAll(list);
    }

    public String toString()
    {
        return ""
            + toString(_value_);
    }

    void removeChild(Node child)
    {
        if(_value_.remove(child))
        {
            return;
        }

    }

    void replaceChild(Node oldChild, Node newChild)
    {
        for(ListIterator i = _value_.listIterator(); i.hasNext();)
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

    private class Value_Cast implements Cast
    {
        public Object cast(Object o)
        {
            PValue node = (PValue) o;

            if((node.parent() != null) &&
                (node.parent() != AMinExpression.this))
            {
                node.parent().removeChild(node);
            }

            if((node.parent() == null) ||
                (node.parent() != AMinExpression.this))
            {
                node.parent(AMinExpression.this);
            }

            return node;
        }
    }
}
