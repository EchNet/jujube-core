package org.terrane.core;

import java.util.Collection;
import java.util.Iterator;

abstract public class IntSequence
{
	/**
	 * Make a collection of numbers act as an IntSequence.
	 */
	public static <T extends Number> IntSequence wrap(final Collection<T> collection)
	{
		return new IntSequence() {
			@Override
			public IntCursor cursor() {
				return new AbstractIntCursor() {
					Iterator<T> iterator = collection.iterator();

					@Override
					public boolean hasNext() {
						return iterator.hasNext();
					}

					@Override
					public int next() {
						return iterator.next().intValue();
					}
				};
			}
		};
	}

	/**
	 * Iterate over the elements of this sequence.
	 */
	abstract public IntCursor cursor();

	/**
	 * Build a new IntSequence with an artificial length limit.
	 */
	public IntSequence limit(final int max)
	{
		final IntSequence me = this;
		return new IntSequence () {
			@Override
			public IntCursor cursor() {
				return new LimitedCursor(me.cursor(), 0, max);
			}
		};
	}

	/**
	 * Build a new IntSequence by mapping the values of this sequence.
	 */
	public IntSequence map(Object obj, String methodName)
		throws NoSuchMethodException
	{
		return map(new MethodIntIntMapping(obj, methodName));
	}

	/**
	 * Build a new IntSequence by mapping the values of this sequence.
	 */
	public IntSequence map(final IntIntMapping mapping)
	{
		return new MappedIntSequence(this, mapping);
	}

	/**
	 * Iterate over the elements of this sequence, starting at the indicated element
	 * @param n the index of the desired starting element, origin at zero
	 * @throws IllegalStateException if you try to seek past the end of the sequence.
	 */
	public IntCursor seek(int n)
	{
		IntCursor curs = cursor();
		for (int i = 0; i < n; ++i) {
			curs.next();
		}
		return curs;
	}

	/**
	 * Count the elements of this sequence.
	 */
	public int length()
	{
		int count = 0;
		for (IntCursor curs = cursor(); curs.hasNext(); ) {
			curs.next();
			++count;
		}
		return count;
	}

	/**
	 * Sum this sequence.
	 */
	public long sum()
	{
		return toLongSequence().sum();
	}

	/**
	 * Take the product of this sequence.
	 */
	public long product()
	{
		return toLongSequence().product();
	}

	/**
	 * Treat this sequence as a LongSequence.
	 */
	public LongSequence toLongSequence()
	{
		return new LongSequence() {
			@Override
			public LongCursor cursor() {
				return new MyLongCursor(IntSequence.this.cursor());
			}
		};
	}

	/**
	 * Capture as array.
	 */
	public int[] toArray()
	{
		int[] array = new int[length()];
		int count = 0;
		for (IntCursor curs = cursor(); curs.hasNext(); ) {
			array[count++] = curs.next();
		}
		return array;
	}

	@Override
	public String toString()
	{
		StringBuilder buf = new StringBuilder();
		for (IntCursor curs = cursor(); curs.hasNext(); ) {
			buf.append(buf.length() == 0 ? "[" : ",");
			buf.append(curs.next());
		}
		buf.append(buf.length() == 0 ? "[]" : "]");
		return buf.toString();
	}

	private class LimitedCursor extends ProxyIntCursor
	{
		final int max;
		int i;

		LimitedCursor(IntCursor inner, int i, int max) {
			super(inner);
			this.i = i;
			this.max = max;
		}

		@Override
		public boolean hasNext() {
			return i < max && super.hasNext();
		}

		@Override
		public int next() {
			if (i >= max) {
				throw new IllegalStateException();
			}
			int v = super.next();
			++i;
			return v;
		}
	}

	private class MyLongCursor
		implements LongCursor
	{
		IntCursor inner;

		MyLongCursor(IntCursor inner) {
			this.inner = inner;
		}

		@Override
		public boolean hasNext() {
			return inner.hasNext();
		}

		@Override
		public long next() {
			return inner.next();
		}

		@Override
		public MyLongCursor copy() {
			return new MyLongCursor(inner.copy());
		}
	}
}
