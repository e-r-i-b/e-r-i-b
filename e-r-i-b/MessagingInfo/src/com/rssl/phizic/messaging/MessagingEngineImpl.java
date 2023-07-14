package com.rssl.phizic.messaging;

import com.rssl.phizic.engine.EngineBase;
import com.rssl.phizic.engine.EngineManager;
import com.rssl.phizic.module.loader.LoadOrder;
import com.rssl.phizic.person.Person;

/**
 * Реализация движка отправки сообщений
 * @author Rtischeva
 * @ created 22.03.2013
 * @ $Author$
 * @ $Revision$
 */
public class MessagingEngineImpl extends EngineBase implements MessagingEngine
{
	/**
	 * конструктор
	 * @param manager - менеджер по движкам модуля
	 */
	public MessagingEngineImpl(EngineManager manager)
	{
		super(manager);
	}

	public LoadOrder getLoadOrder()
	{
		return LoadOrder.MESSAGING_ENGINE_ORDER;
	}

	public void start()
	{
	}

	public void stop()
	{
	}

	public PersonSmsMessanger createPersonSmsMessanger(Person person)
	{
		return new PersonSmsMessangerImpl(getModule(), person);
	}
}
