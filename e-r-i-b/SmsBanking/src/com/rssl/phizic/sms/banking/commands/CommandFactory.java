package com.rssl.phizic.sms.banking.commands;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.sms.banking.security.UserSendException;

/**
 * @author hudyakov
 * @ created 01.11.2008
 * @ $Author$
 * @ $Revision$
 */
public class CommandFactory
{
	private static final CommandService commandService = new CommandService();

	public Command newInstance(String message) throws BusinessException, UserSendException
	{
		String commandName = message.trim().toUpperCase().split("( )*( |,|;)( )*")[0];

		Class<? extends Command> commandSourse = commandService.getCommand(commandName);
		Command command = null;
		try
		{
			command = commandSourse.newInstance();
		}
		catch (InstantiationException e)
		{
			throw new BusinessException(e);
		}
		catch (IllegalAccessException e)
		{
			throw new BusinessException(e);
		}
		command.initialize(message);
		command.setParameters(commandService.getParameters(commandName));
		command.setExceptions(commandService.getExceptions());
		command.setHelp(commandService.getHelp(commandName));

		return command;
	}
}
