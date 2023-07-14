package com.rssl.phizicgate.sbrf.ws.listener.handler;

import com.rssl.phizic.gate.documents.AbstractAccountTransfer;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.AccountToCardTransfer;
import com.rssl.phizicgate.sbrf.bankroll.RequestHelper;
import com.rssl.phizicgate.sbrf.ws.util.ServiceReturnHelper;

/**
 * @author krenev
 * @ created 07.09.2010
 * @ $Author$
 * @ $Revision$
 */
public class AccountToCardTransferConfirmationOfflineHandler extends ClientAccountsTransferConfirmationOfflineHandler
{
	protected String getDestinationAccount(AbstractAccountTransfer document) throws GateLogicException, GateException
	{
		AccountToCardTransfer payment = (AccountToCardTransfer) document;
		RequestHelper helper = new RequestHelper(ServiceReturnHelper.getInstance().factoryFor(document));
		return helper.getCardAccountNumber(payment.getReceiverCard());
	}
}
