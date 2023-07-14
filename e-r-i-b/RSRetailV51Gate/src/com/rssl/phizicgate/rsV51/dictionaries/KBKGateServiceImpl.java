package com.rssl.phizicgate.rsV51.dictionaries;

import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.dictionaries.KBKGateService;
import com.rssl.phizic.gate.dictionaries.KBKRecord;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizicgate.rsV51.data.GateRSV51Executor;

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
public class KBKGateServiceImpl extends AbstractService implements KBKGateService
{
	public KBKGateServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public List<KBKRecord> getAll(final int firstResult,final int maxResults) throws GateException, GateLogicException
	{
		try
		{

			return GateRSV51Executor.getInstance().execute(new HibernateAction<List<KBKRecord>>()
			   {
					public List<KBKRecord> run(Session session) throws Exception
					{
						 //noinspection unchecked
						 Query query = session
								   .getNamedQuery("GetKBK");
						//noinspection unchecked
						query.setFirstResult(firstResult);
						query.setMaxResults(maxResults);
						//noinspection unchecked
						List<KBKRecord> result = query.list();
						return result == null? new ArrayList<KBKRecord>(): result;
					}
			});
		}
		catch (Exception ex)
		{
			throw new GateException(ex);
		}
	}

	public	List<KBKRecord> getAll(final KBKRecord template, final int firstResult, final int listLimit) throws GateException
	{
		try
		{
		    final String code = template == null ? null : template.getCode();
			final Long superior = template==null ? null : template.getSuperior();
			return GateRSV51Executor.getInstance().execute(new HibernateAction<List<KBKRecord>>()
			   {
					public List<KBKRecord> run(Session session) throws Exception
					{
						 //noinspection unchecked
						 Query query = session
								   .getNamedQuery("GetKBKByTemplate");
						//noinspection unchecked
						query.setFirstResult(firstResult);
						query.setMaxResults(listLimit);
						query.setParameter("code", code);
						query.setParameter("superior", superior);
						//noinspection unchecked
						List<KBKRecord> result = query.list();
						return result == null? new ArrayList<KBKRecord>(): result;
					}
			});
		}
		catch (Exception ex)
		{
			throw new GateException(ex);
		}
	}
}

