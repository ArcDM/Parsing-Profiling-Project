// CPP14Walker.java

import grammars.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

class Global
{
	public static int valid = 0, invalid = 0;
}

public class GolangWalker
{
	public static boolean walker( String input_file ) throws Exception
	{
		// original code
		GolangLexer lexer = new GolangLexer( new ANTLRFileStream( input_file ) );
		CommonTokenStream tokens = new CommonTokenStream( lexer );
		GolangParser parser = new GolangParser( tokens );

		GolangParser.SourceFileContext file_context = parser.sourceFile();
		ParseTreeWalker walker = new ParseTreeWalker();
		GolangListener listener = new GolangListener();

		walker.walk( listener, file_context );

		System.out.printf( "Valid count = %d\nInvalid count = %d\n", Global.valid, Global.invalid );
		return true;
	}
}

class GolangListener extends GolangBaseListener
{
	private short 	PrintVariables = 0,
					FileVariables = 0;

	private boolean fmt_flag = false,
					OS_flag = false,
					Stdout_flag = false,
					Stderr_flag = false,
					std_print_flag = false,
					err_print_flag = false,
					file_print_flag = false;

	@Override
	public void enterOperandName( GolangParser.OperandNameContext context )
	{
		switch( context.getText() )
		{
			case "fmt":
				fmt_flag = true;
				break;
			case "os":
				OS_flag = true;
				break;

			default: // assumed to be variable
				PrintVariables += ( std_print_flag || err_print_flag )? 1 : 0;
				FileVariables += ( file_print_flag )? 1 : 0;
				break;
		}
	}

	@Override
	public void exitOperandName( GolangParser.OperandNameContext context )
	{
	}

	@Override
	public void enterSelector( GolangParser.SelectorContext context )
	{
		if( context.getChildCount() > 1 )
		{
			switch( context.getChild( 1 ).getText() )
			{
				case "Print":
				case "Println":
				case "Printf":
					std_print_flag = fmt_flag;
					fmt_flag = false;
					break;
				case "Fprint":
				case "Fprintln":
				case "Fprintf":
					file_print_flag = fmt_flag;
					fmt_flag = false;
					break;
				case "Stdout":
					Stdout_flag = ( OS_flag && !file_print_flag );
					std_print_flag = ( OS_flag && file_print_flag );

					PrintVariables += ( OS_flag && file_print_flag )? FileVariables : 0;
					FileVariables = ( OS_flag && file_print_flag )? 0 : FileVariables;

					file_print_flag = ( OS_flag )? false : file_print_flag;
					OS_flag = false;
					break;
				case "Stderr":
					Stderr_flag = ( OS_flag && !file_print_flag );
					err_print_flag = ( OS_flag && file_print_flag );

					PrintVariables += ( OS_flag && file_print_flag )? FileVariables : 0;
					FileVariables = ( OS_flag && file_print_flag )? 0 : FileVariables;

					file_print_flag = ( OS_flag )? false : file_print_flag;
					OS_flag = false;
					break;
				case "WriteString":
					std_print_flag = Stdout_flag;
					err_print_flag = Stderr_flag;
					Stdout_flag = false;
					Stderr_flag = false;
					break;

				default: // assumed to be variable
					fmt_flag = false;
					OS_flag = false;
					Stdout_flag = false;
					Stderr_flag = false;

					PrintVariables += ( std_print_flag || err_print_flag )? 1 : 0;
					FileVariables += ( file_print_flag )? 1 : 0;
					break;
			}
		}
	}

	@Override
	public void exitSelector( GolangParser.SelectorContext context )
	{
	}

	@Override
	public void enterArguments( GolangParser.ArgumentsContext context )
	{
	}

	@Override
	public void exitArguments( GolangParser.ArgumentsContext context )
	{
		if( std_print_flag || err_print_flag )
		{
			if( PrintVariables != 0 )
			{ // resolve
				PrintVariables = 0;
				++Global.valid;
				System.out.printf( "Valid line profiled: \"%s\"\n", context.getParent().getText() );
			}
			else
			{
				++Global.invalid;
				System.out.printf( "Invalid line profiled: \"%s\"\n", context.getParent().getText() );
			}

			std_print_flag = false;
			err_print_flag = false;
		}
	}
}