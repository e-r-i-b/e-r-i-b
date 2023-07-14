package com.rssl.phizic.messaging;

import com.rssl.phizic.engine.Engine;
import com.rssl.phizic.person.Person;

/**
 * Движок отправки смс-сообщений
 * @author Rtischeva
 * @ created 22.03.2013
 * @ $Author$
 * @ $Revision$
 */
public interface MessagingEngine extends Engine
{
	/**
	 * Создаёт СМС-мессенжера для указанного клиента
	 * @param person - клиент (never null)
	 * @return новый СМС-мессенжер (never null)
	 */
	PersonSmsMessanger createPersonSmsMessanger(Person person);
}
