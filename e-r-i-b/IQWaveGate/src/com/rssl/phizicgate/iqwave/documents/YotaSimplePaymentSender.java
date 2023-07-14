package com.rssl.phizicgate.iqwave.documents;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;

/**
 * @author krenev
 * @ created 14.05.2010
 * @ $Author$
 * @ $Revision$
 * Yota (�������� ��������)  �
 * ����� � �������������� ����������� 7.
 * ��� ������� (��������)
 * ���������� ���������	�������� ���������
 *       CZ	                134
 */
public class YotaSimplePaymentSender extends SimplePaymentSender
{
	/**
	 * ctor
	 * @param factory - �������� �������
	 */
	public YotaSimplePaymentSender(GateFactory factory)
	{
		super(factory);
	}

	public Object getIdentifier(CardPaymentSystemPayment payment) throws GateException
	{
		String identifier = super.getIdentifier(payment).toString();
		return '7' + identifier;
	}
}