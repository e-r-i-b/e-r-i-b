package com.rssl.phizicgate.rsretailV6r4.senders;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.AccountToCardTransfer;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizicgate.rsretailV6r4.bankroll.RequestHelper;

import java.util.Map;

/**
 * @author krenev
 * @ created 12.07.2010
 * @ $Author$
 * @ $Revision$
 * Сендер перевода счет-карта через карточный счет.
 */
public class AccountToCardTransferSender extends ClientAccountsTransferSender
{
	public AccountToCardTransferSender(GateFactory factory)
	{
		super(factory);
	}

	protected String getReceiverAccount(GateDocument document) throws GateException, GateLogicException
	{
		if (!(document instanceof AccountToCardTransfer))
			throw new GateException("Неверный тип платежа, должен быть - AccountToCardTransfer.");

		AccountToCardTransfer accountToCardTransfer = (AccountToCardTransfer) document;
		RequestHelper helper = new RequestHelper(getFactory());
		String cardNumber = accountToCardTransfer.getReceiverCard();
		Map<String, String> cardsAccountIds = helper.getCardsAccountIds(cardNumber);
		String cardAccountId = cardsAccountIds.get(cardNumber);
		if (cardAccountId == null)
		{
			throw new GateLogicException("Невозможно получить информацию по карте зачисления");
		}
		try{
			Account account = GroupResultHelper.getOneResult(getFactory().service(BankrollService.class).getAccount(cardAccountId));
			if (account == null)
			{
				throw new GateLogicException("Невозможно получить информацию по карте зачисления");
			}
			return account.getNumber();
		}
		catch (LogicException e)
		{
			throw new GateLogicException(e);
		}
		catch (SystemException e)
		{
			throw new GateException(e);
		}
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		try
		{
			//пытаемся получить КС.
			getReceiverAccount(document);
		}
		catch (Exception e)
		{
			throw new GateLogicException("Невозможно получить информацию по карте зачисления", e);
		}
	}
}
