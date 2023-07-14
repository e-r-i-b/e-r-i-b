package com.rssl.phizic.business.dictionaries.synchronization.processors.serviceProvider.payment.link;

import com.rssl.phizic.business.limits.link.PhysicalExternalCardLimitPaymentLink;

/**
 * @author komarov
 * @ created 17.03.2014
 * @ $Author$
 * @ $Revision$
 */
public class PhysicalExternalCardLimitPaymentLinkProcessor extends LimitPaymentsLinkBaseProcessor<PhysicalExternalCardLimitPaymentLink>
{
	@Override protected Class<PhysicalExternalCardLimitPaymentLink> getEntityClass()
	{
		return PhysicalExternalCardLimitPaymentLink.class;
	}

	@Override protected PhysicalExternalCardLimitPaymentLink getNewEntity()
	{
		return new PhysicalExternalCardLimitPaymentLink();
	}
}
