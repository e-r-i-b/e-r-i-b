package com.rssl.phizgate.common.payments;

import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.documents.DocumentService;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.Calendar;

/**
 * CommissionCalculator �� ���������, ���������� �������� ���������� �� �����,
 * ���� ������ ������ ������ ��� � ������� �������� �������
 *
 * @author khudyakov
 * @ created 21.03.2011
 * @ $Author$
 * @ $Revision$
 */
public class DefaultCommissionCalculator extends AbstractCommissionCalculator
{
	public void calcCommission(GateDocument transfer) throws GateException, GateLogicException
	{
		Calendar currentDate = Calendar.getInstance();
		currentDate.add(Calendar.DAY_OF_MONTH, -1);

		//���� ����� ���� �������� ������� ������ ������ ������ ���,
		//�� ������������� �������� ������
		if (currentDate.after(transfer.getClientCreationDate()))
		{
			DocumentService documentService = GateSingleton.getFactory().service(DocumentService.class);
			documentService.prepare(transfer);
		}
	}
}
