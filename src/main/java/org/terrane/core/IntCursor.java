package org.terrane.core;

public interface IntCursor
{
	/**
	 * Get the next value.
	 */
	public int next();

	/**
	 * Is there a next value?
	 */
	public boolean hasNext();

	/**
	 * Create a copy of this cursor.
	 */
	public IntCursor copy();
}
