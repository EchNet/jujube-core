package org.terrane.core;

import org.junit.*;
import static org.junit.Assert.*;

public class MethodIntIntMappingTest
{
	@Test
    public void testStaticMethod() throws Exception
    {
		assertEquals(10, new MethodIntIntMapping(this, "twice").map(5));
    }

	public static int twice(int value)
	{
		return value * 2;
	}
}
