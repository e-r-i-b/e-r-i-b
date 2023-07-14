package com.rssl.phizicgate.esberibgate.documents.commissionCalculators.BankTransfer;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.payments.ExternalCardsTransferToOtherBank;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.light.common.commissionCalculator.CardToCardCommissionCalculatorBase;

/**
 * @author akrenev
 * @ created 09.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * ����������� �������� ��� "������� � ����� �� ����� ���������� ����� (�� ��������� � ���������)."
 */

public class CardToCardIntraBankTransferCommissionCalculator extends CardToCardCommissionCalculatorBase<ExternalCardsTransferToOtherBank>
{
	/**
	 * �����������
	 * @param factory ������� �����
	 */
	public CardToCardIntraBankTransferCommissionCalculator(GateFactory factory)
	{
		super(factory);
	}
}
