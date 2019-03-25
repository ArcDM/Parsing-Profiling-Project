#include <iostream>
#include <string>
#include <stdio.h>
#include <unistd.h>
using namespace std;

/**	This program demonstrates all of the print statements I know of.
  *		It also includes error printing and only goes to standard output
  *	If you know of any other methods of printing, please add them.
  */

#define macro_string "pass macro line."

char* func()
{
	return "pass returned line.";
}

int main()
{
	struct struct_example
	{
		char var[ 18 ] = "pass struct line.";
	} stru;

	int index;

	char var[] = "variable",
		*str[] = {
			"pass line 1.",
			"pass line 2 ",
			"pass line 3.",
			"pass line 4 ",
			"pass line 5.\n",
			"pass line 6 %s.\n",
			"pass line 7.\n",
			"pass line 8 %s.\n",
			"pass line 9.\n",
			"pass line 10.",
			"pass line 11.\n",
			"pass line 12.\n",
			"pass line 13.\n",
			"pass line 14.\n",
			"pass line 15.",
			"pass line 16 ",
			"pass line 17.",
			"pass line 18 ",
			"pass line 19.\n",
			"pass line 20 %s.\n",
			"pass line 21.\n",
			"pass line 22.\n",
			"pass line 23.\n",
			"pass line 24.\n",
			"pass line 25." };

	cout << func() << endl;
	cout << stru.var << endl;
	cout << macro_string << endl;
	cout << 1234 << endl;

	// standard output stream
	cout << str[ 0 ] << endl;
	cout << "catch line 1." << endl;

	cout << str[ 1 ] << var << "." << endl;
	cout << "pass line 2 " << var << "." << endl;
	cout << "catch line 2." << "" << endl;
	cout << "catch line 2" << " none." << endl;

	wcout << str[ 2 ] << endl;
	wcout << "catch line 3." << endl;

	wcout << str[ 3 ] << var << "." << endl;
	wcout << "pass line 4 " << var << "." << endl;
	wcout << "catch line 4." << "" << endl;
	wcout << "catch line 4" << " none." << endl;

	printf( str[ 4 ] );
	printf( "catch line 5.\n" );

	printf( str[ 5 ], var );
	printf( "pass line 6 %s.\n", var );
	printf( "catch line 6 %s.\n", "none" );

	fprintf( stdout, str[ 6 ] );
	fprintf( stdout, "catch line 7.\n" );

	fprintf( stdout, str[ 7 ], var );
	fprintf( stdout, "pass line 8 %s.\n", var );
	fprintf( stdout, "catch line 8 %s.\n", "none" );

	write( STDOUT_FILENO, str[ 8 ], 13 );
	write( STDOUT_FILENO, "catch line 9.\n", sizeof( "catch line 9.\n" ) );

	puts( str[ 9 ] );
	puts( "catch line 10." );

	fputs( str[ 10 ], stdout );
	fputs( "catch line 11.\n", stdout );

	index = 0;

	do
	{
		putchar( str[ 11 ][ index ] );
	} while( str[ 11 ][ index++ ] != '\n' );

	putchar( 'x' );
	putchar( '\n' );

	index = 0;

	do
	{
		putc( str[ 12 ][ index ], stdout );
	} while( str[ 12 ][ index++ ] != '\n' );

	putc( 'x', stdout );
	putc( '\n', stdout );

	index = 0;

	do
	{
		fputc( str[ 13 ][ index ], stdout );
	} while( str[ 13 ][ index++ ] != '\n' );

	fputc( 'x', stdout );
	fputc( '\n', stdout );

	// standard error stream
	cerr << str[ 14 ] << endl;
	cerr << "catch line 15." << endl;

	cerr << str[ 15 ] << var << "." << endl;
	cerr << "pass line 16 " << var << "." << endl;
	cerr << "catch line 16." << "" << endl;
	cerr << "catch line 16" << " none." << endl;

	wcerr << str[ 16 ] << endl;
	wcerr << "catch line 17." << endl;

	wcerr << str[ 17 ] << var << "." << endl;
	wcerr << "pass line 18 " << var << "." << endl;
	wcerr << "catch line 18." << "" << endl;
	wcerr << "catch line 18" << " none." << endl;

	fprintf( stderr, str[ 18 ] );
	fprintf( stderr, "catch line 19.\n" );

	fprintf( stderr, str[ 19 ], var );
	fprintf( stderr, "pass line 20 %s.\n", var );
	fprintf( stderr, "catch line 20 %s.\n", "none" );

	write( STDERR_FILENO, str[ 20 ], 14 );
	write( STDERR_FILENO, "catch line 21.\n", sizeof( "catch line 21.\n" ) );

	fputs( str[ 21 ], stderr );
	fputs( "catch line 22.\n", stderr );

	index = 0;

	do
	{
		putc( str[ 22 ][ index ], stderr );
	} while( str[ 22 ][ index++ ] != '\n' );

	putc( 'x', stderr );
	putc( '\n', stderr );

	index = 0;

	do
	{
		fputc( str[ 23 ][ index ], stderr );
	} while( str[ 23 ][ index++ ] != '\n' );

	fputc( 'x', stderr );
	fputc( '\n', stderr );

	perror( str[ 24 ] );
	perror( "catch line 25." );

	return 0;
}

