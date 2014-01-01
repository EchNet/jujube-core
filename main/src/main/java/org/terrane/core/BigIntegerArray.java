package org.terrane.core;

import java.math.BigInteger;
import java.util.AbstractList;
import java.util.List;
import org.terrane.core.operator.BigIntegerAddition;
import org.terrane.core.operator.BigIntegerMax;
import org.terrane.core.operator.CommutativeOperator;

abstract public class BigIntegerArray
	extends AbstractList<BigInteger>
	implements List<BigInteger>
{
	public final static BigIntegerAddition ADDITION = new BigIntegerAddition();
	public final static BigIntegerMax MAX = new BigIntegerMax();

	public static BigIntegerArray wrap(final BigInteger[] array)
	{
		return new BigIntegerArray() {

			@Override
			public int size() {
				return array.length;
			}

			@Override
			public BigInteger get(int index) {
				return array[index];
			}
		};
	}

	protected BigIntegerArray() {}

	public BigInteger sum()
	{
		return rollup(ADDITION);
	}

	public BigInteger max()
	{
		return rollup(MAX);
	}

	public BigInteger rollup(CommutativeOperator<BigInteger> operator)
	{
		return operator.rollup(this);
	}
}
