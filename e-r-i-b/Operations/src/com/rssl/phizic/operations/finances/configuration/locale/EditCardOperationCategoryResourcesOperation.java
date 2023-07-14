package com.rssl.phizic.operations.finances.configuration.locale;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.finances.CardOperationCategory;
import com.rssl.phizic.business.dictionaries.finances.CardOperationCategoryService;
import com.rssl.phizic.business.dictionaries.finances.locale.CardOperationCategoryResources;
import com.rssl.phizic.business.locale.dynamic.resources.LanguageResourceService;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.locale.entities.ERIBLocale;
import com.rssl.phizic.locale.services.MultiInstanceEribLocaleService;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.locale.dynamic.resources.EditLanguageResourcesOperation;

/**
 * @author komarov
 * @ created 02.10.2014
 * @ $Author$
 * @ $Revision$
 */
public class EditCardOperationCategoryResourcesOperation extends OperationBase implements EditEntityOperation, EditLanguageResourcesOperation<CardOperationCategoryResources,Long>
{
	private ERIBLocale locale;
	private CardOperationCategoryResources resource;

	private static final CardOperationCategoryService CARD_OPERATION_CATEGORY_SERVICE = new CardOperationCategoryService();
	private static final MultiInstanceEribLocaleService LOCALE_SERVICE = new MultiInstanceEribLocaleService();
	private static final LanguageResourceService<CardOperationCategoryResources> LANGUAGE_RESOURCE_SERVICE = new LanguageResourceService<CardOperationCategoryResources>(CardOperationCategoryResources.class);


	public void initialize(Long id, String localeId) throws BusinessException, BusinessLogicException
	{
		CardOperationCategory category = CARD_OPERATION_CATEGORY_SERVICE.findById(id);
		if(category == null)
			throw new BusinessLogicException("Категория с id " + id + " не найдена.");
		if(category.getOwnerId() != null)
			throw new BusinessLogicException("Вы не можете редактировать клиентскую категорию");

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

		resource = LANGUAGE_RESOURCE_SERVICE.findResById(id, localeId);
		if(resource == null)
		{
			resource = new CardOperationCategoryResources();
			resource.setId(id);
			resource.setLocaleId(localeId);
		}
	}

	public ERIBLocale getLocale()
	{
		return locale;
	}

	public CardOperationCategoryResources getEntity()
	{
		return resource;
	}

	public void save() throws BusinessException, BusinessLogicException
	{
	 	LANGUAGE_RESOURCE_SERVICE.addOrUpdate(resource);
	}
}
