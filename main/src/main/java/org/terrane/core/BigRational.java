package org.terrane.core;

import java.math.BigInteger;

public class BigRational
    implements Comparable<BigRational>
{
    public final static BigRational ZERO = new BigRational(BigInteger.ZERO);
    public final static BigRational ONE = new BigRational(BigInteger.ONE);
    public final static BigRational NaN = new BigRational(0, 0);

    private BigInteger num, denom;

    public static BigRational valueOf(long num)
    {
		if (num == 0) return ZERO;
		if (num == 1) return ONE;
		return new BigRational(num, 1);
    }

    public BigRational(long num)
    {
        this(BigInteger.valueOf(num));
    }

    public BigRational(BigInteger num)
    {
        this.num = num;
        this.denom = BigInteger.ONE;
    }

    public BigRational(long num, long denom)
    {
        this(BigInteger.valueOf(num), BigInteger.valueOf(denom));
    }

    public BigRational(BigInteger num, BigInteger denom)
    {
        this.num = num;
        this.denom = denom;
        reduce();
    }

    private BigRational()
    {
    }

    public BigInteger getNumerator()
    {
        return num;
    }

    public BigInteger getDenominator()
    {
        return denom;
    }

    /**
     * Only approximate.
     */
    public double doubleValue()
    {
        return num.doubleValue() / denom.doubleValue();
    }

    public BigRational plus (BigRational that)
    {
        BigInteger sumNum = this.num.multiply(that.denom).add(that.num.multiply(this.denom));
        BigInteger sumDenom = this.denom.multiply(that.denom);
        return new BigRational(sumNum, sumDenom);
    }

    public BigRational minus (BigRational that)
    {
        BigInteger sumNum = this.num.multiply(that.denom).subtract(that.num.multiply(this.denom));
        BigInteger sumDenom = this.denom.multiply(that.denom);
        return new BigRational(sumNum, sumDenom);
    }

    public BigRational times (BigRational that)
    {
        BigInteger prodNum = this.num.multiply(that.num);
        BigInteger prodDenom = this.denom.multiply(that.denom);
        return new BigRational(prodNum, prodDenom);
    }

    public BigRational reciprocal ()
    {
        BigRational r = new BigRational();
        r.denom = num;
        r.num = denom;
        return r;
    }

	public int signum()
	{
		return num.signum();
	}

    public String toString()
    {
        return num + "/" + denom;
    }

    private void reduce()
    {
		int signum = denom.signum();
		if (signum == 0) {
            num = BigInteger.ZERO;
		}
		else {
			if (signum < 0) {
				num = num.negate();
				denom = denom.negate();
			}
			BigInteger d = gcd(num, denom);
			if (!d.equals(BigInteger.ONE)) {
				num = num.divide(d);
				denom = denom.divide(d);
			}
		}
    }

    public static BigInteger gcd (BigInteger a, BigInteger b)
    {
        if (a.equals(BigInteger.ZERO)) return b;
        if (b.equals(BigInteger.ZERO)) return a;
		a = a.abs();
		b = b.abs();

        if (b.compareTo(a) < 0) {
            BigInteger tmp = a;
            a = b;
            b = tmp;
        }

        BigInteger rem;
        while (!(rem = b.mod(a)).equals(BigInteger.ZERO)) {
            b = a;
            a = rem;
        }

        return a;
    }

    public int hashCode()
    {
        return num.hashCode() ^ denom.hashCode();
    }

    public boolean equalsBigRational(BigRational that)
    {
        return that.num.equals(num) && that.denom.equals(denom);
    }

    public boolean equals(Object that)
    {
        try {
			return equalsBigRational((BigRational) that);
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public int compareTo(BigRational that)
    {
		if (equalsBigRational(that)) return 0;
        return this.num.multiply(that.denom).compareTo(that.num.multiply(this.denom));
    }
}
