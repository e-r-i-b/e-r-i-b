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
	 * Создает простой счетчик, со значениями по умолчанию.
	 * @param name имя счетчика
	 * @return Простой счетчик
	 */
	public static Counter createSimpleCounter(String name)
	{
		return createSimpleCounter(name, Long.MAX_VALUE);
	}

	/**
	 * Создает циклический счетчик.
	 * @param name имя счетчика
	 * @param maxValue максимальное значение
	 * @return Простой счетчик
	 */
	public static Counter createSimpleCounter(String name, Long maxValue)
	{
		return createExtendedCounter(name, maxValue, CounterNameGenerator.SIMPLE);
	}

	/**
	 * Создает расширенный счетчик
	 * @param name имя счетчика
	 * @param generator генератор имени счетчика
	 * @return счетчик
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
	 * @return имя счетчика
	 */
	public String getName()
	{
		return generator.generate(name);
	}

	/**
	 * @return максимальное значение счетчика
	 */
	public long getMaxval()
	{
		return maxval;
	}
}
