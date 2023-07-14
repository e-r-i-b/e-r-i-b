package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.documents.BlockingCardClaim;
import com.rssl.phizic.business.documents.payments.BusinessDocument;

/**
 * ������� ��� ����������� ������������� ������� ������ ��� ���������� �����
 * @author Pankin
 * @ created 10.02.2013
 * @ $Author$
 * @ $Revision$
 */

public class OfflineDelayedBlockingCardClaimHandler extends OfflineDelayedHandlerBase
{
	public void innerProcess(BusinessDocument document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof BlockingCardClaim))
			throw new DocumentException("������������ ��� ���������. ��������� BlockingCardClaim.");

		BlockingCardClaim claim = (BlockingCardClaim) document;

		// ��������� ����������� ���� � �����
		if (checkESB(claim))
			return;
		// ��������� ����������� way
		else if (checkResource(claim.getBlockingCardLink(), claim))
			return;
	}
}
