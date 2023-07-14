package com.rssl.phizic.einvoicing;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ShopListenerConfig;
import com.rssl.phizic.dataaccess.DatabaseServiceBase;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.query.ExecutorQuery;
import com.rssl.phizic.einvoicing.exceptions.ProviderNotFoundException;
import com.rssl.phizic.gate.einvoicing.FacilitatorProvider;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.shoplistener.generated.registration.ExceededFacilitatorLimitException;
import com.rssl.phizic.utils.RandomHelper;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

/**
 * Сервис для работы с конечными поставщиками услуг (поставщики за фасилитаторами)
 * @author gladishev
 * @ created 25.12.2014
 * @ $Author$
 * @ $Revision$
 */

public class FacilitatorProviderService
{
	private static final DatabaseServiceBase simpleService = new DatabaseServiceBase();
	private static final String LIMIT_ERROR = "Превышение лимита на загрузку КПУ";
	private String instanceName;

	/**
	 * ctor
	 * @param instanceName - префикс БД
	 */
	public FacilitatorProviderService(String instanceName)
	{
		this.instanceName = instanceName;
	}

	/**
	 * Добавить КПУ с предварительной проверкой лимита
	 * @param provider - КПУ
	 */
	public void add(FacilitatorProvider provider) throws GateException, ConstraintViolationException
	{
		Integer facilitatorProvidersLimit = ConfigFactory.getConfig(ShopListenerConfig.class).getFacilitatorProvidersLimit();
		if (facilitatorProvidersLimit > 0 && getProvidersCount(provider.getFacilitatorCode()) >= facilitatorProvidersLimit)
			throw new ExceededFacilitatorLimitException(LIMIT_ERROR);

		try
		{
			simpleService.add(provider, instanceName);
		}
		catch (ConstraintViolationException cve)
		{
			throw cve;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Обновление КПУ.
	 *
	 * @param providerId идентификаторв.
	 * @param mcheckoutEnabled mobilecheckout.
	 * @param eInvoicingEnabled einvoicing.
	 * @param mbCheckEnabled mbCheck.
	 * @throws GateException
	 */
	public void update(final long providerId, final Boolean mcheckoutEnabled, final Boolean eInvoicingEnabled, final Boolean mbCheckEnabled) throws GateException, ProviderNotFoundException
	{
		try
		{
			HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					FacilitatorProvider provider = findById(providerId);
					if (provider == null)
						throw new ProviderNotFoundException();

					if (mcheckoutEnabled != null)
						provider.setMobileCheckoutEnabled(mcheckoutEnabled);

					if (mbCheckEnabled != null)
						provider.setMbCheckEnabled(mbCheckEnabled);

					if (eInvoicingEnabled != null)
						provider.setEinvoiceEnabled(eInvoicingEnabled);

					simpleService.update(provider, instanceName);

					return null;
				}
			});
		}
		catch (ProviderNotFoundException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Обновление свойства "доступность оплаты по mobile-checkout" всех КПУ, привязанных к конкретному фасилитатору.
	 *
	 * @param facilitatorCode код фасилитатора.
	 * @param mcheckout новое значение признака.
	 */
	public void updateMcheckoutByFacilitator(final String facilitatorCode, final Boolean mcheckout) throws GateException
	{
		updateProperty("updateMcheckout", facilitatorCode, mcheckout);
	}

	/**
	 * Обновление свойства "доступность оплаты по e-invoicing" всех КПУ, привязанных к конкретному фасилитатору.
	 *
	 * @param facilitatorCode код фасилитатора.
	 * @param eInvoicing новое значение признака.
	 */
	public void updateEInvoicingByFacilitator(final String facilitatorCode, final Boolean eInvoicing) throws GateException
	{
		updateProperty("updateEInvoicing", facilitatorCode, eInvoicing);
	}

	/**
	 * Обновление свойства "доступность проверки номера МТ в МБК" всех КПУ, привязанных к конкретному фасилитатору.
	 *
	 * @param facilitatorCode код фасилитатора.
	 * @param mbCheck новое значение признака.
	 */
	public void updateMbCheckByFacilitator(final String facilitatorCode, final Boolean mbCheck) throws GateException
	{
		updateProperty("updateMbCheck", facilitatorCode, mbCheck);
	}

	private void updateProperty(final String requestName, final String facilitatorCode, final Boolean value) throws GateException
	{
		if (value == null)
			throw new GateException("Не задано значение параметра для обновления");
		try
		{
			HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					session.getNamedQuery("com.rssl.phizic.einvoicing.FacilitatorProvider." + requestName)
							.setParameter("facilitatorCode", facilitatorCode)
							.setParameter("newValue", value)
							.executeUpdate();

					return null;
				}
			});
		}
		catch(Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Поиск КПУ по id
	 * @param id - идентификатор
	 * @return КПУ
	 */
	public FacilitatorProvider findById(Long id) throws GateException
	{
		try
		{
			return simpleService.findById(FacilitatorProvider.class, id, null, instanceName);
		}
		catch (Exception ex)
		{
			throw new GateException(ex);
		}
	}

	/**
	 * Поиск КПУ по коду фасилитатора и коду поставщика
	 * Опорный объект: UNIQ_PROV
	 * Предикаты доступа: access("FACILITATOR_CODE"=:R AND "CODE"=:T)
	 * Кардинальность: 1
	 *
	 * @param facilitatorCode - код фасилитатора
	 * @param providerCode - код поставщика
	 * @return КПУ
	 */
	public FacilitatorProvider findByCode(String facilitatorCode, String providerCode) throws GateException
	{
		try
		{
			DetachedCriteria criteria = DetachedCriteria.forClass(FacilitatorProvider.class);
			criteria.add(Expression.eq("facilitatorCode", facilitatorCode));
			criteria.add(Expression.eq("code", providerCode));
			return simpleService.findSingle(criteria, null, instanceName);
		}
		catch (Exception ex)
		{
			throw new GateException(ex);
		}
	}

	/**
	 * Поиск КПУ по коду фасилитатора.
	 *
	 * @param facilitatorCode код фасилитатора.
	 * @param firstResult начать с.
	 * @param maxResult всего записей.
	 * @return спиоск КПУ.
	 */
	public List<FacilitatorProvider> findByFacilitatorCode(final String facilitatorCode, final int firstResult, final int maxResult) throws GateException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<List<FacilitatorProvider>>()
			{
				public List<FacilitatorProvider> run(Session session) throws Exception
				{
					 return session.getNamedQuery("com.rssl.phizic.gate.einvoicing.FacilitatorProvider.findByCode")
							 .setParameter("code", facilitatorCode)
							 .setFirstResult(firstResult)
							 .setMaxResults(maxResult)
							 .list();
				}
			});
		}
		catch(Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Поиск КПУ по имени или ИНН.
	 *
	 * @param name имя.
	 * @param inn ИНН.
	 * @param firstResult начать с.
	 * @param maxResult всего записей.
	 * @return список КПУ.
	 */
	public List<FacilitatorProvider> findByName(final String name, final String inn, final int firstResult, final int maxResult) throws GateException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<List<FacilitatorProvider>>()
			{
				public List<FacilitatorProvider> run(Session session) throws Exception
				{
					 return session.getNamedQuery("com.rssl.phizic.gate.einvoicing.FacilitatorProvider.findByName")
							 .setParameter("name", name)
							 .setParameter("inn", inn)
							 .setFirstResult(firstResult)
							 .setMaxResults(maxResult)
							 .list();
				}
			});
		}
		catch(Exception e)
		{
			throw new GateException(e);
		}
	}


	/**
	 * Возвращает количество КПУ для фасилитатора.
	 * @param code код фасилитатора.
	 * @return количество КПУ для фасилитатора.
	 */
	private int getProvidersCount(final String code) throws GateException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Integer>()
			{
				public Integer run(Session session) throws Exception
				{
					 return (Integer) session.getNamedQuery("com.rssl.phizic.gate.einvoicing.FacilitatorProvider.findCount")
							 .setParameter("facilitatorCode", code).uniqueResult();
				}
			});
		}
		catch(Exception e)
		{
			throw new GateException(e);
		}
	}
}
