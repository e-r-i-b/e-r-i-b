package com.rssl.phizicgate.esberibgate.mock.rate;

import java.math.BigDecimal;
import java.util.Random;

/**
 * @author gulov
 * @ created 01.10.2010
 * @ $Authors$
 * @ $Revision$
 */

/**
 * Формирование курсов валюты по определенному алгоритму
 */
class MockCurrencyRate
{
	/**
	 * Добавление или вычитание для удаленных курсов валют
	 */
	private static final int ADD_TO_REMOTE_RATE = 1;

	/**
	 * Массив возможных значений масштаба
	 */
	private static final int[] RANDOM_QUOTIENT = {1, 2, 3};

	/**
	 * Начальная база для определения курсов
	 */
	private int base;

	/**
	 * Код валюты (EUR", "USD")
	 */
	private String currencyCode;

	public MockCurrencyRate(int base, String currencyCode)
	{
		this.base = base;
		this.currencyCode = currencyCode;
	}

	public String getCurrencyCode()
	{
		return currencyCode;
	}

	public BigDecimal getBuyRate()
	{
		return (new BigDecimal(base - getRandomValue())).setScale(4, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getSaleRate()
	{
		return (new BigDecimal(base + getRandomValue())).setScale(4, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getCBRate()
	{
		return (new BigDecimal(base + (getRandomValue() / 2))).setScale(4, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getBuyRemoteRate()
	{
		return getBuyRate().subtract(new BigDecimal(ADD_TO_REMOTE_RATE)).setScale(4, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getSaleRemoteRate()
	{
		return getSaleRate().add(new BigDecimal(ADD_TO_REMOTE_RATE)).setScale(4, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getQuotient()
	{
		return new BigDecimal(RANDOM_QUOTIENT[new Random().nextInt(3)]).setScale(4, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getDelta()
	{
		return (new BigDecimal(getRandomValue() / 2)).setScale(4, BigDecimal.ROUND_HALF_UP);
	}

	private double getRandomValue()
	{
		return new Random().nextDouble();
	}
}
