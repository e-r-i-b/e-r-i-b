package com.rssl.phizic.business.dictionaries.payment.services;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.asyncSearch.AsynchSearchDictionariesService;
import com.rssl.phizic.business.dictionaries.asyncSearch.AsynchSearchObjectState;
import com.rssl.phizic.business.dictionaries.synchronization.log.ChangeType;
import com.rssl.phizic.business.dictionaries.synchronization.log.DictionaryRecordChangeInfoService;
import com.rssl.phizic.business.image.ImageService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

/**
 * @author lepihina
 * @ created 23.01.14
 * @ $Author$
 * @ $Revision$
 */
public class MultiInstancePaymentServiceService
{
	protected static final String QUERY_PREFIX = "com.rssl.phizic.business.dictionaries.payment.services";

	private static final SimpleService simpleService = new SimpleService();
	private static final ImageService imageService = new ImageService();
	private static final AsynchSearchDictionariesService asynchSearchDictionariesService = new AsynchSearchDictionariesService();
	private static final DictionaryRecordChangeInfoService dictionaryRecordChangeInfoService = new DictionaryRecordChangeInfoService();

	/**
	 * Получение услуги по id
	 * @param id - идентификатор услуги
	 * @param instance - имя инстанса модели БД
	 * @return услуга
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public PaymentService findById(final Long id, String instance) throws BusinessException
	{
		return simpleService.findById(PaymentService.class, id, instance);
	}

	/**
	 * Получение списка групп услуг по списку идентификаторов групп услуг
	 * @param ids - идентификаторы групп услуг
	 * @param instance - имя инстанса модели БД
	 * @return список групп услуг
	 * @throws BusinessException
	 */
	public List<PaymentService> findByIds(final List<Long> ids, String instance) throws BusinessException
	{
		return simpleService.findByIds(PaymentService.class, ids, instance);
	}

