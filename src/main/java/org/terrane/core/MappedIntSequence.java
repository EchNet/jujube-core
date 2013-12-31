package org.terrane.core;

public class MappedIntSequence
	extends IntSequence
{
	private final IntSequence source;
	private final IntIntMapping mapping;

	/**
	 * Construct a new IntSequence by mapping the values of a source sequence.
	 */
	public MappedIntSequence(IntSequence source, IntIntMapping mapping)
	{
		this.source = source;
		this.mapping = mapping;
	}

	/**
	 * @inheritDoc
	 */
	public IntCursor cursor()
	{
		return new MappedCursor();
	}

	private class MappedCursor extends ProxyIntCursor
	{
		MappedCursor()
		{
			super(source.cursor());
		}

		@Override
		public int next()
		{
			return mapping.map(super.next());
		}
	}
}
