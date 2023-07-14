package com.rssl.phizic.dataaccess.common.counters;

/**
 * @author Barinov
 * @ created 18.10.2011
 * @ $Author$
 * @ $Revision$
 */

public class Counter
{
	private final String name;
	private final long maxval;
	private final CounterNameGenerator generator;

	/**
	 * ������� ������� �������, �� ���������� �� ���������.
	 * @param name ��� ��������
	 * @return ������� �������
	 */
	public static Counter createSimpleCounter(String name)
	{
		return createSimpleCounter(name, Long.MAX_VALUE);
	}

	/**
	 * ������� ����������� �������.
	 * @param name ��� ��������
	 * @param maxValue ������������ ��������
	 * @return ������� �������
	 */
	public static Counter createSimpleCounter(String name, Long maxValue)
	{
		return createExtendedCounter(name, maxValue, CounterNameGenerator.SIMPLE);
	}

	/**
	 * ������� ����������� �������
	 * @param name ��� ��������
	 * @param generator ��������� ����� ��������
	 * @return �������
	 */
	public static Counter createExtendedCounter(String name, long maxval, CounterNameGenerator generator)
	{
		return new Counter(name, maxval, generator);
	}

	private Counter(String name, long maxval, CounterNameGenerator generator)
	{
		this.maxval = maxval;
		this.name = name;
		this.generator = generator;
	}

	/**
	 * @return ��� ��������
	 */
	public String getName()
	{
		return generator.generate(name);
	}

	/**
	 * @return ������������ �������� ��������
	 */
	public long getMaxval()
	{
		return maxval;
	}
}