	/**
	 * Получение услуги по коду услуги
	 * @param  synchKey код услуги
	 * @param instance - имя инстанса модели БД
	 * @return услуга
	 * @throws BusinessException
	 */
	public PaymentService findBySynchKey(final String synchKey, String instance) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instance).execute(new HibernateAction<PaymentService>()
			{
				public PaymentService run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(PaymentService.class.getName() + ".findBySynchKey");
					query.setParameter("synchKey", synchKey);

					return (PaymentService) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Сохранение услуги
	 * @param paymentService - объект услуга
	 * @param instance - имя инстанса модели БД
	 * @return добавленная услуга
	 * @throws BusinessException
	 */
	public PaymentService addOrUpdate(final PaymentService paymentService, String instance) throws BusinessException, DublicatePaymentServiceException
	{
		{
			try
			{
				PaymentService dbPaymentService = HibernateExecutor.getInstance(instance).execute(new HibernateAction<PaymentService>()
				{
					public PaymentService run(Session session) throws Exception
					{
						session.saveOrUpdate(paymentService);
						addToLog(session, paymentService, ChangeType.update);
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
	 * Удаление услуги
	 * @param paymentService - удаляемая услуга
	 * @param instance - имя инстанса модели БД
	 * @throws BusinessException
	 */
	public void remove(final PaymentService paymentService, String instance) throws BusinessException, BusinessLogicException
	{
		try
		{
			HibernateExecutor.getInstance(instance).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					session.delete(paymentService);
					addToLog(session, paymentService, ChangeType.delete);
					session.flush();
					return null;
				}
			});
			//Картинка будет удалена, если на нее нет ссылки.
			if (paymentService.getImageId() != null)
				imageService.removeImageById(paymentService.getImageId(), null);

			asynchSearchDictionariesService.addObjectInfoForAsynchSearch(paymentService, AsynchSearchObjectState.DELETED);
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
	 * Получение списка подчиненных услуг
	 * @param paymentService - услуга-предок
	 * @param instance - имя инстанса модели БД
	 * @return список услуг-потомков
	 * @throws BusinessException
	 */
	public List<PaymentService> getChildren(final PaymentService paymentService, String instance) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instance).execute(new HibernateAction<List<PaymentService>>()
			{
				public List<PaymentService> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.dictionaries.payment.services.getPaymentServiceChildren");
					query.setParameter("parent", paymentService.getId());
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
	 * Ищем id всех дочерних услуг для данной услуги
	 * @param parentId - услуга, для которой ищем дочерние
	 * @param instance - имя инстанса модели БД
	 * @return id всех дочерних услуг для данной услуги
	 * @throws BusinessException
	 */
	public List<Long> getChildrenIds(final Long parentId, String instance) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instance).execute(new HibernateAction<List<Long> >()
			{
				public List<Long>  run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(PaymentService.class.getName() + ".getChildrenIds");
					query.setParameter("parent_id", parentId);
					return query.list();
				}
			});
		}
		catch(Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Привязаны ли к услуге поставщики
	 * @param id - идентификатор услуги
	 * @param instance - имя инстанса модели БД
	 * @return  true - привязаны; false - не привязаны
	 * @throws BusinessException
	 */
	public boolean hasProvider(final Long id, String instance) throws BusinessException
	{
		return getCountOfProviders(id, instance) > 0;
	}

	/**
	 * Количество поставщиков, привязанных к услуге
	 * @param id - идентификатор услуги
	 * @param instance - имя инстанса модели БД
	 * @return  true - привязаны; false - не привязаны
	 * @throws BusinessException
	 */
	public Integer getCountOfProviders(final Long id, String instance) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instance).execute(new HibernateAction<Integer>()
			{
				public Integer run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(PaymentService.class.getName() + ".getCountOfProvider");
					query.setParameter("id", id);
					return Integer.decode(query.uniqueResult().toString());
				}
			});
		}
		catch(Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Определяем, есть ли у услуги дочерние, в соответствии с новой концепцией справочника
	 * @param id - услуги
	 * @param instance - имя инстанса модели БД
	 * @return есть дочерние
	 * @throws BusinessException
	 */
	public Boolean hasChild(final Long id, String instance)  throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instance).execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.dictionaries.payment.services.countServiceChildren");
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
	 * уровень иерархии услуги "сверху"
	 * @param id - идентификатор услуги
	 * @param instance - имя инстанса модели БД
	 * @return  уровень иерархии
	 * @throws BusinessException
	 */
	public Integer getLevelOfHierarchy(final Long id, String instance) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instance).execute(new HibernateAction<Integer>()
			{
				public Integer run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(PaymentService.class.getName() + ".getLevelOfHierarchy");
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

	/**
	 * уровень иерархии услуги "снизу"
	 * @param id - идентификатор услуги
	 * @param instance - имя инстанса модели БД
	 * @return  уровень иерархии
	 * @throws BusinessException
	 */
	public Integer getLevelOfHierarchyDown(final Long id, String instance) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instance).execute(new HibernateAction<Integer>()
			{
				public Integer run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(PaymentService.class.getName() + ".getLevelOfHierarchyDown");
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

	/**
	 * Ищем услуги у которых нет дочерних
	 * @param ids список id услуг, среди которых проводим поиск
	 * @param instance - имя инстанса модели БД
	 * @return  список услуг, у которых нет дочерних
	 * @throws BusinessException
	 */
	public List<PaymentService> getServiceNoChildren(final List<Long> ids, String instance) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instance).execute(new HibernateAction<List<PaymentService>>()
			{
				public List<PaymentService> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(PaymentService.class.getName() + ".getServiceNoChildren");
					query.setParameterList("ids", ids);
					return query.list();
				}
			});
		}
		catch(Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получаем список услуг, к которым привязан поставщик
	 * @param providerId - id поставщика
	 * @param instance - имя инстанса модели БД
	 * @return     список услуг
	 * @throws BusinessException
	 */
	public List<PaymentService> getServicesForProvider(final Long  providerId, String instance) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instance).execute(new HibernateAction<List<PaymentService>>()
			{
				public List<PaymentService> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + ".PaymentService.getServicesForProvider");
					query.setParameter("providerId", providerId);
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

	private void addToLog(Session session, PaymentService paymentService, ChangeType changeType) throws BusinessException
	{
		dictionaryRecordChangeInfoService.addChangesToLog(session, paymentService, changeType);
	}
}
