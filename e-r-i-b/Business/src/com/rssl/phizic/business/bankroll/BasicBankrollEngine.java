package com.rssl.phizic.business.bankroll;

import com.rssl.phizic.bankroll.BankrollEngine;
import com.rssl.phizic.bankroll.PersonBankrollManager;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.engine.EngineBase;
import com.rssl.phizic.engine.EngineManager;
import com.rssl.phizic.module.loader.LoadOrder;
import com.rssl.phizic.person.Person;

/**
 * @author Erkin
 * @ created 26.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Основная реализация движка по банковским продуктам
 */
public class BasicBankrollEngine extends EngineBase implements BankrollEngine
{
	/**
	 * ctor
	 * @param manager - менеджер по движкам
	 */
	public BasicBankrollEngine(EngineManager manager)
	{
		super(manager);
	}

	public LoadOrder getLoadOrder()
	{
		return LoadOrder.BANKROLL_ENGINE_ORDER;
	}

	public void start()
	{
	}

	public void stop()
	{
	}

	public PersonBankrollManager createPersonBankrollManager(Person person)
	{
		return new BasicPersonBankrollManager(getModule(), (ActivePerson)person);
	}
}
