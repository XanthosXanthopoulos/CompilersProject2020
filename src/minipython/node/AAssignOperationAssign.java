/* This file was generated by SableCC (http://www.sablecc.org/). */

package minipython.node;

import java.util.*;
import minipython.analysis.*;

public final class AAssignOperationAssign extends POperationAssign
{

    public AAssignOperationAssign()
    {
    }
    public Object clone()
    {
        return new AAssignOperationAssign();
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAAssignOperationAssign(this);
    }

    public String toString()
    {
        return "";
    }

    void removeChild(Node child)
    {
    }

    void replaceChild(Node oldChild, Node newChild)
    {
    }
}
