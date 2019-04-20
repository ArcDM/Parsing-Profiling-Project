// JavaWalker.java

import grammars.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;


public class JavaWalker
{
	static JAVARules JAVA_rules = null;

	public static boolean walker( ANTLRFileStream input_file ) throws Exception
	{
		JAVA_rules = antlr.initialize_rules( JAVA_rules, JAVARules.class );

		JavaLexer lexer = new JavaLexer( input_file );
		CommonTokenStream tokens = new CommonTokenStream( lexer );
		JavaParser parser = new JavaParser( tokens );

		JavaParser.CompilationUnitContext compilation_context = parser.compilationUnit();
		ParseTreeWalker walker = new ParseTreeWalker();
		JavaListener listener = new JavaListener();

		walker.walk( listener, compilation_context );

		//JAVA_rules.print_rules();

		return JAVA_rules.validate();
	}
}


class JavaListener extends JavaParserBaseListener
{
	private int 	PrintVariables = 0,
					expression_list_level = 0,
					print_level;

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
				PrintVariables += ( ( std_print_flag || err_print_flag ) && expression_list_level == print_level )? 1 : 0;
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
		PrintVariables += ( ( std_print_flag || err_print_flag ) && expression_list_level == print_level )? 1 : 0; // if already in print method call

		switch( context.getChild( 0 ).getText() )
		{
			case "print":
			case "println":
			case "printf":
				std_print_flag = OUT_flag;
				err_print_flag = ERR_flag;
				print_level = ( OUT_flag || ERR_flag )? expression_list_level + 1 : 0;
				OUT_flag = false;
				ERR_flag = false;
				break;
		}
	}

	@Override
	public void exitMethodCall( JavaParser.MethodCallContext context )
	{
		if( ( std_print_flag || err_print_flag ) && expression_list_level == print_level - 1 )
		{
			JavaWalker.JAVA_rules.increment_rule( "output" );

			if( PrintVariables == 0 )
			{
				JavaWalker.JAVA_rules.increment_rule( "print_w/o_variable" );
			}

			PrintVariables = 0;
			std_print_flag = false;
			err_print_flag = false;
		}
	}

	@Override
	public void enterLiteral( JavaParser.LiteralContext context )
	{
		PrintVariables -= ( ( std_print_flag || err_print_flag ) && expression_list_level == print_level )? 1 : 0;
	}

	@Override
	public void exitLiteral( JavaParser.LiteralContext context )
	{
	}

	@Override
	public void enterExpressionList(JavaParser.ExpressionListContext context )
	{
		expression_list_level += 1;
	}

	@Override
	public void exitExpressionList(JavaParser.ExpressionListContext context )
	{
		expression_list_level -= 1;
	}
}

class JAVARules extends Rules
{
	public JAVARules( Rules InputRules )
	{
		super( InputRules );
	}

	@Override
	String customize_rule( String rule_name )
	{
		switch( rule_name )
		{
			//case "sample_case":
			//	break;
		}

		return rule_name;
	}
}