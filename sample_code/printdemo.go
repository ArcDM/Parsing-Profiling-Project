package main

import "fmt"
import "os"

/**	This program demonstrates all of the print statements I know of.
  *		It also includes error printing and only goes to standard output
  *	If you know of any other methods of printing, please add them.
  */
func funct() string {
	return "pass returned line.";
}

type struct_example struct {
    Var string
}

func main() {
	Var := "variable";
	str := []string{
		"pass line 1.\n",
		"pass line 2.",
		"pass line 3.\n",
		"pass line 4 %s.\n",
		"pass line 5.\n",
		"pass line 6.\n",
		"pass line 7.",
		"pass line 8.\n",
		"pass line 9 %s.\n",
		"pass line 10.\n",
		"pass line 11.\n",
		"pass line 12.",
		"pass line 13.\n",
		"pass line 14 %s.\n" };

	stru := struct_example{ "pass struct line." };

	fmt.Println( funct() );
	fmt.Println( stru.Var );
	fmt.Println( 1234 );

	// standard output stream
    fmt.Print( str[ 0 ] );
    fmt.Print( "catch line 1.\n" );

    fmt.Println( str[ 1 ] );
    fmt.Println( "catch line 2." );

	fmt.Printf( str[ 2 ] );
	fmt.Printf( "catch line 3.\n" );

	fmt.Printf( str[ 3 ], Var );
	fmt.Printf( "pass line 4 %s.\n", Var );
	fmt.Printf( "catch line 4 %s.\n", "none" );

	os.Stdout.WriteString( str[ 4 ] );
	os.Stdout.WriteString( "catch line 5.\n" );

	fmt.Fprint( os.Stdout, str[ 5 ] );
	fmt.Fprint( os.Stdout, "catch line 6.\n" );

	fmt.Fprintln( os.Stdout, str[ 6 ] );
	fmt.Fprintln( os.Stdout, "catch line 7." );

	fmt.Fprintf( os.Stdout, str[ 7 ] );
	fmt.Fprintf( os.Stdout, "catch line 8.\n" );

	fmt.Fprintf( os.Stdout, str[ 8 ], Var );
	fmt.Fprintf( os.Stdout, "pass line 9 %s.\n", Var );
	fmt.Fprintf( os.Stdout, "catch line 9 %s.\n", "none" );

	// standard error stream
	os.Stderr.WriteString( str[ 9 ] );
	os.Stderr.WriteString( "catch line 10.\n" );

	fmt.Fprint( os.Stderr, str[ 10 ] );
	fmt.Fprint( os.Stderr, "catch line 11.\n" );

	fmt.Fprintln( os.Stderr, str[ 11 ] );
	fmt.Fprintln( os.Stderr, "catch line 12." );

	fmt.Fprintf( os.Stderr, str[ 12 ] );
	fmt.Fprintf( os.Stderr, "catch line 13.\n" );

	fmt.Fprintf( os.Stderr, str[ 13 ], Var );
	fmt.Fprintf( os.Stderr, "pass line 14 %s.\n", Var );
	fmt.Fprintf( os.Stderr, "catch line 14 %s.\n", "none" );
}
