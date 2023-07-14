package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.claims.AccountChangeInterestDestinationClaim;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.ActionName_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.Action_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.VariantInterestPayment_Type;

/**
 * Заявка на изменение порядка перечисления процентов
 * @author Pankin
 * @ created 15.09.13
 * @ $Author$
 * @ $Revision$
 */
public class AccountChangeInterestDestinationSender extends AccountChangeInfoSenderBase
{
	public AccountChangeInterestDestinationSender(GateFactory factory)
	{
		super(factory);
	}

	protected Action_Type getAction(GateDocument document)
	{
		AccountChangeInterestDestinationClaim claim = (AccountChangeInterestDestinationClaim) document;
		Action_Type action = new Action_Type();
		action.setActionName(ActionName_Type.VariantInterestPayment);
		VariantInterestPayment_Type variantInterestPayment = new VariantInterestPayment_Type();
		boolean isInterestToCard = StringHelper.isNotEmpty(claim.getPercentCardNumber());
		variantInterestPayment.setIsInterestToCard(isInterestToCard ? "1" : "0");
		if (isInterestToCard)
			variantInterestPayment.setCardNumber(claim.getPercentCardNumber());
		action.setVariantInterestPayment(variantInterestPayment);
		return action;
	}

	protected Account getChangeAccount(GateDocument document) throws GateLogicException, GateException
	{
		AccountChangeInterestDestinationClaim claim = (AccountChangeInterestDestinationClaim) document;
		try
		{
			return getAccount(claim.getChangePercentDestinationAccountNumber(), document.getOffice());
		}
		catch (DocumentException e)
		{
			throw new GateException(e.getMessage(),e);
		}
	}
}
