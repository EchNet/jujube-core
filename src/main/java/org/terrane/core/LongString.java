package org.terrane.core;

import java.util.Arrays;

//
// LongString is an immutable, variable length array of 32-bit integers, offering 
// IntArray behavior as well as Javascript-like slice and splice methods.  LongString
// is usable as a hash key.
//
public class LongString
	extends LongArray
{
	private final static long[] EMPTY_ARRAY = new long[0];

	private long[] values;

	public LongString()
	{
		this.values = EMPTY_ARRAY;
	}

	public LongString(LongString that)
	{
		this.values = that.values;
	}

	public LongString(int length)
	{
		this.values = new long[length];
	}

	public LongString(long[] ini)
	{
		this(ini, ini.length);
	}

	public LongString(long[] ini, int length)
	{
		values = new long[length];
		System.arraycopy(ini, 0, values, 0, length);
	}

	private LongString(boolean dummy, long[] values)
	{
		this.values = values;
	}

	public int length()
	{
		return values.length;
	}

	public long get(int ix)
	{
		return values[ix];
	}

	public LongString copy()
	{
		return new LongString(true, values);
	}

	public long[] copyLongs()
	{
		return sliceLongs(0);
	}

	public LongString slice(int start)
	{
		return slice(start, values.length - start);
	}

	public long[] sliceLongs(int start)
	{
		return sliceLongs(start, values.length - start);
	}

	public LongString slice(int start, int length)
	{
		return new LongString(true, sliceLongs(start, length));
	}

	public long[] sliceLongs(int start, int length)
	{
		int newLength = Math.min(length, Math.max(values.length - start, 0));
		long[] newValues = new long[newLength];
		System.arraycopy(values, start, newValues, 0, newLength);
		return newValues;
	}

	public LongString append(LongString addl)
	{
		return append(addl.values);
	}

	public LongString append(long[] addl)
	{
		long[] newValues = new long[values.length + addl.length];
		System.arraycopy(values, 0, newValues, 0, values.length);
		System.arraycopy(addl, 0, newValues, values.length, addl.length);
		return new LongString(true, newValues);
	}

	public LongString splice(int start, int length)
	{
		return splice(start, length, EMPTY_ARRAY);
	}

	public LongString splice(int start, int length, LongString addl)
	{
		return splice(start, length, addl.values);
	}

	public LongString splice(int start, int length, long[] addl)
	{
		if (start < 0 || start >= values.length) throw new IllegalArgumentException();
		int tailStart = Math.min(start + length, values.length);
		int tailLength = values.length - tailStart;
		long[] newValues = new long[start + addl.length + tailLength];
		System.arraycopy(values, 0, newValues, 0, start);
		System.arraycopy(addl, 0, newValues, start, addl.length);
		System.arraycopy(values, tailStart, newValues, start + addl.length, tailLength);
		return new LongString(true, newValues);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof LongString) {
			return Arrays.equals(this.values, ((LongString) obj).values);
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		return Arrays.hashCode(values);
	}

	@Override
	public String toString()
	{
		return Arrays.toString(values);
	}
}
