package main

/**	This program program has no output.
  *	It demonstrates most of the operators I know of.
  *	If you know of any other operators, please add them.
  */

func main() {
	// arithmetic operators
    var1 := 1;
    var2 := 2;
	arr := []int{ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

	var3 := var1 + var2;
	var3 = var1 - var2;
	var3 = var1 * var2;
	var3 = var1 / var2;
	var3 = var1 % var2;

	var3 = +var1;
	var3 = -var1;
	var1++;
	var1--;

	// relational operators
	eq := var1 == var2;
	eq = var1 != var2;
	eq = var1 < var2;
	eq = var1 > var2;
	eq = var1 <= var2;
	eq = var1 >= var2;

	// logical operators
	eq = !eq;
	eq = eq && true;
	eq = eq || false;

	// bitwise operators
	var3 = ^var1;
	var3 = var1 & var2;
	var3 = var1 | var2;
	var3 = var1 ^ var2;
	var3 = var1 &^ var2;
	var3 = var1 >> 1;
	var3 = var1 << 1;

	// compound assignment operators
	var3 += var2;
	var3 -= var2;
	var3 *= var2;
	var3 /= var2;
	var3 %= var2;
	var3 &= var2;
	var3 |= var2;
	var3 ^= var2;
	var3 >>= 1;
	var3 <<= 1;

	// other
	var3 = arr[ 2 ];
}
