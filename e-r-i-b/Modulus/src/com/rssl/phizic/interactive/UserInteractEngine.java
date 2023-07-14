package com.rssl.phizic.interactive;

import com.rssl.phizic.engine.Engine;
import com.rssl.phizic.person.Person;

/**
 * @author Erkin
 * @ created 14.04.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Движок взаимодействия с пользователем
 */
public interface UserInteractEngine extends Engine
{
	/**
	 * Создаёт интерактив-менеджера для указанного клиента
	 * @param person - клиент (never null)
	 * @return новый интерактив-менеджер (never null)
	 */
	PersonInteractManager createPersonInteractManager(Person person);
}
