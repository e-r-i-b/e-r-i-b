package com.rssl.phizic.operations.loanreport;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.config.loanreport.CreditBureauConstants;
import com.rssl.phizic.operations.config.EditPropertiesOperation;

/**
 * User: Moshenko
 * Date: 01.10.14
 * Операция настройки идентификатора поставщика услуги для ОКБ
 */
public class EditBKIProviderIdOperation extends EditPropertiesOperation<Restriction>
{
	private final ServiceProviderService serviceProviderService = new ServiceProviderService();

	@Override
	public void save() throws BusinessException, BusinessLogicException
	{
		String providerUUID = getEntity().get(CreditBureauConstants.SERVICE_PROVIDER_OKB_ID);
		validateProviderId(providerUUID);
		super.save();
	}

	private void validateProviderId(String providerUUID) throws BusinessException, BusinessLogicException
	{
		ServiceProviderBase provider = serviceProviderService.findByMultiBlockId(providerUUID);
		if (provider == null)
			throw new BusinessLogicException("Не найден поставщик услуг для ОКБ по UID " + providerUUID);
	}
}
