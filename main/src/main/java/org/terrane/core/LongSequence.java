package org.terrane.core;

import java.util.Collection;
import java.util.Iterator;
import org.terrane.core.operator.LongAddition;
import org.terrane.core.operator.LongCommutativeOperator;
import org.terrane.core.operator.LongMax;
import org.terrane.core.operator.LongMin;
import org.terrane.core.operator.LongMultiplication;

abstract public class LongSequence
{
	public final static LongMax MAX = new LongMax();

	public final static LongMin MIN = new LongMin();

	public final static LongAddition ADDITION = new LongAddition();

	public final static LongMultiplication MULTIPLICATION = new LongMultiplication();

	/**
	 * Make a collection of numbers act as a LongSequence.
	 */
	public static <T extends Number> LongSequence wrap(final Collection<T> collection)
	{
		return new LongSequence() {
			@Override
			public LongCursor cursor() {
				return new AbstractLongCursor() {
					Iterator<T> iterator = collection.iterator();

					@Override
					public boolean hasNext() {
						return iterator.hasNext();
					}

					@Override
					public long next() {
						return iterator.next().longValue();
					}
				};
			}
		};
	}

	/**
	 * Iterate over the elements of this sequence.
	 */
	abstract public LongCursor cursor();

	/**
	 * Build a new LongSequence with an artificial length limit.
	 */
	public LongSequence limit(final int max)
	{
		final LongSequence me = this;
		return new LongSequence () {
			@Override
			public LongCursor cursor() {
				return new LimitedCursor(me.cursor(), 0, max);
			}
		};
	}

	/**
	 * Build a new LongSequence by mapping the values of this sequence.
	 */
	public LongSequence map(final LongLongMapping mapping)
	{
		return new MappedLongSequence(this, mapping);
	}

	public long sum()
	{
		return rollup(ADDITION);
	}

	public long product()
	{
		return rollup(MULTIPLICATION);
	}

	public long max()
	{
		return rollup(MAX);
	}

	public long min()
	{
		return rollup(MIN);
	}

	public long rollup(LongCommutativeOperator operator)
	{
		return operator.rollup(this);
	}

	public int length()
	{
		int count = 0;
		for (LongCursor curs = cursor(); curs.hasNext(); ) {
			curs.next();
			++count;
		}
		return count;
	}

	public long[] toArray()
	{
		long[] array = new long[length()];
		int i = 0;
		for (LongCursor curs = cursor(); curs.hasNext(); ) {
			array[i++] = curs.next();
		}
		return array;
	}

	@Override
	public String toString()
	{
		StringBuilder buf = new StringBuilder();
		for (LongCursor curs = cursor(); curs.hasNext(); ) {
			buf.append(buf.length() == 0 ? "[" : ",");
			buf.append(curs.next());
		}
		buf.append(buf.length() == 0 ? "[]" : "]");
		return buf.toString();
	}

	private class LimitedCursor
		implements LongCursor
	{
		final LongCursor inner;
		final int max;
		int i;

		LimitedCursor(LongCursor inner, int i, int max) {
			this.inner = inner;
			this.i = i;
			this.max = max;
		}

		@Override
		public boolean hasNext() {
			return i < max && inner.hasNext();
		}

		@Override
		public long next() {
			if (i >= max) {
				throw new IllegalStateException();
			}
			long v = inner.next();
			++i;
			return v;
		}

		@Override
		public LongCursor copy() {
			return new LimitedCursor(inner, i, max);
		}
	}
}
