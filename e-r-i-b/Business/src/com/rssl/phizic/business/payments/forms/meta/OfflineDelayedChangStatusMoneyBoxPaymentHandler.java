package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.ChangeStatusMoneyBoxPayment;

/**
 * @author tisov
 * @ created 07.10.14
 * @ $Author$
 * @ $Revision$
 */

public class OfflineDelayedChangStatusMoneyBoxPaymentHandler extends OfflineDelayedHandlerBase
{
	@Override
	protected void innerProcess(BusinessDocument document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof ChangeStatusMoneyBoxPayment))
			throw new DocumentException("Некорректный тип документа. Ожидается BlockingCardClaim.");

		ChangeStatusMoneyBoxPayment claim = (ChangeStatusMoneyBoxPayment) document;

		if (checkESB(claim))
			return;
	}
}
