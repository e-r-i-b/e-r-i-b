package com.rssl.phizic.business.limits.link;

import com.rssl.phizic.gate.payments.AccountRUSPayment;
import com.rssl.phizic.gate.payments.CardRUSPayment;
import com.rssl.phizic.gate.payments.longoffer.AccountRUSPaymentLongOffer;
import com.rssl.phizic.gate.payments.longoffer.CardRUSPaymentLongOffer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ������ �� ���� �������� ���� � ������ ���� � �����/�� �����
 *
 * @author khudyakov
 * @ created 21.08.2012
 * @ $Authors$
 * @ $Revision$
 */
public class PhysicalExternalAccountLimitPaymentLink extends LimitPaymentsLinkBase
{
	private static final List<Class> typesOfPayments = new ArrayList<Class>();

	static
	{
		typesOfPayments.add(AccountRUSPayment.class);                   //������� �� ����� �� ���� ����������� ���� � ������ ���� ����� ��������� ������� ������.
		typesOfPayments.add(AccountRUSPaymentLongOffer.class);          //���������� ��������� �� ������� �� ����� �� ���� ����������� ���� � ������ ���� ����� ��������� ������� ������.
		typesOfPayments.add(CardRUSPayment.class);                      //������� � ����� �� ���� ����������� ���� � ������ ���� ����� ��������� ������� ������.
		typesOfPayments.add(CardRUSPaymentLongOffer.class);             //���������� ��������� �� ������� � ����� �� ���� ����������� ���� � ������ ���� ����� ��������� ������� ������.
	}

	public List<Class> getPaymentTypes()
	{
		return Collections.unmodifiableList(typesOfPayments);
	}

	public LimitPaymentsType getLinkType()
	{
		return LimitPaymentsType.PHYSICAL_EXTERNAL_ACCOUNT_PAYMENT_LINK;
	}
}
