package org.terrane.core;

/**
 * An IntCursor that copies by cloning.
 */
public abstract class CopyableIntCursor
	extends AbstractIntCursor
	implements IntCursor, Cloneable
{
	/**
	 * @inheritDoc
	 */
	public IntCursor copy()
	{
		try {
			return (IntCursor) clone();
		}
		catch (CloneNotSupportedException e) {
			throw new Error("should not be reached");
		}
	}
}
