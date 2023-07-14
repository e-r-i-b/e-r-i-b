package com.rssl.phizic.business.dictionaries.currencies;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.business.BusinessException;

/**
 @author Pankin
 @ created 08.02.2011
 @ $Author$
 @ $Revision$
 */
public class CurrencyUtils
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/**
	 * ���������� ������ �� ��������� ����.
	 *
	 * @param numericCode �������� ���.
	 * @return ������.
	 * @throws GateException
	 */
	public static Currency getCurrency(String numericCode) throws GateException
	{
		CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
		Currency currency = currencyService.findByNumericCode(numericCode);
		if (currency == null)
		{
			log.error("� ����������� �� ������� ������ � �������� ����� " + numericCode);
			return null;
		}
		return currency;
	}

	/**
	 * ��������� ����� ��������� ������ �� ISO ����
	 * @param currencyCode ��� ������
	 * @return ������ ��� null, ���� �� �������.
	 */
	public static Currency getCurrencyByISOCode(String currencyCode)
	{
		try
		{
			CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
			Currency currency = currencyService.findByAlphabeticCode(currencyCode);
			if (currency != null)
				return currency;

			log.error("� ����������� �� ������� ������ � ����� " + currencyCode);
		}
		catch (GateException e)
		{
			log.error("������ ��� ��������� ������ � ����� " + currencyCode, e);
			return null;
		}
		return null;
	}

	/**
	 * ��������� ���������� ISO ���� ������ �� ���������
	 * @param numericCode �������� ISO ��� ������
	 * @return ��������� ISO ���
	 */
	public static String getCurrencyCodeByNumericCode(String numericCode)
	{
		try
		{
			Currency currency = getCurrency(numericCode);
			return currency == null ? "" : currency.getCode();
		}
		catch (GateException e)
		{
			log.error("������ ��� ��������� ������ �� ��������� ���� " + numericCode, e);
			return "";
		}
	}

	/**
	 * ���������� ���������� ������.
	 *
	 * @param currency ������ ��� ������������.
	 * @return �������������� ������.
	 * @throws BusinessException
	 */
	public static Currency adaptCurrency(Currency currency) throws BusinessException
	{
		try
		{
			if (currency instanceof CurrencyImpl) //���� ������ ���������� ����������, �� ��� ������.
				return currency;
			else
			{
				return getCurrency(currency.getNumber());
			}
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}
}
