package com.rssl.phizic.business.timeoutsessionrequest;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.utils.RandomHelper;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.Session;
import org.hibernate.Query;

/**
 * @author belyi
 * @ created 18.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class TimeoutSessionService
{
	private static final String QUERY_PREFIX = TimeoutSession.class.getName() + ".";

	private static final SimpleService simpleService = new SimpleService();

	public TimeoutSession addOrUpdate(final TimeoutSession timeoutSession) throws BusinessException
	{
		if (timeoutSession.getRandomRecordId() == null)
			timeoutSession.setRandomRecordId(RandomHelper.rand(10, RandomHelper.DIGITS + RandomHelper.ENGLISH_LETTERS));

		return simpleService.addOrUpdate(timeoutSession);
	}

	public TimeoutSession getByRandomRecordId(final String randomRecordId) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(TimeoutSession.class).add(Expression.eq("randomRecordId", randomRecordId));
		return simpleService.findSingle(criteria);
	}

	public void remove(final TimeoutSession ts) throws BusinessException
	{
		simpleService.remove(ts);
	}

	/**
	 * ”даление всех записей, у которых есть совпадение с url
	 * @param url - url
	 * @throws BusinessException
	 */
	public void removeByUrl(final String url) throws BusinessException
	{
		try
		{
		    HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
		    {
		        public Void run(Session session) throws Exception
		        {
			        Query query = session.getNamedQuery(QUERY_PREFIX + "removeByUrl");
			        query.setParameter("url", url).executeUpdate();
			        session.flush();
		            return null;
		        }
		    });
		}
		catch (Exception e)
		{
		   throw new BusinessException(e);
		}
	}
}


