package com.rssl.phizic.business.certification;

import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.auth.CommonLogin;
import org.hibernate.Session;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.List;

/**
 * Created by IntelliJ IDEA. User: Omeliyanchuk Date: 17.11.2006 Time: 11:06:41 To change this template use
 * File | Settings | File Templates.
 */
public class CertDemandService
{
	public CertDemand add(final CertDemand demand ) throws SecurityException
	{
		demand.setStatus( CertDemandStatus.STATUS_ENTERED );
		try
		{
			HibernateExecutor trnExecutor = HibernateExecutor.getInstance();

			return trnExecutor.execute(new HibernateAction<CertDemand>()
			{
				public CertDemand run(Session session) throws Exception
				{
					session.save(demand);
					session.flush();
					return demand;
				}
			}
			);
		}
		catch (SecurityException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new SecurityException(e);
		}
	}

	public CertDemand update(final CertDemand demand ) throws SecurityException
	{
		try
		{
			HibernateExecutor trnExecutor = HibernateExecutor.getInstance();

			return trnExecutor.execute(new HibernateAction<CertDemand>()
			{
				public CertDemand run(Session session) throws Exception
				{
					session.update(demand);
					session.flush();
					return demand;
				}
			}
			);
		}
		catch (SecurityException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new SecurityException(e);
		}
	}

	public List<CertDemand> getPersonsCertDemands(final CommonLogin login) throws SecurityException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<CertDemand>>()
			{
				public List<CertDemand> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.security.certification.getLoginCertDemands");
					query.setParameter("login", login);
					//noinspection unchecked
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new SecurityException(e);
		}
	}

	public CertDemand findDemandById(Long id) throws SecurityException
	{
		List<CertDemand> results = find(DetachedCriteria.forClass(CertDemand.class).add(Expression.eq("id", id)));
        return results.size() == 1 ? results.get(0) : null;
	}

    public <T> List<T> find(final DetachedCriteria detachedCriteria) throws SecurityException
    {
        HibernateExecutor lightExecutor = HibernateExecutor.getInstance();
        try
        {
            return lightExecutor.execute(new HibernateAction<List<T>>()
            {
                public List<T> run(Session session) throws Exception
                {
	                //noinspection unchecked
	                return detachedCriteria.getExecutableCriteria(session).list();
                }
            }
            );
        }
        catch (Exception e)
        {
            throw new SecurityException(e);
        }
    }

	public void setDeletedStatus(final long id) throws SecurityException
	{
        try
        {
	        HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
	        {
	            public Void run(Session session) throws Exception
	            {
		            CertDemand cd = findDemandById(id);
		            cd.setStatus(CertDemandStatus.STATUS_CERT_DELETED);
		            session.update(cd);
		            
		            return null;
                }
            });
		}
	    catch (Exception e)
	    {
			throw new SecurityException(e);
	    }
	}
}