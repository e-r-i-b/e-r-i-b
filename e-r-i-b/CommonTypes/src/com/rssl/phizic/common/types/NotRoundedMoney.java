package com.rssl.phizic.common.types;

import java.math.BigDecimal;

/** тип Money, учитывающий доли копейки
 * @author akrenev
 * @ created 23.11.2011
 * @ $Author$
 * @ $Revision$
 */
public class NotRoundedMoney extends Money
{
	private BigDecimal centPartValue = BigDecimal.ZERO;  // дробная часть

	public NotRoundedMoney()
	{}

	public NotRoundedMoney(Money from)
	{
		super(from);
		if (from instanceof NotRoundedMoney)
		{
			NotRoundedMoney money = (NotRoundedMoney) from;
			centPartValue = centPartValue.add(money.centPartValue);
		}
	}

	public NotRoundedMoney(BigDecimal val, Currency currency)
	{
		super(val, currency);
		centPartValue = val.subtract(super.getDecimal());
	}

	public NotRoundedMoney(long wholePart, long cents, Currency currency)
	{
		super(wholePart, cents, currency);
	}

	public BigDecimal getDecimal()
	{
		return super.getDecimal().add(centPartValue);
	}

	public int compareTo(Object o)
    {
        Money that = (Money) o;

	    int compareCurrencies = getCurrency().getCode().compareTo(that.getCurrency().getCode());

	    if(compareCurrencies != 0)
	        return compareCurrencies;

        return getDecimal().compareTo(that.getDecimal());
    }

	public static Money fromCents(long cents, Currency currency)
	{
		return new NotRoundedMoney(BigDecimal.valueOf(cents).divide(CENTS_DECIMAL), currency);
	}

	public boolean equals(Object o)
	{
		if ( this == o )
            return true;

        if ( o == null || !(o instanceof Money) )
            return false;

		final Money money = (Money) o;

		if (!getDecimal().equals(money.getDecimal()))
            return false;

        return getCurrency().getCode().equals(money.getCurrency().getCode());
	}
}
