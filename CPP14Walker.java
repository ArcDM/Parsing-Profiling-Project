// CPP14Walker.java

import java.io.File;
import grammars.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class CPP14Walker
{
	static CPPRules CPP_rules = null;

	public static boolean walker( ANTLRFileStream input_file ) throws Exception
	{
		CPP_rules = antlr.initialize_rules( CPP_rules, CPPRules.class );

		CPP14Lexer lexer = new CPP14Lexer( input_file );
		CommonTokenStream tokens = new CommonTokenStream( lexer );
		CPP14Parser parser = new CPP14Parser( tokens );

		CPP14Parser.TranslationunitContext file_context = parser.translationunit();
		ParseTreeWalker walker = new ParseTreeWalker();
		CPP14Listener listener = new CPP14Listener();

		walker.walk( listener, file_context );

		//CPP_rules.print_rules();

		return CPP_rules.validate();
	}
}

class CPP14Listener extends CPP14BaseListener
{
	private int 	ShiftBlockCount = 0,
					PrintVariables = 0,
					FileVariables = 0,
					expression_list_level = 0,
					print_level, file_print_level;

	private boolean COUT_flag = false,
					CERR_flag = false,
					std_print_flag = false,
					err_print_flag = false,
					file_print_flag = false,
					write_stream_flag = false;

	@Override
	public void enterUnqualifiedid( CPP14Parser.UnqualifiedidContext context )
	{	//	unqualifiedid seems to be the catch all for non literals and non symbols.
		switch( context.getText() )
		{
			case "cout":
			case "wcout":
				COUT_flag = ( ShiftBlockCount > 0 ); // this should always give true
				print_level = ( COUT_flag )? expression_list_level : 0;
				break;
			case "endl":
				break; // skip
			case "printf":
			case "puts":
			case "putchar":
				std_print_flag = true;
				print_level = expression_list_level + 1;
				break;
			case "fprintf":
			case "fputs":
			case "putc":
			case "fputc":
				file_print_flag = true;
				file_print_level = expression_list_level + 1;
				break;
			case "write":
				write_stream_flag = true;
				file_print_level = expression_list_level + 1;
				break;
			case "cerr":
			case "wcerr":
				CERR_flag = ( ShiftBlockCount > 0 ); // this should always give true
				print_level = ( CERR_flag )? expression_list_level : 0;
				break;
			case "perror":
				err_print_flag = true;
				print_level = expression_list_level + 1;
				break;
			case "STDOUT_FILENO":
				std_print_flag = ( write_stream_flag && expression_list_level == file_print_level );

				PrintVariables += ( std_print_flag )? FileVariables : 0;
				FileVariables	= ( std_print_flag )? 0 : FileVariables;

				print_level		= ( std_print_flag )? file_print_level : 0;
				FileVariables	= ( std_print_flag )? 0 : FileVariables;

				write_stream_flag = ( std_print_flag )? false : write_stream_flag;
				break;
			case "STDERR_FILENO":
				err_print_flag = ( write_stream_flag && expression_list_level == file_print_level );

				PrintVariables += ( err_print_flag )? FileVariables : 0;
				FileVariables	= ( err_print_flag )? 0 : FileVariables;

				print_level		= ( err_print_flag )? file_print_level : 0;
				FileVariables	= ( err_print_flag )? 0 : FileVariables;

				write_stream_flag = ( err_print_flag )? false : write_stream_flag;
				break;
			case "stdout":
				std_print_flag = ( file_print_flag && expression_list_level == file_print_level );

				PrintVariables += ( std_print_flag )? FileVariables : 0;
				FileVariables	= ( std_print_flag )? 0 : FileVariables;

				print_level		= ( std_print_flag )? file_print_level : 0;
				FileVariables	= ( std_print_flag )? 0 : FileVariables;

				file_print_flag = ( std_print_flag )? false : file_print_flag;
				break;
			case "stderr":
				err_print_flag = ( file_print_flag && expression_list_level == file_print_level );

				PrintVariables += ( err_print_flag )? FileVariables : 0;
				FileVariables	= ( err_print_flag )? 0 : FileVariables;

				print_level		= ( err_print_flag )? file_print_level : 0;
				FileVariables	= ( err_print_flag )? 0 : FileVariables;

				file_print_flag = ( err_print_flag )? false : file_print_flag;
				break;
			default: // assumed to be variable
				PrintVariables += ( COUT_flag || CERR_flag ||
					( ( std_print_flag | err_print_flag ) && expression_list_level == print_level ) )? 1 : 0;
				FileVariables += ( ( write_stream_flag || file_print_flag ) && expression_list_level == print_level )? 1 : 0;
				break;
		}
	}

	@Override
	public void exitUnqualifiedid( CPP14Parser.UnqualifiedidContext context )
	{
	}

	@Override
	public void enterExpressionlist( CPP14Parser.ExpressionlistContext context )
	{
		++expression_list_level;
	}

	@Override
	public void exitExpressionlist( CPP14Parser.ExpressionlistContext context )
	{
		if( print_level == expression_list_level-- && ( std_print_flag || err_print_flag ) )
		{
			if( PrintVariables == 0 )
			{
				CPP14Walker.CPP_rules.increment_rule( "-print-w/o-variable-" );
			}
			else
			{
				CPP14Walker.CPP_rules.increment_rule( "output" );
			}

			PrintVariables = 0;
			std_print_flag = false;
			err_print_flag = false;
		}
	}

	@Override
	public void enterShiftexpression( CPP14Parser.ShiftexpressionContext context )
	{
		++ShiftBlockCount;
	}

	@Override
	public void exitShiftexpression( CPP14Parser.ShiftexpressionContext context )
	{
		if( ( --ShiftBlockCount ) == 0 ) // decrement then check against zero
		{
			if( print_level == expression_list_level && (COUT_flag || CERR_flag ) )
			{
				if( PrintVariables == 0 )
				{
					CPP14Walker.CPP_rules.increment_rule( "-print-w/o-variable-" );
				}
				else
				{
					CPP14Walker.CPP_rules.increment_rule( "output" );
				}

				PrintVariables = 0;
				COUT_flag = false;
				CERR_flag = false;
			}
		}
	}

	// a simple way to test each parsed elemental node
	@Override
	public void visitTerminal( TerminalNode node )
	{
		CPP14Walker.CPP_rules.increment_rule( node.getText() );
	}
}

class CPPRules extends Rules
{
	public CPPRules( File InputRules )
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