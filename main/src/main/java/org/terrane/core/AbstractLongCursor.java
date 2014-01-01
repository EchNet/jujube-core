package org.terrane.core;

/**
 * Default implementations of some LongCursor methods.
 */
public abstract class AbstractLongCursor
	implements LongCursor
{
	/**
	 * @inheritDoc
	 */
	public boolean hasNext()
	{
		return true;
	}

	/**
	 * @inheritDoc
	 */
	public LongCursor copy()
	{
		throw new UnsupportedOperationException();
	}
}
