package com.rssl.phizic.payment;

import com.rssl.phizic.engine.EngineBase;
import com.rssl.phizic.engine.EngineManager;
import com.rssl.phizic.module.loader.LoadOrder;
import com.rssl.phizic.person.Person;

/**
 * @author Erkin
 * @ created 01.04.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Реализация движка платежей
 */
public abstract class PaymentEngineBase extends EngineBase implements PaymentEngine
{
	protected PaymentEngineBase(EngineManager manager)
	{
		super(manager);
	}

	public LoadOrder getLoadOrder()
	{
		return LoadOrder.PAYMENT_ENGINE_ORDER;
	}

	public void start()
	{
	}

	public void stop()
	{
	}

	public PersonPaymentManager createPersonPaymentManager(Person person)
	{
		return new PersonPaymentManagerImpl(getModule(), person);
	}
}
