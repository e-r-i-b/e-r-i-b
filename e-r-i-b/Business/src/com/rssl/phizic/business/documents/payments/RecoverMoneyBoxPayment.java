package com.rssl.phizic.business.documents.payments;

import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.payments.longoffer.RecoverCardToAccountLongOffer;

/**
 * @author tisov
 * @ created 08.10.14
 * @ $Author$
 * @ $Revision$
 * заявка на возобновление копилки
 */

public class RecoverMoneyBoxPayment extends ChangeStatusMoneyBoxPayment
{
	public Class<? extends GateDocument> getType()
	{
		return RecoverCardToAccountLongOffer.class;
	}

	public FormType getFormType()
	{
		return FormType.RECOVER_CARD_TO_ACCOUNT_AUTO_SUBSCRIPTION_CLAIM;
	}
}

