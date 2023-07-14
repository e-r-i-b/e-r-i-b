package com.rssl.phizic.sms.banking.commands;

import com.rssl.phizic.sms.banking.security.WrongCommandFormatException;
import com.rssl.phizic.sms.banking.security.UserSendException;
import com.rssl.phizic.business.BusinessException;

/**
 * @author hudyakov
 * @ created 31.10.2008
 * @ $Author$
 * @ $Revision$
 */
public class HelpCommand extends CommandBase
{
	private static final CommandFactory commandFactory = new CommandFactory();

	public String execute() throws BusinessException, UserSendException
	{
		String [] parseMessage = parseString(message);

		if (parseMessage.length == 1 ) // комманда help
		   	return getHelp();

		if (parseMessage.length >= 3) // ничего кроме названия команды и названия операции
		{
			throw new WrongCommandFormatException();
		}

		Command command = commandFactory.newInstance(parseMessage[1]);

		return command.getHelp();
	}
}
