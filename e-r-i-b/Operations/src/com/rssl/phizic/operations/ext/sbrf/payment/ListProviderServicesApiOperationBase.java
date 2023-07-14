package com.rssl.phizic.operations.ext.sbrf.payment;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.regions.RegionHelper;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @ author: Gololobov
 * @ created: 21.11.14
 * @ $Author$
 * @ $Revision$
 */
public class ListProviderServicesApiOperationBase extends OperationBase
{
	private ServiceProviderService service = new ServiceProviderService();

	/**
	 * Возвращает список услуго-поставщиков по id одного из них.
	 * @param id id поставщика.
	 * @return список услуго-поставщиков.
	 */
	public List<BillingServiceProvider> getList(Long id) throws BusinessException
	{
		if (id == null)
			throw new BusinessException("Не указан идентификатор поставщика для получения списка услуг.");

		ServiceProviderBase provider = service.findById(id);

		if(provider == null)
			throw new BusinessException("Не найден поставщик с id="+id);

		if(!(provider instanceof BillingServiceProvider))
			throw new BusinessException("Некорректный тип поставщика с id="+id);

		return service.getProviderMobileServices((BillingServiceProvider) provider);
	}

	/**
	 * Возвращает список услуго-поставщиков по id поставщика с учетом региона.
	 * @param providerId - ИД поставщика
	 * @param providerGuid - кросблочный ИД пставщика
	 * @return
	 */
	public List<BillingServiceProvider> getServiceProviders(Long providerId, String providerGuid) throws BusinessException
	{
		//Пока костыль только для atmAPI, потом (после команды от РП) переделать для всех API,
		//возможно с использованием данных из агрегированных таблиц (PAYMENT_SERVICES_AGGR, SERVICE_PROVIDERS_AGGR)
		if (providerId == null && StringHelper.isEmpty(providerGuid))
			throw new BusinessException("Не указан идентификатор поставщика для получения списка услуг.");

		ServiceProviderBase provider = null;
		if (providerId != null)
			provider = service.findById(providerId);
		else
			provider = service.findByMultiBlockId(providerGuid);

		if(provider == null)
			throw new BusinessException("Не найден поставщик с " + providerId != null ? "id="+providerId : "guid="+providerGuid);

		if(!(provider instanceof BillingServiceProvider))
			throw new BusinessException("Некорректный тип поставщика с " + providerId != null ? "id="+providerId : "guid="+providerGuid);


		return service.getProviderMobileServices((BillingServiceProvider) provider);
	}
}
