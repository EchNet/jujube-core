package org.terrane.core;

/**
 * Base class for an LongCursor type that is implemented in terms
 * of another cursor.
 */
public abstract class ProxyLongCursor
	extends CopyableLongCursor
	implements LongCursor
{
	private LongCursor inner;

	public ProxyLongCursor(LongCursor inner)
	{
		this.inner = inner;
	}

	/**
	 * @inheritDoc
	 */
	public boolean hasNext()
	{
		return inner.hasNext();
	}

	/**
	 * @inheritDoc
	 */
	public long next()
	{
		return inner.next();
	}

	/**
	 * @inheritDoc
	 */
	public LongCursor copy()
	{
		ProxyLongCursor copy = (ProxyLongCursor) super.copy();
		copy.inner = inner.copy();
		return copy;
	}
}
