package com.rssl.phizic.ermb.transport;

import com.rssl.phizic.TestEjbMessage;
import com.rssl.phizic.messaging.MessageProcessorBase;
import com.rssl.phizic.module.Module;

/**
 * ����������� ��������� ������� � �������� ����������� �����
 * @author Puzikov
 * @ created 05.06.14
 * @ $Author$
 * @ $Revision$
 */

public class EchoTestProcessor extends MessageProcessorBase<TestEjbMessage>
{
	/**
	 * ctor
	 * @param module - ������
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
		// � �������� ���������� �� ����
		return false;
	}
}
