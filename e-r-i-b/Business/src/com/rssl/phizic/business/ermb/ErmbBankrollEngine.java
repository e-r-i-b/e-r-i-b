package com.rssl.phizic.business.ermb;

import com.rssl.phizic.bankroll.PersonBankrollManager;
import com.rssl.phizic.business.bankroll.BasicBankrollEngine;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.engine.EngineManager;
import com.rssl.phizic.person.Person;

/**
 *  Реализация движка по банковским продуктам для ЕРМБ
 * @author Rtischeva
 * @ created 11.04.2013
 * @ $Author$
 * @ $Revision$
 */
public class ErmbBankrollEngine extends BasicBankrollEngine
{
	/**
	 * ctor
	 * @param manager - менеджер по движкам
	 */
	public ErmbBankrollEngine(EngineManager manager)
	{
		super(manager);
	}

	@Override
	public void stop()
	{
	}

	public PersonBankrollManager createPersonBankrollManager(Person person)
	{
		return new SmsPersonBankrollManager(getModule(), (ActivePerson)person);
	}
}
