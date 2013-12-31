package org.terrane.core;

import java.util.List;

/**
 * A read-only view on a linear array or list of integer values.  Enables generic
 * int array traversal logic based on native arrays, lists, or other structures,
 * while avoiding the overhead of "boxing/unboxing".
 */
abstract public class LongArray
	extends LongSequence
{
	public final static LongArray EMPTY_ARRAY = LongArray.wrap(new int[0]);

	public static LongArray wrap(final int[] array)
	{
		return new LongArray() {

			@Override
			public int length() {
				return array.length;
			}

			@Override
			public long get(int index) {
				return array[index];
			}
		};
	}

	public static LongArray wrap(final long[] array)
	{
		return new LongArray() {

			@Override
			public int length() {
				return array.length;
			}

			@Override
			public long get(int index) {
				return array[index];
			}
		};
	}

	public static <T extends Number> LongArray wrap(final List<T> list)
	{
		return new LongArray() {

			@Override
			public int length() {
				return list.size();
			}

			@Override
			public long get(int index) {
				return list.get(index).longValue();
			}
		};
	}

	protected LongArray() {}

	/**
	 * Get the length (a.k.a. "size") of the array.
	 */
	abstract public int length();

	/**
	 * Index an element of the array.
	 */
	abstract public long get(int index);

	// Default implementation of LongSequence.cursor() based on length() and get().
	@Override
	public LongCursor cursor()
	{
		return new Cursor();
	}

	private class Cursor
		implements LongCursor
	{
		private int i;

		@Override
		public boolean hasNext()
		{
			return i < length();
		}

		@Override
		public long next()
		{
			return get(i++);
		}

		@Override
		public LongCursor copy()
		{
			Cursor c = new Cursor();
			c.i = i;
			return c;
		}
	}
}
