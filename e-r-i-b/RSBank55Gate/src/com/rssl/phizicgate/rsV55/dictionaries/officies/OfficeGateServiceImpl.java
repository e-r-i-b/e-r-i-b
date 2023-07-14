package com.rssl.phizicgate.rsV55.dictionaries.officies;

import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.dictionaries.officies.OfficeGateService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizicgate.rsV55.data.GateRSV55Executor;

import org.hibernate.Session;
import org.hibernate.Query;

import java.util.List;
import javax.naming.NameNotFoundException;

/**
 * @author Omeliyanchuk
 * @ created 04.04.2008
 * @ $Author$
 * @ $Revision$
 */

//todo отладить
public class OfficeGateServiceImpl extends AbstractService implements OfficeGateService
{

		public OfficeGateServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public Office getOfficeById(final String id) throws GateException
	{
        try
        {
            return GateRSV55Executor.getInstance().execute(new HibernateAction<Office>()
            {
                public Office run(Session session) throws Exception
                {
                    Query query = session.getNamedQuery("com.rssl.phizicgate.rsV55.dictionaries.officies.findfById");
	                query.setParameter("fnCash",id);
	                return (Office)query.uniqueResult();
                }
            });
        }
        catch (Exception e)
        {
           throw new GateException(e);
        }
	}

	public List<Office> getAll(final Office template, int firstResult, int listLimit) throws GateException
	{
		try
		{
			return GateRSV55Executor.getInstance().execute(new HibernateAction<List<Office>>()
			{
				public List<Office> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizicgate.rsV55.dictionaries.officies.GetOfficies");
					String depName = "".equals(template.getName()) ? null : template.getName();
					query.setParameter("depName", depName);
					return (List<Office>) query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public List<Office> getAll(final int firstResult, final int maxResults) throws GateException
	{
		try
		{
			return GateRSV55Executor.getInstance().execute(new HibernateAction<List<Office>>()
			{
				public List<Office> run(Session session)
				{
					Query query = session.getNamedQuery("com.rssl.phizicgate.rsV55.dictionaries.officies.GetOfficies");
					query.setParameter("depName", null);
					query.setFirstResult(firstResult);
					query.setMaxResults(maxResults);
					return (List<Office>) query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}
}
