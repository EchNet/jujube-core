package org.terrane.core;

import java.util.List;

/**
 * A read-only view on a linear array of integer values.
 */
abstract public class IntArray
	extends IntSequence
{
	public static IntArray wrap(final int[] array)
	{
		return wrap(array, array.length);
	}

	public static IntArray wrap(final int[] array, final int limit)
	{
		return new IntArray() {

			@Override
			public int length() {
				return limit;
			}

			@Override
			public int get(int index) {
				return array[index];
			}
		};
	}

	public static <T extends Number> IntArray wrap(final List<T> list)
	{
		return new IntArray() {

			@Override
			public int length() {
				return list.size();
			}

			@Override
			public int get(int index) {
				return list.get(index).intValue();
			}
		};
	}

	public static IntArray wrap(final IntSequence sequence, final int limit)
	{
		int[] values = new int[limit];

		int i = 0;
		IntCursor cursor = sequence.cursor();
		while (i < limit && cursor.hasNext()) {
			values[i++] = cursor.next();
		}

		return wrap(values, i);
	}

	protected IntArray() {}

	/**
	 * Get the length (a.k.a. "size") of the array.
	 */
	abstract public int length();

	/**
	 * Index an element of the array.
	 */
	abstract public int get(int index);

	// Default implementation of IntSequence.cursor() based on length() and get().
	@Override
	public IntCursor cursor()
	{
		return new Cursor(0);
	}

	private class Cursor
		implements IntCursor
	{
		private int i;

		Cursor(int start) {
			this.i = start;
		}

		@Override
		public boolean hasNext() {
			return i < length();
		}

		@Override
		public int next() {
			return get(i++);
		}

		@Override
		public IntCursor copy() {
			return new Cursor(i);
		}
	}
}
