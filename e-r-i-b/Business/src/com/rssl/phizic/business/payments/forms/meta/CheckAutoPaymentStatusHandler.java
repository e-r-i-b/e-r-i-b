package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.longoffer.autopayment.AutoPaymentHelper;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPayment;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPaymentStatus;
import com.rssl.phizic.gate.payments.autopayment.EditAutoPayment;
import com.rssl.phizic.gate.payments.autopayment.RefuseAutoPayment;

/**
 * �������� ������� ����������� ��� ���������� ��������
 * @author gladishev
 * @ created 12.05.2011
 * @ $Author$
 * @ $Revision$
 */
public class CheckAutoPaymentStatusHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof AutoPayment))
			throw new DocumentException("�������� ��������� AutoPayment");

		AutoPayment payment = (AutoPayment) document;
		AutoPaymentStatus status = payment.getReportStatus();
		if (payment.getType() == EditAutoPayment.class)
		{
			if (status != AutoPaymentStatus.ACTIVE)
				throw new DocumentLogicException("�� �� ������ ������������� ���������� � ������� " + getStatusName(status));
		}
		else if (payment.getType() == RefuseAutoPayment.class)
		{
			if (!AutoPaymentHelper.isRefuseSupported(payment))
				throw new DocumentLogicException("�� �� ������ �������� ���������� � ������� " + getStatusName(status));
		}
		else
			throw new DocumentException("������������ ��� �������");
	}

	private String getStatusName(AutoPaymentStatus status)
	{
		switch (status)
		{
			case NEW: return "�����";
			case ACTIVE: return "��������";
			case UPDATING: return "�����������";
			case BLOCKED: return "������������";
			case DELETED: return "������";
			case NO_CREATE: return "�� ����������";
			default: throw new IllegalArgumentException("����������� �������� ������� �����������");
		}
	}
}
