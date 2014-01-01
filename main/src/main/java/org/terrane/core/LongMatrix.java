package org.terrane.core;

/**
 * A simple matrix class.
 */
public class LongMatrix
{
	private int rows;
	private int columns;
	private long[] data;

	public LongMatrix(int rows, int columns)
	{
		this.rows = rows;
		this.columns = columns;
		this.data = new long[rows * columns];
	}

	/**
	 * Create a new Matrix using the given array, in row major order, as backing
	 * storage. 
	 */
	public LongMatrix(int rows, int columns, long[] data)
	{
		if (data.length != rows * columns) {
			throw new IllegalArgumentException("matrix data length error: expected " + (rows * columns) + ", got " + data.length);
		}
		this.rows = rows;
		this.columns = columns;
		this.data = data;
	}

	/**
	 * Initialize all the values of this matrix to the values produced by the given
	 * sequence, in row major order.
	 */
	public LongMatrix(int rows, int columns, LongSequence ini)
	{
		this(rows, columns, ini.limit(rows * columns).toArray());
	}

	/**
	 * Create a copy of the given matrix.
	 */
	public LongMatrix(LongMatrix matrix)
	{
		this(matrix.getRows(), matrix.getColumns(), matrix.values());
	}

	public int getRows()
	{
		return rows;
	}

	public int getColumns()
	{
		return columns;
	}

	public int getDiagonals()
	{
		return rows + columns - 1;
	}

	public LongSequence values()
	{
		return LongArray.wrap(data);
	}

	public boolean check(int r, int c)
	{
		return r >= 0 && r < rows && c >= 0 && c < columns;
	}

	public long get(int r, int c)
	{
		checkBounds(r, rows);
		checkBounds(c, columns);
		return data[r * columns + c];
	}

	public void set(int r, int c, long value)
	{
		checkBounds(r, rows);
		checkBounds(c, columns);
		data[r * columns + c] = value;
	}

	public LongSequence getRow(final int r)
	{
		checkBounds(r, getRows());
		return new LongSequence() {
			@Override
			public LongCursor cursor() {
				return new IndexedSequence(r, 0) {
					@Override
					protected void advance() {
						++c;
					}
				};
			}
		};
	}

	public LongSequence getColumn(final int c)
	{
		checkBounds(c, getColumns());
		return new LongSequence() {
			@Override
			public LongCursor cursor() {
				return new IndexedSequence(0, c) {
					@Override
					protected void advance() {
						++r;
					}
				};
			}
		};
	}

	public LongSequence getBackwardDiagonal(final int d)
	{
		checkBounds(d, getDiagonals());
		return new LongSequence() {
			@Override
			public LongCursor cursor() {
				return new IndexedSequence(Math.min(d, getRows() - 1), Math.max(0, d - getRows() + 1)) {
					@Override
					protected void advance() {
						--r;
						++c;
					}
				};
			}
		};
	}

	public LongSequence getForwardDiagonal(final int d)
	{
		checkBounds(d, getDiagonals());
		return new LongSequence() {
			@Override
			public LongCursor cursor() {
				return new IndexedSequence(Math.max(0, d - getColumns() + 1), Math.max(0, getColumns() - 1 - d)) {
					@Override
					protected void advance() {
						++r;
						++c;
					}
				};
			}
		};
	}

	private abstract class IndexedSequence
		implements LongCursor, Cloneable
	{
		int r, c;

		public IndexedSequence(int r0, int c0)
		{
			this.r = r0;
			this.c = c0;
		}

		@Override
		public boolean hasNext() {
			return check(r, c);
		}

		@Override
		public long next() {
			long val = data[r * columns + c];
			advance();
			return val;
		}

		@Override
		public LongCursor copy() {
			try {
				return (LongCursor) this.clone();
			}
			catch (CloneNotSupportedException e) {
				throw new Error(e);
			}
		}

		abstract protected void advance();
	};

	private static void checkBounds(int index, int limit)
	{
		if (index < 0 || index >= limit) {
			throw new ArrayIndexOutOfBoundsException(index + " not in (0.." + limit + "]");
		}
	}
}
