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
 * �������� �� ��, ��� ������ ������ �� ���������� ������. ���������� ������� ������������ ������!
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
