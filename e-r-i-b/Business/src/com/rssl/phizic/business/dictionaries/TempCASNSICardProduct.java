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
 * ����� ��� �������� ��������� ������ ��� �������� ����������� ���� ��� ���
 */

public class TempCASNSICardProduct
{
	private Calendar date; // ���� ����������� �������� �������
	private String name; // �������� ���������� ����
	private List<Currency> currencies = new ArrayList<Currency>(); // ������ ����� ��� ������� ���� ����

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
