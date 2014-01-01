package org.terrane.core;

import org.terrane.core.operator.RationalMin;
import org.terrane.core.operator.RationalMax;
import org.terrane.core.operator.RationalAddition;
import org.terrane.core.operator.RationalMultiplication;
import static org.terrane.core.operator.LongGreatestCommonDivisor.gcd;

public class Rational
	extends Number
    implements Comparable<Rational>
{
    public final static Rational ZERO = new Rational(0);
    public final static Rational ONE = new Rational(1);
    public final static Rational NaN = new Rational(0,0);

	public final static RationalMin MIN = new RationalMin();
	public final static RationalMax MAX = new RationalMax();
	public final static RationalAddition ADDITION = new RationalAddition();
	public final static RationalMultiplication MULTIPLICATION = new RationalMultiplication();

    private long num, denom;

    public static Rational valueOf(long num)
    {
        return valueOf(num, 1);
    }

    public static Rational valueOf(long num, long denom)
    {
        if (denom == 0) return NaN;
        if (num == 0) return ZERO;
        if (num == denom) return ONE;
        return new Rational(num, denom);
    }

    public Rational(long num)
    {
        this.num = num;
        this.denom = 1;
    }

    public Rational(long num, long denom)
    {
        this.num = num;
        this.denom = denom;
        reduce();
    }

    private Rational()
    {
    }

    public long getNumerator()
    {
        return num;
    }

    public long getDenominator()
    {
        return denom;
    }

	public boolean isNaN()
	{
		return denom == 0;
	}

	@Override
    public long longValue()
    {
        return num / denom;
    }

	@Override
    public int intValue()
    {
        return (int) (num / denom);
    }

	@Override
    public double doubleValue()
    {
        return (double) num / (double) denom;
    }

	@Override
    public float floatValue()
    {
        return (float) num / (float) denom;
    }

	public int signum()
	{
		return num == 0 ? 0 : (num > 0 ? 1 : -1); 
	}

    public Rational plus (Rational that)
    {
		return ADDITION.execute(this, that);
    }

    public Rational minus (Rational that)
    {
		if (this.denom > Integer.MAX_VALUE || that.denom > Integer.MAX_VALUE) throw new RuntimeException("Rational overflow");
        long sumNum = this.num * that.denom - that.num * this.denom;
        long sumDenom = this.denom * that.denom;
        return new Rational(sumNum, sumDenom);
    }

    public Rational times (Rational that)
    {
		return MULTIPLICATION.execute(this, that);
    }

    public Rational reciprocal ()
    {
        Rational r = new Rational();
        r.denom = num;
        r.num = denom;
        return r;
    }

    public String toString()
    {
        return num + (denom == 1 ? "" : ("/" + denom));
    }

    private void reduce()
    {
        if (denom == 0) {
            num = 0;
        }
        else {
            if (denom < 0) {
                num *= -1;
                denom *= -1;
            }
            long d = gcd(num, denom);
            num /= d;
            denom /= d;
        }
    }

    public int hashCode()
    {
        long bits = Double.doubleToLongBits(doubleValue());
        return ((int) (bits >> 32)) ^ (int) bits;
    }

    public boolean equalsRational(Rational that)
    {
        return that.num == num && that.denom == denom;
    }

    public boolean equals(Object that)
    {
        try {
            return equalsRational((Rational) that);
        }
        catch (Exception e) {
            return false;
        }
    }

    public int compareTo(Rational that)
    {
		if (this.denom > Integer.MAX_VALUE || that.denom > Integer.MAX_VALUE) throw new RuntimeException("Rational overflow");
		if (equalsRational(that)) return 0;
        return (this.num * that.denom) > (that.num * this.denom) ? 1 : -1;
    }

    public int compareTo(long that)
    {
		if (this.denom > Integer.MAX_VALUE) throw new RuntimeException("Rational overflow");
		if (this.denom == 1 && this.num == that) return 0;
        return this.num > (that * this.denom) ? 1 : -1;
    }
}
