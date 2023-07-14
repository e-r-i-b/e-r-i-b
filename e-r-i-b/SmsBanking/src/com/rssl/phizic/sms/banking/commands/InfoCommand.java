package com.rssl.phizic.sms.banking.commands;

import com.rssl.phizic.business.smsbanking.pseudonyms.*;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.pseudonyms.EditPseudonymsListOperation;
import com.rssl.phizic.sms.banking.security.WrongCommandFormatException;
import com.rssl.phizic.sms.banking.security.UserSendException;

import java.util.List;

/**
 * @author hudyakov
 * @ created 31.10.2008
 * @ $Author$
 * @ $Revision$
 */
public class InfoCommand extends CommandBase
{
	private static final String DEFAULT_ACCOUNT_MESSAGE = "Подключены следующие счета";
   	private static final String DEFAULT_CARD_MESSAGE    = "карты";
	private static final String DEFAULT_DEPOSIT_MESSAGE = "вклады";

	private	StringBuffer accountMessage = new StringBuffer();
	private StringBuffer cardMessage    = new StringBuffer();
	private	StringBuffer depositMessage = new StringBuffer();

	public String execute() throws BusinessException, BusinessLogicException, UserSendException
	{
		String [] parseMessage = parseString(message);

		if (parseMessage.length != 1) // только название команды
		{
			throw new UserSendException("Количество входящих параметров превышает допустимое");
		}

		EditPseudonymsListOperation operation = (EditPseudonymsListOperation) createOperation(EditPseudonymsListOperation.class);
		operation.initialize();

		List<Pseudonym> pseudos = operation.getAccessibleLinks();
		if (pseudos.size() == 0)
		{
			return "у Вас нет объектов выдачи информации";
		}

		for(Pseudonym pseudo : pseudos)
		{
			if (pseudo instanceof AccountPseudonym)
			{
				accountMessage.append(BLANK).append(pseudo.getName());
			}
			if (pseudo instanceof CardPseudonym)
			{
				cardMessage.append(BLANK).append(pseudo.getName());
			}
			if (pseudo instanceof DepositPseudonym)
			{
				depositMessage.append(BLANK).append(pseudo.getName());
			}
		}

		StringBuffer result = new StringBuffer();
		if (accountMessage.length() > 0)
		{
			result.append(DEFAULT_ACCOUNT_MESSAGE).append(COLON).append(accountMessage).append(SEMICOLON).append(BLANK);
		}
		if (cardMessage.length() > 0)
		{
			result.append(DEFAULT_CARD_MESSAGE).append(COLON).append(cardMessage).append(SEMICOLON).append(BLANK);
		}
		if (depositMessage.length() > 0)
		{
			result.append(DEFAULT_DEPOSIT_MESSAGE).append(COLON).append(depositMessage).append(SEMICOLON);
		}

		return result.toString();
	}
}
