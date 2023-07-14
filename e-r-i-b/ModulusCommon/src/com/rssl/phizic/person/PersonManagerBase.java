package com.rssl.phizic.person;

import com.rssl.phizic.module.Module;
import com.rssl.phizic.text.MessageComposer;
import com.rssl.phizic.text.TextEngine;
import com.rssl.phizic.interactive.PersonInteractManager;
import com.rssl.phizic.interactive.UserInteractEngine;
import com.rssl.phizic.messaging.PersonSmsMessanger;
import com.rssl.phizic.messaging.MessagingEngine;

/**
 * @author Erkin
 * @ created 14.04.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Базовый класс для всех менеджеров персоны
 */
public abstract class PersonManagerBase
{
	private final Module module;

	private final Person person;

	private MessageComposer messageComposer;

	private PersonInteractManager personInteractManager;

	private PersonSmsMessanger personSmsMessanger;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 * @param module - модуль, в котором работает менеджер (never null)
	 * @param person - клиент, с которым работает менеджер (never null)
	 */
	protected PersonManagerBase(Module module, Person person)
	{
		this.module = module;
		this.person = person;
	}

	protected Module getModule()
	{
		return module;
	}

	protected Person getPerson()
	{
		return person;
	}

	protected MessageComposer getMessageComposer()
	{
		if (messageComposer == null)
		{
			TextEngine textEngine = module.getTextEngine();
			messageComposer = textEngine.createMessageComposer();
		}
		return messageComposer;
	}

	protected PersonInteractManager getPersonInteractManager()
	{
		if (personInteractManager == null)
		{
			UserInteractEngine userInteractEngine = module.getUserInteractEngine();
			if (userInteractEngine == null)
				throw new UnsupportedOperationException("UserInteractEngine не доступен в модуле " + module.getName());
			personInteractManager = userInteractEngine.createPersonInteractManager(person);
		}
		return personInteractManager;
	}

	protected PersonSmsMessanger getPersonSmsMessanger()
	{
		if (personSmsMessanger == null)
		{
			MessagingEngine messagingEngine = module.getMessagingEngine();
			if (messagingEngine == null)
				throw new UnsupportedOperationException("MessagingEngine не доступен в модуле " + module.getName());
			personSmsMessanger = messagingEngine.createPersonSmsMessanger(person);
		}
		return personSmsMessanger;
	}
}
