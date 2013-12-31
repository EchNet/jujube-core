package org.terrane.core.operator;

public class LongMin extends LongCommutativeOperator
{
	@Override
	public long execute(long l1, long l2)
	{
		return l1 < l2 ? l1 : l2;
	}
}
