package org.terrane.core.operator;

import org.terrane.core.LongCursor;
import org.terrane.core.LongSequence;

abstract public class LongCommutativeOperator
	extends LongBinaryOperator
{
	public long getIdentity()
	{
		throw new UnsupportedOperationException();
	}

	public long rollup(LongSequence sequence)
	{
		return rollup(sequence.cursor());
	}

	private long rollup(LongCursor cursor)
	{
		long result = cursor.hasNext() ? cursor.next() : getIdentity();
		while (cursor.hasNext()) {
			result = execute(result, cursor.next());
		}
		return result;
	}
}
