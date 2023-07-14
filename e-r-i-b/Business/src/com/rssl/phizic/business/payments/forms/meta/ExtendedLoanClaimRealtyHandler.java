package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.gate.loanclaim.type.RealEstate;

/**
 * @author EgorovaA
 * @ created 23.01.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * ���� ��������� ������, ��� ��������� � ����������� ��������, ������ ���� ������� ���������� � ������������ ���� "��������"
 */
public class ExtendedLoanClaimRealtyHandler extends BusinessDocumentHandlerBase
{
	private static String DEFAULT_ERROR_MESSAGE = "�� �������, ��� ���������� �� ������ ������������ ���������� �� ��������� ������������� �� ��������. ������� ���������� � ��������.";

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof ExtendedLoanClaim))
		{
			throw new DocumentException("�������� ��� ������ id=" + ((BusinessDocument) document).getId() + " (��������� ExtendedLoanClaim)");
		}

		ExtendedLoanClaim loanClaim = (ExtendedLoanClaim) document;

		if (loanClaim.getApplicantResidenceRight().isNeedRealtyInfo())
		{
			for (RealEstate realEstate : loanClaim.getApplicantRealEstates())
			{
				if (realEstate.getType().isResidence())
					return;
			}
			throw new DocumentLogicException(DEFAULT_ERROR_MESSAGE);
		}
	}

}
