package org.terrane.core;

public class MappedLongSequence
	extends LongSequence
{
	private final LongSequence source;
	private final LongLongMapping mapping;

	/**
	 * Construct a new LongSequence by mapping the values of a source sequence.
	 */
	public MappedLongSequence(LongSequence source, LongLongMapping mapping)
	{
		this.source = source;
		this.mapping = mapping;
	}

	/**
	 * @inheritDoc
	 */
	public LongCursor cursor()
	{
		return new MappedCursor();
	}

	private class MappedCursor extends ProxyLongCursor
	{
		MappedCursor()
		{
			super(source.cursor());
		}

		@Override
		public long next()
		{
			return mapping.map(super.next());
		}
	}
}
