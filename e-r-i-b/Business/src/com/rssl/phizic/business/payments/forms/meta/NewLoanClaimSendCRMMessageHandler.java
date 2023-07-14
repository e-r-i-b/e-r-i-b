package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.ikfl.crediting.CRMMessageService;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;

/**
 * ������� �������� � CRM ��������� � �������� ����� ������ �� ������
 *
 * @ author: Gololobov
 * @ created: 10.03.15
 * @ $Author$
 * @ $Revision$
 */
public class NewLoanClaimSendCRMMessageHandler extends BusinessDocumentHandlerBase
{
	private static final CRMMessageService crmService = new CRMMessageService();

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		if (!(document instanceof ExtendedLoanClaim))
			throw new DocumentException("�������� ��� ������� id=" + document.getId() + " (��������� ExtendedLoanClaim)");
		ExtendedLoanClaim claim = (ExtendedLoanClaim) document;

		//���� �� ���������� ������ � ���, ������ ������ ���� ��������� ���������
		if (!stateMachineEvent.getNextState().equals("VISIT_OFFICE"))
			if (!claim.getCompleteAppFlag())
				return;

		try
		{
			//�������� � CRM ��������� � �������� ����� ������ �� ������
			crmService.createNewExtendedLoanClaim(claim);
		}
		catch (Exception e)
		{
			throw new DocumentException(e);
		}

	}
}
