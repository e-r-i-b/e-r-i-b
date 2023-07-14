package com.rssl.phizic.sms.banking.commands;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.smsbanking.pseudonyms.*;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.gate.bankroll.AccountAbstract;
import com.rssl.phizic.gate.bankroll.CardAbstract;
import com.rssl.phizic.gate.bankroll.TransactionBase;
import com.rssl.phizic.operations.account.GetAccountAbstractOperation;
import com.rssl.phizic.operations.card.GetCardAbstractOperation;
import com.rssl.phizic.operations.deposits.GetDepositAbstractOperation;
import com.rssl.phizic.operations.pseudonyms.EditPseudonymsListOperation;
import com.rssl.phizic.security.config.Constants;
import com.rssl.phizic.sms.banking.security.UserSendException;
import com.rssl.phizic.sms.banking.security.WrongCommandFormatException;
import com.rssl.phizic.utils.ClientConfig;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author hudyakov
 * @ created 31.10.2008
 * @ $Author$
 * @ $Revision$
 */
public class ExtractCommand extends CommandBase
{
	public String execute() throws BusinessException, BusinessLogicException, UserSendException
	{
		if (message == null)
		{
			throw new WrongCommandFormatException();
		}

		Long count;

		String [] parseMessage = parseString(message);

		if (parseMessage.length == 2)
		{
			count = ConfigFactory.getConfig(ClientConfig.class).getSmsBankingOperationsDefaultCount();
		}
		else if (parseMessage.length == 3)
		{
			count = Long.parseLong(parseMessage[2]);
			Long maxCount = ConfigFactory.getConfig(ClientConfig.class).getSmsBankingOperationsMaxCount();
			if (maxCount < count)
				throw new UserSendException("Максимальное количество операций в выписке не должно превышать <" + maxCount + ">.");
		}
		else throw new WrongCommandFormatException();

		List<TransactionBase> transactions = null;

		EditPseudonymsListOperation operation = (EditPseudonymsListOperation) createOperation(EditPseudonymsListOperation.class);
		operation.initialize();

		Pseudonym pseudo = operation.getLink(parseMessage[1]);

		if (!pseudo.isHasAccess())
			throw new AccessPseudonymException("Доступ к псевдониму " + parseMessage[1] + " запрещен.");

		if (pseudo instanceof AccountPseudonym)
		{
			AccountLink link = (AccountLink) pseudo.getLink();
			GetAccountAbstractOperation accountOperation =
					(GetAccountAbstractOperation) createOperation(GetAccountAbstractOperation.class);
			accountOperation.initialize(link.getId());
			AccountAbstract accountAbstract = accountOperation.getAccountAbstract(count).get(link);
			if(accountAbstract==null)
				transactions = null;
			else
				transactions = accountAbstract.getTransactions();

		}
		if (pseudo instanceof DepositPseudonym)
		{
			GetDepositAbstractOperation depositOperation =
					(GetDepositAbstractOperation) createOperation(GetDepositAbstractOperation.class);
			depositOperation.initialize(pseudo.getValue());
			transactions = depositOperation.getDepositAbstract(count).getTransactions();
		}
		if (pseudo instanceof CardPseudonym)
		{
			CardLink link = (CardLink) pseudo.getLink();
			GetCardAbstractOperation cardOperation =
					(GetCardAbstractOperation) createOperation(GetCardAbstractOperation.class);
			cardOperation.initialize(link.getId());
			CardAbstract cardAbstr = cardOperation.getCardAbstract(count).get(link);
			if(cardAbstr==null)
				transactions = null;
			else
				transactions = cardAbstr.getTransactions();
		}
		if (transactions == null)
		{
			return "Операций с псевдонимом" + pseudo.getName() + " не совершалось";
		}

        StringBuffer result = new StringBuffer();
		result.append("Последние операции по счету").append(BLANK)
		      .append(pseudo.getName())
		      .append(COLON).append(BLANK);
		
		for (TransactionBase transaction : transactions)
		{
			result.append(String.format("%1$te.%1$tm.%1$tY", transaction.getDate())).append(BLANK);
			if (!transaction.getCreditSum().equals(new Money(BigDecimal.ZERO, transaction.getCreditSum().getCurrency())))
			{
				result.append("приход").append(BLANK)
					  .append(transaction.getCreditSum().getDecimal()).append(BLANK)
					  .append(transaction.getCreditSum().getCurrency().getCode()).append(SEMICOLON).append(BLANK);
			}
			if (!transaction.getDebitSum().equals(new Money(BigDecimal.ZERO, transaction.getDebitSum().getCurrency())))
			{
				result.append("расход").append(BLANK)
					  .append(transaction.getDebitSum().getDecimal()).append(BLANK)
					  .append(transaction.getCreditSum().getCurrency().getCode()).append(SEMICOLON).append(BLANK);
			}
		}
        return result.toString();
	}
}
