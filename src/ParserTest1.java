import java.io.*;

import minipython.lexer.Lexer;
import minipython.parser.Parser;
import minipython.node.Start;

public class ParserTest1
{
    public static void main(String[] args)
    {
        try
        {
            Parser parser = new Parser(new Lexer(new PushbackReader(new FileReader(args[0]), 1024)));

            Start ast = parser.parse();

            HierarchicalSymbolTable hierarchicalSymbolTable = new HierarchicalSymbolTable();
            Positions positions = new Positions();

            ast.apply(positions);
            FirstAdapter fa = new FirstAdapter(positions, hierarchicalSymbolTable);
            ast.apply(fa);
            ast.apply(new SecondAdapter(fa.getErrors(), positions, hierarchicalSymbolTable));

            System.out.println(ast);
        }
        catch (Exception e)
        {
            System.err.println(e);
        }
    }
}

