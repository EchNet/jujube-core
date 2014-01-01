package org.terrane.core;

/**
 * Note: no overflow/underflow protection.
 */
public class ArithmeticIntSequence
	extends IntSequence
	implements Cloneable
{
	private int start;
	private int incr = 1;
	private boolean doStop;
	private int stop;

	public static ArithmeticIntSequence sequence()
	{
		return new ArithmeticIntSequence();
	}

	public ArithmeticIntSequence from(int start)
	{
		if (start != this.start) {
			ArithmeticIntSequence copy = copy();
			copy.start = start;
			return copy;
		}
		return this;
	}

	public ArithmeticIntSequence to(int stop)
	{
		if (!doStop || stop != this.stop) {
			ArithmeticIntSequence copy = copy();
			copy.doStop = true;
			copy.stop = stop;
			return copy;
		}
		return this;
	}

	public ArithmeticIntSequence by(int incr)
	{
		if (incr != this.incr) {
			if (incr == 0) {
				throw new IllegalArgumentException();
			}
			ArithmeticIntSequence copy = copy();
			copy.incr = incr;
			return copy;
		}
		return this;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public IntCursor cursor()
	{
		return doStop ? (incr > 0 ? new PosStopCursor() : new NegStopCursor()) : new Cursor();
	}

	private class Cursor extends CopyableIntCursor
	{
		int next = start;

		@Override
		public int next() {
			int v = next;
			next += incr;
			return v;
		}
	}

	private class PosStopCursor extends Cursor
	{
		@Override
		public boolean hasNext() {
			return next <= stop;
		}
	}

	private class NegStopCursor extends Cursor
	{
		@Override
		public boolean hasNext() {
			return next >= stop;
		}
	}

	private ArithmeticIntSequence copy()
	{
		try {
			return (ArithmeticIntSequence) clone();
		}
		catch (CloneNotSupportedException e) {
			throw new Error();
		}
	}
}
