package com.rssl.phizic.task;

import com.rssl.phizic.bankroll.BankrollEngine;
import com.rssl.phizic.bankroll.PersonBankrollManager;
import com.rssl.phizic.interactive.PersonInteractManager;
import com.rssl.phizic.interactive.UserInteractEngine;
import com.rssl.phizic.messaging.MessagingEngine;
import com.rssl.phizic.messaging.PersonSmsMessanger;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.person.PersonTask;
import com.rssl.phizic.security.ConfirmEngine;
import com.rssl.phizic.security.PersonConfirmManager;
import com.rssl.phizic.text.MessageComposer;
import com.rssl.phizic.text.TextEngine;

/**
 * @author Erkin
 * @ created 22.05.2013
 * @ $Author$
 * @ $Revision$
 */

public abstract class PersonTaskBase extends TaskBase implements PersonTask
{
	private transient Person person;

	private transient MessageComposer messageComposer;

	private transient PersonInteractManager personInteractManager;

	private transient PersonSmsMessanger personSmsMessanger;

	private transient PersonBankrollManager personBankrollManager;

	private transient PersonConfirmManager personConfirmManager;

	///////////////////////////////////////////////////////////////////////////

	public void setPerson(Person person)
	{
		this.person = person;
	}

	protected Person getPerson()
	{
		return person;
	}

	@Override
	protected void checkParameters() throws TaskNotReadyException
	{
		super.checkParameters();
		if (person == null)
			throw new TaskNotReadyException("Не указан параметр person");
	}

	protected MessageComposer getMessageComposer()
	{
		if (messageComposer == null)
		{
			TextEngine textEngine = getModule().getTextEngine();
			messageComposer = textEngine.createMessageComposer();
		}
		return messageComposer;
	}

	protected PersonInteractManager getPersonInteractManager()
	{
		if (personInteractManager == null)
		{
			UserInteractEngine userInteractEngine = getModule().getUserInteractEngine();
			if (userInteractEngine == null)
				throw new UnsupportedOperationException("UserInteractEngine не доступен в модуле " + getModule().getName());
			personInteractManager = userInteractEngine.createPersonInteractManager(getPerson());
		}
		return personInteractManager;
	}

	protected PersonSmsMessanger getPersonSmsMessanger()
	{
		if (personSmsMessanger == null)
		{
			MessagingEngine messagingEngine = getModule().getMessagingEngine();
			if (messagingEngine == null)
				throw new UnsupportedOperationException("MessagingEngine не доступен в модуле " + getModule().getName());
			personSmsMessanger = messagingEngine.createPersonSmsMessanger(getPerson());
		}
		return personSmsMessanger;
	}

	protected PersonBankrollManager getPersonBankrollManager()
	{
		if (personBankrollManager == null)
		{
			BankrollEngine bankrollEngine = getModule().getBankrollEngine();
			if (bankrollEngine == null)
				throw new UnsupportedOperationException("BankrollEngine не доступен в модуле " + getModule().getName());
			personBankrollManager = bankrollEngine.createPersonBankrollManager(getPerson());
		}
		return personBankrollManager;
	}

	protected PersonConfirmManager getPersonConfirmManager()
	{
		if (personConfirmManager == null)
		{
			ConfirmEngine confirmEngine = getModule().getConfirmEngine();
			if (confirmEngine == null)
				throw new UnsupportedOperationException("ConfirmEngine не доступен в модуле " + getModule().getName());
			personConfirmManager = confirmEngine.createPersonConfirmManager(getPerson());
		}
		return personConfirmManager;
	}
}
