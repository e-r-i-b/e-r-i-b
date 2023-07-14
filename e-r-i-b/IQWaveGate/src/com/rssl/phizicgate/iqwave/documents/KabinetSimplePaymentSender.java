package com.rssl.phizicgate.iqwave.documents;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author krenev
 * @ created 14.05.2010
 * @ $Author$
 * @ $Revision$
 * Kabinet (��������)  �
 * ��� �������� ������� �� ������ ������������� ���������� ����������� ������ ����� �� ����� ��������
 * (���� �������� ����� ������ 6 ������) � ����������� ���������� ����� �k� (��������� ���������),
 * � ����� ������������� ������ ��������� ������ ����: kNNNNNN
 *
 * ��� ������� (��������)
 * ���������� ���������	�������� ���������
 *      DA	                145
 */
public class KabinetSimplePaymentSender extends SimplePaymentSender
{
	/**
	 * ctor
	 * @param factory - �������� �������
	 */
	public KabinetSimplePaymentSender(GateFactory factory)
	{
		super(factory);
	}

	public Object getIdentifier(CardPaymentSystemPayment payment) throws GateException
	{
		String identifier = super.getIdentifier(payment).toString();
		return 'k' + StringHelper.appendLeadingZeros(identifier, 6);
	}
}