package com.rssl.phizic.business.ext.sbrf.payments.handlers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.phizic.business.resources.external.PaymentAbilityERL;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.auth.modes.UserVisitingMode;

/**
 * @author vagin
 * @ created 16.08.2012
 * @ $Author$
 * @ $Revision$
 * ������� �������� ������� ��������(�����) �� �����������.
 * � ������ ���� ������ ����������(������� � ������� ���� ��� ����� �� ����������) ��� �������������� ��������� ��������� �� ��� ������ ���������� ������� ��������� �� ����(warning-message).
 */
public class CheckResourceVisibilityHandler extends BusinessDocumentHandlerBase
{
	private static final String WARNING_MESSAGE_PARAM_NAME = "warning-message";

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if(!(document instanceof AbstractPaymentDocument))
			throw new DocumentException("��������� ��������� AbstractPaymentDocument");

		AbstractPaymentDocument payment = (AbstractPaymentDocument) document;
		PaymentAbilityERL resourceLink = payment.getChargeOffResourceLink();

		if(resourceLink == null)
		{
		    log.error("�� ������ ����-��-�������� �������� ��� ������� � id:" + payment.getId());
			stateMachineEvent.addMessage(getParameter(WARNING_MESSAGE_PARAM_NAME));
		}

		try
		{
			if (UserVisitingMode.isEmployeeInputMode(PersonHelper.getLastClientLogin().getLastUserVisitingMode()))
				return;
		}
		catch(BusinessException ex)
		{
			throw new DocumentException(ex);
		}

		if(!resourceLink.getShowInSystem())
		{
			stateMachineEvent.addMessage(getParameter(WARNING_MESSAGE_PARAM_NAME));
			return;
		}
	}
}
