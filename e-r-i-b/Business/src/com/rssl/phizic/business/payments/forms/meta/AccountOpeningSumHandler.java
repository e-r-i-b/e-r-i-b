package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.common.forms.state.StateObject;
import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.phizic.business.documents.AccountOpeningClaim;

/**
 * ������� ��� �������� ����� ���������� ��� �������� ������
 * @author Pankin
 * @ created 20.03.2012
 * @ $Author$
 * @ $Revision$
 */

public class AccountOpeningSumHandler extends BusinessDocumentHandlerBase
{
	private static final String LESS_SUM_MESSAGE = "����� ���������� �� ����� ���� ������ ������������ " +
			"������� �� ������. ����������, ����������� ������������ � ��������� ������, ������� ������ �������.";

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof AccountOpeningClaim))
		{
			throw new DocumentException("�������� ��� ������� id=" + ((BusinessDocument) document).getId() +
					" (��������� AccountOpeningClaim)");
		}

		AccountOpeningClaim accountOpeningClaim = (AccountOpeningClaim) document;

		// ����� ����������� ������ ��� ������� � ����������� ��������
		if (!accountOpeningClaim.isWithMinimumBalance())
			return;

		// ����������� ������� �� ����� ���� ������ ����� ����������
		if (accountOpeningClaim.getIrreducibleAmmount().compareTo(accountOpeningClaim.getDestinationAmount().getDecimal()) > 0)
			throw new DocumentLogicException(LESS_SUM_MESSAGE);
	}
}
