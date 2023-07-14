package com.rssl.phizic.business.dictionaries.payment.services.api;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.payment.services.DublicatePaymentServiceException;
import com.rssl.phizic.business.image.ImageService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

/**
 * @author lukina
 * @ created 27.05.2013
 * @ $Author$
 * @ $Revision$
 */

public class PaymentServiceOldService
{
	private static final SimpleService simpleService = new SimpleService();
	private static final ImageService imageService = new ImageService();
	/**
	 * Получение услуги по id
	 * @param id услуги
	 * @return услуга
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public PaymentServiceOld findById(final Long id) throws BusinessException
	{
		return simpleService.findById(PaymentServiceOld.class, id);
	}

	/**
	 * Получение списка групп услуг по списку идентификаторов групп услуг
	 * @param  ids идентификаторы групп услуг
	 * @return список групп услуг
	 * @throws BusinessException
	 */
	public List<PaymentServiceOld> findByIds(final List<Long> ids) throws BusinessException
	{
		return simpleService.findByIds(PaymentServiceOld.class, ids);
	}

		/**
	 * Удаление услуги
	 * @param paymentService удаляемая услуга
	 * @throws BusinessException
	 */
	public void remove (final PaymentServiceOld paymentService) throws BusinessException, BusinessLogicException
	{
		try
		{
		    HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
		    {
		        public Void run(Session session) throws Exception
		        {
			        session.delete(paymentService);
			        session.flush();
		            return null;
		        }
		    });
			//Картинка будет удалена, если на нее нет ссылки.
			if (paymentService.getImageId() != null)
				imageService.removeImageById(paymentService.getImageId(), null);

		}
		catch (ConstraintViolationException e)
		{
		   throw new BusinessLogicException(paymentService.getName());
		}
		catch(Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Сохранение услуги
	 * @param paymentService объект услуга
	 * @return добавленная услуга
	 * @throws BusinessException
	 */
	public PaymentServiceOld addOrUpdate( final PaymentServiceOld paymentService) throws BusinessException, DublicatePaymentServiceException
	{
		{
			try
			{
				PaymentServiceOld dbPaymentService = HibernateExecutor.getInstance().execute(new HibernateAction<PaymentServiceOld>()
					{
						public PaymentServiceOld run(Session session) throws Exception
						{
							session.saveOrUpdate(paymentService);
							session.flush();
							return paymentService;
						}
					}
				);
				return dbPaymentService;
			}
			catch(ConstraintViolationException e)
			{
				throw new DublicatePaymentServiceException();
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


   /**
	 * Привязаны ли к услуге поставщики
	 * @param id услуги
	 * @return  true - привязаны; false - не привязаны
	 * @throws BusinessException
	 */
	public boolean hasProvider(final Long id) throws BusinessException
	{
		try
		{
		    return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(PaymentServiceOld.class.getName() + ".getCountOfProvider");
					query.setParameter("id", id);
					return Integer.decode(query.uniqueResult().toString()) > 0;
				}
			});
		}
		catch(Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получение списка подчиненных услуг (старый каталог)
	 * @param paymentService услуга-предок
	 * @return список услуг-потомков
	 * @throws BusinessException
	 */
	public List<PaymentServiceOld> getChildrens(final PaymentServiceOld paymentService) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<PaymentServiceOld>>()
			{
				public List<PaymentServiceOld> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.dictionaries.payment.services.api.getPaymentServiceChildren");
					query.setParameter("parent", paymentService);
					//noinspection unchecked
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * уровень иерархии услуги
	 *
	 * @param id услуги
	 * @return  уровень иерархии
	 * @throws BusinessException
	 */
	public Integer getLevelOfHierarchy(final Long id) throws BusinessException
	{
		try
		{
		    return HibernateExecutor.getInstance().execute(new HibernateAction<Integer>()
			{
				public Integer run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(PaymentServiceOld.class.getName() + ".getLevelOfHierarchy");
					query.setParameter("id", id);
					Object res = query.uniqueResult();
					return res == null ? 0 : Integer.decode(res.toString());
				}
			});
		}
		catch(Exception e)
		{
			throw new BusinessException(e);
		}
	}

}
