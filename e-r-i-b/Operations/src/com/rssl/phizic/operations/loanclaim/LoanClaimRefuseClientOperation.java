package com.rssl.phizic.operations.loanclaim;

import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;

/**
 * Операция отказа клиента от заполнения заявки на кредит
 *
 * @ author: Gololobov
 * @ created: 11.05.15
 * @ $Author$
 * @ $Revision$
 */
public class LoanClaimRefuseClientOperation extends LoanClaimChangeStateOperation
{
	/**
	 * Отказ клиента от заполнения заявки
	 * @param claim
	 * @throws com.rssl.phizic.business.BusinessLogicException
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public void doRefuseClient(ExtendedLoanClaim claim) throws Exception
	{
		fireEventLoanClaim(claim, DocumentEvent.REFUSE);
		//Отправка в CRM сообщения об обновлении статуса заявки
		sendRefuseMessageToCRM(claim);
	}
}
