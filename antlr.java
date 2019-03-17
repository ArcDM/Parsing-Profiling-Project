// antlr.java

//import JavaWalker;

/*
import JavaLexer.*;
import JavaParser.*;
import JavaParserBaseListener.*;
import JavaParserListener.*;*/

public class antlr
{
	final static int STATUS_PASS = 1,
					STATUS_FAIL = 0;

	private static String getExtension( String inputString )
	{
		int lastIndexOf = inputString.lastIndexOf(".");

		return ( lastIndexOf == -1 )? "" : inputString.substring( lastIndexOf + 1 );
	}

	public static void main( String[] args) throws Exception
	{
	    int argument_index = 0;
	    boolean return_boolean = true;

		switch( args.length )
        {
            case 0:
                System.out.println( "Usage is \"java antlr [parsing_file]\"" );
                //System.out.println( "\tor\"java antlr [rules_file] [parsing_files]\"" );
				System.exit( -1 ); // returns -1
            default:
				System.out.println( "Development: rules parsing not implemented." );
                // load rules from String[ 0 ]
                // ++argument_index;
            case 1:
                for( ; argument_index < args.length; ++argument_index )
                {
					switch( getExtension( args[ argument_index ] ) )
					{
						case "cpp":
							return_boolean = CPP14Walker.walker( args[ argument_index ] );
							break;
						case "go":
							return_boolean = GolangWalker.walker( args[ argument_index ] );
							break;
						case "java":
							return_boolean = JavaWalker.walker( args[ argument_index ] );
							break;
						case "py":
							return_boolean = Python3Walker.walker( args[ argument_index ] );
							break;
						default:
							System.out.println( "Unknown file extention" );
							System.exit( -2 ); // returns -2
					}

					if( !return_boolean )
					{
						System.exit( STATUS_FAIL );
					}
                }
        }

		System.exit( STATUS_PASS );
	}
}