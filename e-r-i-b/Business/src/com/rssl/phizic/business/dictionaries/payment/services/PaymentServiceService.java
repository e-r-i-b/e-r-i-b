package com.rssl.phizic.business.dictionaries.payment.services;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.finances.CardOperationCategory;
import com.rssl.phizic.business.locale.MultiLocaleQueryHelper;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.query.BeanQuery;
import com.rssl.phizic.dataaccess.query.ExecutorQuery;
import com.rssl.phizicgate.manager.config.AdaptersConfig;
import com.rssl.phizicgate.manager.routing.Adapter;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import java.sql.Clob;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author akrenev
 * @ created 01.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class PaymentServiceService extends MultiInstancePaymentServiceService
{
	/**
	 * Получение услуги по id
	 * @param id услуги
	 * @return услуга
	 * @throws BusinessException
	 */
	public PaymentService findById(final Long id) throws BusinessException
	{
		return super.findById(id, null);
	}

	/**
	 * Получение списка групп услуг по списку идентификаторов групп услуг
	 * @param  ids идентификаторы групп услуг
	 * @return список групп услуг
	 * @throws BusinessException
	 */
	public List<PaymentService> findByIds(final List<Long> ids) throws BusinessException
	{
		return super.findByIds(ids, null);
	}

	/**
	 * Получение услуги по коду услуги
	 * @param  synchKey код услуги
	 * @return услуга
	 * @throws BusinessException
	 */
	public PaymentService findBySynchKey(final String synchKey) throws BusinessException
	{
		return findBySynchKey(synchKey, null);
	}

    /**
	 * Получение id сервиса по "Идентификатору услуги в биллинговой системе" поставщика услуг
	 * @param code – "Идентификатор услуги в биллинговой системе"
	 * @return id сервиса
	 * @throws BusinessException
	 */
	public Long getServisIdByCodeService(final String code) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Long>()
		    {
		        public Long run(Session session) throws Exception
		        {
		            Query query = session.getNamedQuery("com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase.getServisIdByCodeService");
			        query.setParameter("code", code);

		            return  (Long)query.uniqueResult();
		        }
		    });
		}
		catch (Exception e)
		{
		    throw new BusinessException(e);
		}
	}

	/**
	 * Получение списка подчиненных услуг
	 * @param paymentService услуга-предок
	 * @return список услуг-потомков
	 * @throws BusinessException
	 */
	public List<PaymentService> getChildren(final PaymentService paymentService) throws BusinessException
	{
		return super.getChildren(paymentService, null);
	}

	/**
	 * Получение списка подчиненных услуг (старый каталог)
	 * @param paymentService услуга-предок
	 * @return список услуг-потомков
	 * @throws BusinessException
	 */
	public List<PaymentService> getChildrensOld(final PaymentService paymentService) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<PaymentService>>()
			{
				public List<PaymentService> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + ".getPaymentServiceChildrenOld");
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
	 * Получаем список услуг, к которым привязан поставщик
	 * @param providerId - id поставщика
	 * @return     список услуг
	 * @throws BusinessException
	 */
	public List<PaymentService> getServicesForProvider(final Long  providerId) throws BusinessException
	{
		return getServicesForProvider(providerId, null);
	}

	/**
	 * Сохранение услуги
	 * @param paymentService объект услуга
	 * @return добавленная услуга
	 * @throws BusinessException
	 */
	public PaymentService addOrUpdate( final PaymentService paymentService) throws BusinessException, DublicatePaymentServiceException
	{
		return super.addOrUpdate(paymentService, null);
	}

	/**
	 * Является ли группа листом
	 * @param id - идентификатор группы
	 * @return true - является листом
	 * @throws BusinessException
	 */
	public Boolean isLeaf(final Long id)  throws BusinessException
	{
		try
		{
		 return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + ".countPaymentServiceChildren");
					query.setParameter("id", id);
					return ((Integer)query.uniqueResult()==0);
				}
			});
		}
		catch(Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Определяем, есть ли у услуги дочерние, в соотв. с новой концепцией справочника
	 * @param id услуги
	 * @return есть дочерние
	 * @throws BusinessException
	 */
	public Boolean hasChild(final Long id)  throws BusinessException
	{
		return super.hasChild(id, null);
	}

	/**
	 * Справочник услуг для выгрузки в файл
	 * @return
	 */
	public List<Object[]> getPaymentServiceDataForUnloadingToFile() throws BusinessException
	{
		try
		{
		    return HibernateExecutor.getInstance().execute(new HibernateAction<List<Object[]>>()
				{
					public List<Object[]> run(Session session) throws Exception
					{
						Query query = session.getNamedQuery(QUERY_PREFIX + ".PaymentService.dataForUnloadingToFile");
						List<Object[]> result = query.list();
						return result;
					}
				});
		}
		catch(Exception e)
		{
			throw new BusinessException(e);
		}

	}

	/**
	 * Сохраняет карточную категорию для услуги
	 * @param code код услуги
	 * @param categoryId идентификатор категории
	 * @throws BusinessException
	 */
	public void saveCardOperationCategoryToPaymentService(String code, Long categoryId) throws BusinessException
	{
		try
		{
			ExecutorQuery query = new ExecutorQuery(HibernateExecutor.getInstance(), QUERY_PREFIX + ".saveCardOperationCategoryToPaymentService");
			query.setParameter("serviceCode", code);
			query.setParameter("categoryId", categoryId);
			query.executeUpdate();
		}
		catch(DataAccessException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Удаляет карточную категорию для услуги
	 * @param code код услуги
	 * @throws BusinessException
	 */
	public void deleteCardOperationCategoryToPaymentService(String code) throws BusinessException
	{
		try
		{
			ExecutorQuery query = new ExecutorQuery(HibernateExecutor.getInstance(), QUERY_PREFIX + ".deleteCardOperationCategoryByServiceCode");
			query.setParameter("serviceCode", code);
			query.executeUpdate();
		}
		catch(DataAccessException e)
		{
			throw new BusinessException(e);
		}
	}


	/**
	 * Ищет карточную категорию для услуги
	 * @param code код услуги
	 * @return карточная категория
	 * @throws BusinessException
	 */
	public CardOperationCategory findCardOperationCategoryByServiceCode(final String code) throws BusinessException
	{
		try
		{

			BeanQuery query = MultiLocaleQueryHelper.getQuery(QUERY_PREFIX + ".findCardOperationCategoryByServiceCode");
			query.setParameter("serviceCode", code);
			return query.executeUnique();

		}
		catch(Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
