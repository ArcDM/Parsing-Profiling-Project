// CPP14Walker.java

import grammars.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

class Global
{
	public static int valid = 0, invalid = 0;
}


public class CPP14Walker
{
	public static boolean walker( String input_file ) throws Exception
	{
		CPP14Lexer lexer = new CPP14Lexer( new ANTLRFileStream( input_file ) );
		CommonTokenStream tokens = new CommonTokenStream( lexer );
		CPP14Parser parser = new CPP14Parser( tokens );

		CPP14Parser.TranslationunitContext file_context = parser.translationunit();
		ParseTreeWalker walker = new ParseTreeWalker();
		CPP14Listener listener = new CPP14Listener();

		walker.walk( listener, file_context );

		System.out.printf( "Valid count = %d\nInvalid count = %d\n", Global.valid, Global.invalid );

		return true;
	}
}

class CPP14Listener extends CPP14BaseListener
{
	private short 	ShiftBlockCount = 0,
					expression_list_count = 0,
					PrintVariables = 0,
					FileVariables = 0;

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
				break;
			case "endl":
				break; // skip
			case "printf":
			case "puts":
			case "putchar":
				std_print_flag = true;
				break;
			case "fprintf":
			case "fputs":
			case "putc":
			case "fputc":
				file_print_flag = true;
				break;
			case "write":
				write_stream_flag = true;
				break;
			case "cerr":
			case "wcerr":
				CERR_flag = ( ShiftBlockCount > 0 ); // this should always give true
				break;
			case "perror":
				err_print_flag = true;
				break;
			case "STDOUT_FILENO":
				std_print_flag = ( write_stream_flag && expression_list_count > 0 );
				PrintVariables |= ( write_stream_flag && expression_list_count > 0 )? FileVariables : 0;
				FileVariables = ( write_stream_flag && expression_list_count > 0 )? 0 : FileVariables;
				write_stream_flag = ( expression_list_count > 0 )? false : write_stream_flag;
				break;
			case "STDERR_FILENO":
				err_print_flag = ( write_stream_flag && expression_list_count > 0 );
				PrintVariables += ( write_stream_flag && expression_list_count > 0 )? FileVariables : 0;
				FileVariables = ( write_stream_flag && expression_list_count > 0 )? 0 : FileVariables;
				write_stream_flag = ( expression_list_count > 0 )? false : write_stream_flag;
				break;
			case "stdout":
				std_print_flag = ( file_print_flag && expression_list_count > 0 );
				PrintVariables += ( file_print_flag && expression_list_count > 0 )? FileVariables : 0;
				FileVariables = ( file_print_flag && expression_list_count > 0 )? 0 : FileVariables;
				file_print_flag = ( expression_list_count > 0 )? false : file_print_flag;
				break;
			case "stderr":
				err_print_flag = ( file_print_flag && expression_list_count > 0 );
				PrintVariables += ( file_print_flag && expression_list_count > 0 )? FileVariables : 0;
				FileVariables = ( file_print_flag && expression_list_count > 0 )? 0 : FileVariables;
				file_print_flag = ( expression_list_count > 0 )? false : file_print_flag;
				break;
			default: // assumed to be variable
				PrintVariables += ( COUT_flag || CERR_flag ||
					( ( std_print_flag | err_print_flag ) && expression_list_count > 0 ) )? 1 : 0;
				FileVariables += ( ( write_stream_flag || file_print_flag ) && expression_list_count > 0 )? 1 : 0;
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
		++expression_list_count;
	}

	@Override
	public void exitExpressionlist( CPP14Parser.ExpressionlistContext context )
	{
		--expression_list_count;

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
	public void enterShiftexpression( CPP14Parser.ShiftexpressionContext context )
	{
		++ShiftBlockCount;
	}

	@Override
	public void exitShiftexpression( CPP14Parser.ShiftexpressionContext context )
	{
		if( ( --ShiftBlockCount ) == 0 ) // decrement then check against zero
		{
			if( COUT_flag || CERR_flag )
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

				COUT_flag = false;
				CERR_flag = false;
			}
		}
	}
}