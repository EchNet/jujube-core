package org.terrane.core.operator;

import org.terrane.core.Rational;

public class RationalMin extends CommutativeOperator<Rational>
{
	@Override
    public Rational execute (Rational operand1, Rational operand2)
    {
		return operand1.compareTo(operand2) < 0 ? operand1 : operand2;
    }
}
