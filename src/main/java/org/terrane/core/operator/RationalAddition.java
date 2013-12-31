package org.terrane.core.operator;

import org.terrane.core.Rational;

public class RationalAddition extends CommutativeOperator<Rational>
{
	@Override
    public Rational execute (Rational operand1, Rational operand2)
    {
		long denom1 = operand1.getDenominator();
		long denom2 = operand2.getDenominator();

		if (denom1 > Integer.MAX_VALUE || denom2 > Integer.MAX_VALUE) {
			throw new RuntimeException("Rational overflow");
		}

		long num1 = operand1.getNumerator();
		long num2 = operand2.getNumerator();

        return new Rational(num1 * denom2 + num2 * denom1, denom1 * denom2);
    }

	@Override
	public Rational getIdentity()
	{
		return Rational.ZERO;
	}
}
