package com.rssl.phizicgate.rsretailV6r20.money;

import com.rssl.phizic.gate.dictionaries.ReplicaSource;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizicgate.rsretailV6r20.data.RSRetailV6r20Executor;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;
import javax.naming.NameNotFoundException;

/**
 * @author Gainanov
 * @ created 17.04.2007
 * @ $Author$
 * @ $Revision$
 */
public class CurrencyReplicaSource implements ReplicaSource
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);
	private Session session;
    private String namedQuery;

	public CurrencyReplicaSource()
	{
		namedQuery = "com.rssl.phizicgate.rsretailV6r20.money.CurrencyHelper.GetCurrencies";
	}

	public void initialize(GateFactory factory) throws GateException
	{
	}

	public Iterator iterator() throws GateException
	{
		if(session != null)
            throw new RuntimeException("Обнаружена незакрытая сессия, вызовите метод close()!");

		try
		{
			//noinspection HibernateResourceOpenedButNotSafelyClosed
			session = RSRetailV6r20Executor.getSessionFactory().openSession();
		}
		catch (NameNotFoundException e)
		{
			throw new GateException(e);
		}

		Query query = session.getNamedQuery(namedQuery);
		List<CurrencyImpl> list = (List<CurrencyImpl>)query.list();
		int numRubCurrencies = 0;
		for(int i = 0; i < list.size(); i++)
		{
			CurrencyImpl currency = list.get(i);
			if(currency.getId() == 0)
			{
				currency.setCode("RUB");
				currency.setName("Рубли");
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
			log.error("Найдено более 1-й рублёвой валюты!");
		}
		return list.iterator();
	}

	public void close()
	{
		if(session == null)
            return;

        session.close();
        session = null;
	}
}
