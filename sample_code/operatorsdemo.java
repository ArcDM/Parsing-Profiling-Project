/**	This program program has no output.
  *	It demonstrates most of the operators I know of.
  *	If you know of any other operators, please add them.
  */

public class operatorsdemo
{
	public static void main( String[] args )
	{
		// arithmetic operators
		int var1 = 1;
		int var2 = 2;
		int var3;
		int[] arr = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

		var3 = var1 + var2;
		var3 = var1 - var2;
		var3 = var1 * var2;
		var3 = var1 / var2;
		var3 = var1 % var2;

		var3 = +var1;
		var3 = -var1;
		++var1;
		var1++;
		--var1;
		var1--;

		// relational operators
		boolean eq = var1 == var2;
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
		var3 = ~var1;
		var3 = var1 & var2;
		var3 = var1 | var2;
		var3 = var1 ^ var2;
		var3 = var1 >> var2;
		var3 = var1 << var2;
		var3 = var1 >>> var2;

		// compound assignment operators
		var1 += var2;
		var1 -= var2;
		var1 *= var2;
		var1 /= var2;
		var1 %= var2;
		var1 &= var2;
		var1 |= var2;
		var1 ^= var2;
		var1 >>= var2;
		var1 <<= var2;
		var1 >>>= var2;

		// other
		var3 = arr[ 2 ];
		var3 = ( eq )? 0 : 1;
	}
}

