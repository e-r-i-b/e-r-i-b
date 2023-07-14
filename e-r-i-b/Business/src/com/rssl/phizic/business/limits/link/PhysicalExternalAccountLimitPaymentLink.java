package com.rssl.phizic.business.limits.link;

import com.rssl.phizic.gate.payments.AccountRUSPayment;
import com.rssl.phizic.gate.payments.CardRUSPayment;
import com.rssl.phizic.gate.payments.longoffer.AccountRUSPaymentLongOffer;
import com.rssl.phizic.gate.payments.longoffer.CardRUSPaymentLongOffer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ќплата на счет частному лицу в другой банк с карты/со счета
 *
 * @author khudyakov
 * @ created 21.08.2012
 * @ $Authors$
 * @ $Revision$
 */
public class PhysicalExternalAccountLimitPaymentLink extends LimitPaymentsLinkBase
{
	private static final List<Class> typesOfPayments = new ArrayList<Class>();

	static
	{
		typesOfPayments.add(AccountRUSPayment.class);                   //ѕеревод со счета на счет физическому лицу в другой банк через платежную систему –оссии.
		typesOfPayments.add(AccountRUSPaymentLongOffer.class);          //ƒлительное поручение на перевод со счета на счет физическому лицу в другой банк через платежную систему –оссии.
		typesOfPayments.add(CardRUSPayment.class);                      //ѕеревод с карты на счет физическому лицу в другой банк через платежную систему –оссии.
		typesOfPayments.add(CardRUSPaymentLongOffer.class);             //ƒлительное поручение на перевод с карты на счет физическому лицу в другой банк через платежную систему –оссии.
	}

	public List<Class> getPaymentTypes()
	{
		return Collections.unmodifiableList(typesOfPayments);
	}

	public LimitPaymentsType getLinkType()
	{
		return LimitPaymentsType.PHYSICAL_EXTERNAL_ACCOUNT_PAYMENT_LINK;
	}
}
