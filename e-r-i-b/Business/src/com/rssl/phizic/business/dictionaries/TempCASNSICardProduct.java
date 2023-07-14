package com.rssl.phizic.business.dictionaries;

import com.rssl.phizic.common.types.Currency;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Mescheryakova
 * @ created 05.10.2011
 * @ $Author$
 * @ $Revision$
 *
 * Класс для хранения временных данных при парсинге справочника карт ЦАС НСИ
 */

public class TempCASNSICardProduct
{
	private Calendar date; // дата прекращения открытия вкладов
	private String name; // название карточного вида
	private List<Currency> currencies = new ArrayList<Currency>(); // список валют для данного вида карт

	public Calendar getDate()
	{
		return date;
	}

	public void setDate(Calendar date)
	{
		this.date = date;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public List<Currency> getCurrencies()
	{
		return currencies;
	}

	public void setCurrencies(List<Currency> currencies)
	{
		this.currencies = currencies;
	}

	public void addCurrencies(Currency currency)
	{
		this.currencies.add(currency);
	}
}
