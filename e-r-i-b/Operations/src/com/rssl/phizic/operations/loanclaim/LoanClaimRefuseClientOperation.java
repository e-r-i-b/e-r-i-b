package com.rssl.phizic.operations.loanclaim;

import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;

/**
 * �������� ������ ������� �� ���������� ������ �� ������
 *
 * @ author: Gololobov
 * @ created: 11.05.15
 * @ $Author$
 * @ $Revision$
 */
public class LoanClaimRefuseClientOperation extends LoanClaimChangeStateOperation
{
	/**
	 * ����� ������� �� ���������� ������
	 * @param claim
	 * @throws com.rssl.phizic.business.BusinessLogicException
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public void doRefuseClient(ExtendedLoanClaim claim) throws Exception
	{
		fireEventLoanClaim(claim, DocumentEvent.REFUSE);
		//�������� � CRM ��������� �� ���������� ������� ������
		sendRefuseMessageToCRM(claim);
	}
}
