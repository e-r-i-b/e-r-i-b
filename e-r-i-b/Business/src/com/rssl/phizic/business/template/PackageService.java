package com.rssl.phizic.business.template;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import org.hibernate.Session;
import org.hibernate.Query;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

/**
 * User: Novikov_A
 * Date: 10.02.2007
 * Time: 15:53:50
 */
public class PackageService
{
	private static final SimpleService simpleService = new SimpleService();

	public PackageTemplate getTemplateById(Long id) throws BusinessException
	{

		return (PackageTemplate) simpleService.findById(PackageTemplate.class, id);
	}

   public PackageTemplate createPackage(final PackageTemplate packageTmp) throws BusinessException, DublicatePackageNameException
   {
		try
		{
			HibernateExecutor trnExecutor = HibernateExecutor.getInstance();

			return trnExecutor.execute(new HibernateAction<PackageTemplate>()
			{
				public PackageTemplate run(Session session) throws Exception
				{
					session.save(packageTmp);
					session.flush();
					return packageTmp;
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

   public PackageTemplate updatePackage(final PackageTemplate packageTmp) throws BusinessException, DublicatePackageNameException
   {
		try
		{
			HibernateExecutor trnExecutor = HibernateExecutor.getInstance();

			return trnExecutor.execute(new HibernateAction<PackageTemplate>()
			{
				public PackageTemplate run(Session session) throws Exception
				{
					session.update(packageTmp);
					return packageTmp;
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

    public List<PackageTemplate> getDepartmentPackages(final Long departmentId) throws BusinessException
    {
        try
		{
		    return HibernateExecutor.getInstance().execute(new HibernateAction<List<PackageTemplate>>()
		    {
		        public List<PackageTemplate> run(Session session) throws Exception
		        {
			        Query query = session.getNamedQuery("GetPackageList");
			        query.setParameter("departmentId", departmentId);

			        return (List<PackageTemplate>) query.list();
		        }
		    });
		}
		catch (Exception e)
		{
		   throw new BusinessException(e);
		}
	}

	public void remove(PackageTemplate packageTmp) throws BusinessException
	{
        simpleService.remove(packageTmp);
	}
}
