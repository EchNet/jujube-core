package org.terrane.core.operator;

public class LongGreatestCommonDivisor extends LongCommutativeOperator
{
	@Override
    public long execute (long a, long b)
	{
		return gcd(a, b);
	}

	// Euclid's algorithm.
    public static long gcd(long a, long b)
    {
        if (a == 0) return b;
        if (b == 0) return a;
        if (a < 0) a *= -1;
        if (b < 0) b *= -1;

        if (b < a) {
            long tmp = a;
            a = b;
            b = tmp;
        }

        long rem;
        while ((rem = b % a) != 0) {
            b = a;
            a = rem;
        }

        return a;
    }
}
