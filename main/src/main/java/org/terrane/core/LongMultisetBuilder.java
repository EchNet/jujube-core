package org.terrane.core;

import java.util.Arrays;

public class LongMultisetBuilder
{
	private final static int BASE_CHUNK = 8;

	private long[] valuebuf;
	private int[] multibuf;
	private int length;

	public LongMultisetBuilder()
	{
		this.valuebuf = new long[BASE_CHUNK];
		this.multibuf = new int[BASE_CHUNK];
	}

	public LongMultisetBuilder(LongMultiset multiset)
	{
		int size = BASE_CHUNK;
		while (size >= multiset.values().length()) {
			size *= 2;
		}
		this.valuebuf = new long[size];
		this.multibuf = new int[size];
		this.length = multiset.values().length();
		for (int i = 0; i < length; ++i) {
			valuebuf[i] = multiset.values().get(i);
			multibuf[i] = multiset.multiplicities().get(i);
		}
	}

	public LongMultisetBuilder add(long value)
	{
		doAdd(value, 1);
		return this;
	}

	public LongMultisetBuilder add(long value, int multiplicity)
	{
		validateMultiplicity(multiplicity);
		doAdd(value, multiplicity);
		return this;
	}

	public LongMultisetBuilder add(LongMultiset multiset)
	{
		for (int i = 0; i < multiset.values().length(); ++i) {
			doAdd(multiset.values().get(i), multiset.multiplicities().get(i));
		}
		return this;
	}

	public long[] values()
	{
		long[] values = new long[length];
		System.arraycopy(valuebuf, 0, values, 0, length);
		return values;
	}

	public int[] multiplicities()
	{
		int[] multiplicities = new int[length];
		System.arraycopy(multibuf, 0, multiplicities, 0, length);
		return multiplicities;
	}

	public LongMultiset produce()
	{
		return new LongMultiset(values(), multiplicities());
	}

	private void validateMultiplicity(int multiplicity)
	{
		if (multiplicity <= 0) {
			throw new IllegalArgumentException("multiplicity=" + multiplicity);
		}
	}

	private void doAdd(long value, int multiplicity)
	{
		int ix = (length == 0 || value > valuebuf[length - 1])
			? (-1 - length)
			: Arrays.binarySearch(valuebuf, value);
		if (ix >= 0) {
			multibuf[ix] += multiplicity;
		}
		else {
			if (length == valuebuf.length) {
				long[] newValuebuf = new long[valuebuf.length * 2];
				int[] newMultibuf = new int[multibuf.length * 2];
				System.arraycopy(valuebuf, 0, newValuebuf, 0, length);
				System.arraycopy(multibuf, 0, newMultibuf, 0, length);
				valuebuf = newValuebuf;
				multibuf = newMultibuf;
			}
			ix = -1 - ix;
			for (int i = length; i > ix; --i) {
				valuebuf[i] = valuebuf[i - 1];
				multibuf[i] = multibuf[i - 1];
			}
			valuebuf[ix] = value;
			multibuf[ix] = multiplicity;
			++length;
		}
	}
}
