package org.terrane.core;

import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

public class IntStringTest
{
	@Test
    public void testEqualsPositive()
    {
		IntString a1 = new IntString(new int[] { 9, 8, 7 });
		IntString a2 = new IntString(new int[] { 9, 8, 7 });
		assertEquals(a1, a2);
    }

	@Test
	public void testEqualsNegative()
	{
		IntString a1 = new IntString(new int[] { 9, 8, 7 });
		IntString a2 = new IntString(new int[] { 9, 8 });
		assertFalse(a1.equals(a2));
	}

	@Test
	public void testLengthConstructor()
	{
		IntString a1 = new IntString(new int[] { 0, 0, 0 });
		IntString a2 = new IntString(3);
		assertEquals(a1, a2);
	}

	@Test
	public void testLength()
	{
		IntString a1 = new IntString(new int[] { 0, 0, 0 });
		assertEquals(3, a1.length());
	}

	@Test
    public void testGet()
    {
		IntString a1 = new IntString(new int[] { 9, 8, 7 });
		assertEquals(9, a1.get(0));
		assertEquals(8, a1.get(1));
		assertEquals(7, a1.get(2));
    }

	@Test
	public void testCopy()
	{
		IntString a1 = new IntString(new int[] { 9, 8, 7 });
		IntString a2 = a1.copy();
		assertNotSame(a1, a2);
		assertEquals(a1, a2);
	}

	@Test
	public void testCopyInts()
	{
		int[] data = new int[] { 9, 8, 7 };
		IntString a1 = new IntString(data);
		assertArrayEquals(data, a1.copyInts());
		assertNotSame(data, a1.copyInts());
	}

	@Test
	public void testSlice1()
	{
		IntString a1 = new IntString(new int[] { 9, 8, 7 });
		IntString a2 = new IntString(new int[] { 8, 7 });
		assertEquals(a2, a1.slice(1));
	}

	@Test
	public void testSlice2()
	{
		IntString a1 = new IntString(new int[] { 9, 8, 7 });
		IntString a2 = new IntString(new int[] { 8, 7 });
		assertEquals(a2, a1.slice(1, 4));
	}

	@Test
	public void testSliceInts1()
	{
		IntString a1 = new IntString(new int[] { 9, 8, 7 });
		assertArrayEquals(new int[] { 8, 7 }, a1.sliceInts(1));
	}

	@Test
	public void testSliceInts2()
	{
		IntString a1 = new IntString(new int[] { 9, 8, 7, 6 });
		assertArrayEquals(new int[] { 7, 6 }, a1.sliceInts(2, 4));
	}

	@Test
	public void testAppend()
	{
		IntString a1 = new IntString(new int[] { 9, 8, 7 });
		IntString a2 = new IntString(new int[] { 6, 5, 4 });
		IntString a3 = new IntString(new int[] { 9, 8, 7, 6, 5, 4 });
		assertEquals(a3, a1.append(a2));
	}

	@Test
	public void testSplice1()
	{
		IntString a1 = new IntString(new int[] { 9, 8, 7, 6, 5, 4 });
		IntString a2 = new IntString(new int[] { 9, 8, 4 });
		assertEquals(a2, a1.splice(2, 3));
	}

	@Test
	public void testSplice2()
	{
		IntString a1 = new IntString(new int[] { 9, 8, 7, 6, 5, 4 });
		IntString a2 = new IntString(new int[] { 9, 8 });
		assertEquals(a2, a1.splice(2, 8));
	}

	@Test
	public void testSplice3()
	{
		IntString a1 = new IntString(new int[] { 9, 8, 5, 4 });
		IntString a2 = new IntString(new int[] { 7, 6 });
		IntString a3 = new IntString(new int[] { 9, 8, 7, 6, 5, 4 });
		assertEquals(a3, a1.splice(2, 0, a2));
	}

	@Test
	public void testSplice4()
	{
		IntString a1 = new IntString(new int[] { 9, 8, 100, 5, 4 });
		IntString a2 = new IntString(new int[] { 7, 6 });
		IntString a3 = new IntString(new int[] { 9, 8, 7, 6, 5, 4 });
		assertEquals(a3, a1.splice(2, 1, a2));
	}
}
