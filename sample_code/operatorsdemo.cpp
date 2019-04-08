#include <iostream>
using namespace std;

/**	This program program has no output.
  *	It demonstrates most of the operators I know of.
  *	If you know of any other operators, please add them.
  */

int main()
{
	// arithmetic operators
    int var1 = 1;
    int var2 = 2;
	int arr[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

	var1 + var2;
	var1 - var2;
	var1 * var2;
	var1 / var2;
	var1 % var2;

	+var1;
	-var1;
	++var1;
	var1++;
	--var1;
	var1--;

	// relational operators
	bool eq = var1 == var2;
		eq = var1 != var2;
		eq = var1 not_eq var2;
		eq = var1 > var2;
		eq = var1 < var2;
		eq = var1 >= var2;
		eq = var1 <= var2;

	// logical operators
	!eq;
	not eq;
	eq && true;
	eq and true;
	eq || false;
	eq or false;

	// bitwise operators
	~var1;
	compl var1;
	var1 & var2;
	var1 bitand var2;
	var1 | var2;
	var1 bitor var2;
	var1 ^ var2;
	var1 xor var2;
	var1 >> var2;
	var1 << var2;

	// compound assignment operators
	var1 += var2;
	var1 -= var2;
	var1 *= var2;
	var1 /= var2;
	var1 %= var2;
	var1 &= var2;
	var1 and_eq var2;
	var1 |= var2;
	var1 or_eq var2;
	var1 ^= var2;
	var1 xor_eq var2;
	var1 >>= var2;
	var1 <<= var2;

	// other
	arr[ 2 ];
	( var1 )? 0 : 1;

    return 0;
}

