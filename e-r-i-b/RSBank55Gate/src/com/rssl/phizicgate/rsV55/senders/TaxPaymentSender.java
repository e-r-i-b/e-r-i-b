package com.rssl.phizicgate.rsV55.senders;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.AbstractRUSPayment;
import com.rssl.phizic.gate.payments.AccountRUSTaxPayment;
import com.rssl.phizicgate.rsV55.demand.Remittee;

import java.math.BigDecimal;

/**
 * @author Krenev
 * @ created 22.08.2007
 * @ $Author$
 * @ $Revision$
 */

public class TaxPaymentSender extends RUSPaymentSender
{
	private static final String PARAMETER_PRIORITY_NAME = "priority";

	protected Remittee createReceiver(AbstractRUSPayment rusPayment) throws GateLogicException, GateException
	{
		Remittee receiver = super.createReceiver(rusPayment);
		AccountRUSTaxPayment rusTaxPayment = (AccountRUSTaxPayment) rusPayment;
		receiver.setKBK(rusTaxPayment.getTaxKBK());
		receiver.setTaxDocumentDate((rusTaxPayment.getTaxDocumentDate() != null)?rusTaxPayment.getTaxDocumentDate().getTime():null);
		receiver.setTaxDocumentNumber(rusTaxPayment.getTaxDocumentNumber());
		receiver.setTaxAssignment(rusTaxPayment.getTaxGround());
		receiver.setOKATO(rusTaxPayment.getTaxOKATO());
		receiver.setTaxPeriod(rusTaxPayment.getTaxPeriod());
		receiver.setNotResident(false);
		receiver.setPriority(Long.valueOf(getPaymentPriority()));
		receiver.setTaxPaymentType(rusTaxPayment.getTaxPaymentType());
		receiver.setTaxAuthorState(rusTaxPayment.getTaxPaymentStatus());
		/*
		 ��������� ����� ������������� ������� 0. ����� ��� ����������� �������� ���������
		 */
		receiver.setSumASh(new BigDecimal(0.0000));
		receiver.setSumAV(new BigDecimal(0.0000));
		receiver.setSumISh(new BigDecimal(0.0000));
		receiver.setSumNS(new BigDecimal(0.0000));
		receiver.setSumPC(new BigDecimal(0.0000));
		receiver.setSumPE(new BigDecimal(0.0000));
		receiver.setSumSA(new BigDecimal(0.0000));
		receiver.setGround(rusTaxPayment.getGround());
/*  TODO: ���� ��������� ����� � ���������� ������� (��� �������� � ������), �� � � �������� ����� ��� ������ ����������! (��. ���������� � 1.9 ���)
		receiver.setGround("�/� �� ��������� ������� �� " + DateHelper.toString(rusTaxPayment.getClientCreationDate().getTime()) + ". " + rusTaxPayment.getGround());
*/
		return receiver;
	}

	private String getPaymentPriority()
	{
		return (String) getParameter(PARAMETER_PRIORITY_NAME);
	}
}
