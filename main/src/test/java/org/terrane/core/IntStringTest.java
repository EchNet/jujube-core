package org.terrane.core;

import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

public class IntStringTest
{
	@Test
	public void testDefaultConstructor()
	{
		IntString s = new IntString();
		assertEquals(0, s.length());
	}

	@Test
	public void testLengthWithArrayConstr()
	{
		IntString s = new IntString(new int[] { 0, 0, 0, 0 });
		assertEquals(4, s.length());
	}

	@Test
	public void testLengthWithVarargsConstr()
	{
		IntString s = new IntString(0, 0, 0, 0);
		assertEquals(4, s.length());
	}

	@Test
    public void testGet()
    {
		IntString a1 = new IntString(-9, -8, -7);
		assertEquals(-9, a1.get(0));
		assertEquals(-8, a1.get(1));
		assertEquals(-7, a1.get(2));
    }

	@Test
    public void testGetOutOfBounds()
    {
		IntString s = new IntString(new int[] { 8, 7 });
		for (int oob : new int[] { -2, -1, 2, 3 }) {
			try {
				s.get(oob);
				fail("should not be reached");
			}
			catch (ArrayIndexOutOfBoundsException e) {
				// expected
			}
		}
    }

	@Test
	public void testSequence()
	{
		IntString s = new IntString(8, 7, 6);
		IntCursor cursor = s.cursor();
		assertTrue(cursor.hasNext());
		assertEquals(8, cursor.next());
		assertTrue(cursor.hasNext());
		assertEquals(7, cursor.next());
		assertTrue(cursor.hasNext());
		assertEquals(6, cursor.next());
		assertFalse(cursor.hasNext());
	}

	@Test
	public void testGetValues()
	{
		int[] data = new int[] { 9, 8, 7 };
		IntString s = new IntString(data);
		assertArrayEquals(data, s.getValues());
		assertNotSame(data, s.getValues());
	}

	@Test
    public void testEqualsPositive()
    {
		int[] data = new int[] { 10, 20, 30, 40, 50 };
		assertEquals(new IntString(data), new IntString(data));
    }

	@Test
	public void testEqualsNegativeDiffValues()
	{
		IntString s1 = new IntString(10);
		IntString s2 = new IntString(20);
		assertFalse(s1.equals(s2));
	}

	@Test
	public void testEqualsNegativeDiffValuesPositiveOffset()
	{
		IntString s1 = new IntString(0, 10);
		IntString s2 = new IntString(0, 20);
		assertFalse(s1.equals(s2));
	}

	@Test
	public void testEqualsNegativeDiffLengths()
	{
		IntString s1 = new IntString(0, 10);
		IntString s2 = new IntString(0, 10, 20);
		assertFalse(s1.equals(s2));
	}

	@Test
	public void testConcatArray()
	{
		int[] a1 = new int[] { 9, 8, 7, 6 };
		int[] a2 = new int[] { 5, 4, 3, 2, 1 };
		int[] expected = new int[] { 9, 8, 7, 6, 5, 4, 3, 2, 1 };
		assertEquals(new IntString(expected), new IntString(a1).concat(a2));
	}

	@Test
	public void testConcatArrays()
	{
		int[] a1 = new int[] { 9, 8, 7, 6 };
		int[] a2 = new int[] { 5, 4, 3, 2, 1 };
		int[] expected = new int[] { 9, 8, 7, 6, 5, 4, 3, 2, 1 };
		assertEquals(new IntString(expected), new IntString().concat(a1, a2, new int[0]));
	}

	@Test
	public void testConcatString()
	{
		IntString a1 = new IntString(9, 8, 7);
		IntString a2 = new IntString(6, 5, 4);
		IntString expected = new IntString(9, 8, 7, 6, 5, 4);
		assertEquals(expected, a1.concat(a2));
	}

	@Test
	public void testConcatStrings()
	{
		IntString a1 = new IntString(9, 8, 7);
		IntString a2 = new IntString(6, 5, 4);
		IntString expected = new IntString(9, 8, 7, 6, 5, 4);
		assertEquals(expected, new IntString().concat(a1, new IntString(), a2));
	}

	@Test
	public void testSliceOneArg()
	{
		IntString s = new IntString(9, 8, 7);
		assertEquals(new IntString(9, 8, 7), s.slice(0));
		assertEquals(new IntString(8, 7), s.slice(1));
		assertEquals(new IntString(7), s.slice(2));
		assertEquals(new IntString(), s.slice(3));
	}

	@Test
	public void testSliceOneArgOutOfBounds()
	{
		IntString s = new IntString(new int[] { 8, 7 });
		for (int oob : new int[] { -2, -1, 2, 3 }) {
			try {
				s.get(oob);
				fail("should not be reached");
			}
			catch (ArrayIndexOutOfBoundsException e) {
				// expected
			}
		}
	}

	@Test
	public void testSliceTwoArgs()
	{
		IntString s = new IntString(9, 8, 7, 6, 5, 4, 3, 2, 1);
		assertEquals(new IntString(9, 8, 7), s.slice(0, 3));
		assertEquals(new IntString(8, 7), s.slice(1, 3));
		assertEquals(new IntString(7), s.slice(2, 3));
		assertEquals(new IntString(), s.slice(3, 3));
	}
}
