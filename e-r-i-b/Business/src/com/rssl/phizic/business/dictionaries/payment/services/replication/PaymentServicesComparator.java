package com.rssl.phizic.business.dictionaries.payment.services.replication;

import com.rssl.phizic.business.dictionaries.payment.services.PaymentService;
import com.rssl.phizic.common.AbstractCompatator;

/**
 * @author khudyakov
 * @ created 03.12.2010
 * @ $Author$
 * @ $Revision$
 */
public class PaymentServicesComparator extends AbstractCompatator 
{
	public int compare(Object o1, Object o2)
	{
		if (o1 == null && o2 == null)
		{
			return 0;
		}

		if (o1 == null || o2 == null)
		{
			return -1;
		}

		PaymentService service1 = (PaymentService) o1;
		PaymentService service = (PaymentService) o2;

		int compareResult = service1.getSynchKey().compareTo(service.getSynchKey());
		if (compareResult != 0)
			return compareResult;

		if (!isObjectsEquals(service1.getName(), service.getName()))
			return -1;

		if (!isObjectsEquals(service1.getDescription(), service.getDescription()))
			return -1;

		if (!isObjectsEquals(service1.getParentServices(), service.getParentServices()))
			return -1;
		if (!isObjectsEquals(service1.getDefaultImage(), service.getDefaultImage()))
			return -1;
		return 0;
	}
}
