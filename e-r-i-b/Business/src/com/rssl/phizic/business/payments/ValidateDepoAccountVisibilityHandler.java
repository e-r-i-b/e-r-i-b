package com.rssl.phizic.business.payments;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.AbstractDepoAccountClaim;
import com.rssl.phizic.business.resources.external.DepoAccountLink;
import com.rssl.phizic.gate.depo.DepoAccount;
import com.rssl.phizic.gate.depo.DepoAccountState;
import com.rssl.phizic.utils.MockHelper;

/** �������� ����� ���� �� ������������ � �������������� � �������
 * @author akrenev
 * @ created 27.07.2011
 * @ $Author$
 * @ $Revision$
 */

public class ValidateDepoAccountVisibilityHandler extends BusinessDocumentHandlerBase
{
	private static final String MESSAGE_NOT_IN_SYSTEN= "�� �� ������ ����������� ������ ��������. " +
			"��� ������� �������� ��������� ��������� ������ ����� ���� � ������ ���� " +
			"���������� � ���������� ������������ � ���������� ��������� ���������.";
	private static final String MESSAGE_CLOSED= "�� �� ������ ��������� ������ ��������: " +
			"��������� ���� ���� ������. ����������, �������������� ��������.";

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof AbstractDepoAccountClaim))
			throw new DocumentException("�������� ��� �������. ��������� AbstractDepoAccountClaim");

		AbstractDepoAccountClaim payment = (AbstractDepoAccountClaim) document;

		DepoAccountLink depoAccLink = payment.getDepoLink();
		if (depoAccLink == null)
			throw new DocumentException("�� ������ ���� �� ���� ����");

		DepoAccount depoAccount = depoAccLink.getDepoAccount();
		if (MockHelper.isMockObject(depoAccount))
			throw new DocumentException("�� ������ ���� ����, ����� " + depoAccLink.getAccountNumber());

		if (DepoAccountState.closed == depoAccount.getState())
			throw makeValidationFail(MESSAGE_CLOSED);

		if (!depoAccLink.getShowInSystem())
			throw makeValidationFail(MESSAGE_NOT_IN_SYSTEN);
	}
}
