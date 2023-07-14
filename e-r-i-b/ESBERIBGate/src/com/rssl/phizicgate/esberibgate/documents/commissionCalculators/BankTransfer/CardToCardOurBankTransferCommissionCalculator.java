package com.rssl.phizicgate.esberibgate.documents.commissionCalculators.BankTransfer;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.payments.ExternalCardsTransferToOurBank;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.light.common.commissionCalculator.CardToCardCommissionCalculatorBase;

/**
 * @author akrenev
 * @ created 09.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * ����������� �������� ��� "������� � ����� �� ����� ��������� (�� ���)."
 */

public class CardToCardOurBankTransferCommissionCalculator extends CardToCardCommissionCalculatorBase<ExternalCardsTransferToOurBank>
{
	/**
	 * �����������
	 * @param factory ������� �����
	 */
	public CardToCardOurBankTransferCommissionCalculator(GateFactory factory)
	{
		super(factory);
	}
}
