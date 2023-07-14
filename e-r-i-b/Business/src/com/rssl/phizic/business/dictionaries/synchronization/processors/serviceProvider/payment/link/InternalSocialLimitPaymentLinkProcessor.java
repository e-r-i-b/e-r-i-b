package com.rssl.phizic.business.dictionaries.synchronization.processors.serviceProvider.payment.link;

import com.rssl.phizic.business.limits.link.InternalSocialLimitPaymentLink;

/**
 * @author komarov
 * @ created 17.03.2014
 * @ $Author$
 * @ $Revision$
 */
public class InternalSocialLimitPaymentLinkProcessor extends LimitPaymentsLinkBaseProcessor<InternalSocialLimitPaymentLink>
{
	@Override
	protected Class<InternalSocialLimitPaymentLink> getEntityClass()
	{
		return InternalSocialLimitPaymentLink.class;
	}

	@Override
	protected InternalSocialLimitPaymentLink getNewEntity()
	{
		return new InternalSocialLimitPaymentLink();
	}
}
