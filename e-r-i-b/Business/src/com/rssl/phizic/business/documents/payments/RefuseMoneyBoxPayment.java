package com.rssl.phizic.business.documents.payments;

import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.payments.longoffer.RefuseCardToAccountLongOffer;

/**
 * @author tisov
 * @ created 08.10.14
 * @ $Author$
 * @ $Revision$
 * заявка на закрытие копилки
 */

public class RefuseMoneyBoxPayment extends ChangeStatusMoneyBoxPayment
{
	public Class<? extends GateDocument> getType()
	{
		return RefuseCardToAccountLongOffer.class;
	}

	public FormType getFormType()
	{
		return FormType.REFUSE_CARD_TO_ACCOUNT_AUTO_SUBSCRIPTION_CLAIM;
	}
}
