package com.rssl.phizic.business.resources.external.resolver;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * @author eMakarov
 * @ created 08.10.2008
 * @ $Author$
 * @ $Revision$
 */
public class ACPLinkResolver<T extends ExternalResourceLink> implements LinkResolver<T>
{
	private String queryName;
	private String inSystemQueryName;
	private String inMobileQueryName;
	private String inSocialQueryName;
	private String inATMQueryName;
	private final String findByExternalIdQueryName;

	public ACPLinkResolver(Class<T> clazz)
	{
		queryName = clazz.getName()+".list";
		inSystemQueryName = clazz.getName()+".list.inSystem";
		inMobileQueryName = clazz.getName()+".list.inMobile";
		inSocialQueryName = clazz.getName()+".list.inSocial";
		inATMQueryName = clazz.getName() + ".list.inATM";
		findByExternalIdQueryName = clazz.getName()+".findByExternalId";
	}

	public List<T> getLinks(final CommonLogin login,String instanceName) throws BusinessException
	{
		return getLinksInternal(login.getId(), queryName, instanceName);
	}

	public List<T> getLinks(Long loginId, String instanceName) throws BusinessException
	{
		return getLinksInternal(loginId, queryName, instanceName);
	}

	private List<T> getLinksInternal(final Long loginId, final String queryName, String instanceName) throws BusinessException
	{
		try {

			HibernateExecutor executor = HibernateExecutor.getInstance(instanceName);
			return executor.execute(new HibernateAction<List<T>>()
			{
				public List<T> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(queryName);
					query.setParameter("login", loginId);
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public List<T> getInSystemLinks(CommonLogin login, String instanceName) throws BusinessException
	{
		return getLinksInternal(login.getId(), inSystemQueryName, instanceName);
	}

	public List<T> getInMobileLinks(CommonLogin login, String instanceName) throws BusinessException
	{
		return getLinksInternal(login.getId(), inMobileQueryName, instanceName);
	}

	public List<T> getInSocialLinks(CommonLogin login, String instanceName) throws BusinessException
	{
		return getLinksInternal(login.getId(), inSocialQueryName, instanceName);
	}

	public List<T> getInATMLinks(CommonLogin login, String instanceName) throws BusinessException
	{
		return getLinksInternal(login.getId(), inATMQueryName, instanceName);
	}

	public T findByExternalId(final CommonLogin login, final String externalId, String instanceName)
			throws BusinessException
	{
		try
		{
			HibernateExecutor executor = HibernateExecutor.getInstance(instanceName);
			return executor.execute(new HibernateAction<T>()
			{
				public T run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(findByExternalIdQueryName);
					query.setParameter("login", login.getId());
					query.setParameter("externalId", externalId);
					query.setMaxResults(1);
					return (T) query.uniqueResult();
				}
			});
		}
		catch (Exception ex)
		{
			throw new BusinessException(ex);
		}
	}
}
