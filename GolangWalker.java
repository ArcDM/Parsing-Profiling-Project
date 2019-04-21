// CPP14Walker.java

import java.io.File;
import grammars.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class GolangWalker
{
	static GORules GO_rules = null;

	public static boolean walker( ANTLRFileStream input_file ) throws Exception
	{
		GO_rules = antlr.initialize_rules( GO_rules, GORules.class );

		GolangLexer lexer = new GolangLexer( input_file );
		CommonTokenStream tokens = new CommonTokenStream( lexer );
		GolangParser parser = new GolangParser( tokens );

		GolangParser.SourceFileContext file_context = parser.sourceFile();
		ParseTreeWalker walker = new ParseTreeWalker();
		GolangListener listener = new GolangListener();

		walker.walk( listener, file_context );

		GO_rules.print_rules();

		return GO_rules.validate();
	}
}

class GolangListener extends GolangBaseListener
{
	private int 	PrintVariables = 0,
					FileVariables = 0,
					arguments_list_level = 0,
					print_level, file_print_level;

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
				PrintVariables += ( ( std_print_flag || err_print_flag ) && arguments_list_level == print_level )? 1 : 0;
				FileVariables += ( file_print_flag && arguments_list_level == print_level )? 1 : 0;
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
					print_level = ( fmt_flag )? arguments_list_level + 1 : 0;
					fmt_flag = false;
					break;
				case "Fprint":
				case "Fprintln":
				case "Fprintf":
					file_print_flag = fmt_flag;
					file_print_level = ( fmt_flag )? arguments_list_level + 1 : 0;
					fmt_flag = false;
					break;
				case "Stdout":
					Stdout_flag = ( OS_flag && !file_print_flag );
					std_print_flag = ( OS_flag && arguments_list_level == print_level && file_print_flag );

					PrintVariables += ( std_print_flag )? FileVariables : 0;
					print_level		= ( std_print_flag )? file_print_level : 0;
					FileVariables	= ( std_print_flag )? 0 : FileVariables;

					file_print_flag = ( std_print_flag )? false : file_print_flag;
					OS_flag = false;
					break;
				case "Stderr":
					Stderr_flag = ( OS_flag && !file_print_flag );
					err_print_flag = ( OS_flag && arguments_list_level == print_level && file_print_flag );

					PrintVariables += ( err_print_flag )? FileVariables : 0;
					print_level		= ( err_print_flag )? file_print_level : 0;
					FileVariables	= ( err_print_flag )? 0 : FileVariables;

					file_print_flag = ( err_print_flag )? false : file_print_flag;
					OS_flag = false;
					break;
				case "WriteString":
					std_print_flag = Stdout_flag;
					err_print_flag = Stderr_flag;
					print_level = ( Stdout_flag || Stderr_flag )? arguments_list_level + 1 : 0;
					Stdout_flag = false;
					Stderr_flag = false;
					break;

				default: // assumed to be variable
					fmt_flag = false;
					OS_flag = false;
					Stdout_flag = false;
					Stderr_flag = false;

					PrintVariables += ( ( std_print_flag || err_print_flag ) && arguments_list_level == print_level )? 1 : 0;
					FileVariables += ( file_print_flag && arguments_list_level == print_level )? 1 : 0;
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
		arguments_list_level += 1;
	}

	@Override
	public void exitArguments( GolangParser.ArgumentsContext context )
	{
		if( print_level == arguments_list_level-- && ( std_print_flag || err_print_flag ) )
		{

			if( PrintVariables == 0 )
			{
				GolangWalker.GO_rules.increment_rule( "-print-w/o-variable-" );
			}
			else
			{
				GolangWalker.GO_rules.increment_rule( "output" );
			}

			PrintVariables = 0;
			std_print_flag = false;
			err_print_flag = false;
		}
	}

	// a simple way to test each parsed elemental node
	@Override
	public void visitTerminal( TerminalNode node )
	{
		GolangWalker.GO_rules.increment_rule( node.getText() );
	}
}

class GORules extends Rules
{
	public GORules( File InputRules )
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