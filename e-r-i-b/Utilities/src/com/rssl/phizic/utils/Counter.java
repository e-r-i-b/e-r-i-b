package com.rssl.phizic.utils;

/**
 * �������
 * @author niculichev
 * @ created 14.05.2012
 * @ $Author$
 * @ $Revision$
 */
public class Counter
{
	private long value;

	public Counter(long value)
	{
		this.value = value;
	}

	/**
	 * @return ������� �������� ��������
	 */
	public long getValue()
	{
		return value;
	}

	/**
	 * ���������� ������� �������� ��������
	 * @param value ����� ������� ��������
	 */
	public void setValue(long value)
	{
		this.value = value;
	}

	/**
	 * ���������
	 */
	public void increment()
	{
		value++;
	}

	/**
	 * ���������
	 */
	public void decrement()
	{
		value--;
	}

	public String toString()
	{
		return Long.toString(value);
	}
}
