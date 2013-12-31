package org.terrane.core;

import java.util.Arrays;
import java.util.Iterator;

//
// Immutable
//
public class LongMultiset
{
	private final static long[] EMPTY_LONG_ARRAY = new long[0];
	private final static int[] EMPTY_INT_ARRAY = new int[0];

	protected long[] values;
	protected int[] multiplicities;
	private LongArray valuesView;
	private IntArray multiplicitiesView;

	public LongMultiset()
	{
		this(EMPTY_LONG_ARRAY, EMPTY_INT_ARRAY);
	}

	public LongMultiset(long value)
	{
		this(new long[] { value }, new int[] { 1 });
	}

	public LongMultiset(long value, int multiplicity)
	{
		this(new long[] { value }, new int[] { multiplicity });
		validateMultiplicity(multiplicity);
	}

	public LongMultiset(LongMultiset mset)
	{
		this(mset.values, mset.multiplicities);
	}

	protected LongMultiset(long[] values, int[] multiplicities)
	{
		this.values = values;
		this.multiplicities = multiplicities;
	}

	public static class Entry
	{
		private long value;
		private int multiplicity;

		Entry(long value, int multiplicity)
		{
			this.value = value;
			this.multiplicity = multiplicity;
		}

		public long getValue()
		{
			return value;
		}

		public int getMultiplicity()
		{
			return multiplicity;
		}
	}

	public LongMultiset add(long value)
	{
		return doAdd(value, 1);
	}

	public LongMultiset add(long value, int multiplicity)
	{
		validateMultiplicity(multiplicity);
		return doAdd(value, multiplicity);
	}

	public LongMultiset add(LongMultiset mset)
	{
		int newLength = combinedLength(mset);
		long[] newValues;
		int[] newMultiplicities;
		if (newLength == values.length) {
			newValues = values;
			newMultiplicities = multiplicities;
		}
		else {
			newValues = new long[newLength];
			newMultiplicities = new int[newLength];
		}
		combine(mset, newValues, newMultiplicities);
		return new LongMultiset(newValues, newMultiplicities);
	}

	public LongMultiset scale(int mult)
	{
		if (mult < 0) {
			throw new IllegalArgumentException("mult=" + mult);
		}
		if (mult == 0) {
			return new LongMultiset();
		}
		else {
			int[] newMultiplicities = new int[multiplicities.length];
			for (int i = 0; i < multiplicities.length; ++i) {
				newMultiplicities[i] = multiplicities[i] * mult;
			}
			return new LongMultiset(values, newMultiplicities);
		}
	}

	/**
	 * Get the multiplicity of the given value.
	 */
	public int getMultiplicity(int value)
	{
		int ix = Arrays.binarySearch(values, value);
		return ix < 0 ? 0 : multiplicities[ix];
	}

	/**
	 * Get an iterable view of the (value, multiplicity) pairs in this
	 * multiset, in ascending value order.
	 */
	public Iterable<Entry> entries()
	{
		return new Iterable<Entry>() {
			@Override
			public Iterator<Entry> iterator()
			{
				return new EntryIterator();
			}
		};
	}

	/**
	 * Get the values in this multiset, in ascending order.
	 */
	public LongArray values()
	{
		if (valuesView == null) {
			valuesView = LongArray.wrap(values);
		}
		return valuesView;
	}

	/**
	 * Get the positive multiplicities in this multiset, in random order.
	 */
	public IntArray multiplicities()
	{
		if (multiplicitiesView == null) {
			multiplicitiesView = IntArray.wrap(multiplicities);
		}
		return multiplicitiesView;
	}

	/**
	 * Return true if this set contains all the elements of that set.
	 */
	public boolean contains(LongMultiset ms)
	{
		int i = 0;
		for (int j = 0; j < ms.values.length; ++j) {
			long v2 = ms.values[j];
			while (i < values.length && values[i] < v2) {
				++i;
			}
			if (i == values.length || values[i] > v2 || multiplicities[i] < ms.multiplicities[j]) {
				return false;
			}
		}
		return true;
	}

	public Iterable<LongMultiset> subsets()
	{
		return new Iterable<LongMultiset>() {
			@Override
			public Iterator<LongMultiset> iterator()
			{
				return new SubsetIterator();
			}
		};
	}

	@Override
	public boolean equals(Object obj)
	{
		return (obj instanceof LongMultiset) && equalsMultiset((LongMultiset) obj);
	}

	public boolean equalsMultiset(LongMultiset mset)
	{
		int i = 0, j = 0;
		for (;;) {
			while (i < values.length && multiplicities[i] == 0) {
				++i;
			}
			while (j < mset.values.length && mset.multiplicities[j] == 0) {
				++j;
			}
			if (i == values.length || j == mset.values.length ||
				values[i] != mset.values[j] ||
				multiplicities[i] != mset.multiplicities[j]) {
				break;
			}
			else {
				++i; ++j;
			}
		}
		return i == values.length && j == mset.values.length;
	}

