package com.rssl.phizic.business.ermb.sms.delayed;

import com.rssl.phizic.person.Person;

/**
 * @author Gulov
 * @ created 27.06.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Класс-обертка для отложенной команды. Содержит объект персона.
 */
public class PersonalDelayedCommand
{
	/**
	 * персона
	 */
	private final Person person;

	/**
	 * Отложенная команда
	 */
	private final DelayedCommand command;

	public PersonalDelayedCommand(Person person, DelayedCommand command)
	{
		this.person = person;
		this.command = command;
	}

	public Person getPerson()
	{
		return person;
	}

	public DelayedCommand getCommand()
	{
		return command;
	}
}
