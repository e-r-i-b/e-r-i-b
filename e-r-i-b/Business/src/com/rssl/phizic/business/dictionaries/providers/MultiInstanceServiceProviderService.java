package com.rssl.phizic.business.dictionaries.providers;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.asyncSearch.AsynchSearchDictionariesService;
import com.rssl.phizic.business.dictionaries.asyncSearch.AsynchSearchObjectState;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.business.dictionaries.synchronization.log.ChangeType;
import com.rssl.phizic.business.dictionaries.synchronization.log.DictionaryRecordChangeInfoService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dictionaries.synchronization.MultiBlockDictionaryRecord;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import java.util.Collections;
import java.util.List;

/**
 * @author akrenev
 * @ created 30.01.2014
 * @ $Author$
 * @ $Revision$
 */

public class MultiInstanceServiceProviderService
{
	private static final DictionaryRecordChangeInfoService dictionaryRecordChangeInfoService = new DictionaryRecordChangeInfoService();

	protected static final String QUERY_PREFIX = ServiceProviderBase.class.getName() + ".";
	protected static final SimpleService simpleService = new SimpleService();
	protected static final AsynchSearchDictionariesService asynchSearchDictionariesService = new AsynchSearchDictionariesService();

	/**
	 * Возвращает название поставщика услуг по идентификатору
	 * @param id идентификатор
	 * @param instance инстанс
	 * @return название поставщика
	 * @throws BusinessException
	 */
	public String findNameById(Long id, String instance) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(ServiceProviderBase.class)
						.add(Restrictions.eq("id", id))
						.setProjection(Projections.property("name"));
		return simpleService.findSingle(criteria, instance);
	}

	/**
	 * Возвращает идентификатор иконки поставщика услуг по идентификатору
	 * @param id идентификатор
	 * @param instance инстанс
	 * @return идентификатор иконки
	 * @throws BusinessException
	 */
	public Long findImageIdById(Long id, String instance) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(ServiceProviderBase.class)
				.add(Restrictions.eq("id", id))
				.setProjection(Projections.property("imageId"));
		return simpleService.findSingle(criteria, instance);
	}
	/**
	 * Получение получателя по id
	 * @param id получателя
	 * @param instanceName инстанс БД
	 * @return получатель
	 * @throws BusinessException
	 */
	public ServiceProviderBase findById(Long id, String instanceName) throws BusinessException
	{
		return simpleService.findById(ServiceProviderBase.class, id, instanceName);
	}

	/**
	 * Сохранение получателя
	 * @param serviceProvider получатель
	 * @param instanceName инстанс БД
	 * @return добавленный получатель
	 * @throws BusinessException, DublicateServiceProviderException
	 */
	public ServiceProviderBase addOrUpdate(final ServiceProviderBase serviceProvider, String instanceName) throws BusinessException, DublicateServiceProviderException
	{
		try
		{
			AsynchSearchObjectState state = serviceProvider.getId() != null ? AsynchSearchObjectState.UPDATED : AsynchSearchObjectState.INSERTED;
			ServiceProviderBase serviceProviderBase = HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<ServiceProviderBase>()
			{
				public ServiceProviderBase run(Session session) throws Exception
				{
					session.saveOrUpdate(serviceProvider);
					addToLog(session, serviceProvider, ChangeType.update);
					session.flush();
					return serviceProvider;
				}
			}
			);
			if (MultiBlockModeDictionaryHelper.needExternalCheck())
				asynchSearchDictionariesService.addObjectInfoForAsynchSearch(serviceProviderBase, state);

			return serviceProviderBase;
		}
		catch (ConstraintViolationException e)
		{
			throw new DublicateServiceProviderException(e);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Удаление получателя
	 * @param serviceProvider удаляемый получатель
	 * @param instanceName инстанс БД
	 * @throws BusinessException
	 */
	public void remove(final ServiceProviderBase serviceProvider, final String instanceName) throws BusinessLogicException, BusinessException
	{
		try
		{
			HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Object>()
			{
				public Object run(Session session) throws Exception
				{
					simpleService.removeWithConstraintViolationException(serviceProvider, instanceName);
					addToLog(serviceProvider, ChangeType.delete);
					return null;
				}
			});
			if (MultiBlockModeDictionaryHelper.needExternalCheck())
				asynchSearchDictionariesService.addObjectInfoForAsynchSearch(serviceProvider, AsynchSearchObjectState.DELETED);
		}
		catch (ConstraintViolationException e)
		{
			throw new BusinessLogicException("Невозможно удалить поставщика услуг.", e);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * обновить поставщика
	 * @param provider поставщик
	 * @param instanceName инстанс БД
	 * @throws BusinessException
	 */
	public void update(final ServiceProviderBase provider, final String instanceName) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Object>()
			{
				public Object run(Session session) throws Exception
				{
					simpleService.update(provider, instanceName);
					addToLog(provider, ChangeType.update);
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Возвращает количество поставщиков, привязанных к биллинговой системе
	 * @param billingId - идентификатор биллинговой системы
	 * @param instanceName инстанс БД
	 * @return количество поставщиков
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public Integer findByBillingId(final long billingId, String instanceName) throws BusinessException,BusinessLogicException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Integer>()
			{
				public Integer run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX+"getRecipientsListByBillingId")
							.setParameter("billingId",billingId);
					return Integer.valueOf(query.uniqueResult().toString());
				}
			});
		}
		catch(Exception e)
		{
			throw new BusinessException(e);
		}
	}


	/**
	 * Получить поставщика услуг по его коду в Мобильном Банке
	 * @param mobilebankCode - код поставщика в МБ
	 * @param instanceName инстанс БД
	 * @return поставщик
	 * @throws BusinessException
	 */
	public BillingServiceProvider findByMobileBankCode(final String mobilebankCode, String instanceName) throws BusinessException
	{
		if (StringHelper.isEmpty(mobilebankCode))
			throw new IllegalArgumentException("Argument 'mobilebankCode' cannot be null nor empty");

		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<BillingServiceProvider>()
			{
				public BillingServiceProvider run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "findByMobileBankCode").setParameter("mobilebankCode", mobilebankCode);
					return (BillingServiceProvider) query.uniqueResult();
				}
			});

		}
		catch (Exception ex)
		{
			throw new BusinessException(ex);
		}
	}

	/**
	 * Получить налогового поставщика услуг по INN
	 * @param inn инн
	 * @param instanceName инстанс БД
	 * @return поставщик услуг
	 * @throws BusinessException
	 */

	public TaxationServiceProvider findTaxProviderByINN(final String inn, String instanceName) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<TaxationServiceProvider>()
			{
				public TaxationServiceProvider run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "findByINN")
							.setParameter("inn", inn);
					return (TaxationServiceProvider) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private void addToLog(MultiBlockDictionaryRecord record, ChangeType changeType) throws BusinessException
	{
		dictionaryRecordChangeInfoService.addChangesToLog(record, changeType);
	}

	private void addToLog(Session session, MultiBlockDictionaryRecord record, ChangeType changeType) throws BusinessException
	{
		dictionaryRecordChangeInfoService.addChangesToLog(session, record, changeType);
	}

	/**
	 * Получение идентификатора поставщика-фасилитатора и наименования по его коду
	 * @param code - код поставщика услуг
	 * @param instanceName - инстанс БД
	 * @return идентификатор и наименование
	 * @throws BusinessException
	 */
	public List<Object[]> getFacilitatorIdAndNameByCode(final String code, final String instanceName) throws BusinessException
	{
		if (StringUtils.isEmpty(code))
			return Collections.emptyList();
		try
		{
			HibernateExecutor executor = HibernateExecutor.getInstance(instanceName);
			return executor.execute(new HibernateAction<List<Object[]>>()
			{
				public List<Object[]> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "getFacilitatorIdAndNameByCode");
					query.setParameter("code", code);
					return query.list();
				}
			});
		}
		catch (Exception ex)
		{
			throw new BusinessException(ex);
		}
	}
}
