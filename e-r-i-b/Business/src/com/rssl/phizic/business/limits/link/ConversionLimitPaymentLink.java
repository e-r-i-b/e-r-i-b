package com.rssl.phizic.business.limits.link;

import com.rssl.phizic.gate.payments.*;
import com.rssl.phizic.gate.payments.longoffer.CardToAccountLongOffer;
import com.rssl.phizic.gate.payments.longoffer.ClientAccountsLongOffer;
import com.rssl.phizic.gate.payments.longoffer.CreateCardToAccountLongOffer;
import com.rssl.phizic.gate.payments.longoffer.EditCardToAccountLongOffer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ������������� ��������.
 *
 * @author khudyakov
 * @ created 21.08.2012
 * @ $Authors$
 * @ $Revision$
 */
public class ConversionLimitPaymentLink extends LimitPaymentsLinkBase
{
	private static final List<Class> typesOfPayments = new ArrayList<Class>();

	static
	{
		//�������� �� ������� ����� �������� ����.
		typesOfPayments.addAll(new PhysicalExternalAccountLimitPaymentLink().getPaymentTypes());
		//�������� �� ������� ����� �������� ����.
		typesOfPayments.addAll(new PhysicalExternalCardLimitPaymentLink().getPaymentTypes());
		//�������� ������ ��������� �� ����� ��� �����.
		typesOfPayments.addAll(new PhysicalInternalLimitPaymentLink().getPaymentTypes());
		//�������� ��. ����.
		typesOfPayments.addAll(new JuridicalExternalLimitPaymentLink().getPaymentTypes());

		//�������� ����� �������/������� �������.
		typesOfPayments.add(InternalCardsTransfer.class);                      //������� ����� ������ �������.
		typesOfPayments.add(CreateCardToAccountLongOffer.class);               //������� �� ����� ����� �� ���� (�������).
		typesOfPayments.add(EditCardToAccountLongOffer.class);                 //������� �� ����� ����� �� ���� (�������������� �������).
		typesOfPayments.add(CardToAccountTransfer.class);                      //������� �� ����� ����� �� ����.
		typesOfPayments.add(AccountToCardTransfer.class);                      //������� �� ������ ����� �� �����.
		typesOfPayments.add(ClientAccountsLongOffer.class);                    //������� ����� ������ ������� (���������� ���������).
		typesOfPayments.add(ClientAccountsTransfer.class);                     //������� ����� ������ �������.
		typesOfPayments.add(IMAToAccountTransfer.class);                       //������� � ��� �� ���� ����.
		typesOfPayments.add(AccountToIMATransfer.class);                       //������� �� ������ ����� �� ���.
	}

	public LimitPaymentsType getLinkType()
	{
		return LimitPaymentsType.CONVERSION_OPERATION_PAYMENT_LINK;
	}

	public List<Class> getPaymentTypes()
	{
		return Collections.unmodifiableList(typesOfPayments);
	}
}

