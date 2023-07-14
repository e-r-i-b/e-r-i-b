package com.rssl.phizic.ermb.transport;

import com.rssl.phizic.TestEjbMessage;
import com.rssl.phizic.messaging.MessageProcessorBase;
import com.rssl.phizic.module.Module;

/**
 * Заглушечный процессор ответов о списании абонентской платы
 * @author Puzikov
 * @ created 05.06.14
 * @ $Author$
 * @ $Revision$
 */

public class EchoTestProcessor extends MessageProcessorBase<TestEjbMessage>
{
	/**
	 * ctor
	 * @param module - модуль
	 */
	public EchoTestProcessor(Module module)
	{
		super(module);
	}

	@Override
	protected void doProcessMessage(TestEjbMessage xmlRequest)
	{
	}

	@Override
	public boolean writeToLog()
	{
		// в заглушке логировать не надо
		return false;
	}
}
