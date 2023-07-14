package com.rssl.phizic.common.types;

/**
 * @author Mescheryakova
 * @ created 26.10.2011
 * @ $Author$
 * @ $Revision$
 *
 * * Класс для хранения минимального и максимального значений
 */

public class MinMax<T>
{
	private final T min; // минимальное значение
	private final T max; // максимальное значение

	/**
	 * @param min - минимальное значение
	 * @param max - максимальное значение
	 */
	public MinMax(T min, T max)
	{
		this.min = min;
		this.max = max;
	}

	public T getMin()
	{
		return min;
	}

	public T getMax()
	{
		return max;
	}
}
