package org.terrane.core.operator;

import java.math.BigInteger;

public class BigIntegerAddition extends CommutativeOperator<BigInteger>
{
	@Override
    public BigInteger execute (BigInteger operand1, BigInteger operand2)
    {
        return operand1.add(operand2);
    }

	@Override
	public BigInteger getIdentity()
	{
		return BigInteger.ZERO;
	}
}
