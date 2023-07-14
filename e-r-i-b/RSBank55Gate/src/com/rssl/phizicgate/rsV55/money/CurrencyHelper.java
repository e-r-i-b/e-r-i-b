package com.rssl.phizicgate.rsV55.money;

import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizicgate.rsV55.data.GateRSV55Executor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.HibernateException;

import java.util.List;

/**
 * @author Roshka
 * @ created 11.12.2006
 * @ $Author$
 * @ $Revision$
 */

public class CurrencyHelper
{
	/**
	 * �������� ������ �� ������� ��, id
	 * @param currencyId �� ������
	 * @return {@link com.rssl.phizic.common.types.Currency}
	 * @throws GateException
	 */
	public CurrencyImpl getCurrencyById(final Long currencyId) throws GateException
	{
		try
		{
			return GateRSV55Executor.getInstance().execute(new HibernateAction<CurrencyImpl>()
			{
				public CurrencyImpl run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizicgate.rsV55.money.CurrencyHelper.getCurrencyById");
					query.setParameter("currencyId", currencyId);
					return (CurrencyImpl) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * �������� ������ �� ����
	 * @param code
	 * @return
	 * @throws GateException
	 */
	public CurrencyImpl getCurrencyByCode(final String code) throws GateException
	{
		try
		{
			return GateRSV55Executor.getInstance().execute(new HibernateAction<CurrencyImpl>()
			{
				public CurrencyImpl run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizicgate.rsV55.money.CurrencyHelper.getCurrencyByCode");
					query.setParameter("code", code);
					return (CurrencyImpl) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * �������� ������ �����
	 * @return
	 * @throws GateException
	 */
	public List<CurrencyImpl> getCurrencies() throws GateException
	{
		try
		{
			return GateRSV55Executor.getInstance().execute(new HibernateAction<List<CurrencyImpl>>()
			{
				public List<CurrencyImpl> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizicgate.rsV55.money.CurrencyHelper.GetCurrencies");
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * �������� ISO-��� �� ������� �� �� ������
	 * @param currencyId �� ������ � �������
	 * @return ISO-���  ������
	 * @throws GateException
	 */
	public String getCurrencyCode(Long currencyId) throws GateException
	{
		Currency retailCurrency = getCurrencyById(currencyId);
		if(retailCurrency.getCode().equals("RUR"))
			return "RUB";
		return retailCurrency.getCode();
	}

	/**
	 * �������� �� ������ � ������� �� ISO ����
	 * @param code - ISO-���
	 * @return ������������� ������ � �������
	 */
	public Long getCurrencyId(String code)
	{
		CurrencyImpl retailCurrency;
		try
		{
			retailCurrency = getCurrencyByCode(code);
		}
		catch (GateException e)
		{
			throw new HibernateException(e);
		}

		return retailCurrency.getId();
	}

	/**
	 * ��������� �� ���������� ������ ������������ ��� retail
	 * @param currency
	 * @return	 
	 */
	public static Boolean isCurrency( Currency currency)
	{
		return currency.getExternalId().equals("0");
	}
}