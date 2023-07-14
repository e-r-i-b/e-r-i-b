package com.rssl.phizic.business.dictionaries.payment.services;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.payment.services.replication.PaymentServicesComparator;

import java.util.List;

/**
 * @author gladishev
 * @ created 09.06.2010
 * @ $Author$
 * @ $Revision$
 */
public class PaymentServiceSynchronizer
{
	private static final PaymentServiceService paymentServiceService = new PaymentServiceService();
	private static final PaymentServicesComparator comparator = new PaymentServicesComparator();
	private List<PaymentService> services;

	public PaymentServiceSynchronizer(List<PaymentService> services)
	{
		this.services = services;
	}

	public void update() throws BusinessException
	{
		for (PaymentService service: services)
		{
			try
			{
				PaymentService existingService = paymentServiceService.findBySynchKey(service.getSynchKey().toString());
				if (comparator.compare(service, existingService) != 0)
				{
					if(existingService != null)
						service.setId(existingService.getId());
					paymentServiceService.addOrUpdate(service);
				}
			}
			catch (DublicatePaymentServiceException e)
			{
				throw new BusinessException(e);
			}
		}
	}
}
