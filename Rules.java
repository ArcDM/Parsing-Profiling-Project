// Rules.java

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Rule
{
	private int limit, count;

	public Rule( int input_limit )
	{
		limit = input_limit;
		count = 0;
	}

	public Rule( Rule input_rule )
	{
		limit = input_rule.limit;
		count = 0;
	}

	public void increment()
	{
		count++;
	}

	public void increment( int amount )
	{
		count += amount;
	}

	public boolean check()
	{
		return limit >= ( ( limit >= 0 )? count : -count );
	}

	public void reset_count()
	{
		count = 0;
	}

	// debugging function
	public int get_count()
	{
		return count;
	}
}

class Rules // super class
{
	private HashMap<String, Rule> rules_list;

	public Rules( File input_file )
	{
		Scanner scanner;
		String line, str_rule, str_modifier;
		int index;

		try
		{
			scanner = new Scanner( input_file );

			rules_list = new HashMap();

			while( scanner.hasNextLine() )
			{
				line = scanner.nextLine().trim();
				index = line.lastIndexOf( ' ' );

				if( index > 0 )
				{
					str_rule = line.substring( 0, index );
					str_modifier = line.substring( index + 1 );

					try
					{
						final Rule new_rule = new Rule( Integer.valueOf( str_modifier ) );
						String[] split_rules = str_rule.split( "#" );

						for( String rule_str : split_rules )
						{
							rule_str = customize_rule( rule_str );

							if( rule_str != null )
							{
								rules_list.put( rule_str, new_rule );
							}
						}
					}
					catch( Exception e )
					{
					} // silently ignore lines that arnt parsed correctly
				}
			}
		}
		catch( FileNotFoundException fnfe )
		{
			System.err.println( "Given Rules File Not Found" );
			System.exit( -4 ); // returns 252
		}

		rules_list.keySet().stream().forEach( key -> customize_rule( key ) );
	}

	public Rules( Rules InputRules )
	{
		rules_list = new HashMap();

		for( Map.Entry<String, Rule> entry : InputRules.rules_list.entrySet() )
		{
			String rule_str = customize_rule( entry.getKey() );

			if( rule_str != null )
			{
				rules_list.put( rule_str, new Rule( entry.getValue() ) );
			}
		}
	}

	/** This function is ment to be overrided
	 *		to allow any changes for a rule if needed.
	 */
	String customize_rule( String rule_name )
	{
		return rule_name;
	}

	public void reset_rules()
	{
		rules_list.keySet().stream().forEach( rule_name ->
			rules_list.get( rule_name ).reset_count() );
	}

	public void increment_rule( String rule_name, int amount )
	{
		Rule referenced_rule = rules_list.get( rule_name );

		if( referenced_rule != null )
		{
			referenced_rule.increment( amount );
		}

		// chaining rules
		switch( rule_name )
		{
			case "-print-w/o-variable-":
				increment_chain_rule( referenced_rule,"-output-", amount );
				break;
			case "if":
			case "switch":
				increment_chain_rule( referenced_rule,"-selection-", amount );
				break;
			case "for":
			case "while":
				increment_chain_rule( referenced_rule,"-iteration-", amount );
				break;
			case "goto":
			case "break":
			case "continue":
				increment_chain_rule( referenced_rule,"-jump-", amount );
				break;
		}
	}

	public void increment_rule( String rule_name )
	{
		increment_rule( rule_name, 1 );
	}

	private void increment_chain_rule( Rule referenced_rule, String chain_rule, int amount )
	{
		Rule output_rule = rules_list.get( chain_rule );

		if( output_rule != null && output_rule != referenced_rule )
		{
			output_rule.increment( amount );
		}
	}

	// debugging function
	public void print_rules()
	{
		rules_list.keySet().stream().forEach( rule_name ->
			System.out.printf( "Rule: %s, Count: %d, Valid: %s \n",
				rule_name, rules_list.get( rule_name ).get_count(),
				( rules_list.get( rule_name ).check() )? "True" : "False" ) );
	}

	public boolean validate()
	{
		boolean valid = true;

		for( Rule referenced_rule : rules_list.values() )
		{
			valid &= referenced_rule.check();
		}

		return valid;
	}
}