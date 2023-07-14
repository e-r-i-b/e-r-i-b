package com.rssl.phizic.operations.sms.locale;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.messages.MessageResource;
import com.rssl.phizic.business.sms.locale.SmsResourceResources;

/**
 * @author koptyaev
 * @ created 10.06.2015
 * @ $Author$
 * @ $Revision$
 */
public class EditERMBSmsSettingsResourcesOperation extends EditCSASmsSettingsResourcesOperation
{
	@Override
	public void initialize(Long id, String localeId) throws BusinessException, BusinessLogicException
	{

		MessageResource product = SERVICE.findSmsResourceById(id);
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

	@Override
	protected String getInstanceName()
	{
		return null;
	}
}
