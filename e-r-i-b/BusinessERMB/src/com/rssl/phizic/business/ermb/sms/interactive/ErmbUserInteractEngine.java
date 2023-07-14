package com.rssl.phizic.business.ermb.sms.interactive;

import com.rssl.phizic.interactive.UserInteractEngine;
import com.rssl.phizic.interactive.PersonInteractManager;
import com.rssl.phizic.engine.EngineBase;
import com.rssl.phizic.engine.EngineManager;
import com.rssl.phizic.module.loader.LoadOrder;
import com.rssl.phizic.person.Person;

/**
 * @author Erkin
 * @ created 14.04.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Реализация интерактив-движка для СМС-канала ЕРМБ
 */
public class ErmbUserInteractEngine extends EngineBase implements UserInteractEngine
{
	/**
	 * ctor
	 * @param manager - менеджер по движкам
	 */
	public ErmbUserInteractEngine(EngineManager manager)
	{
		super(manager);
	}

	public LoadOrder getLoadOrder()
	{
		return LoadOrder.INTERACTIVE_ENGINE_ORDER;
	}

	public void start()
	{
	}

	public void stop()
	{
	}

	public PersonInteractManager createPersonInteractManager(Person person)
	{
		return new ErmbPersonInteractManager(getModule(), person);
	}
}
