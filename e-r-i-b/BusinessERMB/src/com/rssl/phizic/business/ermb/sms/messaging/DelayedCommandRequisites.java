package com.rssl.phizic.business.ermb.sms.messaging;

import com.rssl.phizic.auth.AuthEngine;
import com.rssl.phizic.business.ermb.sms.command.Command;
import com.rssl.phizic.business.ermb.sms.delayed.DelayedCommandService;
import com.rssl.phizic.business.ermb.sms.delayed.PersonalDelayedCommand;
import com.rssl.phizic.module.Module;
import com.rssl.phizic.person.ErmbProfile;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.session.PersonSession;
import com.rssl.phizic.session.SessionEngine;
import com.rssl.phizic.text.MessageComposer;

/**
 * @author Gulov
 * @ created 25.06.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * –еквизиты и действи€ исполнител€ команды, которые используютс€ при выполнении отложенных команд
 */
public class DelayedCommandRequisites implements ExecutorRequisites
{
	private static final DelayedCommandService delayedCommandService = new DelayedCommandService();

	/**
	 * ќтложенна€ команда и персона (never null)
	 */
	private final PersonalDelayedCommand personalDelayedCommand;

	/**
	 * ћодуль (never null)
	 */
	private final Module module;

	/**
	 * —есси€ клиента
	 */
	private PersonSession session;

	/**
	 *  онструктор
	 * @param personalDelayedCommand - отложенна€ команда, по которой создаетс€ команда дл€ выполнени€
	 * @param module - модуль
	 */
	public DelayedCommandRequisites(PersonalDelayedCommand personalDelayedCommand, Module module)
	{
		this.personalDelayedCommand = personalDelayedCommand;
		this.module = module;
	}

	public PersonSession authenticate()
	{
		Person person = personalDelayedCommand.getPerson();
		AuthEngine authEngine = module.getAuthEngine();
		session = authEngine.authenticateByPerson(person);
		return session;
	}

	public Command createCommand()
	{
		return delayedCommandService.createFrom(personalDelayedCommand.getCommand());
	}

	public Module getModule()
	{
		return module;
	}

	public String getPhone()
	{
		ErmbProfile ermbProfile = session.getPerson().getErmbProfile();
		return ermbProfile.getMainPhoneNumber();
	}

	public void handleInactiveExternalSystemException(Command command, MessageComposer messageComposer)
	{

	}

	public void doAfterExecute(Command command)
	{
		delayedCommandService.remove(personalDelayedCommand.getCommand());
	}

	/**
	 * ”бить сессию
	 */
	public void destroySession()
	{
		if (session != null)
		{
			SessionEngine sessionEngine = module.getSessionEngine();
			sessionEngine.destroySession(session);
		}
	}
}
