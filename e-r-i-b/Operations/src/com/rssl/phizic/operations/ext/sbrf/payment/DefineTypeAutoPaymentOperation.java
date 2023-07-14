package com.rssl.phizic.operations.ext.sbrf.payment;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.*;
import com.rssl.phizic.business.longoffer.autopayment.AutoPaymentHelper;
import com.rssl.phizic.operations.OperationBase;

/**
 * ќпераци€ дл€ определени€ типа автоплатежа, который можно создать в адрес указанного поставщика(iqw или шинный)
 * @author niculichev
 * @ created 16.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class DefineTypeAutoPaymentOperation extends OperationBase
{
	private static final ServiceProviderService serviceProviderService = new ServiceProviderService();

	private ServiceProviderShort provider;

	public void initialize(Long providerId) throws BusinessException
	{
		if(providerId == null)
			throw new BusinessException("Ќе указан поставщик, в адрес которого совершаетс€ автоплатеж");

		ServiceProviderShort serviceProvide = serviceProviderService.findShortProviderById(providerId);
		if(serviceProvide == null)
			throw new BusinessException("ѕоставщика с id = " + providerId + " не существует");

		if(!(serviceProvide.getKind().equals("B")) || !AutoPaymentHelper.checkAutoPaymentSupport(serviceProvide))
			throw new BusinessException("¬ адрес поставщика с id = " + providerId + " невозможно создать автоплатеж");

		provider =  serviceProvide;
	}

	public boolean isESBAutoPayProvider() throws BusinessException
	{
		//не IQW и карточный и доступен автоплатеж
		return (provider.getAccountType() == AccountType.CARD || provider.getAccountType() == AccountType.ALL)
				&& !AutoPaymentHelper.isIQWProvider(provider.getSynchKey())
				&& AutoPaymentHelper.checkAutoPaymentSupport(provider);
	}
}
