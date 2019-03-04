// antlr.java

//import JavaWalker;

/*
import JavaLexer.*;
import JavaParser.*;
import JavaParserBaseListener.*;
import JavaParserListener.*;*/

public class antlr
{
	private static String getExtension( String inputString )
	{
		int lastIndexOf = inputString.lastIndexOf(".");

		return ( lastIndexOf == -1 )? "" : inputString.substring( lastIndexOf + 1 );
	}

	public static void main( String[] args) throws Exception
	{
	    int argument_index = 0;

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
							CPP14Walker.walker( args[ argument_index ] );
							break;
						case "go":
							GolangWalker.walker( args[ argument_index ] );
							break;
						case "java":
							JavaWalker.walker( args[ argument_index ] );
							break;
						case "py":
							Python3Walker.walker( args[ argument_index ] );
							break;
						default:
							System.out.println( "Unknown file extention" );
							System.exit( -2 ); // returns -2
					}
                }
        }

		System.exit( 0 ); // returns 0
	}
}