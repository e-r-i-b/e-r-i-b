package com.rssl.phizic.operations.creditcards.products.locale;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.creditcards.products.CreditCardProduct;
import com.rssl.phizic.business.creditcards.products.CreditCardProductService;
import com.rssl.phizic.business.creditcards.products.locale.CreditCardProductResources;
import com.rssl.phizic.business.locale.EribLocaleService;
import com.rssl.phizic.business.locale.dynamic.resources.LanguageResourceService;
import com.rssl.phizic.locale.entities.ERIBLocale;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.locale.dynamic.resources.EditLanguageResourcesOperation;

/**
 * Операция редактирования CreditCardProductResources
 * @author komarov
 * @ created 22.05.2015
 * @ $Author$
 * @ $Revision$
 */
public class EditCreditCardProductResourceOperation extends OperationBase implements EditLanguageResourcesOperation<CreditCardProductResources, Long>
{

	private static final CreditCardProductService service = new CreditCardProductService();
	private static final LanguageResourceService<CreditCardProductResources> CARD_PRODUCT_LANGUAGE_RESOURCE_SERVICE = new LanguageResourceService<CreditCardProductResources>(CreditCardProductResources.class);
	private static final EribLocaleService LOCALE_SERVICE = new EribLocaleService();

	private ERIBLocale locale;
	private CreditCardProductResources entity;

	public void initialize(Long id, String localeId) throws BusinessException, BusinessLogicException
	{
		CreditCardProduct product = service.findById(id);
		if(product == null)
			throw new BusinessLogicException("Продукт с id = " + id + " не найден");

		locale = LOCALE_SERVICE.getById(localeId, getInstanceName());

		if(locale == null)
			throw new BusinessLogicException("Локаль с id= " + localeId + "не найдена");

		entity = CARD_PRODUCT_LANGUAGE_RESOURCE_SERVICE.findResById(id, localeId, getInstanceName());
		if(entity == null)
		{
			entity = new CreditCardProductResources();
			entity.setId(id);
			entity.setLocaleId(localeId);
		}
	}

	public ERIBLocale getLocale()
	{
		return locale;
	}

	public CreditCardProductResources getEntity()
	{
		return entity;
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		CARD_PRODUCT_LANGUAGE_RESOURCE_SERVICE.addOrUpdate(entity, getInstanceName());
	}
}
