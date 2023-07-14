package com.rssl.phizic.bankroll;

import com.rssl.phizic.engine.Engine;
import com.rssl.phizic.person.Person;

/**
 * @author Erkin
 * @ created 26.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Движок по банковским продуктам
 */
public interface BankrollEngine extends Engine
{
	/**
	 * Создаёт банкролл-менеджера для указанного клиента
	 * @param person - клиент (never null)
	 * @return новый банкролл-менеджер (never null)
	 */
	PersonBankrollManager createPersonBankrollManager(Person person);
}
