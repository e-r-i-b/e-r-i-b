package com.rssl.phizic.business.template;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Query;
import org.hibernate.exception.ConstraintViolationException;

/**
 * Created by IntelliJ IDEA.
 * User: Novikov_A
 * Date: 15.02.2007
 * Time: 16:26:13
 * To change this template use File | Settings | File Templates.
 */
public class ClientsPackageTemplatesService
{
	 public List<ClientsPackageTemplate> getClientsPackageById(final CommonLogin login) throws BusinessException
	 {
		try
		{
		    return HibernateExecutor.getInstance().execute(new HibernateAction<List<ClientsPackageTemplate>>()
		    {
		        public List<ClientsPackageTemplate> run(Session session) throws Exception
		        {
			        Query query = session.getNamedQuery("GetClientPackageList");
			        query.setParameter("login", login);

			        return (List<ClientsPackageTemplate>) query.list();
		        }
		    });
		}
		catch (Exception e)
		{
		   throw new BusinessException(e);
		}
	}

	public void  RemoveClientsPackage(ClientsPackageTemplate clientsPackageTemplate) throws BusinessException
	{
	   SimpleService service = new SimpleService();

	   service.remove(clientsPackageTemplate);
   }

	public void EditClientsPackage(ClientsPackageTemplate clientsPackageTemplate) throws BusinessException
	{
	   SimpleService service = new SimpleService();

       service.update(clientsPackageTemplate);
   }

	public ClientsPackageTemplate AddClientsPackage(final ClientsPackageTemplate clientsPackageTemplate) throws BusinessException, DublicatePackageNameException
	{
		try
		{
			HibernateExecutor trnExecutor = HibernateExecutor.getInstance();

			return trnExecutor.execute(new HibernateAction<ClientsPackageTemplate>()
			{
				public ClientsPackageTemplate run(Session session) throws Exception
				{
					session.save(clientsPackageTemplate);
					session.flush();

					return clientsPackageTemplate;
				}
			}
			);
		}
		catch(ConstraintViolationException e)
		{
			throw new DublicatePackageNameException();
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
}
