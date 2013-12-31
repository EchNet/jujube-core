package org.terrane.core;

/**
 * Default implementations of some LongCursor methods.
 */
public abstract class CopyableLongCursor
	extends AbstractLongCursor
	implements LongCursor, Cloneable
{
	/**
	 * @inheritDoc
	 */
	public LongCursor copy()
	{
		try {
			return (LongCursor) clone();
		}
		catch (CloneNotSupportedException e) {
			throw new Error("should not be reached");
		}
	}
}
