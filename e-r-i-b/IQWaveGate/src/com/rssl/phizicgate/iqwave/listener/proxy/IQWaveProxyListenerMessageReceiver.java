package com.rssl.phizicgate.iqwave.listener.proxy;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizicgate.iqwave.listener.IQWaveListenerMessageReceiverBase;
import com.rssl.phizicgate.iqwave.listener.MesssageProcessor;

/**
 * ������� ����������� ��������� �� IQWave ��� ������-�����
 * @author gladishev
 * @ created 15.10.13
 * @ $Author$
 * @ $Revision$
 */
public class IQWaveProxyListenerMessageReceiver extends IQWaveListenerMessageReceiverBase
{
	/**
	 * ctor
	 * @param factory - �������� �������
	 */
	public IQWaveProxyListenerMessageReceiver(GateFactory factory)
	{
		super(factory);
	}

	@Override
	protected MesssageProcessor getMessageProcessor(String requestType, String request)
	{
		return new ProxyExecutionResultProcessor(request);
	}
}
