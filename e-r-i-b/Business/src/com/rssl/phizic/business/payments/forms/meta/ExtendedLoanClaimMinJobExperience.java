package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.gate.loanclaim.type.Employee;
import com.rssl.phizic.gate.loanclaim.type.SbrfRelation;

/**
 * �������� ������������ ����� �� ��������� ����� ������
 * @author Puzikov
 * @ created 17.04.14
 * @ $Author$
 * @ $Revision$
 */

public class ExtendedLoanClaimMinJobExperience extends BusinessDocumentHandlerBase
{
	private static final String ERROR_MESSAGE = "���� ������ �� ��������� ����� �� ������������� �������� �������";

	private static final String COMMON_CONDITION_PRODUCT_CODE = "10";

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof ExtendedLoanClaim))
		{
			throw new DocumentException("�������� ��� ������ id=" + ((BusinessDocument) document).getId() + " (��������� ExtendedLoanClaim)");
		}
		ExtendedLoanClaim loanClaim = (ExtendedLoanClaim) document;

		if (loanClaim.getSbrfRelation() != SbrfRelation.NOT_RELATED_TO_SBER)
			return;

		Employee applicantAsEmployee = loanClaim.getApplicantAsEmployee();
		//��� ������� ��������� � ����������� �� ���������
		if (applicantAsEmployee == null)
			return;

		if (COMMON_CONDITION_PRODUCT_CODE.equals(loanClaim.getLoanProductCode()) && applicantAsEmployee.getJobExperience().isLoanNotAllowed())
			throw new DocumentLogicException(ERROR_MESSAGE);
	}
}
