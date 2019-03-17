
/**	This program demonstrates all of the print statements I know of.
  *		It also includes error printing and only goes to standard output
  *	If you know of any other methods of printing, please add them.
  */

public class printdemo
{
	public static void main( String[] args )
	{
		String var = "variable",
				str1 = "pass line 1.\n",
				str2 = "pass line 2.",
				str3 = "pass line 3.\n",
				str4 = "pass line 4 %s.\n",
				str5 = "pass line 5.\n",
				str6 = "pass line 6.",
				str7 = "pass line 7.\n",
				str8 = "pass line 8 %s.\n";

		// standard output stream
    	System.out.print( str1 );
    	System.out.print( "catch line 1.\n" );

    	System.out.println( str2 );
    	System.out.println( "catch line 2." );

		System.out.printf( str3 );
		System.out.printf( "catch line 3.\n" );

		System.out.printf( str4, var );
		System.out.printf( "pass line 4 %s.\n", var );
		System.out.printf( "catch line 4 %s.\n", "none" );

		// standard error stream
    	System.err.print( str5 );
    	System.err.print( "catch line 5.\n" );

    	System.err.println( str6 );
    	System.err.println( "catch line 6." );

		System.err.printf( str7 );
		System.err.printf( "catch line 7.\n" );

		System.err.printf( str8, var );
		System.err.printf( "pass line 8 %s.\n", var );
		System.err.printf( "catch line 8 %s.\n", "none" );
	}
}
