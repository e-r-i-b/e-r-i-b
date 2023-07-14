package com.rssl.phizicgate.rsretailV6r4.dictionaries;

import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.dictionaries.KBKRecord;
import com.rssl.phizic.gate.dictionaries.KBKGateService;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizicgate.rsretailV6r4.data.RSRetailV6r4Executor;

import java.util.List;
import java.util.ArrayList;
import javax.naming.NameNotFoundException;

import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author: Pakhomova
 * @created: 07.05.2009
 * @ $Author$
 * @ $Revision$
 */
public class KBKGateServiceImpl extends AbstractService implements KBKGateService
{
	protected Session session;

	public KBKGateServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public List<KBKRecord> getAll(final int firstResult, final int maxResults) throws GateException, GateLogicException
	{
		try
		{

			return RSRetailV6r4Executor.getInstance().execute(new HibernateAction<List<KBKRecord>>()
			   {
					public List<KBKRecord> run(Session session) throws Exception
					{
						 //noinspection unchecked
						 Query query = session
								   .getNamedQuery("GetKBK");
						//noinspection unchecked
						query.setFirstResult(firstResult);
						query.setMaxResults(maxResults);
						 List<KBKRecord> kbks = query.list();
						return (kbks != null  ?  kbks  :  new ArrayList<KBKRecord>());
					}
			});
		}
		catch (Exception ex)
		{
			throw new GateException(ex);
		}
	}

	public	List<KBKRecord> getAll(KBKRecord template, int firstResult, int listLimit) throws GateException
	{
	   throw new UnsupportedOperationException();
	}
}
