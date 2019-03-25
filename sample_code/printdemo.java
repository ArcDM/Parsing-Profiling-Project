
/**	This program demonstrates all of the print statements I know of.
  *		It also includes error printing and only goes to standard output
  *	If you know of any other methods of printing, please add them.
  */

class clas
{
	public static String var = "pass class line.";
}

public class printdemo
{
	private static String func()
	{
		return "pass returned line.";
	}

	public static void main( String[] args )
	{
		String var = "variable";
		String[] str = { "pass line 1.\n",
						"pass line 2.",
						"pass line 3.\n",
						"pass line 4 %s.\n",
						"pass line 5.\n",
						"pass line 6.",
						"pass line 7.\n",
						"pass line 8 %s.\n" };

		System.out.println( func() );
		System.out.println( clas.var );
		System.out.println( 1234 );

		// standard output stream
    	System.out.print( str[ 0 ] );
    	System.out.print( "catch line 1.\n" );

    	System.out.println( str[ 1 ] );
    	System.out.println( "catch line 2." );

		System.out.printf( str[ 2 ] );
		System.out.printf( "catch line 3.\n" );

		System.out.printf( str[ 3 ], var );
		System.out.printf( "pass line 4 %s.\n", var );
		System.out.printf( "catch line 4 %s.\n", "none" );

		// standard error stream
    	System.err.print( str[ 4 ] );
    	System.err.print( "catch line 5.\n" );

    	System.err.println( str[ 5 ] );
    	System.err.println( "catch line 6." );

		System.err.printf( str[ 6 ] );
		System.err.printf( "catch line 7.\n" );

		System.err.printf( str[ 7 ], var );
		System.err.printf( "pass line 8 %s.\n", var );
		System.err.printf( "catch line 8 %s.\n", "none" );
	}
}
