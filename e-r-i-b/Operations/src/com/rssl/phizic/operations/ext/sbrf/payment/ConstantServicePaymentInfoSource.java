package com.rssl.phizic.operations.ext.sbrf.payment;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderHelper;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderShort;

import java.util.HashMap;
import java.util.Map;

/**
 * @author krenev
 * @ created 07.05.2010
 * @ $Author$
 * @ $Revision$
 * Константный источник
 */
public class ConstantServicePaymentInfoSource implements ServicePaymentInfoSource
{
	private Long serviceId;
	private Long providerId;
	private Map<String, Object> keyFields;

	/**
	 * ctor
	 * @param serviceId идентифкатор услуги
	 * @param providerId идентифкатор поставщика
	 * @param keyFields  ключевые поля.
	 */
	public ConstantServicePaymentInfoSource(Long serviceId, Long providerId, Map<String, Object> keyFields) throws BusinessException
	{
		this.providerId = providerId;
		this.keyFields = keyFields;
		if (serviceId != null && serviceId > 0)
		{
			this.serviceId = serviceId;
			return;
		}
		//получаем услугу по поставщику
		ServiceProviderShort provider = ServiceProviderHelper.getServiceProvider(providerId);
		if (provider == null)
		{
			throw new BusinessException("Не найден поставщик с идентифкатором " + providerId);
		}
		//TODO в поставщике услуг сейчас нет id услуги
		//this.serviceId = provider.getPaymentService().getId();
	}

	/**
	 * ctor
	 * @param serviceId идентифкатор услуги
	 * @param providerId идентифкатор поставщика
	 */
	public ConstantServicePaymentInfoSource(Long serviceId, Long providerId) throws BusinessException
	{
		this(serviceId, providerId, new HashMap<String, Object>());
	}

	public Long getServiceId()
	{
		return serviceId;
	}

	public Long getProviderId()
	{
		return providerId;
	}

	public Map<String, Object> getKeyFields()
	{
		return keyFields;
	}
}
