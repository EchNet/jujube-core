package org.terrane.core;

/**
 * Default implementations of some IntCursor methods.
 */
public abstract class AbstractIntCursor
	implements IntCursor
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
	public IntCursor copy()
	{
		throw new UnsupportedOperationException();
	}
}
