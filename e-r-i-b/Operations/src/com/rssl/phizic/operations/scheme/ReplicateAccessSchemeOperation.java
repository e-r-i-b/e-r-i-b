package com.rssl.phizic.operations.scheme;

import com.rssl.phizic.gate.csa.MQInfo;
import com.rssl.phizic.gate.csa.NodeInfo;
import com.rssl.auth.csa.wsclient.NodeInfoConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.schemes.AccessScheme;
import com.rssl.phizic.business.schemes.AccessSchemeService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.jms.JmsService;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;
import javax.jms.JMSException;


/**
 * Операция синхронизации схем прав доступа между блоками
 * @author koptyaev
 * @ created 26.05.14
 * @ $Author$
 * @ $Revision$
 */
public class ReplicateAccessSchemeOperation extends OperationBase
{
	private static final String REPLICATE_ACCESS_SCHEME_MESSAGE_KEY = "ReplicateAccessSchemes";
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final JmsService jmsService = new JmsService();
	private List<Long> nodeIds = new ArrayList<Long>();
	private ArrayList<AccessScheme> schemes = new ArrayList<AccessScheme>();
	private static final AccessSchemeService accessSchemeService = new AccessSchemeService();

	/**
	 * Инициализация
	 * @param schemeIds список идентификаторов схем прав доступа
	 * @param nodeIds список идентификаторов нод
	 */
	public void initialize(String[] schemeIds, Long[] nodeIds)
	{
		this.nodeIds = Arrays.asList(nodeIds);
		for(String schemeId: schemeIds)
		{
			try
			{
				AccessScheme scheme = accessSchemeService.findByExternalId(Long.parseLong(schemeId));
				schemes.add(scheme);
			}
			catch (Exception e)
			{
				log.error("Не удалось получить схему прав с идентификатором "+schemeId, e);
			}
		}
	}

	/**
	 * Репликация выбранных настроек в MQ блоков
	 * @return список нереплицированных нод
	 */
	public List<Long> replicate() throws BusinessException, BusinessLogicException
	{
		if (CollectionUtils.isEmpty(nodeIds))
			throw new BusinessLogicException("Не указаны блоки для репликации настроек");

		List<Long> errorNodes = new ArrayList<Long>();

		for (Long nodeId : nodeIds)
		{
			NodeInfo node = ConfigFactory.getConfig(NodeInfoConfig.class).getNode(nodeId);

			if (node == null)
			{
				log.error("Ошибка репликации: не найден блок с идентификатором " + nodeId);
				continue;
			}

			MQInfo dictionaryMQ = node.getDictionaryMQ();
			try
			{
				jmsService.sendObjectToQueue(schemes, dictionaryMQ.getQueueName(), dictionaryMQ.getFactoryName(), REPLICATE_ACCESS_SCHEME_MESSAGE_KEY, null);
			}
			catch (JMSException e)
			{
				log.error(e.getMessage(), e);
				errorNodes.add(nodeId);
			}
		}

		return errorNodes;
	}
}
