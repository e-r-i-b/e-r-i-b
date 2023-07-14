package com.rssl.phizic.business.ext.sbrf.payments.handlers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.payments.CreateMoneyBoxPayment;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;

/**
 * @author vagin
 * @ created 12.03.15
 * @ $Author$
 * @ $Revision$
 * ������� �������� ������������ ������������� ��������/�������������� ������� � ���������� ��������� �������� � ����������.
 */
public class CheckMoneyBoxResourcesHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		if (!(CreateMoneyBoxPayment.class.isAssignableFrom(document.getClass())))
			throw new DocumentException("��������� ��������� CreateMoneyBoxPayment");

		CreateMoneyBoxPayment moneyBoxPayment = (CreateMoneyBoxPayment) document;

		if (!DocumentHelper.isAccountForMoneyBoxMatched((AccountLink) moneyBoxPayment.getDestinationResourceLink()))
			throw new DocumentLogicException("��������� ���� ����� ����� �� �������� ��� ����������� �������. ���������� �������� ������ �����.");

		if (!DocumentHelper.isCardForMoneyBoxMatched((CardLink) moneyBoxPayment.getChargeOffResourceLink()))
			throw new DocumentLogicException("�� ��������� ���� ����� �� �������� ����������� �������. ���������� �������� ������ �����.");
	}
}
