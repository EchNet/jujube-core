package org.terrane.core;

import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

public class RationalTest
{
	@Test
    public void testNaN()
    {
		Rational n = new Rational(865, 0);
		assertEquals(0, n.getNumerator());
		assertEquals(0, n.getDenominator());
		assertEquals(Rational.NaN, n);
		assertTrue(n.isNaN());
	}

	@Test
    public void testHashEquality()
    {
		Set<Rational> set = new HashSet<Rational>();
		for (int i = 0; i < 10; ++i) {
			set.add(new Rational(1, 1));
			set.add(new Rational(1, 2));
			set.add(new Rational(1, 3));
			set.add(new Rational(2, 1));
			set.add(new Rational(3, 1));
			set.add(new Rational(4, 1));
		}
		assertEquals(6, set.size());
    }
}
