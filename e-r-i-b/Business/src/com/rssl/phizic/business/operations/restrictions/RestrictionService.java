package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.operations.OperationDescriptor;
import com.rssl.phizic.business.services.Service;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.auth.CommonLogin;
import org.hibernate.Session;

/**
 * @author Roshka
 * @ created 17.04.2006
 * @ $Author$
 * @ $Revision$
 */

public class RestrictionService
{
	private static final String QUERY_PREFIX = RestrictionService.class.getName() + ".";

	private static final SimpleService simpleService = new SimpleService();

	public void add(RestrictionData restrictionData) throws BusinessException
	{
		simpleService.update(restrictionData);
	}

	public void remove(RestrictionData restrictionData) throws BusinessException
	{
		simpleService.remove(restrictionData);
	}

	public void update(RestrictionData restrictionData) throws BusinessException
	{
		simpleService.addOrUpdate(restrictionData);
	}

	public RestrictionData find(final CommonLogin login, final Service service, final OperationDescriptor operationDescriptor) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<RestrictionData>()
			{
				public RestrictionData run(Session session) throws Exception
				{
					//noinspection unchecked
					return (RestrictionData) session
							.getNamedQuery(QUERY_PREFIX + "find")
							.setParameter("loginId"    , login               == null ? null : login.getId())
							.setParameter("serviceId"  , service             == null ? null : service.getId())
							.setParameter("operationId", operationDescriptor == null ? null : operationDescriptor.getId())
							.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}