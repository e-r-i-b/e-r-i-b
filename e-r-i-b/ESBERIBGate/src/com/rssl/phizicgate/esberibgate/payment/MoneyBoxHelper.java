package com.rssl.phizicgate.esberibgate.payment;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.autosubscriptions.AutoSubscriptionClaim;
import com.rssl.phizicgate.esberibgate.ws.generated.AutoPaymentTemplate_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.BankAcctRec_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.CardAcctId_Type;

/**
 * ������ ��� ������ � ��������
 *
 * @author khudyakov
 * @ created 10.10.14
 * @ $Author$
 * @ $Revision$
 */
public class MoneyBoxHelper extends AutoSubscriptionHelper
{
	public MoneyBoxHelper(GateFactory factory)
	{
		super(factory);
	}

	/**
	 * ��������� ��������� AutoPaymentTemplate_Type
	 * @param autoPaymentTemplate_type ���������
	 * @param payment ������
	 */
	@Override
	protected void fillAutoPaymentTemplate_Type(AutoPaymentTemplate_Type autoPaymentTemplate_type, AutoSubscriptionClaim payment) throws GateException, GateLogicException
	{
		Client owner = getBusinessOwner(payment);
		Card card = getCard(owner, payment.getChargeOffCard(), payment.getOffice());

		BankAcctRec_Type bankAcctRec_type = new BankAcctRec_Type();
		CardAcctId_Type fromCardAcctId_Type = new CardAcctId_Type();
		fillInternalCardAcctId_Type(fromCardAcctId_Type, owner, card);
		bankAcctRec_type.setCardAcctId(fromCardAcctId_Type);
		autoPaymentTemplate_type.setBankAcctRec(new BankAcctRec_Type[]{bankAcctRec_type});
		//CardAcctId_Type, �� ����� ��� CardAcctId, CardAcctIdTo, DepAcctIdTo � �.�. (c) �������� ������� ������������. ��������.
		CardAcctId_Type toDepAcctId_Type = new CardAcctId_Type();
		fillDepAcctId_Type(toDepAcctId_Type, owner, getAccount(payment.getAccountNumber(), payment.getOffice()));
		autoPaymentTemplate_type.setDepAcctIdTo(toDepAcctId_Type);
	}
}
