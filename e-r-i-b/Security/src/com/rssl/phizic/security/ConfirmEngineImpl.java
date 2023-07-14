package com.rssl.phizic.security;

import com.rssl.phizic.engine.EngineBase;
import com.rssl.phizic.engine.EngineManager;
import com.rssl.phizic.module.loader.LoadOrder;
import com.rssl.phizic.person.Person;

/**
 * @author Erkin
 * @ created 06.05.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Базовая реализация движка подтверждений
 */
public class ConfirmEngineImpl extends EngineBase implements ConfirmEngine
{
	/**
	 * ctor
	 * @param manager - менеджер по движкам
	 */
	public ConfirmEngineImpl(EngineManager manager)
	{
		super(manager);
	}

	public LoadOrder getLoadOrder()
	{
		return LoadOrder.CONFIRM_ENGINE;
	}

	public void start()
	{
	}

	public void stop()
	{
	}

	public PersonConfirmManager createPersonConfirmManager(Person person)
	{
		return new PersonConfirmManagerImpl(getModule(), person);
	}
}
