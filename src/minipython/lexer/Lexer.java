/* This file was generated by SableCC (http://www.sablecc.org/). */

package minipython.lexer;

import java.io.*;
import java.util.*;
import minipython.node.*;

public class Lexer
{
    protected Token token;
    protected State state = State.INITIAL;

    private PushbackReader in;
    private int line;
    private int pos;
    private boolean cr;
    private boolean eof;
    private final StringBuffer text = new StringBuffer();

    protected void filter() throws LexerException, IOException
    {
    }

    public Lexer(PushbackReader in)
    {
        this.in = in;

        if(gotoTable == null)
        {
            try
            {
                DataInputStream s = new DataInputStream(
                    new BufferedInputStream(
                    Lexer.class.getResourceAsStream("lexer.dat")));

                // read gotoTable
                int length = s.readInt();
                gotoTable = new int[length][][][];
                for(int i = 0; i < gotoTable.length; i++)
                {
                    length = s.readInt();
                    gotoTable[i] = new int[length][][];
                    for(int j = 0; j < gotoTable[i].length; j++)
                    {
                        length = s.readInt();
                        gotoTable[i][j] = new int[length][3];
                        for(int k = 0; k < gotoTable[i][j].length; k++)
                        {
                            for(int l = 0; l < 3; l++)
                            {
                                gotoTable[i][j][k][l] = s.readInt();
                            }
                        }
                    }
                }

                // read accept
                length = s.readInt();
                accept = new int[length][];
                for(int i = 0; i < accept.length; i++)
                {
                    length = s.readInt();
                    accept[i] = new int[length];
                    for(int j = 0; j < accept[i].length; j++)
                    {
                        accept[i][j] = s.readInt();
                    }
                }

                s.close();
            }
            catch(Exception e)
            {
                throw new RuntimeException("The file \"lexer.dat\" is either missing or corrupted.");
            }
        }
    }

    public Token peek() throws LexerException, IOException
    {
        while(token == null)
        {
            token = getToken();
            filter();
        }

        return token;
    }

    public Token next() throws LexerException, IOException
    {
        while(token == null)
        {
            token = getToken();
            filter();
        }

        Token result = token;
        token = null;
        return result;
    }

