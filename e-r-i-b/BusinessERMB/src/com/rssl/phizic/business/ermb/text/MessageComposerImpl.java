package com.rssl.phizic.business.ermb.text;

import com.rssl.phizic.bankroll.BankrollProductLink;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.ermb.sms.command.*;
import com.rssl.phizic.business.ermb.sms.messaging.MessageBuilder;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.common.types.TextMessage;
import com.rssl.phizic.security.ConfirmCodeMessage;
import com.rssl.phizic.security.ConfirmableTask;
import com.rssl.phizic.task.Task;
import com.rssl.phizic.text.MessageComposer;

/**
 * @author Erkin
 * @ created 28.05.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Реализация персонального композитора сообщений
 */
class MessageComposerImpl implements MessageComposer
{
	private static final MessageBuilder messageBuilder = new MessageBuilder();

	public ConfirmCodeMessage buildConfirmCodeMessage(String confirmCode, ConfirmableTask confirmableTask)
	{
	    TextMessage message =  messageBuilder.buildConfirmMessage(confirmCode, confirmableTask);
		return new ConfirmCodeMessage(confirmCode, message.getText(), message.getTextToLog());
	}

	public TextMessage buildFatalErrorMessage(Task task)
	{
		if (task instanceof BalanceCommand)
		{
			BalanceCommand command = (BalanceCommand) task;
			BankrollProductLink link = command.getLink();
			if (link instanceof AccountLink)
				return new TextMessage(messageBuilder.buildBalanceUnexpectedError(ResourceType.ACCOUNT, link).getText());
			else if (link instanceof CardLink)
				return new TextMessage(messageBuilder.buildBalanceUnexpectedError(ResourceType.CARD, link).getText());
			else if (link instanceof LoanLink)
				return new TextMessage(messageBuilder.buildBalanceUnexpectedError(ResourceType.LOAN, link).getText());
		}
		else if (task instanceof HistoryCommand)
		{
			HistoryCommand command = (HistoryCommand) task;
			BankrollProductLink link = command.getLink();
			return messageBuilder.buildHistoryErrorMessage(link);
		}
		return messageBuilder.buildUnknownErrorMessage();
	}

	public TextMessage buildAccessControlErrorMessage()
	{
		return new TextMessage(messageBuilder.buildAccessErrorMessage().getText());
	}

	public TextMessage buildProfileNotFoundErrorMessage()
	{
		return new TextMessage(messageBuilder.buildProfileNotFoundErrorMessage().getText());
	}

	public TextMessage buildInactiveExternalSystemErrorMessage(Task task)
	{
		Command command = (Command) task;
		return messageBuilder.buildInactiveSystemMessage(command);
	}
}
