package com.rssl.phizic.operations.dictionaries.provider;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderState;
import com.rssl.phizic.business.operations.restrictions.RestrictionViolationException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.gate.GateInfoService;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import org.hibernate.Session;

/**
 * @author krenev
 * @ created 16.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class ActivateOrLockServiceProviderOperation extends EditServiceProvidersOperationBase
{
	public void initialize(Long id) throws BusinessException
	{
		ServiceProviderBase temp = providerService.findById(id, getInstanceName());
		if (temp == null)
			throw new BusinessException("Поставщик услуг с id = " + id + " не найден");

		if (!getRestriction().accept(temp))
		{
			throw new RestrictionViolationException("Поставщик ID= " + temp.getId());
		}

		provider = temp;
	}

	public ServiceProviderBase getEntity()
	{
		return provider;
	}

	/**
	 * Подключить поставщика
	 */
	public void activate() throws BusinessLogicException, BusinessException
	{
		if (ServiceProviderState.ACTIVE == provider.getState())
		{
			throw new BusinessLogicException("Поставщик услуг уже подключен (активирован). Действие не было выполнено.");
		}

		executeTransactional(new HibernateAction<Object>()
		{
			public Object run(Session session) throws Exception
			{
				//Если у ПУ указан бизнес-признак. То проверяем задано ли поле.
				checkServiceProviderSubTypeFieldRestriction();
				// проверяем количество ключевых полей у поставщика, поддерживающего автоплатежи, или поставщика МБ
				checkBillingServiceProviderNumOfKeyField();

				//Проверяем заполненность комисиий
				if (provider instanceof BillingServiceProvider)
				{
					GateInfoService gateInfoService = GateSingleton.getFactory().service(GateInfoService.class);
					try
					{
						Boolean available = gateInfoService.isPaymentCommissionAvailable(((BillingServiceProvider) provider).getBilling());
						if (!available && (((BillingServiceProvider) provider).getComissionRate() == null || ((BillingServiceProvider) provider).getMaxComissionAmount() == null || ((BillingServiceProvider) provider).getMinComissionAmount() == null))
						{
							throw new BusinessLogicException("Для подключения поставщика необходимо задать комиссию.");
						}
					}
					catch (GateException e)
					{
						throw new BusinessException(e);
					}
					catch (GateLogicException e)
					{
						throw new BusinessLogicException(e);
					}
				}
				provider.setState(ServiceProviderState.ACTIVE);
				providerService.addOrUpdate(provider, getInstanceName());
				clearEntitiesListCache();
				return null;
			}
		});
	}

	/**
	 * Отключить поставщика
	 */
	public void lock() throws BusinessLogicException, BusinessException
	{
		if (ServiceProviderState.ACTIVE != provider.getState())
		{
			throw new BusinessLogicException("Поставщик услуг уже отключен (деактивирован). Действие не было выполнено.");
		}
		executeTransactional(new HibernateAction<Object>()
		{
			public Object run(Session session) throws Exception
			{
				provider.setState(ServiceProviderState.NOT_ACTIVE);
				providerService.addOrUpdate(provider, getInstanceName());
				clearEntitiesListCache();
				return null;
			}
		});
	}
}
