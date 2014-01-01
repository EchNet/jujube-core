package org.terrane.core.operator;

abstract public class BinaryOperator<T extends Number>
{
	abstract public T execute(T operand1, T operand2);
}
