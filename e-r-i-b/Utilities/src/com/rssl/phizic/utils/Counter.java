package com.rssl.phizic.utils;

/**
 * —четчик
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
	 * @return текущее значение счетчика
	 */
	public long getValue()
	{
		return value;
	}

	/**
	 * ”становить текущее значение счетчика
	 * @param value новое текущее значение
	 */
	public void setValue(long value)
	{
		this.value = value;
	}

	/**
	 * инкремент
	 */
	public void increment()
	{
		value++;
	}

	/**
	 * декремент
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
