package com.rssl.phizic.business.dictionaries.providers;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * @author akrenev
 * @ created 14.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class ServiceProviderRegionService
{
	private static final SimpleService simpleService = new SimpleService();

	/**
	 * Сохранение связи между поставщиком и регионом
	 * @param serviceProviderRegion
	 * @return
	 * @exception BusinessException
	 * */
	public ServiceProviderRegion addOrUpdate(ServiceProviderRegion serviceProviderRegion) throws BusinessException
	{
		return simpleService.addOrUpdate(serviceProviderRegion);
	}

	/**
	 * Удаление связи между поставщиком и регионом
	 * @exception BusinessException
	 * */
	public void remove(ServiceProviderRegion serviceProviderRegion) throws BusinessException
	{
		simpleService.remove(serviceProviderRegion);
	}

	/**
	 * доступен ли поставщик для региона
	 * @param providerId - id поставщика
	 * @param regionId - id региона
	 * @return - список регионов
	 */
	public Boolean providerAllowedInRegion(final Long providerId, final Long regionId) throws BusinessException
	{
	try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
		    {
		        public Boolean run(Session session) throws Exception
		        {
		            Query query = session.getNamedQuery(ServiceProviderRegion.class.getName() + ".providerAllowedInRegion");
			        query.setParameter("providerId", providerId);
			        query.setParameter("regionId", regionId);

		            return Boolean.parseBoolean((String)query.uniqueResult());
		        }
		    });
		}
		catch (Exception e)
		{
		    throw new BusinessException(e);
		}
	}

	/**
	 * Получить регионы к которым привязан поставщик
	 * @param providerId идентификатор поставщика
	 * @return список регионов
	 * @throws BusinessException
	 */
	public List<Region> getProviderRegions(final Long providerId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<Region>>()
		    {
		        public List<Region> run(Session session) throws Exception
		        {
			        Query query = session.getNamedQuery(ServiceProviderRegion.class.getName() + ".getRegionsProvider");
			        query.setParameter("providerId", providerId);
			        return (List<Region>) query.list();
		        }
		    });
		}
		catch (Exception e)
		{
		    throw new BusinessException(e);
		}
	}
}
