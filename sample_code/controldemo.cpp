#include <cstdlib>
using namespace std;

/**	This program demonstrates all of the control statements I know of.
  *	If you know of any other methods of printing, please add them.
  */

#define macro_string "pass macro line."

void func()
{
	return;
}

int main()
{
	struct struct_example
	{
		char var = 'a';
	} stru;

	int index = 0;

	if( index == 0 )
	{
		index++;
	}

	if( index > 0 )
	{
		index--;
	}
	else
	{
		index++;
	}

	if( index > 0 )
	{
		index--;
	}
	else if( index < 0 )
	{
		index++;
	}
	else
	{
		index = 5;
	}

	while( index > 0 )
	{
		index--;
	}

	do {
		index++;
	} while( index < 10 );

	for( int i = 10; i > 0; i-- )
	{
		i--;
	}

	for( int i = 10; i > 0; i-- )
	{
		if( i > 5 )
		{
			continue;
		}

		if( i < 4 )
		{
			break;
		}
	}

	goto lable;

	exit( 0 );

	lable:
 
	switch( index )
	{
		case 0:
			index = 5;
			break;
		case 1:
		case 2:
			index--;
			break;
		default:
			index++;
			break;
	}

	func();

	return 0;
}

