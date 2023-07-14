package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.IMAOpeningClaim;
import com.rssl.phizic.business.documents.payments.ExchangeCurrencyTransferBase;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.util.ApplicationUtil;

/**
 * �������� ��� �������/������� ����. ��������
 * @author Pankin
 * @ created 30.06.2011
 * @ $Author$
 * @ $Revision$
 */

public class IMAPaymentHandler extends BusinessDocumentHandlerBase
{
	private static final String CONVERTION_RATE_NOT_FOUND_MESSAGE = "�� ����������� �������� �������� " +
			"�������� ����������. ��������� ������� �����.";
	private static final String ROUND_SUMM_MESSAGE1 = "�� ���������� ������ �������� �������� �� ���������.";
	private static final String ROUND_SUMM_MESSAGE2 = "�������� ��������! ��������� ���� ����� ���� ����������� � ������������ � ������ ���������.";

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof ExchangeCurrencyTransferBase))
			throw new DocumentException("�������� ��� �������. ��������� ExchangeCurrencyTransferBase");

		ExchangeCurrencyTransferBase transfer = (ExchangeCurrencyTransferBase) document;
		// ���� �� �������� ���� ���������, �� ���������� ������
		if (compareCurrency(transfer) && transfer.getConvertionRate() == null)
			throw new DocumentLogicException(CONVERTION_RATE_NOT_FOUND_MESSAGE);

		if (ApplicationUtil.isMobileApi())
		{
			if (transfer instanceof IMAOpeningClaim)
				stateMachineEvent.addMessage(ROUND_SUMM_MESSAGE1);
			// ���� ��������� �������� ����� ���� �����������, ����� ������� ��������������� ��������������
			if (transfer.getValueOfExactAmount().compareTo(transfer.getExactAmount().getDecimal()) != 0)
				stateMachineEvent.addMessage(ROUND_SUMM_MESSAGE2);
		}

	}

	private boolean compareCurrency(ExchangeCurrencyTransferBase transfer) throws DocumentException
	{
		try
		{
			return transfer.getChargeOffCurrency() != transfer.getDestinationCurrency();
		}
		catch (GateException e)
		{
			throw new DocumentException(e);
		}
	}
}
