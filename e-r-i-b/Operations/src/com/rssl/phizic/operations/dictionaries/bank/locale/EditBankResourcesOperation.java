package com.rssl.phizic.operations.dictionaries.bank.locale;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.BankDictionaryService;
import com.rssl.phizic.business.dictionaries.bank.locale.ResidentBankResourceService;
import com.rssl.phizic.business.dictionaries.bank.locale.ResidentBankResources;

import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.locale.entities.ERIBLocale;
import com.rssl.phizic.locale.services.MultiInstanceEribLocaleService;
import com.rssl.phizic.operations.dictionaries.synchronization.EditDictionaryEntityOperationBase;
import com.rssl.phizic.operations.locale.dynamic.resources.EditLanguageResourcesOperation;

/**
 * @author koptyaev
 * @ created 06.10.2014
 * @ $Author$
 * @ $Revision$
 */
public class EditBankResourcesOperation extends EditDictionaryEntityOperationBase implements EditLanguageResourcesOperation<ResidentBankResources,String>
{
	private static final ResidentBankResourceService RESIDENT_BANK_RESOURCES_SERVICE = new ResidentBankResourceService();
	private static final BankDictionaryService BANK_DICTIONARY_SERVICE = new BankDictionaryService();
	private static final MultiInstanceEribLocaleService LOCALE_SERVICE = new MultiInstanceEribLocaleService();
	private ERIBLocale locale;
	private ResidentBankResources entity;
	@Override
	protected void doSave() throws BusinessException, BusinessLogicException
	{
		RESIDENT_BANK_RESOURCES_SERVICE.addOrUpdate(entity, getInstanceName());
	}

	public void initialize(String id, String localeId) throws BusinessException, BusinessLogicException
	{
		ResidentBank bank = BANK_DICTIONARY_SERVICE.findByBIC(id, getInstanceName());
		if(bank == null)
			throw new BusinessLogicException("Ѕанк с Ѕ»  " + id + " не найден.");
		try
		{
			locale = LOCALE_SERVICE.getById(localeId, null);
		}
		catch (SystemException e)
		{
			throw new BusinessException(e);
		}
		if(locale == null)
			throw new BusinessLogicException("язык идентификатором " + localeId + " не найден.");
		entity = RESIDENT_BANK_RESOURCES_SERVICE.findResById(id, localeId, getInstanceName());
		if (entity == null)
		{
			entity = new ResidentBankResources();
			entity.setId(id);
			entity.setLocaleId(localeId);
		}

	}


	public ERIBLocale getLocale()
	{
		return locale;
	}

	public ResidentBankResources getEntity()
	{
		return entity;
	}
}
