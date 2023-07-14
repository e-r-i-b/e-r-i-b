package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.gate.payments.ReceiverCardType;

/**
 * @author lepihina
 * @ created 19.02.2013
 * @ $Author$
 * @ $Revision$
 *
 * ������� ����������� ��������� � ��������
 */
public class AddPaymentCommissionSaveHandler extends BusinessDocumentHandlerBase
{
	private static final String DEFAULT_PREFIX_MESSAGE = "�� ������ �������� ����� ��������� �������� � ������������ � �������� �����. ";
	private static final String FROM_ACCOUNT_COMMISSION_MESSAGE = DEFAULT_PREFIX_MESSAGE + "����� �������� �� ������ ���������� " +
			"<a href='http://www.sberbank.ru/common/img/uploaded/files/pdf/person/bank_cards/Perevody__Tarify.pdf' target='_blank'>�����</a>.";
	private static final String DEFAULT_COMMISSION_MESSAGE_API = DEFAULT_PREFIX_MESSAGE + "����� �������� �� ������ ���������� �� ����� �����.";
	private static final String FROM_CARD_COMMISSION_MESSAGE = DEFAULT_PREFIX_MESSAGE + "����� �������� �� ������ ���������� " +
			"<a href='http://www.sberbank.ru/common/img/uploaded/files/pdf/person/bank_cards/Beznalichnoe_perechislenie.pdf' target='_blank'>�����</a>.";
	private static final String ZERO_COMMISSION_MASSEGE = "�� ���������� ������ �������� �������� �� ���������.";
	private static final String MASTERCARD_EXTERNAL_CARD_COMMISSION_MASSEGE = "�� ���������� ������ �������� ��������� �������� � ������� 1,5% �� ����� ��������, �� �� ����� 30 ������.";

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof RurPayment))
			throw new DocumentException("document ������ ���� RurPayment");

		RurPayment payment = (RurPayment) document;

		if (payment.getCommission() != null)
		{
			//���� �������� �������, �� �������� �� ���� ������������.
			if (payment.getCommission().isZero())
			{
				stateMachineEvent.addMessage(ZERO_COMMISSION_MASSEGE);
			}
			return;
		}

		//���� �������� �� ������, �� ������� ����������� ��������� � ����������� �������� ��������.
		if (payment.getChargeOffResourceType() == ResourceType.CARD)
		{
			//������ �� �����
			if (ReceiverCardType.MASTERCARD == payment.getReceiverCardType())
			{
				//��� ������ ���� - ���� ���������
				stateMachineEvent.addMessage(MASTERCARD_EXTERNAL_CARD_COMMISSION_MASSEGE);
			}
			else if (ApplicationUtil.isApi())
			{
				//��� ��� - ��� ������
				stateMachineEvent.addMessage(DEFAULT_COMMISSION_MESSAGE_API);
			}
			else
			{
				//��� ��������� ������� - � �������
				stateMachineEvent.addMessage(FROM_CARD_COMMISSION_MESSAGE);
			}
		}
		else if (payment.getChargeOffResourceType() == ResourceType.ACCOUNT)
		{
			if (ApplicationUtil.isApi())
			{
				//��� ��� - ��� ������
				stateMachineEvent.addMessage(DEFAULT_COMMISSION_MESSAGE_API);
			}
			else
			{
				//��� ��������� ������� - � �������
				stateMachineEvent.addMessage(FROM_ACCOUNT_COMMISSION_MESSAGE);
			}
		}
	}
}
