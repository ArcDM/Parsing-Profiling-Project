package main

import "fmt"
import "os"

/**	This program demonstrates all of the print statements I know of.
  *		It also includes error printing and only goes to standard output
  *	If you know of any other methods of printing, please add them.
  */

func main() {
	Var := "variable";
	str1 := "pass line 1.\n";
	str2 := "pass line 2.";
	str3 := "pass line 3.\n";
	str4 := "pass line 4 %s.\n";
	str5 := "pass line 5.\n";
	str6 := "pass line 6.\n";
	str7 := "pass line 7.";
	str8 := "pass line 8.\n";
	str9 := "pass line 9 %s.\n";
	str10 := "pass line 10.\n";
	str11 := "pass line 11.\n";
	str12 := "pass line 12.";
	str13 := "pass line 13.\n";
	str14 := "pass line 14 %s.\n";

	// standard output stream
    fmt.Print( str1 );
    fmt.Print( "catch line 1.\n" );

    fmt.Println( str2 );
    fmt.Println( "catch line 2." );

	fmt.Printf( str3 );
	fmt.Printf( "catch line 3.\n" );

	fmt.Printf( str4, Var );
	fmt.Printf( "pass line 4 %s.\n", Var );
	fmt.Printf( "catch line 4 %s.\n", "none" );

	os.Stdout.WriteString( str5 );
	os.Stdout.WriteString( "catch line 5.\n" );

	fmt.Fprint( os.Stdout, str6 );
	fmt.Fprint( os.Stdout, "catch line 6.\n" );

	fmt.Fprintln( os.Stdout, str7 );
	fmt.Fprintln( os.Stdout, "catch line 7." );

	fmt.Fprintf( os.Stdout, str8 );
	fmt.Fprintf( os.Stdout, "catch line 8.\n" );

	fmt.Fprintf( os.Stdout, str9, Var );
	fmt.Fprintf( os.Stdout, "pass line 9 %s.\n", Var );
	fmt.Fprintf( os.Stdout, "catch line 9 %s.\n", "none" );

	// standard error stream
	os.Stderr.WriteString( str10 );
	os.Stderr.WriteString( "catch line 10.\n" );

	fmt.Fprint( os.Stderr, str11 );
	fmt.Fprint( os.Stderr, "catch line 11.\n" );

	fmt.Fprintln( os.Stderr, str12 );
	fmt.Fprintln( os.Stderr, "catch line 12." );

	fmt.Fprintf( os.Stderr, str13 );
	fmt.Fprintf( os.Stderr, "catch line 13.\n" );

	fmt.Fprintf( os.Stderr, str14, Var );
	fmt.Fprintf( os.Stderr, "pass line 14 %s.\n", Var );
	fmt.Fprintf( os.Stderr, "catch line 14 %s.\n", "none" );
}
