package com.rssl.phizic.operations.sms.locale;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.Constants;
import com.rssl.phizic.business.locale.EribLocaleService;
import com.rssl.phizic.business.locale.dynamic.resources.LanguageResourceService;
import com.rssl.phizic.business.sms.CSASmsResource;
import com.rssl.phizic.business.sms.SMSResourcesService;
import com.rssl.phizic.business.sms.locale.SmsResourceResources;
import com.rssl.phizic.locale.entities.ERIBLocale;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.locale.dynamic.resources.EditLanguageResourcesOperation;

/**
 * @author koptyaev
 * @ created 09.06.2015
 * @ $Author$
 * @ $Revision$
 */
public class EditCSASmsSettingsResourcesOperation  extends OperationBase implements EditLanguageResourcesOperation<SmsResourceResources, Long>
{
	protected static final LanguageResourceService<SmsResourceResources> SMS_RESOURCE_RESOURCES_LANGUAGE_RESOURCE_SERVICE = new LanguageResourceService<SmsResourceResources>(SmsResourceResources.class);
	protected static final EribLocaleService LOCALE_SERVICE = new EribLocaleService();
	protected static final SMSResourcesService SERVICE = new SMSResourcesService();

	protected ERIBLocale locale;
	protected SmsResourceResources entity;

	public void initialize(Long id, String localeId) throws BusinessException, BusinessLogicException
	{

		CSASmsResource product = SERVICE.findCSAResourcesById(id, getInstanceName());
		if(product == null)
			throw new BusinessLogicException("Продукт с id = " + id + " не найден");

		locale = LOCALE_SERVICE.getById(localeId, getInstanceName());

		if(locale == null)
			throw new BusinessLogicException("Локаль с id= " + localeId + "не найдена");

		entity = SMS_RESOURCE_RESOURCES_LANGUAGE_RESOURCE_SERVICE.findResById(id, localeId, getInstanceName());

		if(entity == null)
		{
			entity = new SmsResourceResources();
			entity.setId(id);
			entity.setLocaleId(localeId);
		}
	}

	public ERIBLocale getLocale()
	{
		return locale;
	}

	public SmsResourceResources getEntity()
	{
		return entity;
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		SMS_RESOURCE_RESOURCES_LANGUAGE_RESOURCE_SERVICE.addOrUpdate(entity, getInstanceName());
	}

	@Override
	protected String getInstanceName()
	{
		return Constants.DB_CSA;
	}
}
