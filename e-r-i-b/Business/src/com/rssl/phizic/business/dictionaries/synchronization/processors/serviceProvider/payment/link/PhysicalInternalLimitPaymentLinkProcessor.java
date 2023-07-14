package com.rssl.phizic.business.dictionaries.synchronization.processors.serviceProvider.payment.link;

import com.rssl.phizic.business.limits.link.PhysicalInternalLimitPaymentLink;

/**
 * @author komarov
 * @ created 17.03.2014
 * @ $Author$
 * @ $Revision$
 */
public class PhysicalInternalLimitPaymentLinkProcessor extends LimitPaymentsLinkBaseProcessor<PhysicalInternalLimitPaymentLink>
{
	@Override protected Class<PhysicalInternalLimitPaymentLink> getEntityClass()
	{
		return PhysicalInternalLimitPaymentLink.class;
	}

	@Override protected PhysicalInternalLimitPaymentLink getNewEntity()
	{
		return new PhysicalInternalLimitPaymentLink();
	}
}
