package com.rssl.phizic.business.dictionaries.synchronization.processors.serviceProvider.payment.link;

import com.rssl.phizic.business.limits.link.PhysicalExternalAccountLimitPaymentLink;

/**
 * @author komarov
 * @ created 17.03.2014
 * @ $Author$
 * @ $Revision$
 */
public class PhysicalExternalAccountLimitPaymentLinkProcessor extends LimitPaymentsLinkBaseProcessor<PhysicalExternalAccountLimitPaymentLink>
{
	@Override protected Class<PhysicalExternalAccountLimitPaymentLink> getEntityClass()
	{
		return PhysicalExternalAccountLimitPaymentLink.class;
	}

	@Override protected PhysicalExternalAccountLimitPaymentLink getNewEntity()
	{
		return new PhysicalExternalAccountLimitPaymentLink();
	}
}
