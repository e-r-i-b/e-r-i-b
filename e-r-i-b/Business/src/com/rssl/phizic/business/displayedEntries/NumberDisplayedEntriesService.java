package com.rssl.phizic.business.displayedEntries;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * @author komarov
 * @ created 07.02.2012
 * @ $Author$
 * @ $Revision$
 */

public class NumberDisplayedEntriesService
{
	private static final SimpleService service = new SimpleService();

	public List<NumberDisplayedEntry> getNumberDisplayedEntriesByLogin(final CommonLogin login) throws BusinessException
	{
		try
		{
			return  HibernateExecutor.getInstance().execute( new HibernateAction<List<NumberDisplayedEntry>>()
			{
				public List<NumberDisplayedEntry> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.displayedEntries.getNumberDisplayedEntriesByLoginId");
					query.setParameter("loginId", login.getId());
					return (List<NumberDisplayedEntry>)query.list();
				}
			});
		}
		catch(Exception e)
		{
		  throw new BusinessException(e);
		}

	}

	public NumberDisplayedEntry addOrOpdateNumberDisplayedEntry(NumberDisplayedEntry entry) throws BusinessException
	{
		return service.addOrUpdate(entry);
	}
}
