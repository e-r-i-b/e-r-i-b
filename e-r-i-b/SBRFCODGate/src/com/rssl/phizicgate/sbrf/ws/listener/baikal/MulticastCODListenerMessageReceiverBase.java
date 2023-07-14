package com.rssl.phizicgate.sbrf.ws.listener.baikal;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.InvalidTargetException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.listener.ListenerMessageReceiver;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

/**
 * @author krenev
 * @ created 01.10.2010
 * @ $Author$
 * @ $Revision$
 * ������� ���������� ��������� �� ������� ��� ������������ �����
 */
public abstract class MulticastCODListenerMessageReceiverBase extends AbstractService implements ListenerMessageReceiver
{
	protected static final String DELEGATE_KEY = ".baikal.delegate";
	private static Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE);
	private ListenerMessageReceiver delegate;

	public MulticastCODListenerMessageReceiverBase(GateFactory factory) throws GateException
	{
		super(factory);
		delegate = (ListenerMessageReceiver) getDelegate(ListenerMessageReceiver.class.getName() + DELEGATE_KEY);
	}

	public String handleMessage(String xmlMessage) throws GateException
	{
		try
		{
			log.debug("�������� ���������� ���������");
			String result = delegate.handleMessage(xmlMessage);
			log.debug("C�������� ���������� ����");
			return result;
		}
		catch (InvalidTargetException e)
		{
			log.debug("��������� � ��� �� ���������: ���������� ������ �� �������", e);
			String result = multicast(xmlMessage);
			log.debug("��������� ���������� �� ����");
			return result;
		}
	}

	/**
	 * ��������� ��������� ������ �� �������.
	 * @param xmlMessage ���������
	 * @return �����
	 */
	protected abstract String multicast(String xmlMessage) throws GateException;
}
