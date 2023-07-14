package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.AccountOpeningClaim;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;

/** ������� ��� �������� ������������ ������ �� �������� ������
 * @author akrenev
 * @ created 10.01.2012
 * @ $Author$
 * @ $Revision$
 */
// ��� ������ �� �������� ������ ����� ����������� �������� ���� �������� � �������.
// ���� ���� �������� ������ - �� ���������� ��������, ���� ������ - ����������, ����� ��������� � ���-����
public class AccountOpeningClaimActualDateHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof AccountOpeningClaim))
			throw new DocumentException("�������� ��� ������� id=" + ((BusinessDocument) document).getId() + " (��������� AccountOpeningClaim)");

		AccountOpeningClaim accountOpeningClaim = (AccountOpeningClaim) document;
		Calendar openingDate = DateHelper.startOfDay(accountOpeningClaim.getOpeningDate());
		Calendar currentDate = DateHelper.getCurrentDate();
		// ������� ���� ������ ���� ������ ����� �������� �����a
		if (currentDate.after(openingDate))
		{
			throw new DocumentLogicException("���� �������� ����� ������ ����������. ����������, ��������� � ���� ����� ������.");
		}
	}
}
