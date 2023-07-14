package com.rssl.phizic.operations.ima;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.CurrencyRate;
import com.rssl.phizic.common.types.CurrencyRateType;

/**
 * @author rydvanskiy
 * @ created 04.07.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * курс покупки и продажи одной валюты в другую
 */

public class RateOfExchange
{

	/**
	 * Курс покупки
	 */
	private CurrencyRate buy;
	/**
	 * Курс продажи
	 */
	private CurrencyRate sale;
    /**
	 * Курс из
	 */
	private Currency fromCurrency;
	/**
	 * Курс в
	 */
	private Currency toCurrency;

	public RateOfExchange (CurrencyRate buy, CurrencyRate sale) throws IllegalArgumentException
	{
		if ( !buy.getFromCurrency().compare(sale.getFromCurrency()) ||
				!buy.getToCurrency().compare(sale.getToCurrency()))
		{
			throw new IllegalArgumentException("Крсы покупки и продажи не соответствуют");
		}

		if ( !(buy.getType() == CurrencyRateType.BUY || buy.getType() == CurrencyRateType.BUY_REMOTE) &&
				!(sale.getType() == CurrencyRateType.SALE || sale.getType() == CurrencyRateType.SALE_REMOTE) )
		{
			throw new IllegalArgumentException("Неверные типы курсов покупки и продажи");
		}

		this.buy = buy;
		this.sale = sale;
		this.toCurrency = buy.getToCurrency();
		this.fromCurrency = buy.getFromCurrency();

	}

	public CurrencyRate getBuy()
	{
		return buy;
	}

	public void setBuy(CurrencyRate buy)
	{
		this.buy = buy;
	}

	public CurrencyRate getSale()
	{
		return sale;
	}

	public void setSale(CurrencyRate sale)
	{
		this.sale = sale;
	}

	public Currency getFromCurrency()
	{
		return fromCurrency;
	}

	public void setFromCurrency(Currency fromCurrency)
	{
		this.fromCurrency = fromCurrency;
	}

	public Currency getToCurrency()
	{
		return toCurrency;
	}

	public void setToCurrency(Currency toCurrency)
	{
		this.toCurrency = toCurrency;
	}
}
