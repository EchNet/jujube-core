package org.terrane.core;

import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

public class ArithmeticIntSequenceTest
{
	@Test
    public void testDefault()
    {
		int[] array = new ArithmeticIntSequence().limit(5).toArray();
		assertTrue(Arrays.equals(new int[] { 0, 1, 2, 3, 4 }, array));
    }

	@Test
    public void testStartAtShortCircuit()
    {
		ArithmeticIntSequence s1 = new ArithmeticIntSequence();
		ArithmeticIntSequence s2 = s1.from(0);
		assertTrue(s1 == s2);
    }

	@Test
    public void testStartAt()
    {
		int[] array = new ArithmeticIntSequence().from(2).limit(5).toArray();
		assertTrue(Arrays.equals(new int[] { 2, 3, 4, 5, 6 }, array));
    }

	@Test
    public void testIncrementByShortCircuit()
    {
		ArithmeticIntSequence s1 = new ArithmeticIntSequence();
		ArithmeticIntSequence s2 = s1.by(1);
		assertTrue(s1 == s2);
    }

	@Test
    public void testIncrementBy()
    {
		int[] array = new ArithmeticIntSequence().by(-2).limit(4).toArray();
		assertTrue(Arrays.equals(new int[] { 0, -2, -4, -6 }, array));
    }

	@Test
    public void testStopAtAscending()
    {
		int[] array = new ArithmeticIntSequence().to(7).by(2).toArray();
		assertTrue(Arrays.toString(array), Arrays.equals(new int[] { 0, 2, 4, 6 }, array));
    }

	@Test
    public void testStopAtDescending()
    {
		int[] array = new ArithmeticIntSequence().to(-3).by(-1).toArray();
		assertTrue(Arrays.toString(array), Arrays.equals(new int[] { 0, -1, -2, -3 }, array));
    }
}
