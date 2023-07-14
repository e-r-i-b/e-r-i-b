package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.AbstractRUSPayment;

/**
 * @author krenev
 * @ created 22.10.2010
 * @ $Author$
 * @ $Revision$
 * ������ ����������� ��������� �� ������� ����������� ���� c� ����� �� ���� � ������ ���� ����� ��������� ������� ������
 */
public class AccountRUSPaymentLongOfferSender extends SDPPaymentSender
{
	public AccountRUSPaymentLongOfferSender(GateFactory factory) throws GateException
	{
		super(factory);
	}

	protected XferMethodType getXferMethod(AbstractRUSPayment document)
	{
		return XferMethodType.EXTERNAL_BANK;
	}
}
