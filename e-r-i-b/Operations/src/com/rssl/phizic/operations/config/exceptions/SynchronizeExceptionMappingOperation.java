package com.rssl.phizic.operations.config.exceptions;

import com.rssl.phizic.gate.csa.MQInfo;
import com.rssl.phizic.gate.csa.NodeInfo;
import com.rssl.auth.csa.wsclient.NodeInfoConfig;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.synchronization.notification.NotificationEntity;
import com.rssl.phizic.business.dictionaries.synchronization.notification.SynchronizationMode;
import com.rssl.phizic.business.exception.ExceptionMapping;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.jms.JmsService;
import org.apache.commons.lang.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.jms.JMSException;

/**
 * @author osminin
 * @ created 26.03.15
 * @ $Author$
 * @ $Revision$
 *
 * Операция синхронизации справочника маппинга ошибок между блоками
 */
public class SynchronizeExceptionMappingOperation extends OperationBase
{
	private static final String SYNCHRONIZE_DICTIONARY_JMS_TYPE_NAME = "SynchronizeDictionary";
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static JmsService jmsService = new JmsService();

	private List<Long> nodeIds = new ArrayList<Long>();

	/**
	 * Инициализация операции
	 * @param nodeIds номера блоков для синхронизации
	 */
	public void initialize(Long[] nodeIds) throws BusinessLogicException
	{
		if (ArrayUtils.isEmpty(nodeIds))
		{
			throw new BusinessLogicException("Не указаны блоки для репликации настроек");
		}
		this.nodeIds = Arrays.asList(nodeIds);
	}

	/**
	 * Синхронизировать справочник между блоками
	 * @return списиок блоков, с которыми не удалось синхронизироваться
	 */
	public List<Long> synchronize()
	{
		List<Long> errorNodes = new ArrayList<Long>(nodeIds.size());
		NotificationEntity notificationMapping = new NotificationEntity(ExceptionMapping.class, SynchronizationMode.SOFT);

		for (Long nodeId : nodeIds)
		{
			NodeInfo node = ConfigFactory.getConfig(NodeInfoConfig.class).getNode(nodeId);

			if (node == null)
			{
				log.error("Ошибка синхронизации: не найден блок с идентификатором " + nodeId);
			}
			else if (!isSynchronize(node.getDictionaryMQ(), notificationMapping))
			{
				errorNodes.add(nodeId);
			}
		}

		return errorNodes;
	}

	private boolean isSynchronize(MQInfo dictionaryMQ, NotificationEntity entity)
	{
		try
		{
			jmsService.sendObjectToQueue(entity, dictionaryMQ.getQueueName(), dictionaryMQ.getFactoryName(), SYNCHRONIZE_DICTIONARY_JMS_TYPE_NAME, null);
			return true;
		}
		catch (JMSException e)
		{
			log.error(e.getMessage(), e);
		}
		return false;
	}
}
