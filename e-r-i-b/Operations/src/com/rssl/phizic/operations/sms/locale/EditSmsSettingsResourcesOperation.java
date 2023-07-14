package com.rssl.phizic.operations.sms.locale;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.locale.EribLocaleService;
import com.rssl.phizic.business.locale.dynamic.resources.LanguageResourceService;
import com.rssl.phizic.business.sms.SMSResource;
import com.rssl.phizic.business.sms.SMSResourcesService;
import com.rssl.phizic.business.sms.locale.SmsResourceResources;
import com.rssl.phizic.locale.entities.ERIBLocale;
import com.rssl.phizic.common.types.limits.ChannelType;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.locale.dynamic.resources.EditLanguageResourcesOperation;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * @author koptyaev
 * @ created 08.06.2015
 * @ $Author$
 * @ $Revision$
 */
public class EditSmsSettingsResourcesOperation extends OperationBase implements EditLanguageResourcesOperation<SmsResourceResources, Long>
{
	private static final LanguageResourceService<SmsResourceResources> SMS_RESOURCE_RESOURCES_LANGUAGE_RESOURCE_SERVICE = new LanguageResourceService<SmsResourceResources>(SmsResourceResources.class);
	private static final EribLocaleService LOCALE_SERVICE = new EribLocaleService();
	private static final SMSResourcesService SERVICE = new SMSResourcesService();

	private ERIBLocale locale;
	private Map<ChannelType,SmsResourceResources> entities;



	public void initialize(Long id, String localeId) throws BusinessException, BusinessLogicException
	{

		List<SMSResource> products = SERVICE.findSmsResourcesById(id);
		if(CollectionUtils.isEmpty(products))
			throw new BusinessLogicException("Продукт с id = " + id + " не найден");

		locale = LOCALE_SERVICE.getById(localeId, getInstanceName());

		if(locale == null)
			throw new BusinessLogicException("Локаль с id= " + localeId + "не найдена");

		entities = new HashMap<ChannelType, SmsResourceResources>(products.size());
		for (SMSResource product:products)
		{
			SmsResourceResources res = SMS_RESOURCE_RESOURCES_LANGUAGE_RESOURCE_SERVICE.findResById(product.getId(), localeId, getInstanceName());

			if(res == null)
			{
				res = new SmsResourceResources();
				res.setId(product.getId());
				res.setLocaleId(localeId);
			}
			entities.put(product.getChannel(),res);
		}

	}

	public ERIBLocale getLocale()
	{
		return locale;
	}

	public SmsResourceResources getEntity()
	{
		return null;
	}

	/**
	 * Возвращает список многоязычных сущностей
	 * @return список многоязычных сущностей
	 */
	public Map<ChannelType,SmsResourceResources> getEntities()
	{
		//желтый код ибо нам нужен оригинал мапы, чтобы потом она правильно сохранялась
		//noinspection ReturnOfCollectionOrArrayField
		return entities;
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		for (SmsResourceResources res:entities.values())
		{
			SMS_RESOURCE_RESOURCES_LANGUAGE_RESOURCE_SERVICE.addOrUpdate(res, getInstanceName());
		}
	}
}
