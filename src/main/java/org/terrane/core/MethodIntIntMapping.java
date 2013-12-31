package org.terrane.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodIntIntMapping
	implements IntIntMapping
{
	private Object obj;
	private Method method;

	public MethodIntIntMapping(Object obj, String methodName)
		throws NoSuchMethodException
	{
		this.obj = obj;
		this.method = obj.getClass().getMethod(methodName, int.class);
	}

	@Override
	public int map(int value)
	{
		try {
			return ((Number) method.invoke(obj, value)).intValue();
		}
		catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		catch (InvocationTargetException e) {
			throw new RuntimeException(e.getTargetException());
		}
	}
}
