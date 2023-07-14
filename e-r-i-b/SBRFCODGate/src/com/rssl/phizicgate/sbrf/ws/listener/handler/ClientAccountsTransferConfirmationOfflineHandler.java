package com.rssl.phizicgate.sbrf.ws.listener.handler;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.documents.AbstractAccountTransfer;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.ClientAccountsTransfer;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizicgate.sbrf.ws.util.ServiceReturnHelper;

/**
 * @author krenev
 * @ created 07.09.2010
 * @ $Author$
 * @ $Revision$
 */
public class ClientAccountsTransferConfirmationOfflineHandler extends PaymentConfirmationOfflineHandlerBase
{
	protected boolean isConvertionPayment(AbstractAccountTransfer document) throws GateException, GateLogicException
	{
		Currency chargeOffAccountCurrency = getAccountCurrency(document.getChargeOffAccount(), document);
		Currency destinationAccountCurrency = getAccountCurrency(getDestinationAccount(document), document);
		return !chargeOffAccountCurrency.compare(destinationAccountCurrency);
	}

	protected String getDestinationAccount(AbstractAccountTransfer document) throws GateLogicException, GateException
	{
		ClientAccountsTransfer payment = (ClientAccountsTransfer) document;
		return payment.getReceiverAccount();
	}

	/**
	 * получить валюту счета
	 * @param number номер счета
	 * @return валюта
	 */
	protected Currency getAccountCurrency(String number, AbstractAccountTransfer document) throws GateException, GateLogicException
	{
		try
		{
			BankrollService bankrollService = ServiceReturnHelper.getInstance().service(document, BankrollService.class);
			Account account = GroupResultHelper.getOneResult(bankrollService.getAccount(number));
			return account.getCurrency();
		}
		catch (SystemException e)
		{
			throw  new GateException(e);
		}
		catch (LogicException e)
		{
			throw  new GateLogicException(e);
		}
	}
}
