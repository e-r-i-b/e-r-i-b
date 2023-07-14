package com.rssl.phizic.csaadmin.listeners.mail;

import com.rssl.phizic.gate.csa.MQInfo;
import com.rssl.phizic.gate.csa.NodeInfo;
import com.rssl.auth.csa.wsclient.NodeInfoConfig;
import com.rssl.phizic.common.types.mail.Constants;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.csaadmin.business.common.AdminException;
import com.rssl.phizic.csaadmin.config.CSAAdminConfig;
import com.rssl.phizic.csaadmin.listeners.generated.GetMultiNodeListParametersType;
import com.rssl.phizic.csaadmin.listeners.generated.GetMultiNodeParametersType;
import com.rssl.phizic.utils.jms.JmsService;
import com.rssl.phizic.utils.xml.XmlEntityBuilder;

import java.util.HashMap;
import java.util.Map;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

/**
 * @author mihaylov
 * @ created 10.06.14
 * @ $Author$
 * @ $Revision$
 *
 * —ервис получени€ данных из нескольких блоков
 */
public class MultiNodeDataService
{
	private static final JmsService jmsService = new JmsService();
	private static final String RESPONSE_SELECTOR_PATTERN = "JMSCorrelationID = '%s'";

	/**
	 * ѕослать запрос на получение данных из нескольких блоков. ѕолучить ответные данные от блоков.
	 * @param request - запрос на получение данных из блока
	 * @param requestType - тип запроса
	 * @param <T> - тип данных получаемых из блока
	 * @return мапа с идентификатором блока в качестве ключа и данными из этого блока в качестве значени€
	 * @throws AdminException
	 */
	public <T> Map<Long,T> getMultiNodeData(String request, MultiNodeRequestType requestType) throws AdminException
	{
		CSAAdminConfig csaConfig = ConfigFactory.getConfig(CSAAdminConfig.class);
		NodeInfoConfig nodeConfig = ConfigFactory.getConfig(NodeInfoConfig.class);
		Map<Long,String> requestIdMap = new HashMap<Long, String>();
		Map<Long,T> resultMap = new HashMap<Long, T>();
		try
		{
			for(NodeInfo nodeInfo : nodeConfig.getNodes())
			{
				MQInfo mqInfo = nodeInfo.getMultiNodeDataMQ();
				TextMessage requestMessage = jmsService.sendMessageToQueue(request, mqInfo.getQueueName(), mqInfo.getFactoryName(), requestType.getRequestName(), null);
				requestIdMap.put(nodeInfo.getId(),requestMessage.getJMSMessageID());
			}
			for(Map.Entry<Long, String> requestId : requestIdMap.entrySet())
			{
				String messageSelector = String.format(RESPONSE_SELECTOR_PATTERN, requestId.getValue());
				ObjectMessage responseMessage = jmsService.receiveMessageFormQueue(Constants.QUEUE_NAME, Constants.FACTORY_NAME, messageSelector, csaConfig.getMultiNodeRequestTimeOut());
				if(responseMessage == null)
					throw new AdminException("ќшибка получени€ списка из блока с идентификатором " + requestId.getKey() + ". ѕревышен таймаут ожидани€");

				Object responseObject = responseMessage.getObject();
				if(responseObject != null && responseObject instanceof Exception)
					throw new AdminException("ќшибка получени€ списка из блока",(Exception)responseObject);
				else if(responseObject != null)
					resultMap.put(requestId.getKey(),(T)responseObject);
			}
		}
		catch (JMSException e)
		{
			throw new AdminException("ќшибка получени€ списка из блока",e);
		}
		return resultMap;
	}

	/**
	 * ѕостроить запрос на получение данных из блока
	 * @param parameters - параметры запроса
	 * @param requestType - тип запроса
	 * @return XML запрос на получение данных
	 */
	public String buildRequest(GetMultiNodeParametersType parameters, MultiNodeRequestType requestType)
	{
		XmlEntityBuilder builder = new XmlEntityBuilder();
		builder.openEntityTag(requestType.getRequestName());
		builder.createEntityTag(Constants.PARAMETERS_TAG_NAME, parameters.getParameters());
		builder.closeEntityTag(requestType.getRequestName());
		return builder.toString();
	}

	/**
	 * ѕостроить запрос на получение листовых данных из блока
	 * @param parameters - параметры запроса
	 * @param requestType - тип запроса
	 * @return XML запрос на получение данных
	 */
	public String buildListRequest(GetMultiNodeListParametersType parameters, MultiNodeRequestType requestType)
	{
		XmlEntityBuilder builder = new XmlEntityBuilder();
		builder.openEntityTag(requestType.getRequestName());
		builder.createEntityTag(Constants.PARAMETERS_TAG_NAME, parameters.getParameters());
		builder.createEntityTag(Constants.MAX_RESULTS_TAG_NAME,String.valueOf(parameters.getFirstResult() + parameters.getMaxResults()));
		builder.createEntityTag(Constants.FIRST_RESULT_TAG_NAME,"0");
		builder.createEntityTag(Constants.ORDER_PARAMETERS_TAG_NAME,parameters.getOrderParameters());
		builder.closeEntityTag(requestType.getRequestName());
		return builder.toString();
	}

}
