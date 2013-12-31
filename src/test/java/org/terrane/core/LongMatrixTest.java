package org.terrane.core;

import java.util.Arrays;
import org.junit.*;
import static org.junit.Assert.*;

public class LongMatrixTest
{
	@Test
    public void testGetColumn()
    {
		LongMatrix m = new LongMatrix (2, 5, new long[] {
			0, 1, 2, 3, 4, 5, 6, 7, 8, 9
		});
		assertEquals(5, m.getColumns());
		assertEqual(new long[] { 0, 5 }, m.getColumn(0));
		assertEqual(new long[] { 1, 6 }, m.getColumn(1));
		assertEqual(new long[] { 2, 7 }, m.getColumn(2));
		assertEqual(new long[] { 3, 8 }, m.getColumn(3));
		assertEqual(new long[] { 4, 9 }, m.getColumn(4));
    }

	@Test
    public void testGetBackwardDiagonal()
    {
		LongMatrix m = new LongMatrix (2, 5, new long[] {
			0, 1, 2, 3, 4, 5, 6, 7, 8, 9
		});
		assertEquals(2, m.getRows());
		assertEqual(new long[] { 0, 1, 2, 3, 4 }, m.getRow(0));
		assertEqual(new long[] { 5, 6, 7, 8, 9 }, m.getRow(1));
    }

	@Test
    public void testGetForwardDiagonal()
    {
		LongMatrix m = new LongMatrix (2, 5, new long[] {
			0, 1, 2, 3, 4, 5, 6, 7, 8, 9
		});
		assertEquals(6, m.getDiagonals());
		assertEqual(new long[] { 4 }, m.getForwardDiagonal(0));
		assertEqual(new long[] { 3, 9 }, m.getForwardDiagonal(1));
		assertEqual(new long[] { 2, 8 }, m.getForwardDiagonal(2));
		assertEqual(new long[] { 1, 7 }, m.getForwardDiagonal(3));
		assertEqual(new long[] { 0, 6 }, m.getForwardDiagonal(4));
		assertEqual(new long[] { 5 }, m.getForwardDiagonal(5));
    }

	private void assertEqual(long[] expected, LongSequence seq)
	{
		LongCursor cursor = seq.cursor();
		for (int i = 0; i < expected.length; ++i) {
			assertTrue(cursor.hasNext());
			assertEquals(expected[i], cursor.next());
		}
		assertTrue(!cursor.hasNext());
	}
}
