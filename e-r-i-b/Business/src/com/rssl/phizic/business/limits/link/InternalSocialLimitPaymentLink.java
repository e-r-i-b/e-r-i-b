package com.rssl.phizic.business.limits.link;

import com.rssl.phizic.gate.payments.AccountToCardTransfer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Перевод оплаты на соц. карту
 *
 * @author khudyakov
 * @ created 21.08.2012
 * @ $Authors$
 * @ $Revision$
 */
public class InternalSocialLimitPaymentLink extends LimitPaymentsLinkBase
{
	private static final List<Class> typesOfPayments = new ArrayList<Class>();

	static
	{
		typesOfPayments.add(AccountToCardTransfer.class);            //Перевод с карты на счет одного клиента без конверсии
	}

	public LimitPaymentsType getLinkType()
	{
		return LimitPaymentsType.INTERNAL_SOCIAL_TRANSFER_LINK;
	}

	public List<Class> getPaymentTypes()
	{
		return Collections.unmodifiableList(typesOfPayments);
	}
}
