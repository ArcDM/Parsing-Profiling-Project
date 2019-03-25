
import sys

"""
This program demonstrates all of the print statements I know of.
	It also includes error printing and only goes to standard output
If you know of any other methods of printing, please add them.
"""

class example:
	var = "pass class line."

def func():
	return "pass returned line."

def main():
	var = "variable"
	str = [ "pass line 1.",
			"pass line 2 {}.",
			"pass line 3 %s.",
			"pass line 4.\n",
			"pass stderr line 5.",
			"pass stderr line 6.\n" ]

	print( example.var )
	print( func() )
	print( 1234 )

	# standard output stream
	print( str[ 0 ] )
	print( "catch line 1." )

	# the following works in python but not python3
	"""
	print( str[ 1 ] ).format( var )
	print( "pass line 2 {}." ).format( var )
	"""

	print( str[ 1 ].format( var ) )
	print( "pass line 2 {}.".format( var ) )

	print( str[ 2 ] %  var )
	print( "pass line 3 %s." %  var )
	print( "catch line 3 %s." %  "none" )

	sys.stdout.write( str[ 3 ] )
	sys.__stdout__.write( str[ 3 ] )
	sys.stdout.write( "catch line 4.\n" )
	sys.__stdout__.write( "catch line 4.\n" )

	# standard error stream
	print( str[ 4 ], file=sys.stderr )
	print( str[ 4 ], file=sys.__stderr__ )
	print( "catch stderr line 5.", file=sys.stderr )
	print( "catch stderr line 5.", file=sys.__stderr__ )

	sys.stderr.write( str[ 5 ] )
	sys.__stderr__.write( str[ 5 ] )
	sys.stderr.write( "catch line 6.\n" )
	sys.__stderr__.write( "catch line 6.\n" )


if __name__ == "__main__":
	main()
