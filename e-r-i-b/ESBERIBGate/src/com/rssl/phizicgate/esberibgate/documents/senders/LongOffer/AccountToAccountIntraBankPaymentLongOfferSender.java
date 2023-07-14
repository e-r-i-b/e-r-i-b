package com.rssl.phizicgate.esberibgate.documents.senders.LongOffer;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.AbstractTransfer;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.gate.longoffer.SumType;
import com.rssl.phizic.gate.payments.longoffer.AccountRUSPaymentLongOffer;
import com.rssl.phizicgate.esberibgate.ws.jms.common.document.AbstractOnlineJMSDocumentSender;

/**
 * @author akrenev
 * @ created 18.05.2015
 * @ $Author$
 * @ $Revision$
 *
 * ������ ������ "������� ����������� ���� �� ������ �� ���� � ������ ���� ����� ��������� ������� ������(���������� ���������)."
 */

public class AccountToAccountIntraBankPaymentLongOfferSender extends AbstractOnlineJMSDocumentSender
{
	/**
	 * �����������
	 * @param factory ������� �����
	 */
	public AccountToAccountIntraBankPaymentLongOfferSender(GateFactory factory)
	{
		super(factory);
	}

	public final void send(GateDocument document) throws GateException, GateLogicException
	{
		process(new AccountToAccountIntraBankPaymentLongOfferProcessor(getFactory(), (AccountRUSPaymentLongOffer) document, getServiceName(document)));
	}

	public void repeatSend(GateDocument document) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("��������� �������� ������ �� ������� ����������� ���� �� ������ �� ���� � ������ ���� ����� ��������� ������� ������(���������� ���������) �� ��������������");
	}

	public void rollback(WithdrawDocument document) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("����� �� ��������������");
	}

	public void prepare(GateDocument document)
	{
		throw new UnsupportedOperationException("���������� ������ �� ������� ����������� ���� �� ������ �� ���� � ������ ���� ����� ��������� ������� ������(���������� ���������) �� ��������������");
	}

	public void confirm(GateDocument document)
	{
		throw new UnsupportedOperationException("������������� ������ �� ������� ����������� ���� �� ������ �� ���� � ������ ���� ����� ��������� ������� ������(���������� ���������) �� ��������������");
	}

	public void validate(GateDocument document) throws GateException
	{
		if (!(document instanceof AbstractTransfer))
		{
			throw new GateException("�������� ��� ���������, ������ ���� - AbstractTransfer, � ������ " + document.getClass());
		}

		//��������� ���������� ������ ������� � ���� ���������
		if (!LongOffer.class.isAssignableFrom(document.getType()))
		{
			throw new GateException("������������ ��� ��������� � ������ ����������:\n ��� ��������� =" + document.getType() + "; isLongOfferMode = true");
		}
		//��������, ��� ����� �������: ���� �����������, ���� <SummaKindCode> = PERCENT_OF_REMAIND.
		LongOffer longOffer = (LongOffer) document;
		if (longOffer.getSumType() == SumType.PERCENT_OF_REMAIND && longOffer.getPercent() == null)
		{
			//���-�� ����� � ������� - ������
			throw new GateException(" �� ����������� ������� �� Percent : ���� �����������, ���� <SummaKindCode> = PERCENT_OF_REMAIND");
		}
		//���������, ��� ������ �����:����� ����������� ��� ���������� �������, � ����� ��� �������� ����������� ��������� � ������
		//1)FIXED_SUMMA,
		//2)REMAIND_OVER_SUMMA
		//3)FIXED_SUMMA_IN_RECIP_CURR
		//� ���� <SummaKindCode>.
		SumType sumType = longOffer.getSumType();
		if ((sumType == SumType.FIXED_SUMMA || sumType == SumType.REMAIND_OVER_SUMMA||sumType == SumType.FIXED_SUMMA_IN_RECIP_CURR) && longOffer.getAmount() == null)
		{
			//���-�� ����� � ������� - ������
			throw new GateException("����� ���������� ��� ���� ����� :" + sumType);
		}
	}
}
