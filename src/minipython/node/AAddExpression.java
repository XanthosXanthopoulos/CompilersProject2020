/* This file was generated by SableCC (http://www.sablecc.org/). */

package minipython.node;

import java.util.*;
import minipython.analysis.*;

public final class AAddExpression extends PExpression
{
    private PExpression _first_;
    private PExpression _second_;

    public AAddExpression()
    {
    }

    public AAddExpression(
        PExpression _first_,
        PExpression _second_)
    {
        setFirst(_first_);

        setSecond(_second_);

    }
    public Object clone()
    {
        return new AAddExpression(
            (PExpression) cloneNode(_first_),
            (PExpression) cloneNode(_second_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAAddExpression(this);
    }

    public PExpression getFirst()
    {
        return _first_;
    }

    public void setFirst(PExpression node)
    {
        if(_first_ != null)
        {
            _first_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _first_ = node;
    }

    public PExpression getSecond()
    {
        return _second_;
    }

    public void setSecond(PExpression node)
    {
        if(_second_ != null)
        {
            _second_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _second_ = node;
    }

    public String toString()
    {
        return ""
            + toString(_first_)
            + toString(_second_);
    }

    void removeChild(Node child)
    {
        if(_first_ == child)
        {
            _first_ = null;
            return;
        }

        if(_second_ == child)
        {
            _second_ = null;
            return;
        }

    }

    void replaceChild(Node oldChild, Node newChild)
    {
        if(_first_ == oldChild)
        {
            setFirst((PExpression) newChild);
            return;
        }

        if(_second_ == oldChild)
        {
            setSecond((PExpression) newChild);
            return;
        }

    }
}
