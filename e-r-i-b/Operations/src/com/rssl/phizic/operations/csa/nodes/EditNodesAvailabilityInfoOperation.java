package com.rssl.phizic.operations.csa.nodes;

import com.rssl.phizic.gate.csa.MQInfo;
import com.rssl.phizic.gate.csa.NodeInfo;
import com.rssl.auth.csa.wsclient.NodeAvailabilityInfoService;
import com.rssl.auth.csa.wsclient.NodeInfoConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.node.events.UpdateNodeContextEvent;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.jms.JmsService;

import java.util.Collections;
import java.util.List;
import javax.jms.JMSException;

/**
 * @author akrenev
 * @ created 17.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * Операция работы с блоками
 */

public class EditNodesAvailabilityInfoOperation extends OperationBase<Restriction> implements EditEntityOperation<List<NodeInfo>, Restriction>
{
	private static final NodeAvailabilityInfoService service = new NodeAvailabilityInfoService();
	private static final JmsService jmsService = new JmsService();

	private List<NodeInfo> newNodesAvailabilityInfo;

	/**
	 * инициализация операции
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public void initialize() throws BusinessLogicException, BusinessException
	{
		newNodesAvailabilityInfo = getNodeInfo();
	}

	/**
	 * Изменение информации о состоянии блоков
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public void save() throws BusinessLogicException, BusinessException
	{
		try
		{
			service.changeAllNodesAvailabilityInfo(newNodesAvailabilityInfo);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException("Ошибка изменения информации о состоянии блоков.", e);
		}
		catch (GateException e)
		{
			throw new BusinessException("Ошибка изменения информации о состоянии блоков.", e);
		}

		for (NodeInfo nodeInfo : newNodesAvailabilityInfo)
			sendNotification(nodeInfo);
	}

	private void sendNotification(NodeInfo nodeInfo) throws BusinessException
	{
		try
		{
			MQInfo dictionaryMQ = ConfigFactory.getConfig(NodeInfoConfig.class).getNode(nodeInfo.getId()).getDictionaryMQ();
			jmsService.sendObjectToQueue(UpdateNodeContextEvent.getRefreshEvent(), dictionaryMQ.getQueueName(), dictionaryMQ.getFactoryName(), UpdateNodeContextEvent.TYPE, null);
		}
		catch (JMSException e)
		{
			throw new BusinessException("Ошибка оповещения об изменении состояния блоков.", e);
		}
	}

	public List<NodeInfo> getEntity()
	{
		return Collections.unmodifiableList(newNodesAvailabilityInfo);
	}


	/**
	 * Получение информации о состоянии блоков
	 * @return информация о состоянии блоков
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	private List<NodeInfo> getNodeInfo() throws BusinessLogicException, BusinessException
	{
		try
		{
			return service.getAllNodesAvailabilityInfo();
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException("Ошибка изменения информации о состоянии блоков.", e);
		}
		catch (GateException e)
		{
			throw new BusinessException("Ошибка изменения информации о состоянии блоков.", e);
		}
	}
}
