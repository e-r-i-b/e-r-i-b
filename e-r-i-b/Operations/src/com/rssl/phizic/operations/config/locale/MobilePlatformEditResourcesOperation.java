package com.rssl.phizic.operations.config.locale;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.locale.dynamic.resources.LanguageResourceService;
import com.rssl.phizic.business.mobileDevices.MobilePlatform;
import com.rssl.phizic.business.mobileDevices.locale.MobilePlatformResources;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.locale.entities.ERIBLocale;
import com.rssl.phizic.locale.services.MultiInstanceEribLocaleService;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.locale.dynamic.resources.EditLanguageResourcesOperation;

/**
 * @author koptyaev
 * @ created 13.10.2014
 * @ $Author$
 * @ $Revision$
 */
public class MobilePlatformEditResourcesOperation extends OperationBase implements EditEntityOperation, EditLanguageResourcesOperation<MobilePlatformResources,Long>
{
	private ERIBLocale locale;
	private MobilePlatformResources resource;

	private static final SimpleService service = new SimpleService();
	private static final MultiInstanceEribLocaleService LOCALE_SERVICE = new MultiInstanceEribLocaleService();
	private static final LanguageResourceService<MobilePlatformResources> LANGUAGE_RESOURCE_SERVICE = new LanguageResourceService<MobilePlatformResources>(MobilePlatformResources.class);


	public void initialize(Long id, String localeId) throws BusinessException, BusinessLogicException
	{
		MobilePlatform platform = service.findById(MobilePlatform.class, id);

		if (platform == null)
			throw new BusinessLogicException("Платформа с id = " + id + " не найдена");

		try
		{
			locale = LOCALE_SERVICE.getById(localeId, getInstanceName());
		}
		catch (SystemException e)
		{
			throw new BusinessException(e);
		}
		if(locale == null)
			throw new BusinessLogicException("Локаль с id= " + localeId + "не найдена");

		resource = LANGUAGE_RESOURCE_SERVICE.findResById(platform.getId(), localeId);
		if (resource == null)
		{
			resource = new MobilePlatformResources();
			resource.setId(platform.getId());
			resource.setLocaleId(localeId);
		}
	}

	public ERIBLocale getLocale()
	{
		return locale;
	}

	public MobilePlatformResources getEntity()
	{
		return resource;
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		service.addOrUpdate(resource);
	}
}
