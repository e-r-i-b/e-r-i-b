package com.rssl.phizic.business.dictionaries.synchronization.processors.serviceProvider.payment.link;

import com.rssl.phizic.business.limits.link.JuridicalExternalLimitPaymentLink;

/**
 * @author komarov
 * @ created 17.03.2014
 * @ $Author$
 * @ $Revision$
 */
public class JuridicalExternalLimitPaymentLinkProcessor extends LimitPaymentsLinkBaseProcessor<JuridicalExternalLimitPaymentLink>
{
	@Override protected Class<JuridicalExternalLimitPaymentLink> getEntityClass()
	{
		return JuridicalExternalLimitPaymentLink.class;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override protected JuridicalExternalLimitPaymentLink getNewEntity()
	{
		return new JuridicalExternalLimitPaymentLink();
	}
}
