package com.rssl.phizic.operations.loanclaim;

import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;

/**
 * �������� �������� ������� � ��� ��� ���������� ��������� ������ �����������
 * @ author: Gololobov
 * @ created: 11.05.15
 * @ $Author$
 * @ $Revision$
 */
public class LoanClaimVisitOfficeOperation extends LoanClaimChangeStateOperation
{
	/**
	 * �������� ������� � ���
	 * @param claim
	 * @throws com.rssl.phizic.business.BusinessException
	 * @throws com.rssl.phizic.business.BusinessLogicException
	 */
	public void doVisitOffice(ExtendedLoanClaim claim) throws BusinessException, BusinessLogicException
	{
		fireEventLoanClaim(claim, DocumentEvent.NEED_VISIT_OFFICE);
	}
}
