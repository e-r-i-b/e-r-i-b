package com.rssl.phizicgate.rsretailV6r4.dictionaries;

import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.dictionaries.CurrenciesGateService;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizicgate.rsretailV6r4.data.RSRetailV6r4Executor;
import com.rssl.phizicgate.rsretailV6r4.money.CurrencyImpl;

import java.util.List;
import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.Query;

/**
 * @author: Pakhomova
 * @created: 07.05.2009
 * @ $Author$
 * @ $Revision$
 */
public class CurrenciesGateServiceImpl extends AbstractService implements CurrenciesGateService
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	public CurrenciesGateServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public List<Currency> getAll(final int firstResult, final int maxResults) throws GateException, GateLogicException
	{
		try
		{

			List<CurrencyImpl> list = RSRetailV6r4Executor.getInstance().execute(new HibernateAction<List<CurrencyImpl>>()
			   {
					public List<CurrencyImpl> run(Session session) throws Exception
					{
						 //noinspection unchecked
						 Query query = session
								   .getNamedQuery("com.rssl.phizicgate.rsretailV6r4.money.CurrencyHelper.GetCurrencies");
						query.setFirstResult(firstResult);
						query.setMaxResults(maxResults);
						//noinspection unchecked
						return (List<CurrencyImpl>)query.list();
					}
			});

			int numRubCurrencies = 0;
			for(int i = 0; i < list.size(); i++)
			{
				CurrencyImpl currency = list.get(i);
				if(currency.getId() == 0)
				{
					currency.setCode("RUB");
					currency.setName("�����");
					currency.setNumber("643");
					numRubCurrencies++;
				}
				else
				{
					if(currency.getCode().equals("RUB") || currency.getCode().equals("RUR"))
					{
						numRubCurrencies++;
						list.remove(currency);
						i--;
					}
				}
			}
			if(numRubCurrencies > 1)
			{
				log.error("������� ����� 1-� ������� ������!");
			}

			return (List<Currency>)(list != null  ?  list  :  new ArrayList<Currency>());
		}
		catch (Exception ex)
		{
			throw new GateException(ex);
		}
	}

	public	List<DictionaryRecord> getAll(DictionaryRecord template, int firstResult, int listLimit) throws GateException
	{
	   throw new UnsupportedOperationException();
	}
}
