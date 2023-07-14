package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.business.loanclaim.LoanClaimHelper;

/**
 * @author Balovtsev
 * @since 16.02.2015.
 */
public class ExtendedLoanClaimAvailabilityHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		if (!(document instanceof ExtendedLoanClaim))
		{
			throw new DocumentException("�������� ��� ������� id=" + document.getId() + " (��������� ExtendedLoanClaim)");
		}

		try
		{
			if (!LoanClaimHelper.isExtendedLoanClaimAvailable())
			{
				throw new DocumentLogicException("��������� ������! � ���������, ���������� ������ �� ������ � �������� ������ ����������. ����������, ���������� � ��������� �����.");
			}
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}
