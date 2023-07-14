package com.rssl.phizic.operations.card.products.locale;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.cardProduct.CardProduct;
import com.rssl.phizic.business.cardProduct.CardProductService;
import com.rssl.phizic.business.cardProduct.locale.CardProductResources;
import com.rssl.phizic.business.locale.EribLocaleService;
import com.rssl.phizic.business.locale.dynamic.resources.LanguageResourceService;
import com.rssl.phizic.locale.entities.ERIBLocale;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.locale.dynamic.resources.EditLanguageResourcesOperation;

/**
 * Операция редактирования CardProductResources
 * @author komarov
 * @ created 22.05.2015
 * @ $Author$
 * @ $Revision$
 */
public class EditCardProductResourceOperation extends OperationBase implements EditLanguageResourcesOperation<CardProductResources, Long>
{

	private static final CardProductService service = new CardProductService();
	private static final LanguageResourceService<CardProductResources> CARD_PRODUCT_LANGUAGE_RESOURCE_SERVICE = new LanguageResourceService<CardProductResources>(CardProductResources.class);
	private static final EribLocaleService LOCALE_SERVICE = new EribLocaleService();

	private ERIBLocale locale;
	private CardProductResources entity;

	public void initialize(Long id, String localeId) throws BusinessException, BusinessLogicException
	{
		CardProduct product = service.findById(id);
		if(product == null)
			throw new BusinessLogicException("Продукт с id = " + id + " не найден");

		locale = LOCALE_SERVICE.getById(localeId, getInstanceName());

		if(locale == null)
			throw new BusinessLogicException("Локаль с id= " + localeId + "не найдена");

		entity = CARD_PRODUCT_LANGUAGE_RESOURCE_SERVICE.findResById(id, localeId, getInstanceName());
		if(entity == null)
		{
			entity = new CardProductResources();
			entity.setId(id);
			entity.setLocaleId(localeId);
		}
	}

	public ERIBLocale getLocale()
	{
		return locale;
	}

	public CardProductResources getEntity()
	{
		return entity;
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		CARD_PRODUCT_LANGUAGE_RESOURCE_SERVICE.addOrUpdate(entity, getInstanceName());
	}
}
