package org.terrane.core;

import org.junit.*;
import static org.junit.Assert.*;

public class BigRationalTest
{
	@Test
    public void testPlus()
    {
		assertEquals(new BigRational(1, 2), new BigRational(1, 4).plus(new BigRational(1, 4)));
    }

	@Test
    public void testTimes()
    {
		assertEquals(new BigRational(1, 3), new BigRational(1, 2).times(new BigRational(2, 3)));
    }
}
