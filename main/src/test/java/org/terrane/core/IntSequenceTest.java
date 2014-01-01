package org.terrane.core;

import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

public class IntSequenceTest
{
	@Test
    public void testMapping()
    {
		int[] orig = { 5, 3, 0, -4 };
		int[] expected = { 10, 6, 0, -8 };
		int[] array = IntArray.wrap(orig).map(new IntIntMapping() {
			@Override
			public int map(int value) {
				return value * 2;
			}
		}).toArray();
		assertTrue(Arrays.toString(array), Arrays.equals(expected, array));
    }

	@Test
    public void testMappingCursorCopy()
    {
		int[] orig = { 5, 3, 0, -4, 10, 6, 0, -8 };
		IntSequence seq = IntArray.wrap(orig).map(new IdentityIntIntMapping());
		testMappingCursorCopy(seq.cursor(), orig, 0);
    }

	private void testMappingCursorCopy(IntCursor cursor, int[] orig, int index)
	{
		int value = cursor.next();
		assertEquals(orig[index], value);
		if (cursor.hasNext()) {
			testMappingCursorCopy(cursor.copy(), orig, index + 1);
			testMappingCursorCopy(cursor, orig, index + 1);
		}
	}
}
