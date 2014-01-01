package org.terrane.core.operator;

import java.math.BigInteger;

public class BigIntegerMax extends CommutativeOperator<BigInteger>
{
	@Override
    public BigInteger execute (BigInteger operand1, BigInteger operand2)
    {
		return operand1.compareTo(operand2) > 0 ? operand1 : operand2;
    }
}
