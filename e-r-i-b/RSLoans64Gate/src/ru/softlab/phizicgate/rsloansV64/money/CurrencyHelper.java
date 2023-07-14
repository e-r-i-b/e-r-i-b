package ru.softlab.phizicgate.rsloansV64.money;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.common.types.Currency;
import org.hibernate.Session;
import org.hibernate.Query;
import org.hibernate.HibernateException;

import java.util.List;

import ru.softlab.phizicgate.rsloansV64.data.RSLoansV64Executor;

/**
 * @author Danilov
 * @ created 03.10.2007
 * @ $Author$
 * @ $Revision$
 */
public class CurrencyHelper
{
	/**
	 * �������� ������ �� ����� �� id
	 * @param currencyId �� ������
	 * @return {@link com.rssl.phizic.common.types.Currency}
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 */
	public CurrencyImpl getCurrencyById(final Long currencyId) throws GateException
	{
		try
		{
			return RSLoansV64Executor.getInstance().execute(new HibernateAction<CurrencyImpl>()
			{
				public CurrencyImpl run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("ru.softlab.phizicgate.rsloansV64.money.CurrencyHelper.getCurrencyById");
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
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 */
	public CurrencyImpl getCurrencyByCode(final String code) throws GateException
	{
		try
		{
			return RSLoansV64Executor.getInstance().execute(new HibernateAction<CurrencyImpl>()
			{
				public CurrencyImpl run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("ru.softlab.phizicgate.rsloansV64.money.CurrencyHelper.getCurrencyByCode");
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
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 */
	public List<CurrencyImpl> getCurrencies() throws GateException
	{
		try
		{
			return RSLoansV64Executor.getInstance().execute(new HibernateAction<List<CurrencyImpl>>()
			{
				public List<CurrencyImpl> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("ru.softlab.phizicgate.rsloansV64.money.CurrencyHelper.GetCurrencies");
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
	 * �������� ISO-��� �� ����� �� �� ������
	 * @param currencyId �� ������ � �����
	 * @return ISO-���  ������
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 */
	public String getCurrencyCode(Long currencyId) throws GateException
	{
		Currency loansCurrency = getCurrencyById(currencyId);
		if(loansCurrency.getCode().equals("RUR"))
			return "RUB";
		return loansCurrency.getCode();
	}

	/**
	 * �������� �� ������ � ����� �� ISO ����
	 * @param code - ISO-���
	 * @return ������������� ������ � �����
	 */
	public Long getCurrencyId(String code)
	{
		CurrencyImpl loansCurrency;
		try
		{
			loansCurrency = getCurrencyByCode(code);
		}
		catch (GateException e)
		{
			throw new HibernateException(e);
		}

		return loansCurrency.getId();
	}

	/**
	 * ��������� �� ���������� ������ ������������ ��� loans
	 * @param currency
	 * @return
	 */
	public static Boolean isCurrency( Currency currency)
	{
		return currency.getExternalId().equals("0");
	}
}
