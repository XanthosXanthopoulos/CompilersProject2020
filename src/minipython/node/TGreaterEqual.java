/* This file was generated by SableCC (http://www.sablecc.org/). */

package minipython.node;

import minipython.analysis.*;

public final class TGreaterEqual extends Token
{
    public TGreaterEqual()
    {
        super.setText(">=");
    }

    public TGreaterEqual(int line, int pos)
    {
        super.setText(">=");
        setLine(line);
        setPos(pos);
    }

    public Object clone()
    {
      return new TGreaterEqual(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTGreaterEqual(this);
    }

    public void setText(String text)
    {
        throw new RuntimeException("Cannot change TGreaterEqual text.");
    }
}