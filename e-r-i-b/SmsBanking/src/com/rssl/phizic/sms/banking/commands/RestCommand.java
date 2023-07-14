package com.rssl.phizic.sms.banking.commands;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.smsbanking.pseudonyms.*;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.deposit.Deposit;
import com.rssl.phizic.operations.pseudonyms.EditPseudonymsListOperation;
import com.rssl.phizic.sms.banking.security.UserSendException;
import com.rssl.phizic.utils.MockHelper;

/**
 * @author hudyakov
 * @ created 31.10.2008
 * @ $Author$
 * @ $Revision$
 */
public class RestCommand extends CommandBase
{
	public String execute() throws BusinessException, BusinessLogicException, UserSendException
	{
		String [] parseMessage = parseString(message);

		if (parseMessage.length != 2) // ничего кроме названия команды и псевдонима
		{
			throw new UserSendException("Количество входящих параметров не совпадает с требуемым");
		}

		EditPseudonymsListOperation operation = (EditPseudonymsListOperation) createOperation(EditPseudonymsListOperation.class);
		operation.initialize();

		Money balance = null;
		Pseudonym pseudo = operation.getLink(parseMessage[1]);
		if (!pseudo.isHasAccess())
			throw new AccessPseudonymException("Доступ к псевдониму " + parseMessage[1] + " запрещен.");

		if (pseudo instanceof AccountPseudonym)
		{
			Account account = ((AccountLink)pseudo.getLink()).getAccount();
			if(!MockHelper.isMockObject(account))
		        balance = account.getBalance();
			else
				balance = null;
		}
		if (pseudo instanceof DepositPseudonym)
		{
			Deposit deposit = (Deposit) pseudo.getLink().getValue();
			balance = deposit.getAmount();
		}
		if (pseudo instanceof CardPseudonym)
		{
			Card card = ((CardLink) pseudo.getLink()).getCard();
			if(!MockHelper.isMockObject(card))
		        balance = card.getAvailableLimit();
			else
				balance = null;


		}

		if(balance!=null)
		{
			return  new StringBuffer().append("Текущий остаток на счете ")
									  .append(pseudo.getName())
									  .append(COLON)
									  .append(" сумма остатка ")
									  .append(balance.getDecimal())
									  .append(BLANK)
									  .append(balance.getCurrency().getCode())
									  .toString();
		}
		else
			return "Операция временно недоступна. Повторите попытку позже.";
	}
}
