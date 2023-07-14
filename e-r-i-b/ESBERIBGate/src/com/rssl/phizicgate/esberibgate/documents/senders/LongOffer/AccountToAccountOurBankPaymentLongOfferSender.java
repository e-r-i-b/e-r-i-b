package com.rssl.phizicgate.esberibgate.documents.senders.LongOffer;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.gate.longoffer.SumType;
import com.rssl.phizic.gate.payments.AccountIntraBankPayment;
import com.rssl.phizic.gate.payments.longoffer.AccountIntraBankPaymentLongOffer;
import com.rssl.phizicgate.esberibgate.messaging.BackRefInfoRequestHelper;
import com.rssl.phizicgate.esberibgate.ws.jms.common.document.AbstractOnlineJMSDocumentSender;

/**
 * @author akrenev
 * @ created 18.05.2015
 * @ $Author$
 * @ $Revision$
 *
 * ������ ������ "������� ����������� ���� �� ������ �� ���� � ������ ���� ������ ��������� ������(���������� ���������)."
 */

public class AccountToAccountOurBankPaymentLongOfferSender extends AbstractOnlineJMSDocumentSender
{
	/**
	 * �����������
	 * @param factory ������� �����
	 */
	public AccountToAccountOurBankPaymentLongOfferSender(GateFactory factory)
	{
		super(factory);
	}

	public final void send(GateDocument document) throws GateException, GateLogicException
	{
		process(new AccountToAccountOurBankPaymentLongOfferProcessor(getFactory(), (AccountIntraBankPaymentLongOffer) document, getServiceName(document)));
	}

	public void repeatSend(GateDocument document) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("��������� �������� ������ �� ������� ����������� ���� �� ������ �� ���� � ������ ���� ������ ��������� ������(���������� ���������) �� ��������������");
	}

	public void rollback(WithdrawDocument document) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("����� �� ��������������");
	}

	public void prepare(GateDocument document)
	{
		throw new UnsupportedOperationException("���������� ������ �� ������� ����������� ���� �� ������ �� ���� � ������ ���� ������ ��������� ������(���������� ���������) �� ��������������");
	}

	public void confirm(GateDocument document)
	{
		throw new UnsupportedOperationException("������������� ������ �� ������� ����������� ���� �� ������ �� ���� � ������ ���� ������ ��������� ������(���������� ���������) �� ��������������");
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		if(!(document instanceof AccountIntraBankPayment))
			throw new GateException("������������ ��� ���������, �������� ��������� AccountIntraBankPayment.");

		AccountIntraBankPayment transfer = (AccountIntraBankPayment) document;
		LongOffer longOffer = (LongOffer) document;
		if(!BackRefInfoRequestHelper.isSameTB(document.getOffice(), transfer.getReceiverAccount()))
		{
			// �������� �� ��, ��� � ���������� �������, ������� � ����� ����� "����� ����������� ��� ���������� ������� �� ����� ���������� ��
			// ��������� �����" ����������� ������ ������ ��.
			if(longOffer.getSumType() == SumType.REMAIND_IN_RECIP)
			{
				throw new GateLogicException("������ ���������� ����� ������� ������ ��� �������� ������ ������ ���������������� ����� ���������. " +
						"����������, �������� ������ ��� ����� ��� ������� ������ ���� ����������.");
			}

			// �������� �� ��, ��� � ���������� �������, ������� � ����� ����� " �� ����� ���������� �� ����� ����������"
			//����������� ������ ������ ��.
			if(longOffer.getSumType() == SumType.OVER_DRAFT )
			{
				throw new GateLogicException("������ ���������� ����� ������� ������ ��� �������� ������ ������ ���������������� ����� ���������. " +
						"����������, �������� ������ ���� ����������.");
			}

		}
	}
}