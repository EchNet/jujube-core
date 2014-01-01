package org.terrane.core;

import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

public class IntStringBufferTest
{
	@Test
	public void testDefaultConstructor()
	{
		IntStringBuffer sbuf = new IntStringBuffer();
		assertEquals(0, sbuf.length());
		assertEquals(new IntString(), sbuf.toIntString());
	}

	@Test
	public void testInitStringConstructor()
	{
		IntString s = new IntString(1, 1, 2, 2, 3);
		IntStringBuffer sbuf = new IntStringBuffer(s);
		assertEquals(5, sbuf.length());
		assertEquals(s, sbuf.toIntString());
	}

	@Test
	public void testAppendFromNada()
	{
		IntStringBuffer sbuf = new IntStringBuffer().append(45);
		assertEquals(new IntString(45), sbuf.toIntString());
	}

	@Test
	public void testAppendFromSomething()
	{
		IntStringBuffer sbuf = new IntStringBuffer(new IntString(15));
		assertEquals(new IntString(15, 45), sbuf.append(45).toIntString());
		assertEquals(new IntString(15, 45, 0), sbuf.append(new int[1]).toIntString());
	}

	@Test
	public void testAppends()
	{
		IntStringBuffer sbuf = new IntStringBuffer().append(1, 2, 3).append(4, 5, 6);
		assertEquals(new IntString(1, 2, 3, 4, 5, 6), sbuf.toIntString());
	}

	@Test
	public void testSpliceAllOut()
	{
		IntStringBuffer sbuf = new IntStringBuffer().append(1, 2, 3, 4, 5);
		assertEquals(new IntString(), sbuf.splice(0, 5).toIntString());
	}

	@Test
	public void testSpliceOut()
	{
		IntStringBuffer sbuf = new IntStringBuffer().append(1, 2, 3, 4, 5);
		assertEquals(new IntString(1, 2, 4, 5), sbuf.splice(2, 1).toIntString());
		assertEquals(new IntString(1, 2, 4), sbuf.splice(3, 1).toIntString());
		assertEquals(new IntString(2, 4), sbuf.splice(0, 1).toIntString());
	}

	@Test
	public void testSpliceIn()
	{
		IntStringBuffer sbuf = new IntStringBuffer();
		assertEquals(new IntString(5), sbuf.splice(0, 0, 5).toIntString());
		assertEquals(new IntString(1, 2, 5), sbuf.splice(0, 0, 1, 2).toIntString());
		assertEquals(new IntString(1, 2, 3, 4, 5), sbuf.splice(2, 0, 3, 4).toIntString());
	}

	@Test
	public void testSpliceReplace()
	{
		IntStringBuffer sbuf = new IntStringBuffer().append(0, 1, 0, 1, 0, 1, 0, 1);
		assertEquals(new IntString(1, 0, 1), sbuf.splice(0, 6, 1).toIntString());
		assertEquals(new IntString(1, 2, 2, 2, 2, 2, 1), sbuf.splice(1, 1, 2, 2, 2, 2, 2).toIntString());
		assertEquals(new IntString(1, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3), sbuf.splice(6, 1, 3, 3, 3, 3, 3).toIntString());
		assertEquals(new IntString(1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3), sbuf.splice(0, 1, 1, 1, 1, 1, 1).toIntString());
	}

	@Test
	public void testSort()
	{
		IntStringBuffer sbuf = new IntStringBuffer().append(2, 3, -1, 5, 0, 3, 8, 8);
		assertEquals(new IntString(-1, 0, 3, 3, 5, 8, 8), sbuf.splice(0, 1).sort().toIntString());
	}

	@Test
	public void testReverse()
	{
		IntStringBuffer sbuf = new IntStringBuffer().append(2, 3, -1, 5, 0, 3, 8, 8);
		assertEquals(new IntString(8, 8, 3, 0, 5), sbuf.splice(0, 3).reverse().toIntString());
	}
}
