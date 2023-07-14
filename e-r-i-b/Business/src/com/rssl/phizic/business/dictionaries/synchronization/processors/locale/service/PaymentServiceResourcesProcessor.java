package com.rssl.phizic.business.dictionaries.synchronization.processors.locale.service;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.payment.services.locale.PaymentServiceResources;
import com.rssl.phizic.business.dictionaries.synchronization.processors.locale.ResourcesProcessorBase;

/**
 * @author mihaylov
 * @ created 24.10.14
 * @ $Author$
 * @ $Revision$
 *
 * Процессор для синхронизации локалезависимых данных в услугах
 */
public class PaymentServiceResourcesProcessor extends ResourcesProcessorBase<PaymentServiceResources>
{
	@Override
	protected Class<PaymentServiceResources> getEntityClass()
	{
		return PaymentServiceResources.class;
	}

	@Override
	protected PaymentServiceResources getNewEntity()
	{
		return new PaymentServiceResources();
	}

	@Override
	protected void update(PaymentServiceResources source, PaymentServiceResources destination) throws BusinessException
	{
		super.update(source, destination);
		destination.setName(source.getName());
		destination.setDescription(source.getDescription());
	}
}
