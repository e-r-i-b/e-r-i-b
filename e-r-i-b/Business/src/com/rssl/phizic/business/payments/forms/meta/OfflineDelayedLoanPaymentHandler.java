package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.LoanPayment;

/**
 * Хэндлер для определения недоступности внешних систем при погашении кредита
 * @author Pankin
 * @ created 10.02.2013
 * @ $Author$
 * @ $Revision$
 */

public class OfflineDelayedLoanPaymentHandler extends OfflineDelayedHandlerBase
{
	public void innerProcess(BusinessDocument document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof LoanPayment))
			throw new DocumentException("Некорректный тип документа. Ожидается LoanPayment.");

		LoanPayment payment = (LoanPayment) document;

		// если неактивен way
		if (checkResource(payment.getChargeOffResourceLink(), payment))
			return;
	}
}
