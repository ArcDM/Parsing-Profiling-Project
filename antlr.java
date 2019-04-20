// antlr.java

import java.io.File;
import java.io.IOException;
import org.antlr.v4.runtime.*;

public class antlr
{
	final static int STATUS_PASS = 0,
					STATUS_FAIL = 1;
	static Rules import_rules = null;

	private static String getExtension( String inputString )
	{
		int lastIndexOf = inputString.lastIndexOf( "." );

		return ( lastIndexOf == -1 )? "" : inputString.substring( lastIndexOf + 1 );
	}

	public static void main( String[] args ) throws Exception
	{
	    int argument_index = 0;
	    boolean return_boolean = true;

		switch( args.length )
        {
            case 0:
			case 1:
                System.out.println( "Usage is \"java antlr [rules_file] [parsing_files]\"" );
				System.exit( -1 ); // returns 255

            default:
				import_rules = new Rules( new File( args[ argument_index ] ) );

                for( ++argument_index ; argument_index < args.length; ++argument_index )
                {
					ANTLRFileStream ANTLRfile = null;

                	try
					{
						ANTLRfile = new ANTLRFileStream( args[ argument_index ] );
					}
                	catch( IOException e )
					{
						System.err.println( e );
						System.err.println( "Exiting program" );
						System.exit( -2 ); // returns 254
					}

					switch( getExtension( args[ argument_index ] ) )
					{
						case "cpp":
							return_boolean = CPP14Walker.walker( ANTLRfile );
							break;

						case "go":
							return_boolean = GolangWalker.walker( ANTLRfile );
							break;

						case "java":
							return_boolean = JavaWalker.walker( ANTLRfile );
							break;

						case "py":
							/*	python is currently inoperable
							if( PY_rules == null )
							{
								PY_rules = new PYRules( import_rules );
							}
							else
							{
								PY_rules.reset_rules();
							}

							return_boolean = Python3Walker.walker( ANTLRfile );
							*/
							return_boolean = true;
							break;

						default:
							System.err.println( "Unknown file extention" );
							System.exit( -3 ); // returns 253
					}

					if( !return_boolean )
					{
						System.err.printf( "File \"%s\" failed validation\n", args[ argument_index ] );
						System.exit( STATUS_FAIL );
					}
                }
        }

		System.err.println( "All file validated" );
		System.exit( STATUS_PASS );
	}

	public static < Rule_Type extends Rules > Rule_Type initialize_rules( Rule_Type ruleSet, Class<Rule_Type> baseClass )
	{
		if( ruleSet == null )
		{
			try
			{ // the work around to generic_type constructor with arguments
				ruleSet = ( Rule_Type ) baseClass.getConstructor( Rules.class ).newInstance( import_rules );
			}
			catch( Exception e)
			{
				System.err.println( e );
				System.exit( -5 ); // returns 251
			}
		}
		else
		{
			ruleSet.reset_rules();
		}

		return ruleSet;
	}
}