package com.rssl.phizic.operations.access;

import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.business.documents.AbstractLongOfferDocument;
import com.rssl.phizic.gate.payments.RefuseLongOffer;
import com.rssl.phizic.auth.modes.StrategyCondition;

/**
 * @author osminin
 * @ created 27.10.2010
 * @ $Author$
 * @ $Revision$
 * ѕроверка на то, что пришла за€вка на регул€рный платеж. –егул€рные платежи подтверждаем всегда!
 */
public class PaymentLongOfferStrategyCondition  implements StrategyCondition
{
	public boolean checkCondition(ConfirmableObject object)
	{
		return !(object instanceof AbstractLongOfferDocument && ((AbstractLongOfferDocument)object).isLongOffer()
					|| object instanceof RefuseLongOffer);
	}

	public String getWarning()
	{
		return null;
	}
}
