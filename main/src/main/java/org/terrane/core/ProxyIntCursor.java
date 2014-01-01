package org.terrane.core;

/**
 * Base class for an IntCursor type that is implemented in terms
 * of another cursor.
 */
public abstract class ProxyIntCursor
	extends CopyableIntCursor
	implements IntCursor
{
	private IntCursor inner;

	public ProxyIntCursor(IntCursor inner)
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
	public int next()
	{
		return inner.next();
	}

	/**
	 * @inheritDoc
	 */
	public IntCursor copy()
	{
		ProxyIntCursor copy = (ProxyIntCursor) super.copy();
		copy.inner = inner.copy();
		return copy;
	}
}
