package com.rssl.phizic.business.ext.sbrf.payments.handlers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedDepartment;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.business.documents.InternalTransfer;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.AccountToCardTransfer;
import com.rssl.phizicgate.manager.ext.sbrf.ESBHelper;

/**
 * @author osminin
 * @ created 21.12.2010
 * @ $Author$
 * @ $Revision$
 *
 * ������� ���������� ��� ����, ��� �� ������ ������ ��������� ���������� � ���, ��� ��������� ����������,
 * ���������� ��� ������ ����� � ����� � ���������� �������, ����� ������������� �� ���������� � ����,
 * ������ ��� � ������������ ������������� �������� ����-�����
 * ����� ������� �� ���������.
 */
public class AccountToCardConvertionSRBDenyHandler extends BusinessDocumentHandlerBase
{
	private static final String ERROR_MESSAGE_PARAMETER_NAME = "error-message";

	public void process(StateObject object, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(object instanceof GateExecutableDocument))
			return;

		if (!(object instanceof InternalTransfer))
			return;

		InternalTransfer document = (InternalTransfer) object;
		if (document.getType()!= AccountToCardTransfer.class)
			return; // ���� �������� �� ����-�����, �� ����������

		if (!document.isConvertion())
			return; // ���� �������� �� �������������, �� ����������

		try
		{
			ExtendedDepartment department = (ExtendedDepartment) document.getDepartment();
			if (department.isEsbSupported())
				return; // ���� ������������� ���������� � ����, �� ����������
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}

		try
		{
			if (!ESBHelper.isSRBPayment(document))
			{
				return; // ���� ������ �� ���, �� ����������
			}
		}
		catch (GateException e)
		{
			throw new DocumentException(e);
		}

		// ������ ���������� � ������ ����������
		throw new DocumentLogicException(getParameter(ERROR_MESSAGE_PARAMETER_NAME));
	}
}
