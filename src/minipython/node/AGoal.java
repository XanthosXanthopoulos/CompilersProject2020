/* This file was generated by SableCC (http://www.sablecc.org/). */

package minipython.node;

import java.util.*;
import minipython.analysis.*;

public final class AGoal extends PGoal
{
    private final LinkedList _action_ = new TypedLinkedList(new Action_Cast());

    public AGoal()
    {
    }

    public AGoal(
        List _action_)
    {
        {
            this._action_.clear();
            this._action_.addAll(_action_);
        }

    }
    public Object clone()
    {
        return new AGoal(
            cloneList(_action_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAGoal(this);
    }

    public LinkedList getAction()
    {
        return _action_;
    }

    public void setAction(List list)
    {
        _action_.clear();
        _action_.addAll(list);
    }

    public String toString()
    {
        return ""
            + toString(_action_);
    }

    void removeChild(Node child)
    {
        if(_action_.remove(child))
        {
            return;
        }

    }

    void replaceChild(Node oldChild, Node newChild)
    {
        for(ListIterator i = _action_.listIterator(); i.hasNext();)
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

    private class Action_Cast implements Cast
    {
        public Object cast(Object o)
        {
            PAction node = (PAction) o;

            if((node.parent() != null) &&
                (node.parent() != AGoal.this))
            {
                node.parent().removeChild(node);
            }

            if((node.parent() == null) ||
                (node.parent() != AGoal.this))
            {
                node.parent(AGoal.this);
            }

            return node;
        }
    }
}
