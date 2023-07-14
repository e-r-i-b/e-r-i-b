package com.rssl.phizic.operations.dictionaries.provider.locale;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;

import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.providers.locale.ServiceProviderResources;
import com.rssl.phizic.business.locale.dynamic.resources.LanguageResourcesBaseService;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.locale.entities.ERIBLocale;
import com.rssl.phizic.locale.services.MultiInstanceEribLocaleService;
import com.rssl.phizic.operations.dictionaries.synchronization.EditDictionaryEntityOperationBase;
import com.rssl.phizic.operations.locale.dynamic.resources.EditLanguageResourcesOperation;

/**
 * @author komarov
 * @ created 06.10.2014
 * @ $Author$
 * @ $Revision$
 */
public class EditServiceProvidersLanguageOperation extends EditDictionaryEntityOperationBase implements EditLanguageResourcesOperation<ServiceProviderResources, Long>
{
	private static final ServiceProviderService PROVIDER_SERVICE = new ServiceProviderService();
	private static final LanguageResourcesBaseService<ServiceProviderResources> LANGUAGE_RESOURCES_SERVICE = new LanguageResourcesBaseService<ServiceProviderResources>(ServiceProviderResources.class);
	private static final MultiInstanceEribLocaleService LOCALE_SERVICE = new MultiInstanceEribLocaleService();

	private ERIBLocale locale;
	private ServiceProviderResources resource;

	public void initialize(Long id, String localeId) throws BusinessException, BusinessLogicException
	{
		ServiceProviderBase provider = PROVIDER_SERVICE.findById(id, getInstanceName());
		if(provider == null)
			throw new BusinessLogicException("поставщик с id = " + id + " не найден");

		try
		{
			locale = LOCALE_SERVICE.getById(localeId, null);
		}
		catch (SystemException e)
		{
			throw new BusinessException(e);
		}
		if(locale == null)
			throw new BusinessLogicException("Локаль с id= " + localeId + "не найдена");

		resource = LANGUAGE_RESOURCES_SERVICE.findResById(provider.getUuid(), localeId, getInstanceName());
		if(resource == null)
		{
			resource = new ServiceProviderResources();
			resource.setUuid(provider.getUuid());
			resource.setLocaleId(localeId);
		}
	}

	@Override
	protected void doSave() throws BusinessException, BusinessLogicException
	{
		LANGUAGE_RESOURCES_SERVICE.addOrUpdate(resource, getInstanceName());
	}


	public ERIBLocale getLocale()
	{
		return locale;
	}

	public ServiceProviderResources getEntity()
	{
		return resource;
	}
}
