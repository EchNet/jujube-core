package org.terrane.core;

public class Pair<A,B>
{
	public A a;
	public B b;

	public Pair()
	{
	}

	public Pair(A a, B b)
	{
		this.a = a;
		this.b = b;
	}

	public Pair(Pair<A,B> that)
	{
		this(that.a, that.b);
	}

	public boolean equals (Object obj)
	{
		try {
			return equalsPair((Pair<?,?>) obj);
		}
		catch (Exception e) {
			return false;
		}
	}

	public boolean equalsPair (Pair<?,?> that)
	{
		return a.equals(that.a) && b.equals(that.b);
	}

	public int hashCode()
	{
		return a.hashCode() * 29 + b.hashCode();
	}

	public String toString()
	{
		return a + "," + b;
	}
}
