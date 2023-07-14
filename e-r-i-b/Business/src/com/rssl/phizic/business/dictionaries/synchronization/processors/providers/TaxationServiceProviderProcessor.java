package com.rssl.phizic.business.dictionaries.synchronization.processors.providers;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.TaxationServiceProvider;

/**
 * @author akrenev
 * @ created 29.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * Процессор записей налоговых получателей платежа
 */

public class TaxationServiceProviderProcessor extends ProviderProcessorBase<TaxationServiceProvider>
{
	@Override
	protected Class<TaxationServiceProvider> getEntityClass()
	{
		return TaxationServiceProvider.class;
	}

	@Override
	protected TaxationServiceProvider getNewProvider()
	{
		return new TaxationServiceProvider();
	}

	@Override
	protected void update(TaxationServiceProvider source, TaxationServiceProvider destination) throws BusinessException
	{
		super.update(source, destination);
		destination.setFullPayment(source.isFullPayment());
		destination.setPayType(source.getPayType());
	}
}
