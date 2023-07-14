package com.rssl.phizic.common.types;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * User: Kidyaev
 * Date: 06.10.2005
 * Time: 18:24:37
 */
public class Money implements Comparable, Serializable
{
    private final static int CENTS = 100;
	protected final static BigDecimal CENTS_DECIMAL = BigDecimal.valueOf(CENTS);

    private long     value;
    private Currency currency;

    private Money(long val, Currency currency)
    {
	    if(currency == null)
	        throw new IllegalArgumentException("Money currency can't be null");
        this.value    = val;
        this.currency = currency;
    }

	public Money(){}

	/**
	 * Копирующий конструктор
	 * @param from
	 */
	public Money(Money from)
	{
		this(from.value, from.currency);
	}

	public Money(BigDecimal val, Currency currency)
	{
		this(valueFromBigDecimal(val), currency);
	}

	public Money(long wholePart, long cents, Currency currency)
	{
	    this(getValue(wholePart, cents), currency);
	}

	/**
	 * Создаёт деньгу из центов (копейки и т.п.)
	 * @param cents - центы
	 * @param currency - валюта
	 * @return деньга
	 */
	public static Money fromCents(long cents, Currency currency)
	{
		return new Money(cents, currency);
	}

    private static long getValue(long wholePart, long cents) {return wholePart * CENTS + cents;}

    public long getWholePart()
    {
        return value / CENTS;
    }

    public long getCents()
    {
        return value % CENTS;
    }

	/**
	 * @return сумму в копейках(центах, евроцентах)
	 */
	public long getAsCents()
	{
	    return value;
	}

    public BigDecimal getDecimal()
    {
        return BigDecimal.valueOf(value, 2);
    }

	/**
	 * можно использовать только в BeanHelper'е для работы с веб-сервисами.
	 * @param decimal
	 */
	@Deprecated
    public void setDecimal(BigDecimal decimal)
    {
	    value = decimal.unscaledValue().longValue();
    }

    public Currency getCurrency()
    {
        return currency;
    }

	/**
	 * можно использовать только в BeanHelper'е для работы с веб-сервисами.
	 * @param currency
	 */
	@Deprecated
	public void setCurrency(Currency currency)
	{
		this.currency = currency;
	}

    public Money add(Money val)
    {
        if(!this.currency.getCode().equals(val.currency.getCode()))
            throw new MoneyException("Операция невозможна для разных валют");

        return new Money(this.value + val.value, this.currency);
    }

	 public Money sub(Money val)
    {
        if(!this.currency.getCode().equals(val.currency.getCode()))
            throw new MoneyException("Операция невозможна для разных валют");

        return new Money(this.value - val.value, this.currency);
    }

    public Money add(long wholePart, long cents)
    {
        long val = getValue(wholePart, cents);
        return new Money(value + val, this.currency);
    }

    public Money multiply(double val)
    {
        double temp = value * val;
        return new Money(round(temp), currency);
    }

    private static long round(double temp)
    {
	    return Math.round(temp);
    }

	private static long valueFromBigDecimal(BigDecimal val)
	{
		return val.multiply(CENTS_DECIMAL).setScale(0, RoundingMode.HALF_UP).longValue();
	}

	public int compareTo(Object o)
    {
        Money that = (Money) o;

	    int compareCurrencies = this.currency.getCode().compareTo(that.getCurrency().getCode());

	    if(compareCurrencies != 0)
	        return compareCurrencies;

        if(this.value > that.value)
            return 1;

        if (this.value < that.value)
            return -1;

        return 0;
    }

	public boolean equals(Object o)
	{
		if ( this == o )
            return true;

        if ( o == null || !(o instanceof Money) )
            return false;

		final Money money = (Money) o;

		if ( value != money.value )
            return false;

        return currency.getCode().equals(money.currency.getCode());
	}

	public int hashCode()
	{
		int result;
		result = (int) (value ^ (value >>> 32));
		result = 29 * result + currency.hashCode();
		return result;
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.append("value", getDecimal())
				.append("currency", currency == null ? "" : currency.getCode())
				.toString();
	}

	/**
	 *
	 * Возвращает true в случае если сумма равна нулю, false в противном случае
	 *
	 * @return boolean
	 */
	public boolean isZero()
	{
		return value == 0;
    }
}