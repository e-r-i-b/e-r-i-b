package com.rssl.phizic.security;

import com.rssl.phizic.engine.Engine;
import com.rssl.phizic.person.Person;

/**
 * @author Erkin
 * @ created 25.04.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Движок подтверждений
 */
public interface ConfirmEngine extends Engine
{
	/**
	 * Создаёт персонального менеджера по подтверждениям
	 * @param person - клиент
	 * @return новый менеджер подтверждения
	 */
	PersonConfirmManager createPersonConfirmManager(Person person);
}
