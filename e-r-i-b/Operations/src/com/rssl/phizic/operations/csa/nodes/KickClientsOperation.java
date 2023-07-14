package com.rssl.phizic.operations.csa.nodes;

import com.rssl.phizic.gate.csa.MQInfo;
import com.rssl.phizic.gate.csa.NodeInfo;
import com.rssl.auth.csa.wsclient.NodeInfoConfig;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.node.events.UpdateNodeContextEvent;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.jms.JmsService;

import java.util.Calendar;
import javax.jms.JMSException;

/**
 * @author akrenev
 * @ created 19.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * �������� ��������� ������ �������� � �����
 */

public class KickClientsOperation extends OperationBase
{
	private static final JmsService jmsService = new JmsService();

	private MQInfo dictionaryMQ;

	/**
	 * ������������� ��������
	 * @param nodeId ������������� �����
	 * @throws BusinessLogicException
	 */
	public void initialize(Long nodeId) throws BusinessLogicException
	{
		NodeInfo node = ConfigFactory.getConfig(NodeInfoConfig.class).getNode(nodeId);

		if (node == null)
			throw new BusinessLogicException("������ ��������� ������ ��������: �� ������ ���� � ��������������� " + nodeId);

		dictionaryMQ = node.getDictionaryMQ();
	}

	/**
	 * ��������� ������ �������� � �����
	 * @throws BusinessLogicException
	 */
	public void kick() throws BusinessLogicException
	{
		try
		{
			jmsService.sendObjectToQueue(UpdateNodeContextEvent.getStopClientSessionEvent(Calendar.getInstance()), dictionaryMQ.getQueueName(), dictionaryMQ.getFactoryName(), UpdateNodeContextEvent.TYPE, null);
		}
		catch (JMSException e)
		{
			throw new BusinessLogicException("������ ��������� ������ ��������: �� ������� ��������� jms ����������.", e);
		}
	}
}
