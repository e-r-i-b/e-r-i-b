package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.claims.AccountChangeMinBalanceClaim;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.esberibgate.ws.generated.ActionName_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.Action_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.AlterMinRemainder_Type;

/**
 * «а€вка на изменение неснижаемого остатка.
 * @author Pankin
 * @ created 15.09.13
 * @ $Author$
 * @ $Revision$
 */
public class AccountChangeMinBalanceSender extends AccountChangeInfoSenderBase
{
	public AccountChangeMinBalanceSender(GateFactory factory)
	{
		super(factory);
	}

	protected Action_Type getAction(GateDocument document)
	{
		AccountChangeMinBalanceClaim claim = (AccountChangeMinBalanceClaim) document;
		Action_Type action = new Action_Type();
		action.setActionName(ActionName_Type.AlterMinRemainder);
		action.setAlterMinRemainder(new AlterMinRemainder_Type(claim.getMinimumBalance(), claim.getInterestRate()));
		return action;
	}

	protected Account getChangeAccount(GateDocument document) throws GateLogicException, GateException
	{
		AccountChangeMinBalanceClaim claim = (AccountChangeMinBalanceClaim) document;
		return getAccount(claim.getAccountNumber(), document.getOffice());
	}
}
