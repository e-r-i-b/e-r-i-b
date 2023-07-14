package com.rssl.phizic.business.limits.link;

import com.rssl.phizic.gate.payments.ExternalCardsTransferToOtherBank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ќплата с карты на карту частному лицу вне —берЅанка
 *
 * @author khudyakov
 * @ created 21.08.2012
 * @ $Authors$
 * @ $Revision$
 */
public class PhysicalExternalCardLimitPaymentLink extends LimitPaymentsLinkBase
{
	private static final List<Class> typesOfPayments = new ArrayList<Class>();

	static
	{
		typesOfPayments.add(ExternalCardsTransferToOtherBank.class);               //ѕеревод физическому лицу с карты на карту в другой банк.
	}

	public List<Class> getPaymentTypes()
	{
		return Collections.unmodifiableList(typesOfPayments);
	}

	public LimitPaymentsType getLinkType()
	{
		return LimitPaymentsType.PHYSICAL_EXTERNAL_CARD_PAYMENT_LINK;
	}
}
