package com.rssl.phizic.business.dictionaries.kbk;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

/**
 * @author akrenev
 * @ created 13.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class KBKService
{
	private static final SimpleService service = new SimpleService();

	public KBK findById(Long id) throws BusinessException
	{
		return service.findById(KBK.class, id);
	}

	public static KBK findByCode(final String code) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<KBK>()
		    {
		        public KBK run(Session session) throws Exception
		        {
		            Query query = session.getNamedQuery(KBK.class.getName() + ".findByCode");
			        query.setParameter("code", code);

		            return (KBK) query.uniqueResult();
		        }
		    });
		}
		catch (Exception e)
		{
		    throw new BusinessException(e);
		}
	}

	public KBK addOrUpdate(final KBK kbk) throws BusinessException, BusinessLogicException
	{
		{
			try
			{
				return HibernateExecutor.getInstance().execute(new HibernateAction<KBK>()
					{
						public KBK run(Session session) throws Exception
						{
							session.saveOrUpdate(kbk);
							session.flush();
							return kbk;
						}
					}
				);
			}
			catch(ConstraintViolationException e)
			{
				throw new DublicateKBKException();
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

	public void remove(KBK kbk) throws BusinessException
	{
		service.remove(kbk);
	}
}
