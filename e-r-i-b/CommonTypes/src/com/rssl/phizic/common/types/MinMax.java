package com.rssl.phizic.common.types;

/**
 * @author Mescheryakova
 * @ created 26.10.2011
 * @ $Author$
 * @ $Revision$
 *
 * * ����� ��� �������� ������������ � ������������� ��������
 */

public class MinMax<T>
{
	private final T min; // ����������� ��������
	private final T max; // ������������ ��������

	/**
	 * @param min - ����������� ��������
	 * @param max - ������������ ��������
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
