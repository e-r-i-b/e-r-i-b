package com.rssl.phizic.business.dictionaries.synchronization.processors.serviceProvider.payment.link;

import com.rssl.phizic.business.limits.link.ConversionLimitPaymentLink;
import com.rssl.phizic.business.limits.link.LimitPaymentsLinkBase;

/**
 * @author komarov
 * @ created 17.03.2014
 * @ $Author$
 * @ $Revision$
 */
public class ConversionLimitPaymentLinkProcessor extends LimitPaymentsLinkBaseProcessor<ConversionLimitPaymentLink>
{
	@Override
	protected Class<ConversionLimitPaymentLink> getEntityClass()
	{
		return ConversionLimitPaymentLink.class;
	}

	@Override
	protected ConversionLimitPaymentLink getNewEntity()
	{
		return new ConversionLimitPaymentLink();
	}
}
