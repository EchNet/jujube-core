package org.terrane.core.operator;

public class LongMultiplication extends LongCommutativeOperator
{
	@Override
	public long execute(long l1, long l2)
	{
		return l1 * l2;
	}

	@Override
	public long getIdentity()
	{
		return 1L;
	}
}
