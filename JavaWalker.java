// JavaWalker.java

//package grammars;

import grammars.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class JavaWalker
{
	public static boolean walker( String input_file ) throws Exception
	{
		// original code
		JavaLexer lexer = new JavaLexer( new ANTLRFileStream( input_file ) );
		CommonTokenStream tokens = new CommonTokenStream( lexer );
		JavaParser parser = new JavaParser( tokens );

		parser.setBuildParseTree( true );
		//RuleContext tree = parser.prog();

		ParseTree tree = parser.compilationUnit();

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