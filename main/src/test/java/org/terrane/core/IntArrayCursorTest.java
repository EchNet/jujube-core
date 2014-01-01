package org.terrane.core;

import org.junit.*;
import static org.junit.Assert.*;

public class IntArrayCursorTest
{
	@Test
    public void testEmptyArray()
    {
		IntCursor cursor = new IntArrayCursor(new int[0]);
		assertFalse(cursor.hasNext());
		try {
			cursor.next();
			fail("should not be reached");
		}
		catch (java.util.NoSuchElementException e) {
		}
		assertFalse(cursor.hasNext());
	}

	@Test
	public void testIteration()
	{
		IntCursor cursor = new IntArrayCursor(new int[] { 11, 22, 33 });
		assertTrue(cursor.hasNext());
		assertEquals(11, cursor.next());
		assertTrue(cursor.hasNext());
		assertEquals(22, cursor.next());
		assertTrue(cursor.hasNext());
		assertEquals(33, cursor.next());
		assertFalse(cursor.hasNext());
	}

	@Test
	public void testCopy()
	{
		IntCursor cursor = new IntArrayCursor(new int[] { 11, 22, 33 });
		IntCursor cursor2 = cursor.copy();
		try {
			assertEquals(cursor.hasNext(), cursor2.hasNext());
			assertEquals(cursor.next(), cursor2.next());
		}
		catch (java.util.NoSuchElementException e) {
		}
	}
}