	@Override
	public String toString()
	{
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < values.length; ++i) {
			if (multiplicities[i] > 0) {
				if (buf.length() > 0) buf.append(",");
				buf.append(values[i]);
				if (multiplicities[i] > 1) {
					buf.append("^");
					buf.append(multiplicities[i]);
				}
			}
		}
		return buf.toString();
	}

	private class EntryIterator implements Iterator<Entry>
	{
		private int i;
		private boolean ready;

		@Override
		public boolean hasNext()
		{
			if (!ready) {
				while (i < values.length) {
					if (multiplicities[i] > 0) {
						ready = true;
						break;
					}
					++i;
				}
			}
			return ready;
		}

		@Override
		public Entry next()
		{
			if (!hasNext()) {
				throw new IllegalStateException();
			}
			Entry entry = new Entry(values[i], multiplicities[i]);
			++i;
			ready = false;
			return entry;
		}

		@Override
		public void remove()
		{
			throw new UnsupportedOperationException();
		}
	}

	private class SubsetIterator implements Iterator<LongMultiset>
	{
		private int[] localMultiplicities = new int[multiplicities.length];
		private boolean ready = true;

		@Override
		public boolean hasNext()
		{
			if (!ready) {
				for (int index = 0; index < localMultiplicities.length; ++index) {
					if (localMultiplicities[index] < multiplicities[index]) {
						localMultiplicities[index] += 1;
						ready = true;
						break;
					}
					localMultiplicities[index] = 0;
				}
			}
			return ready;
		}

		@Override
		public LongMultiset next()
		{
			if (!hasNext()) {
				throw new IllegalStateException();
			}
			ready = false;
			return new LongMultiset(values, localMultiplicities);
		}

		@Override
		public void remove()
		{
			throw new UnsupportedOperationException();
		}
	}

	private void validateMultiplicity(int multiplicity)
	{
		if (multiplicity <= 0) {
			throw new IllegalArgumentException("multiplicity=" + multiplicity);
		}
	}

	private LongMultiset doAdd(long value, int multiplicity)
	{
		long[] newValues;
		int[] newMultiplicities;
		int ix = Arrays.binarySearch(values, value);
		if (ix >= 0) {
			newValues = values;
			newMultiplicities = new int[multiplicities.length];
			System.arraycopy(multiplicities, 0, newMultiplicities, 0, multiplicities.length);
			newMultiplicities[ix] += multiplicity;
		}
		else {
			ix = -1 - ix;
			newValues = new long[values.length + 1];
			newMultiplicities = new int[multiplicities.length + 1];
			System.arraycopy(values, 0, newValues, 0, ix);
			System.arraycopy(multiplicities, 0, newMultiplicities, 0, ix);
			System.arraycopy(values, ix, newValues, ix + 1, values.length - ix);
			System.arraycopy(multiplicities, ix, newMultiplicities, ix + 1, values.length - ix);
			newValues[ix] = value;
			newMultiplicities[ix] = multiplicity;
		}
		return new LongMultiset(newValues, newMultiplicities);
	}

	private int combinedLength(LongMultiset mset)
	{
		int newLength = 0;
		int i = 0, j = 0;
		while (i < values.length && j < mset.values.length) {
			long v1 = values[i], v2 = mset.values[j];
			if (v1 <= v2) {
				++i;
			}
			if (v1 >= v2) {
				++j;
			}
			++newLength;
		}
		newLength += values.length - i;
		newLength += mset.values.length - j;
		return newLength;
	}

	private void combine(LongMultiset mset, long[] newValues, int[] newMultiplicities)
	{
		int newIndex = 0;
		int i = 0, j = 0;
		while (i < values.length && j < mset.values.length) {
			long v1 = values[i], v2 = mset.values[j];
			int multiplicity = 0;
			if (v1 <= v2) {
				multiplicity += multiplicities[i++];
			}
			if (v1 >= v2) {
				multiplicity += mset.multiplicities[j++];
			}
			newValues[newIndex] = v1 < v2 ? v1 : v2;
			newMultiplicities[newIndex++] = multiplicity;
		}
		if (i < values.length) {
			System.arraycopy(values, i, newValues, newIndex, values.length - i);
			System.arraycopy(multiplicities, i, newMultiplicities, newIndex, values.length - i);
		}
		if (j < mset.values.length) {
			System.arraycopy(mset.values, i, newValues, newIndex, mset.values.length - j);
			System.arraycopy(mset.multiplicities, i, newMultiplicities, newIndex, mset.values.length - j);
		}
	}
}
