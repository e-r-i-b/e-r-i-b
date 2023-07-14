package com.rssl.phizic.business.limits.link;

import com.rssl.phizic.gate.payments.AccountIntraBankPayment;
import com.rssl.phizic.gate.payments.CardIntraBankPayment;
import com.rssl.phizic.gate.payments.ExternalCardsTransferToOurBank;
import com.rssl.phizic.gate.payments.autosubscriptions.EditExternalP2PAutoTransfer;
import com.rssl.phizic.gate.payments.autosubscriptions.ExternalCardsTransferToOtherBankLongOffer;
import com.rssl.phizic.gate.payments.autosubscriptions.ExternalCardsTransferToOurBankLongOffer;
import com.rssl.phizic.gate.payments.longoffer.AccountIntraBankPaymentLongOffer;
import com.rssl.phizic.gate.payments.longoffer.CardIntraBankPaymentLongOffer;
import com.rssl.phizic.gate.payments.autosubscriptions.InternalCardsTransferLongOffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ������ �� ����/����� �������� ���� ������ ��������� � �����/�� �����
 *
 * @author khudyakov
 * @ created 21.08.2012
 * @ $Authors$
 * @ $Revision$
 */
public class PhysicalInternalLimitPaymentLink extends LimitPaymentsLinkBase
{
	private static final List<Class> typesOfPayments = new ArrayList<Class>();

	static
	{
		typesOfPayments.add(AccountIntraBankPayment.class);                     //������� ����������� ���� �� ����� � ������ ���� ������ ��������� ������
		typesOfPayments.add(AccountIntraBankPaymentLongOffer.class);            //������� ����������� ���� �� ����� � ������ ���� ������ ��������� ������ (���������� ���������)
		typesOfPayments.add(CardIntraBankPayment.class);                        //������� ����������� ���� � ����� � ������ ���� ������ ��������� ������ (���������� ���������)
		typesOfPayments.add(CardIntraBankPaymentLongOffer.class);               //������� ����������� ���� � ����� � ������ ���� ������ ��������� ������ (���������� ���������)
		typesOfPayments.add(ExternalCardsTransferToOurBank.class);              //������� ����������� ���� � ����� �� ����� ������ ���������
		typesOfPayments.add(ExternalCardsTransferToOurBankLongOffer.class);     //����������� ����������� ���� � ����� �� ����� ������ ���������
		typesOfPayments.add(ExternalCardsTransferToOtherBankLongOffer.class);   //����������� ����������� ���� � ����� �� ����� ������ ���������
		typesOfPayments.add(EditExternalP2PAutoTransfer.class);                 //����������� ����������� ���� � ����� �� ����� ������ ���������
		typesOfPayments.add(InternalCardsTransferLongOffer.class);              //����������� ����� ������ �������
	}

	public LimitPaymentsType getLinkType()
	{
		return LimitPaymentsType.PHYSICAL_INTERNAL_PAYMENT_LINK;
	}

	public List<Class> getPaymentTypes()
	{
		return Collections.unmodifiableList(typesOfPayments);
	}
}
