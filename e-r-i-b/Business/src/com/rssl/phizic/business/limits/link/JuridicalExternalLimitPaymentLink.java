package com.rssl.phizic.business.limits.link;

import com.rssl.phizic.gate.payments.*;
import com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.gate.payments.longoffer.CardJurIntraBankTransferLongOffer;
import com.rssl.phizic.gate.payments.longoffer.CardJurTransferLongOffer;
import com.rssl.phizic.gate.payments.longoffer.AccountPaymentSystemPaymentLongOfer;
import com.rssl.phizic.gate.payments.longoffer.CardPaymentSystemPaymentLongOffer;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * ������ ������������ ���� � �����/����� �� ����
 *
 * @author khudyakov
 * @ created 21.08.2012
 * @ $Authors$
 * @ $Revision$
 */
public class JuridicalExternalLimitPaymentLink extends LimitPaymentsLinkBase
{
	private static final List<Class> typesOfPayments = new ArrayList<Class>();

	static
	{
		typesOfPayments.add(CardJurIntraBankTransfer.class);            //������� ������������ ���� c ����� �� ���� ������ ��������� ������
		typesOfPayments.add(CardJurIntraBankTransferLongOffer.class);   //���������� ��������� �� ������� ������������ ���� � ����� ������� ������ �����
		typesOfPayments.add(CardJurTransfer.class);                     //������� �� ����� ������� ������������ ���� � ������ ���� ����� ��������� ������� ������.
		typesOfPayments.add(CardJurTransferLongOffer.class);            //���������� ��������� �� ������� ������������ ���� � ����� �������
		typesOfPayments.add(AccountJurTransfer.class);                  //������� �� ����� ������� ������������ ���� � ������ ���� ����� ��������� ������� ������.
		typesOfPayments.add(AccountJurIntraBankTransfer.class);         //������� ������������ ���� c� ����� �� ���� ������ ��������� ������.
		typesOfPayments.add(AccountPaymentSystemPayment.class);         //����������� ������ �� �����
		typesOfPayments.add(AccountPaymentSystemPaymentLongOfer.class); //���������� �� ������� �� ����� �� ����������� ����������
		typesOfPayments.add(CardPaymentSystemPayment.class);            //����������� ������ � �����
		typesOfPayments.add(CardPaymentSystemPaymentLongOffer.class);   //���������� �� ������� � ����� �� ����������� ����������
		typesOfPayments.add(CardRUSTaxPayment.class);                   //������ ������� � �����
		typesOfPayments.add(AccountRUSTaxPayment.class);                //������ ������� �� �����
	}

	public LimitPaymentsType getLinkType()
	{
		return LimitPaymentsType.JURIDICAL_PAYMENT_LINK;
	}

	public List<Class> getPaymentTypes()
	{
		return Collections.unmodifiableList(typesOfPayments);
	}
}
