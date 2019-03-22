// JavaWalker.java

//package grammars;

import grammars.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

class Global
{
	public static int valid = 0, invalid = 0;
}

public class JavaWalker
{
	public static boolean walker( String input_file ) throws Exception
	{
		JavaLexer lexer = new JavaLexer( new ANTLRFileStream( input_file ) );
		CommonTokenStream tokens = new CommonTokenStream( lexer );
		JavaParser parser = new JavaParser( tokens );

		JavaParser.CompilationUnitContext compilation_context = parser.compilationUnit();
		ParseTreeWalker walker = new ParseTreeWalker();
		JavaListener listener = new JavaListener();

		walker.walk( listener, compilation_context );

		System.out.printf( "Valid count = %d\nInvalid count = %d\n", Global.valid, Global.invalid );

		return true;
	}
}


class JavaListener extends JavaParserBaseListener
{
	private short 	PrintVariables = 0;
	private boolean SYSTEM_flag = false,
					OUT_flag = false,
					ERR_flag = false,
					std_print_flag = false,
					err_print_flag = false;

	@Override
	public void enterPrimary( JavaParser.PrimaryContext context )
	{
		switch( context.getText() )
		{
			case "System":
				SYSTEM_flag = true;
				break;

			default: // assumed to be variable
				PrintVariables += ( std_print_flag || err_print_flag )? 1 : 0;
				break;
		}
	}

	@Override
	public void exitPrimary( JavaParser.PrimaryContext context )
	{
	}

	@Override
	public void enterExpression( JavaParser.ExpressionContext context )
	{
	}

	@Override
	public void exitExpression( JavaParser.ExpressionContext context )
	{
		OUT_flag = ( SYSTEM_flag && context.getChildCount() == 3 && context.getChild( 2 ).getText().equals( "out" ) );
		ERR_flag = ( SYSTEM_flag && context.getChildCount() == 3 && context.getChild( 2 ).getText().equals( "err" ) );
		SYSTEM_flag = ( SYSTEM_flag && context.getChildCount() > 1 )? false : SYSTEM_flag;
	}

	@Override
	public void enterMethodCall( JavaParser.MethodCallContext context )
	{
		switch( context.getChild( 0 ).getText() )
		{
			case "print":
			case "println":
			case "printf":
				std_print_flag = OUT_flag;
				err_print_flag = ERR_flag;
				OUT_flag = false;
				ERR_flag = false;
				break;
		}
	}

	@Override
	public void exitMethodCall( JavaParser.MethodCallContext context )
	{
		if( std_print_flag || err_print_flag )
		{
			if( PrintVariables != 0 )
			{ // resolve
				PrintVariables = 0;
				++Global.valid;
				System.out.printf( "Valid line profiled: \"%s\"\n", context.getText() );
			}
			else
			{
				++Global.invalid;
				System.out.printf( "Invalid line profiled: \"%s\"\n", context.getText() );
			}

			std_print_flag = false;
			err_print_flag = false;
		}
	}

	@Override
	public void enterLiteral( JavaParser.LiteralContext context )
	{
		PrintVariables -= ( std_print_flag || err_print_flag )? 1 : 0;
	}

	@Override
	public void exitLiteral( JavaParser.LiteralContext context )
	{
	}
}