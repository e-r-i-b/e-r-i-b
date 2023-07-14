package com.rssl.phizic.business.longoffer.links;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.resources.external.LongOfferLink;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class LongOfferLinksService
{
	public List<LongOfferLink> findByUserId(final CommonLogin login) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<LongOfferLink>>()
			{
				public List<LongOfferLink> run(Session session)
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.resources.external.LongOfferLink.list");
					query.setParameter("login", login.getId());
					return query.list();
				}
			});
		}
		catch(Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
