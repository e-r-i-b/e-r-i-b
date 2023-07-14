package com.rssl.phizic.business.ermb.sms.interactive;

import com.rssl.phizic.interactive.PersonInteractManager;
import com.rssl.phizic.messaging.MessagingEngine;
import com.rssl.phizic.messaging.PersonSmsMessanger;
import com.rssl.phizic.module.Module;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.person.PersonManagerBase;
import com.rssl.phizic.common.types.TextMessage;
import org.apache.commons.lang.StringUtils;

import java.util.Collection;

/**
 * @author Erkin
 * @ created 14.04.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Реализация интеракт-менеджера для СМС-канала ЕРМБ
 */
class ErmbPersonInteractManager extends PersonManagerBase implements PersonInteractManager
{
	private final PersonSmsMessanger messanger;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 * @param module - модуль, в котором работает менеджер (never null)
	 * @param person - клиент, с которым работает менеджер (never null)
	 */
	ErmbPersonInteractManager(Module module, Person person)
	{
		super(module, person);

		MessagingEngine messagingEngine = getModule().getMessagingEngine();
		messanger = messagingEngine.createPersonSmsMessanger(person);
	}

	public void reportError(String error)
	{
		messanger.sendSms(new TextMessage(error));
	}

	public void reportErrors(Collection<String> errors)
	{
		String error = StringUtils.join(errors, ". ");
		messanger.sendSms(new TextMessage(error));
	}

	public void askForConfirmCode()
	{
	}
}
