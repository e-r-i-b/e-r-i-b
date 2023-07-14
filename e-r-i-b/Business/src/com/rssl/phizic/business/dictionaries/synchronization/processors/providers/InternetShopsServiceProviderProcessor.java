package com.rssl.phizic.business.dictionaries.synchronization.processors.providers;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.InternetShopsServiceProvider;

/**
 * @author akrenev
 * @ created 29.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * Процессор записей биллинговых поставщиков интернет магазинов
 */

public class InternetShopsServiceProviderProcessor extends BillingServiceProviderProcessorBase<InternetShopsServiceProvider>
{
	@Override
	protected Class<InternetShopsServiceProvider> getEntityClass()
	{
		return InternetShopsServiceProvider.class;
	}

	@Override
	protected InternetShopsServiceProvider getNewProvider()
	{
		return new InternetShopsServiceProvider();
	}

	@Override
	protected void update(InternetShopsServiceProvider source, InternetShopsServiceProvider destination) throws BusinessException
	{
		super.update(source, destination);
		destination.setCheckOrder(source.isCheckOrder());
		destination.setUrl(source.getUrl());
		destination.setBackUrl(source.getBackUrl());
		destination.setAfterAction(source.isAfterAction());
		destination.setFormName(source.getFormName());
		destination.setSendChargeOffInfo(source.isSendChargeOffInfo());
		destination.setAvailableMobileCheckout(source.isAvailableMobileCheckout());
		destination.setFacilitator(source.isFacilitator());
	}
}
