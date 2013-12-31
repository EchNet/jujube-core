package org.terrane.core;

public interface LongCursor
{
	/**
	 * Get the next value and advance to the next position.
	 */
	public long next();

	/**
	 * Is there a next value?
	 */
	public boolean hasNext();

	/**
	 * Create a copy of this cursor.
	 */
	public LongCursor copy();
}
