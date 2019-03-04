// CPP14Walker.java

import grammars.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class CPP14Walker
{
	public static boolean walker( String input_file ) throws Exception
	{
		// original code
		CPP14Lexer lexer = new CPP14Lexer( new ANTLRFileStream( input_file ) );
		CommonTokenStream tokens = new CommonTokenStream( lexer );
		CPP14Parser parser = new CPP14Parser( tokens );

		parser.setBuildParseTree( true );
		//RuleContext tree = parser.prog();

		ParseTree tree = parser.translationunit();

		//tree.inspect(parser); // show in gui
		//tree.save(parser, "/tmp/R.ps"); // Generate postscript
		System.out.println( tree.toStringTree( parser ) );

		/*
        ParseTree tree = parser.r();
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk( new HelloWalker(), tree );
        */
		return true;
	}
}