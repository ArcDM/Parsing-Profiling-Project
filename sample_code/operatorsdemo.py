"""
This program has no output.
It demonstrates most of the operators I know of.
If you know of any other operators, please add them.
"""

def main():

	# arithmetic operators
	var1 = 1;
	var2 = 2;
	var3 = var1;
	arr = [ i for i in range( 10 ) ]

	var1 + var2;
	var1 - var2;
	var1 * var2;
	var1 ** var2;
	var1 / var2;
	var1 // var2;
	var1 % var2;

	"test" + " string"

	+var1;
	-var1;

	# relational operators
	eq = var1 == var2;
	eq = var1 != var2;
	eq = var1 > var2;
	eq = var1 < var2;
	eq = var1 >= var2;
	eq = var1 <= var2;
	eq = var1 is var2;
	eq = var1 is not var2;

	1 in arr
	11 not in arr

	# logical operators
	not eq;
	eq and True;
	eq or False;

	# bitwise operators
	~var1;
	var1 & var2;
	var1 | var2;
	var1 ^ var2;
	var1 >> var2;
	var1 << var2;

	# compound assignment operators
	var1 += var2;
	var1 -= var2;
	var1 *= var2;
	var3 /= var2;
	var1 %= var2;
	var1 **= var2;
	var1 //= var2;
	var1 &= var2;
	var1 |= var2;
	var1 ^= var2;
	var1 >>= var2;
	var1 <<= var2;

	# other
	arr[ 2 ]
	arr[ 1 : 5 ]

if __name__ == "__main__":
	main()
