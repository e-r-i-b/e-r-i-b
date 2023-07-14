package com.rssl.phizic.business.services;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.MultiInstanceSimpleService;
import com.rssl.phizic.business.resources.own.MultiInstanceSchemeOwnService;
import com.rssl.phizic.business.schemes.AccessScheme;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.person.Person;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.exception.ConstraintViolationException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Omeliyanchuk
 * @ created 23.07.2008
 * @ $Author$
 * @ $Revision$
 */

public class MultiInstanceServiceService
{
    private static final MultiInstanceSimpleService simpleService = new MultiInstanceSimpleService();
    private static final MultiInstanceSchemeOwnService schemeOwnService = new MultiInstanceSchemeOwnService();
	private static final Integer INTEGER_TRUE = Integer.valueOf("1");

    private static final String QUERY_PREFIX = ServiceService.class.getName() + ".";

    public List<Service> getAll(String instanceName) throws BusinessException
    {
        return simpleService.find(DetachedCriteria.forClass(Service.class), instanceName);
    }

    public void add(Service service,String instanceName) throws BusinessException
    {
        if ( findByKey(service.getKey(), instanceName) != null )
            throw new BusinessException("Уже есть услуга с таким ключом: "+service.getKey());
        simpleService.add(service, instanceName);
    }

    public void update(Service service, String instanceName) throws BusinessException
    {
        simpleService.update(service,instanceName);
    }

    public void remove(final Service service, final boolean ignoreUsages, String instanceName) throws BusinessLogicException, BusinessException
    {
        try
        {
            HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Void>()
            {
                public Void run(Session session) throws Exception
                {
	                if(ignoreUsages)
	                {
						Query q1 = session.getNamedQuery("com.rssl.phizic.business.services.ServiceService.deleteServiceUsages.1");
						q1.setParameter("serviceId", service.getId());
						q1.executeUpdate();
						Query q2 = session.getNamedQuery("com.rssl.phizic.business.services.ServiceService.deleteServiceUsages.2");
						q2.setParameter("serviceId", service.getId());
						q2.executeUpdate();
	                }

                    session.getNamedQuery(QUERY_PREFIX + "delete")
                            .setParameter("service", service)
                            .executeUpdate();
                    session.delete(service);
                    return null;
                }
            });
        }
        catch (ConstraintViolationException cve)
        {
            throw new BusinessLogicException("Сервис используется ", cve);
        }
        catch (Exception e)
        {
           throw new BusinessException(e);
        }
    }

	public void remove(final Service service,String instanceName) throws BusinessLogicException, BusinessException
	{
		remove(service, false, instanceName);
	}

	public Service findByKey(final String key,String instanceName) throws BusinessException
    {
        try
        {
            return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Service>()
            {
                public Service run(Session session) throws Exception
                {
                    return (Service) session
                            .getNamedQuery(QUERY_PREFIX + "findByKey")
                            .setParameter("key", key).uniqueResult();
                }
            });
        }
        catch (Exception e)
        {
           throw new BusinessException(e);
        }
    }

    public List<ServiceOperationDescriptor> getServiceOperations(final Service service,String instanceName) throws BusinessException
    {
        try
        {
            return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<List<ServiceOperationDescriptor>>()
            {
                public List<ServiceOperationDescriptor> run(Session session) throws Exception
                {
                    // для того чтоб не создавался новый инстанс session
                    session.refresh(service);
                    //noinspection unchecked
                    return session
                            .getNamedQuery(QUERY_PREFIX + "selectServiceOperations")
                            .setParameter("service", service).list();
                }
            });
        }
        catch (Exception e)
        {
           throw new BusinessException(e);
        }
    }

    public List<Service> findByCategory(final String category, String instanceName) throws BusinessException
    {
        try
        {
            return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<List<Service>>()
            {
                public List<Service> run(Session session) throws Exception
                {
                    //noinspection unchecked
                    return session
                            .getNamedQuery(QUERY_PREFIX + "selectByCategory")
                            .setParameter("category", category).list();

                }
            });
        }
        catch (BusinessException e)
        {
            throw e;
        }
        catch (Exception e)
        {
           throw new BusinessException(e);
        }
    }

    public List<Service> getPersonServices(Person person,String instanceName) throws BusinessException
    {
        AccessScheme scheme = schemeOwnService.findScheme(person.getLogin(), AccessType.simple, instanceName);
        List<Service> services;

        if (scheme==null)
        {
            services = new ArrayList<Service>();
        }
        else
        {
            services = scheme.getServices();
        }

        return services;
    }

	/**
	 * Ищет есть ли у логина, в правах указанный сервис.
	 * @param loginId id логина
	 * @param serviceName имя сервиса
	 * @param instanceName ..
	 * @return Есть то 1, нет 0
	 * @throws BusinessException
	 */
	public Boolean isPersonServices(final Long loginId,final String serviceName,String instanceName) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session) throws Exception
				{
					 Query query = session.getNamedQuery(QUERY_PREFIX + "checkServiceFromUserScheme")
							.setParameter("login_id", loginId)
							.setParameter("access_type",AccessType.simple.toString())
							.setParameter("service_key",serviceName);

					return query.uniqueResult().equals(INTEGER_TRUE);
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public Service findById(final Long serviceId,String instanceName) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Service>()
			{
				public Service run(Session session) throws Exception
				{
					return (Service) session
							.getNamedQuery(QUERY_PREFIX + "findById")
							.setParameter("id", serviceId).uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public void replicateAll(String toInstance, String fromInstance) throws BusinessException
	{
		List<Service> list = getAll(fromInstance);
		for (Service service : list)
		{
			List<ServiceOperationDescriptor> operationList = getServiceOperations(service, fromInstance);
			simpleService.replicate(service,toInstance);
			for (ServiceOperationDescriptor operationDescriptor : operationList)
			{
				simpleService.replicate(operationDescriptor,toInstance);
			}

		}
	}
}
