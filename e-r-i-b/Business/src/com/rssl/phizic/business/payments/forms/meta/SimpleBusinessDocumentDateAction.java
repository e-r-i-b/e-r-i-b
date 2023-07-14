package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;

import java.util.Calendar;

/**
 * @author Krenev
 * @ created 09.07.2008
 * @ $Author$
 * @ $Revision$
 */
//TODO ����� ����� ��������� ���� ������ ��������� ������� �����.
//������ � ������ �����-�� ������! ���� �����������
public class SimpleBusinessDocumentDateAction extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		Calendar currentDate = Calendar.getInstance();
		((BusinessDocument) document).setAdmissionDate(currentDate);
	}
}
