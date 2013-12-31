package org.terrane.core;

import java.util.Arrays;

//
// ByteString is an immutable wrapper for an array of 8-bit integers, offering 
// Javascript-like slice and splice methods.  ByteString is usable as a hash key.
//
public class ByteString
{
	private final static byte[] EMPTY_ARRAY = new byte[0];

	private byte[] values;

	public ByteString()
	{
		this.values = EMPTY_ARRAY;
	}

	public ByteString(ByteString that)
	{
		this.values = that.values;
	}

	public ByteString(int length)
	{
		this.values = new byte[length];
	}

	public ByteString(byte[] ini)
	{
		this(ini, ini.length);
	}

	public ByteString(byte[] ini, int length)
	{
		values = new byte[length];
		System.arraycopy(ini, 0, values, 0, length);
	}

	public int length()
	{
		return values.length;
	}

	public byte get(int ix)
	{
		return values[ix];
	}

	public ByteString copy()
	{
		return wrap(values);
	}

	public byte[] copyBytes()
	{
		return sliceBytes(0);
	}

	public ByteString slice(int start)
	{
		return slice(start, values.length - start);
	}

	public byte[] sliceBytes(int start)
	{
		return sliceBytes(start, values.length - start);
	}

	public ByteString slice(int start, int length)
	{
		return wrap(sliceBytes(start, length));
	}

	public byte[] sliceBytes(int start, int length)
	{
		int newLength = Math.min(length, Math.max(values.length - start, 0));
		byte[] newValues = new byte[newLength];
		System.arraycopy(values, start, newValues, 0, newLength);
		return newValues;
	}

	public ByteString append(ByteString addl)
	{
		return append(addl.values);
	}

	public ByteString append(byte[] addl)
	{
		byte[] newValues = new byte[values.length + addl.length];
		System.arraycopy(values, 0, newValues, 0, values.length);
		System.arraycopy(addl, 0, newValues, values.length, addl.length);
		return wrap(newValues);
	}

	public ByteString splice(int start, int length)
	{
		return splice(start, length, EMPTY_ARRAY);
	}

	public ByteString splice(int start, int length, ByteString addl)
	{
		return splice(start, length, addl.values);
	}

	public ByteString splice(int start, int length, byte[] addl)
	{
		if (start < 0 || start >= values.length) throw new IllegalArgumentException();
		int tailStart = Math.min(start + length, values.length);
		int tailLength = values.length - tailStart;
		byte[] newValues = new byte[start + addl.length + tailLength];
		System.arraycopy(values, 0, newValues, 0, start);
		System.arraycopy(addl, 0, newValues, start, addl.length);
		System.arraycopy(values, tailStart, newValues, start + addl.length, tailLength);
		return wrap(newValues);
	}

	@Override
	public boolean equals(Object obj)
	{
		try {
			ByteString that = (ByteString) obj;
			return Arrays.equals(this.values, that.values);
		}
		catch (Exception e) {
			return false;
		}
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

	private ByteString wrap(byte[] values)
	{
		ByteString newState = new ByteString();
		newState.values = values;
		return newState;
	}
}
