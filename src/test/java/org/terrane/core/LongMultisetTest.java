package org.terrane.core;

import java.util.Iterator;
import org.junit.*;
import static org.junit.Assert.*;

public class LongMultisetTest
{
	@Test
	public void testConstructEmpty()
	{
		LongMultiset multiset = new LongMultiset();
		assertNotNull(multiset.entries());
		assertNotNull(multiset.entries().iterator());
		assertFalse(multiset.entries().iterator().hasNext());
	}

	@Test
	public void testConstructSingle()
	{
		LongMultiset multiset = new LongMultiset(5);
		Iterator<LongMultiset.Entry> cursor = multiset.entries().iterator();
		assertNotNull(cursor);
		assertTrue(cursor.hasNext());
		LongMultiset.Entry entry = cursor.next();
		assertNotNull(entry);
		assertEquals(5, entry.getValue());
		assertEquals(1, entry.getMultiplicity());
		assertFalse(cursor.hasNext());
	}

	@Test
	public void testConstructMultiple()
	{
		LongMultiset multiset = new LongMultiset(5, 2);
		Iterator<LongMultiset.Entry> cursor = multiset.entries().iterator();
		assertNotNull(cursor);
		assertTrue(cursor.hasNext());
		LongMultiset.Entry entry = cursor.next();
		assertNotNull(entry);
		assertEquals(5, entry.getValue());
		assertEquals(2, entry.getMultiplicity());
		assertFalse(cursor.hasNext());
	}

	@Test
	public void testAugmentSingle()
	{
		LongMultiset multiset = new LongMultiset(5);
		multiset = multiset.add(5);
		Iterator<LongMultiset.Entry> cursor = multiset.entries().iterator();
		assertNotNull(cursor);
		assertTrue(cursor.hasNext());
		LongMultiset.Entry entry = cursor.next();
		assertNotNull(entry);
		assertEquals(5, entry.getValue());
		assertEquals(2, entry.getMultiplicity());
		assertFalse(cursor.hasNext());
	}

	@Test
	public void testAugmentMultiple()
	{
		LongMultiset multiset = new LongMultiset(5, 2);
		multiset = multiset.add(5, 2);
		Iterator<LongMultiset.Entry> cursor = multiset.entries().iterator();
		assertNotNull(cursor);
		assertTrue(cursor.hasNext());
		LongMultiset.Entry entry = cursor.next();
		assertNotNull(entry);
		assertEquals(5, entry.getValue());
		assertEquals(4, entry.getMultiplicity());
		assertFalse(cursor.hasNext());
	}

	@Test
	public void testSorted()
	{
		LongMultiset multiset = new LongMultiset();
		multiset = multiset.add(8, 2);
		multiset = multiset.add(5);
		multiset = multiset.add(1);
		multiset = multiset.add(10, 2);
		multiset = multiset.add(2, 2);
		multiset = multiset.add(7);
		multiset = multiset.add(9);
		multiset = multiset.add(4, 2);
		multiset = multiset.add(3);
		multiset = multiset.add(6, 2);
		Iterator<LongMultiset.Entry> cursor = multiset.entries().iterator();
		assertNotNull(cursor);
		for (int i = 1; i <= 10; ++i) {
			assertTrue(cursor.hasNext());
			LongMultiset.Entry entry = cursor.next();
			assertNotNull(entry);
			assertEquals(i, entry.getValue());
			assertEquals(i % 2 == 0 ? 2 : 1, entry.getMultiplicity());
		}
		assertFalse(cursor.hasNext());
	}

	@Test
	public void testAddMultiple()
	{
		LongMultisetBuilder evens = new LongMultisetBuilder();
		LongMultisetBuilder odds = new LongMultisetBuilder();
		for (int i = 1; i <= 10; ++i) {
			(i % 2 == 0 ? evens : odds).add(i);
		}
		LongMultiset multiset = evens.produce();
		multiset = multiset.add(odds.produce());
		multiset = multiset.add(evens.produce());
		Iterator<LongMultiset.Entry> cursor = multiset.entries().iterator();
		assertNotNull(cursor);
		for (int i = 1; i <= 10; ++i) {
			assertTrue(cursor.hasNext());
			LongMultiset.Entry entry = cursor.next();
			assertNotNull(entry);
			assertEquals(i, entry.getValue());
			assertEquals(i % 2 == 0 ? 2 : 1, entry.getMultiplicity());
		}
		assertFalse(cursor.hasNext());
	}

	@Test
	public void testScale()
	{
		LongMultiset multiset = new LongMultiset();
		for (int i = 1; i <= 10; ++i) {
			multiset = multiset.add(i, i);
		}
		multiset = multiset.scale(2);
		Iterator<LongMultiset.Entry> cursor = multiset.entries().iterator();
		assertNotNull(cursor);
		for (int i = 1; i <= 10; ++i) {
			assertTrue(cursor.hasNext());
			LongMultiset.Entry entry = cursor.next();
			assertNotNull(entry);
			assertEquals(i, entry.getValue());
			assertEquals(i * 2, entry.getMultiplicity());
		}
		assertFalse(cursor.hasNext());
	}

	@Test
	public void testContains()
	{
		LongMultiset ms1 = new LongMultisetBuilder().add(1, 2).add(2).produce();
		LongMultiset ms2 = new LongMultisetBuilder().add(1).produce();
		LongMultiset ms3 = new LongMultisetBuilder().add(1).add(2, 2).produce();
		assertTrue(ms1.contains(ms2));
		assertFalse(ms2.contains(ms1));
		assertFalse(ms2.contains(ms3));
		assertTrue(ms3.contains(ms2));
		assertFalse(ms1.contains(ms3));
		assertFalse(ms3.contains(ms1));
	}

	@Test
	public void testSubsets()
	{
		LongMultiset ms = new LongMultisetBuilder().add(1, 2).add(2).produce();
		Iterator<LongMultiset> subsets = ms.subsets().iterator();
		assertEquals(new LongMultiset(), subsets.next());
		assertEquals(new LongMultiset(1), subsets.next());
		assertEquals(new LongMultiset(1, 2), subsets.next());
		assertEquals(new LongMultiset(2), subsets.next());
		assertEquals(new LongMultiset(1).add(2), subsets.next());
		assertEquals(new LongMultiset(1, 2).add(2), subsets.next());
		assertTrue(!subsets.hasNext());
	}
}
