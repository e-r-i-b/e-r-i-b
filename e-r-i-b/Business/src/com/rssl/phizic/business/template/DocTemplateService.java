package com.rssl.phizic.business.template;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

/**
 * User: Novikov_A
 * Date: 10.02.2007
 * Time: 15:53:50
 */
public class DocTemplateService
{
	private static final SimpleService simpleService = new SimpleService();

	public DocTemplate getById(Long id) throws BusinessException
    {
   	    return (DocTemplate)simpleService.findById(DocTemplate.class, id);
	}

   public DocTemplate createDocTemplate(final DocTemplate docTemplate) throws DublicateDocTemplateNameException, BusinessException
   {
		try
		{
			HibernateExecutor trnExecutor = HibernateExecutor.getInstance();

			return trnExecutor.execute(new HibernateAction<DocTemplate>()
			{
				public DocTemplate run(Session session) throws Exception
				{
					session.save(docTemplate);
					session.flush();
					return docTemplate;
				}
			}
			);
		}
		catch(ConstraintViolationException e)
		{
			throw new DublicateDocTemplateNameException();
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

   public DocTemplate updateDocTemplate(final DocTemplate docTemplate) throws BusinessException, DublicateDocTemplateNameException
   {
		try
		{
			HibernateExecutor trnExecutor = HibernateExecutor.getInstance();

			return trnExecutor.execute(new HibernateAction<DocTemplate>()
			{
				public DocTemplate run(Session session) throws Exception
				{
					session.update(docTemplate);
					return docTemplate;
				}
			}
			);
		}
		catch(ConstraintViolationException e)
		{
			throw new DublicateDocTemplateNameException();
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

	public void remove(DocTemplate docTemplate) throws BusinessException
	{
		simpleService.remove(docTemplate);
	}
}