    protected Token getToken() throws IOException, LexerException
    {
        int dfa_state = 0;

        int start_pos = pos;
        int start_line = line;

        int accept_state = -1;
        int accept_token = -1;
        int accept_length = -1;
        int accept_pos = -1;
        int accept_line = -1;

        int[][][] gotoTable = this.gotoTable[state.id()];
        int[] accept = this.accept[state.id()];
        text.setLength(0);

        while(true)
        {
            int c = getChar();

            if(c != -1)
            {
                switch(c)
                {
                case 10:
                    if(cr)
                    {
                        cr = false;
                    }
                    else
                    {
                        line++;
                        pos = 0;
                    }
                    break;
                case 13:
                    line++;
                    pos = 0;
                    cr = true;
                    break;
                default:
                    pos++;
                    cr = false;
                    break;
                };

                text.append((char) c);

                do
                {
                    int oldState = (dfa_state < -1) ? (-2 -dfa_state) : dfa_state;

                    dfa_state = -1;

                    int[][] tmp1 =  gotoTable[oldState];
                    int low = 0;
                    int high = tmp1.length - 1;

                    while(low <= high)
                    {
                        int middle = (low + high) / 2;
                        int[] tmp2 = tmp1[middle];

                        if(c < tmp2[0])
                        {
                            high = middle - 1;
                        }
                        else if(c > tmp2[1])
                        {
                            low = middle + 1;
                        }
                        else
                        {
                            dfa_state = tmp2[2];
                            break;
                        }
                    }
                }while(dfa_state < -1);
            }
            else
            {
                dfa_state = -1;
            }

            if(dfa_state >= 0)
            {
                if(accept[dfa_state] != -1)
                {
                    accept_state = dfa_state;
                    accept_token = accept[dfa_state];
                    accept_length = text.length();
                    accept_pos = pos;
                    accept_line = line;
                }
            }
            else
            {
                if(accept_state != -1)
                {
                    switch(accept_token)
                    {
                    case 0:
                        {
                            Token token = new0(
                                getText(accept_length),
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 1:
                        {
                            Token token = new1(
                                getText(accept_length),
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 2:
                        {
                            Token token = new2(
                                getText(accept_length),
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 3:
                        {
                            Token token = new3(
                                getText(accept_length),
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 4:
                        {
                            Token token = new4(
                                getText(accept_length),
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 5:
                        {
                            Token token = new5(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 6:
                        {
                            Token token = new6(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 7:
                        {
                            Token token = new7(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 8:
                        {
                            Token token = new8(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 9:
                        {
                            Token token = new9(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 10:
                        {
                            Token token = new10(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 11:
                        {
                            Token token = new11(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 12:
                        {
                            Token token = new12(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 13:
                        {
                            Token token = new13(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 14:
                        {
                            Token token = new14(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 15:
                        {
                            Token token = new15(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 16:
                        {
                            Token token = new16(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 17:
                        {
                            Token token = new17(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 18:
                        {
                            Token token = new18(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 19:
                        {
                            Token token = new19(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 20:
                        {
                            Token token = new20(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 21:
                        {
                            Token token = new21(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 22:
                        {
                            Token token = new22(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 23:
                        {
                            Token token = new23(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 24:
                        {
                            Token token = new24(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 25:
                        {
                            Token token = new25(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 26:
                        {
                            Token token = new26(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 27:
                        {
                            Token token = new27(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 28:
                        {
                            Token token = new28(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 29:
                        {
                            Token token = new29(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 30:
                        {
                            Token token = new30(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 31:
                        {
                            Token token = new31(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 32:
                        {
                            Token token = new32(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 33:
                        {
                            Token token = new33(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 34:
                        {
                            Token token = new34(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 35:
                        {
                            Token token = new35(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 36:
                        {
                            Token token = new36(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 37:
                        {
                            Token token = new37(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 38:
                        {
                            Token token = new38(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 39:
                        {
                            Token token = new39(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 40:
                        {
                            Token token = new40(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 41:
                        {
                            Token token = new41(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 42:
                        {
                            Token token = new42(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 43:
                        {
                            Token token = new43(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 44:
                        {
                            Token token = new44(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 45:
                        {
                            Token token = new45(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 46:
                        {
                            Token token = new46(
                                getText(accept_length),
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    }
                }
                else
                {
                    if(text.length() > 0)
                    {
                        throw new LexerException(
                            "[" + (start_line + 1) + "," + (start_pos + 1) + "]" +
                            " Unknown token: " + text);
                    }
                    else
                    {
                        EOF token = new EOF(
                            start_line + 1,
                            start_pos + 1);
                        return token;
                    }
                }
            }
        }
    }

    Token new0(String text, int line, int pos) { return new TTab(text, line, pos); }
    Token new1(String text, int line, int pos) { return new TNumber(text, line, pos); }
    Token new2(String text, int line, int pos) { return new TString(text, line, pos); }
    Token new3(String text, int line, int pos) { return new TComment(text, line, pos); }
    Token new4(String text, int line, int pos) { return new TBlank(text, line, pos); }
    Token new5(int line, int pos) { return new TDef(line, pos); }
    Token new6(int line, int pos) { return new TPlus(line, pos); }
    Token new7(int line, int pos) { return new TMinus(line, pos); }
    Token new8(int line, int pos) { return new TDmult(line, pos); }
    Token new9(int line, int pos) { return new TMult(line, pos); }
    Token new10(int line, int pos) { return new TDiv(line, pos); }
    Token new11(int line, int pos) { return new TMod(line, pos); }
    Token new12(int line, int pos) { return new TLogicPlus(line, pos); }
    Token new13(int line, int pos) { return new TLPar(line, pos); }
    Token new14(int line, int pos) { return new TRPar(line, pos); }
    Token new15(int line, int pos) { return new TLBr(line, pos); }
    Token new16(int line, int pos) { return new TRBr(line, pos); }
    Token new17(int line, int pos) { return new TComma(line, pos); }
    Token new18(int line, int pos) { return new TSemi(line, pos); }
    Token new19(int line, int pos) { return new TEqual(line, pos); }
    Token new20(int line, int pos) { return new TNotEqual(line, pos); }
    Token new21(int line, int pos) { return new TMinusEqual(line, pos); }
    Token new22(int line, int pos) { return new TDivEqual(line, pos); }
    Token new23(int line, int pos) { return new TLess(line, pos); }
    Token new24(int line, int pos) { return new TGreater(line, pos); }
    Token new25(int line, int pos) { return new TLessEqual(line, pos); }
    Token new26(int line, int pos) { return new TGreaterEqual(line, pos); }
    Token new27(int line, int pos) { return new TTrue(line, pos); }
    Token new28(int line, int pos) { return new TFalse(line, pos); }
    Token new29(int line, int pos) { return new TNot(line, pos); }
    Token new30(int line, int pos) { return new TAnd(line, pos); }
    Token new31(int line, int pos) { return new TOr(line, pos); }
    Token new32(int line, int pos) { return new TAssign(line, pos); }
    Token new33(int line, int pos) { return new TOpen(line, pos); }
    Token new34(int line, int pos) { return new TType(line, pos); }
    Token new35(int line, int pos) { return new TMax(line, pos); }
    Token new36(int line, int pos) { return new TMin(line, pos); }
    Token new37(int line, int pos) { return new TIf(line, pos); }
    Token new38(int line, int pos) { return new TWhile(line, pos); }
    Token new39(int line, int pos) { return new TFor(line, pos); }
    Token new40(int line, int pos) { return new TPrint(line, pos); }
    Token new41(int line, int pos) { return new TReturn(line, pos); }
    Token new42(int line, int pos) { return new TDot(line, pos); }
    Token new43(int line, int pos) { return new TAssert(line, pos); }
    Token new44(int line, int pos) { return new TIn(line, pos); }
    Token new45(int line, int pos) { return new TNone(line, pos); }
    Token new46(String text, int line, int pos) { return new TIdentifier(text, line, pos); }

    private int getChar() throws IOException
    {
        if(eof)
        {
            return -1;
        }

        int result = in.read();

        if(result == -1)
        {
            eof = true;
        }

        return result;
    }

    private void pushBack(int acceptLength) throws IOException
    {
        int length = text.length();
        for(int i = length - 1; i >= acceptLength; i--)
        {
            eof = false;

            in.unread(text.charAt(i));
        }
    }

    protected void unread(Token token) throws IOException
    {
        String text = token.getText();
        int length = text.length();

        for(int i = length - 1; i >= 0; i--)
        {
            eof = false;

            in.unread(text.charAt(i));
        }

        pos = token.getPos() - 1;
        line = token.getLine() - 1;
    }

    private String getText(int acceptLength)
    {
        StringBuffer s = new StringBuffer(acceptLength);
        for(int i = 0; i < acceptLength; i++)
        {
            s.append(text.charAt(i));
        }

        return s.toString();
    }

    private static int[][][][] gotoTable;
/*  {
        { // INITIAL
            {{9, 9, 1}, {10, 10, 2}, {13, 13, 3}, {32, 32, 4}, {33, 33, 5}, {34, 34, 6}, {35, 35, 7}, {37, 37, 8}, {38, 38, 9}, {39, 39, 10}, {40, 40, 11}, {41, 41, 12}, {42, 42, 13}, {43, 43, 14}, {44, 44, 15}, {45, 45, 16}, {46, 46, 17}, {47, 47, 18}, {48, 57, 19}, {58, 58, 20}, {60, 60, 21}, {61, 61, 22}, {62, 62, 23}, {65, 77, 24}, {78, 78, 25}, {79, 90, 24}, {91, 91, 26}, {93, 93, 27}, {95, 95, 28}, {97, 97, 29}, {98, 99, 30}, {100, 100, 31}, {101, 101, 30}, {102, 102, 32}, {103, 104, 30}, {105, 105, 33}, {106, 108, 30}, {109, 109, 34}, {110, 110, 35}, {111, 111, 36}, {112, 112, 37}, {113, 113, 30}, {114, 114, 38}, {115, 115, 30}, {116, 116, 39}, {117, 118, 30}, {119, 119, 40}, {120, 122, 30}, },
            {},
            {},
            {},
            {},
            {{61, 61, 41}, },
            {{0, 9, 42}, {11, 12, 42}, {14, 33, 42}, {34, 34, 43}, {35, 127, 42}, },
            {{0, 9, 44}, {10, 10, 45}, {11, 12, 44}, {13, 13, 46}, {14, 127, 44}, },
            {},
            {{38, 38, 47}, },
            {{0, 9, 48}, {11, 12, 48}, {14, 38, 48}, {39, 39, 49}, {40, 127, 48}, },
            {},
            {},
            {{42, 42, 50}, },
            {},
            {},
            {{61, 61, 51}, },
            {},
            {{61, 61, 52}, },
            {{46, 46, 53}, {48, 57, 19}, },
            {},
            {{61, 61, 54}, },
            {{61, 61, 55}, },
            {{61, 61, 56}, },
            {{48, 57, 57}, {65, 90, 58}, {95, 95, 59}, {97, 122, 60}, },
            {{48, 95, -26}, {97, 110, 60}, {111, 111, 61}, {112, 122, 60}, },
            {},
            {},
            {{48, 122, -26}, },
            {{48, 95, -26}, {97, 109, 60}, {110, 110, 62}, {111, 114, 60}, {115, 115, 63}, {116, 122, 60}, },
            {{48, 122, -26}, },
            {{48, 95, -26}, {97, 100, 60}, {101, 101, 64}, {102, 122, 60}, },
            {{48, 95, -26}, {97, 97, 65}, {98, 110, 60}, {111, 111, 66}, {112, 122, 60}, },
            {{48, 95, -26}, {97, 101, 60}, {102, 102, 67}, {103, 109, 60}, {110, 110, 68}, {111, 122, 60}, },
            {{48, 95, -26}, {97, 97, 69}, {98, 104, 60}, {105, 105, 70}, {106, 122, 60}, },
            {{48, 110, -27}, {111, 111, 71}, {112, 122, 60}, },
            {{48, 95, -26}, {97, 111, 60}, {112, 112, 72}, {113, 113, 60}, {114, 114, 73}, {115, 122, 60}, },
            {{48, 95, -26}, {97, 113, 60}, {114, 114, 74}, {115, 122, 60}, },
            {{48, 100, -33}, {101, 101, 75}, {102, 122, 60}, },
            {{48, 113, -39}, {114, 114, 76}, {115, 120, 60}, {121, 121, 77}, {122, 122, 60}, },
            {{48, 95, -26}, {97, 103, 60}, {104, 104, 78}, {105, 122, 60}, },
            {},
            {{0, 127, -8}, },
            {{0, 127, -8}, },
            {{0, 127, -9}, },
            {},
            {{10, 10, 79}, },
            {},
            {{0, 127, -12}, },
            {{0, 127, -12}, },
            {},
            {},
            {},
            {{48, 57, 80}, },
            {},
            {},
            {},
            {{48, 122, -26}, },
            {{48, 122, -26}, },
            {{48, 122, -26}, },
            {{48, 122, -26}, },
            {{48, 109, -31}, {110, 110, 81}, {111, 122, 60}, },
            {{48, 95, -26}, {97, 99, 60}, {100, 100, 82}, {101, 122, 60}, },
            {{48, 95, -26}, {97, 114, 60}, {115, 115, 83}, {116, 122, 60}, },
            {{48, 101, -35}, {102, 102, 84}, {103, 122, 60}, },
            {{48, 95, -26}, {97, 107, 60}, {108, 108, 85}, {109, 122, 60}, },
            {{48, 113, -39}, {114, 114, 86}, {115, 122, 60}, },
            {{48, 122, -26}, },
            {{48, 122, -26}, },
            {{48, 95, -26}, {97, 119, 60}, {120, 120, 87}, {121, 122, 60}, },
            {{48, 109, -31}, {110, 110, 88}, {111, 122, 60}, },
            {{48, 95, -26}, {97, 115, 60}, {116, 116, 89}, {117, 122, 60}, },
            {{48, 100, -33}, {101, 101, 90}, {102, 122, 60}, },
            {{48, 122, -26}, },
            {{48, 95, -26}, {97, 104, 60}, {105, 105, 91}, {106, 122, 60}, },
            {{48, 115, -73}, {116, 116, 92}, {117, 122, 60}, },
            {{48, 95, -26}, {97, 116, 60}, {117, 117, 93}, {118, 122, 60}, },
            {{48, 111, -38}, {112, 112, 94}, {113, 122, 60}, },
            {{48, 104, -76}, {105, 105, 95}, {106, 122, 60}, },
            {},
            {{48, 57, 80}, },
            {{48, 100, -33}, {101, 101, 96}, {102, 122, 60}, },
            {{48, 122, -26}, },
            {{48, 100, -33}, {101, 101, 97}, {102, 122, 60}, },
            {{48, 122, -26}, },
            {{48, 114, -65}, {115, 115, 98}, {116, 122, 60}, },
            {{48, 122, -26}, },
            {{48, 122, -26}, },
            {{48, 122, -26}, },
            {{48, 122, -26}, },
            {{48, 109, -31}, {110, 110, 99}, {111, 122, 60}, },
            {{48, 109, -31}, {110, 110, 100}, {111, 122, 60}, },
            {{48, 116, -78}, {117, 117, 101}, {118, 122, 60}, },
            {{48, 100, -33}, {101, 101, 102}, {102, 122, 60}, },
            {{48, 100, -33}, {101, 101, 103}, {102, 122, 60}, },
            {{48, 107, -67}, {108, 108, 104}, {109, 122, 60}, },
            {{48, 122, -26}, },
            {{48, 113, -39}, {114, 114, 105}, {115, 122, 60}, },
            {{48, 100, -33}, {101, 101, 106}, {102, 122, 60}, },
            {{48, 122, -26}, },
            {{48, 115, -73}, {116, 116, 107}, {117, 122, 60}, },
            {{48, 113, -39}, {114, 114, 108}, {115, 122, 60}, },
            {{48, 122, -26}, },
            {{48, 122, -26}, },
            {{48, 100, -33}, {101, 101, 109}, {102, 122, 60}, },
            {{48, 115, -73}, {116, 116, 110}, {117, 122, 60}, },
            {{48, 122, -26}, },
            {{48, 122, -26}, },
            {{48, 109, -31}, {110, 110, 111}, {111, 122, 60}, },
            {{48, 122, -26}, },
            {{48, 122, -26}, },
            {{48, 122, -26}, },
        }
    };*/

    private static int[][] accept;
/*  {
        // INITIAL
        {-1, 0, 4, 4, 4, -1, -1, -1, 11, -1, -1, 13, 14, 9, 6, 17, 7, 42, 10, 1, 18, 23, 32, 24, 46, 46, 15, 16, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 20, -1, 2, -1, 3, 3, 12, -1, 2, 8, 21, 22, -1, 25, 19, 26, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 37, 44, 46, 46, 46, 46, 31, 46, 46, 46, 46, 46, 3, 1, 46, 30, 46, 5, 46, 39, 35, 36, 29, 46, 46, 46, 46, 46, 46, 45, 46, 46, 33, 46, 46, 27, 34, 46, 46, 28, 40, 46, 38, 43, 41, },

    };*/

    public static class State
    {
        public final static State INITIAL = new State(0);

        private int id;

        private State(int id)
        {
            this.id = id;
        }

        public int id()
        {
            return id;
        }
    }
}
