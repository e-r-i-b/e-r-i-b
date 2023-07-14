package com.rssl.phizicgate.rsretailV6r4.money;

import com.rssl.phizic.gate.dictionaries.ReplicaSource;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizicgate.rsretailV6r4.data.RSRetailV6r4Executor;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;

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
		namedQuery = "com.rssl.phizicgate.rsretailV6r4.money.CurrencyHelper.GetCurrencies";
	}

	public void initialize(GateFactory factory) throws GateException
	{
	}

	public Iterator iterator() throws GateException
	{
		try
		{
			return RSRetailV6r4Executor.getInstance().execute(new HibernateAction<Iterator>()
			{
				public Iterator run(Session session)
				{
					Query query = session.getNamedQuery(namedQuery);
					List<CurrencyImpl> list = (List<CurrencyImpl>)query.list();
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
					return list.iterator();
				}
			});
		}
		catch(Exception e)
		{
			throw new GateException(e);
		}
	}

	public void close()
	{
		if(session == null)
            return;

        session.close();
        session = null;
	}
}
