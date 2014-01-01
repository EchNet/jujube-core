package org.terrane.core;

import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

public class PairTest
{
	@Test
    public void testHashEquality()
    {
		Set<Pair<Integer,Integer>> set = new HashSet<Pair<Integer,Integer>>();
		for (int i = 0; i < 10; ++i) {
			set.add(new Pair(0, 1));
			set.add(new Pair(0, 2));
			set.add(new Pair(0, 3));
			set.add(new Pair(1, 1));
			set.add(new Pair(2, 1));
			set.add(new Pair(3, 1));
		}
		assertEquals(6, set.size());
    }
}
