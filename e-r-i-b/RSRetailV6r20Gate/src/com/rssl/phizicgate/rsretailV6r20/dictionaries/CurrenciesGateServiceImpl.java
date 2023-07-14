package com.rssl.phizicgate.rsretailV6r20.dictionaries;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.CurrenciesGateService;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizicgate.rsretailV6r20.data.RSRetailV6r20Executor;
import com.rssl.phizicgate.rsretailV6r20.money.CurrencyImpl;

import java.util.ArrayList;
import java.util.List;

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
		com.rssl.phizic.dataaccess.query.Query query = RSRetailV6r20Executor.getInstance().getNamedQuery("com.rssl.phizicgate.rsretailV6r20.money.CurrencyHelper.GetCurrencies");
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		//noinspection unchecked
		List<Currency> list = null;

		try
		{
			list = query.executeList();
		}
		catch (DataAccessException e)
		{
			throw new GateException(e);
		}

		int numRubCurrencies = 0;
		for (int i = 0; i < list.size(); i++)
		{
			CurrencyImpl currency = (CurrencyImpl) list.get(i);
			if (currency.getId() == 0)
			{
				currency.setCode("RUB");
				currency.setName("Рубли");
				currency.setNumber("643");
				numRubCurrencies++;
			}
			else
			{
				if (currency.getCode().equals("RUB") || currency.getCode().equals("RUR"))
				{
					numRubCurrencies++;
					list.remove(currency);
					i--;
				}
			}
		}
		if (numRubCurrencies > 1)
		{
			log.error("Найдено более 1-й рублёвой валюты!");
		}

		return list != null ? list : new ArrayList<Currency>();
	}

	public List getAll(DictionaryRecord template, int firstResult, int listLimit) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}
}
