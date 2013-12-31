package org.terrane.core;

import java.util.Arrays;

//
// IntString is an immutable wrapper for an array of 32-bit integers, offering 
// IntArray behavior as well as Javascript-like slice and splice methods.  IntString
// is usable as a hash key.
//
public class IntString
	extends IntArray
{
	private final static int[] EMPTY_ARRAY = new int[0];

	private int[] values;

	public IntString()
	{
		this.values = EMPTY_ARRAY;
	}

	public IntString(IntString that)
	{
		this.values = that.values;
	}

	public IntString(int length)
	{
		this.values = new int[length];
	}

	public IntString(int[] ini)
	{
		this(ini, ini.length);
	}

	public IntString(int[] ini, int length)
	{
		values = new int[length];
		System.arraycopy(ini, 0, values, 0, length);
	}

	private IntString(boolean marker, int[] values)
	{
		this.values = values;
	}

	public int length()
	{
		return values.length;
	}

	public int get(int ix)
	{
		return values[ix];
	}

	public IntString copy()
	{
		return new IntString(false, values);
	}

	public int[] copyInts()
	{
		return sliceInts(0);
	}

	public IntString slice(int start)
	{
		return slice(start, values.length - start);
	}

	public int[] sliceInts(int start)
	{
		return sliceInts(start, values.length - start);
	}

	public IntString slice(int start, int length)
	{
		return new IntString(false, sliceInts(start, length));
	}

	public int[] sliceInts(int start, int length)
	{
		int newLength = Math.min(length, Math.max(values.length - start, 0));
		int[] newValues = new int[newLength];
		System.arraycopy(values, start, newValues, 0, newLength);
		return newValues;
	}

	public IntString append(IntString addl)
	{
		return append(addl.values);
	}

	public IntString append(int[] addl)
	{
		int[] newValues = new int[values.length + addl.length];
		System.arraycopy(values, 0, newValues, 0, values.length);
		System.arraycopy(addl, 0, newValues, values.length, addl.length);
		return new IntString(false, newValues);
	}

	public IntString splice(int start, int length)
	{
		return splice(start, length, EMPTY_ARRAY);
	}

	public IntString splice(int start, int length, IntString addl)
	{
		return splice(start, length, addl.values);
	}

	public IntString splice(int start, int length, int[] addl)
	{
		if (start < 0 || start >= values.length) throw new IllegalArgumentException();
		int tailStart = Math.min(start + length, values.length);
		int tailLength = values.length - tailStart;
		int[] newValues = new int[start + addl.length + tailLength];
		System.arraycopy(values, 0, newValues, 0, start);
		System.arraycopy(addl, 0, newValues, start, addl.length);
		System.arraycopy(values, tailStart, newValues, start + addl.length, tailLength);
		return new IntString(false, newValues);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof IntString) {
			return Arrays.equals(this.values, ((IntString) obj).values);
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
