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
public class BanksDocumentService
{
	private static final SimpleService simpleService = new SimpleService();

	public BanksDocument getById(Long id) throws BusinessException
    {

	    return (BanksDocument) simpleService.findById(BanksDocument.class, id);
	}

   public BanksDocument createBanksDocument(final BanksDocument banksDocument) throws BusinessException, DublicateBanksDocumentNameException
   {
		try
		{
			HibernateExecutor trnExecutor = HibernateExecutor.getInstance();

			return trnExecutor.execute(new HibernateAction<BanksDocument>()
			{
				public BanksDocument run(Session session) throws Exception
				{
					session.save(banksDocument);
					session.flush();
					return banksDocument;
				}
			}
			);
		}
		catch(ConstraintViolationException e)
		{
			throw new DublicateBanksDocumentNameException();
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

   public BanksDocument updateBanksDocument(final BanksDocument banksDocument) throws BusinessException, DublicateBanksDocumentNameException
   {
		try
		{
			HibernateExecutor trnExecutor = HibernateExecutor.getInstance();

			return trnExecutor.execute(new HibernateAction<BanksDocument>()
			{
				public BanksDocument run(Session session) throws Exception
				{
					session.update(banksDocument);
					return banksDocument;
				}
			}
			);
		}
		catch(ConstraintViolationException e)
		{
			throw new DublicateBanksDocumentNameException();
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

	public void remove(BanksDocument banksDocument) throws BusinessException
	{
        simpleService.remove(banksDocument);
	}
}
