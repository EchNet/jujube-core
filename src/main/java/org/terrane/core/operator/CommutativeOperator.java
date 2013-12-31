package org.terrane.core.operator;

import java.util.Iterator;

abstract public class CommutativeOperator<T extends Number>
	extends BinaryOperator<T>
{
	public T getIdentity()
	{
		throw new UnsupportedOperationException();
	}

	public T rollup(Iterable<T> sequence)
	{
		return rollup(sequence.iterator());
	}

	private T rollup(Iterator<T> iterator)
	{
		T result = iterator.hasNext() ? iterator.next() : getIdentity();
		while (iterator.hasNext()) {
			result = execute(result, iterator.next());
		}
		return result;
	}
}
