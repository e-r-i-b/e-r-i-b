package com.rssl.phizic.business.dictionaries.synchronization.notification;

import com.rssl.phizic.gate.csa.MQInfo;
import com.rssl.phizic.gate.csa.NodeInfo;
import com.rssl.auth.csa.wsclient.NodeInfoConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.jms.JmsService;

import javax.jms.JMSException;

/**
 * @author akrenev
 * @ created 04.02.2014
 * @ $Author$
 * @ $Revision$
 *
 * Сервис опопвещения блоков о необходимости синхронизации справочников
 */

public class NotificationService
{
	public static final String SYNCHRONIZE_DICTIONARY_JMS_TYPE_NAME = "SynchronizeDictionary";

	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	private static final JmsService jmsService = new JmsService();

	/**
	 *
	 * @param dictionaryClass класс справочника
	 * @param mode режим синхронизации
	 */
	public void notify(Class dictionaryClass, SynchronizationMode mode)
	{
		NodeInfoConfig nodeInfoConfig = ConfigFactory.getConfig(NodeInfoConfig.class);
		NotificationEntity notificationEntity = new NotificationEntity(dictionaryClass, mode);
		for (NodeInfo info : nodeInfoConfig.getNodes())
		{
			try
			{
				MQInfo dictionaryMQ = info.getDictionaryMQ();
				jmsService.sendObjectToQueue(notificationEntity, dictionaryMQ.getQueueName(), dictionaryMQ.getFactoryName(), SYNCHRONIZE_DICTIONARY_JMS_TYPE_NAME, null);
			}
			catch (JMSException e)
			{
				log.error("Ошибка отправки JMS оповещения об изменении справочника " + notificationEntity.getClass().getSimpleName() + " на узел " + info.getName(), e);
			}
		}
	}
}
