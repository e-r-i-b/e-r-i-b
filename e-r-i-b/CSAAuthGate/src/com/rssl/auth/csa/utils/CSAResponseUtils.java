package com.rssl.auth.csa.utils;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.CSAResponseConstants;
import com.rssl.phizic.gate.csa.MQInfo;
import com.rssl.phizic.gate.csa.NodeInfo;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.xml.transform.TransformerException;

/**
 * @author osminin
 * @ created 26.09.13
 * @ $Author$
 * @ $Revision$
 *
 * Класс для работы с ответами ЦСА
 */
public class CSAResponseUtils
{
	/**
	 * Создать информацию об узле
	 * @param element элемент содержащий информацию об узле
	 * @return информация об узле
	 * @throws javax.xml.transform.TransformerException
	 */
	public static NodeInfo createNodeInfo(Element element) throws TransformerException
	{
		Element nodeInfo = XmlHelper.selectSingleNode(element, CSAResponseConstants.NODE_INFO_TAG);
		return fillNodeInfo(nodeInfo);
	}

	/**
	 * @return список с информацией о блоках
	 */
	public static List<NodeInfo> getNodesInfo() throws Exception
	{
		Document document = CSABackRequestHelper.sendGetNodesInfoRq();
		return parseNodeInfo(document);
	}

	/**
	 * парсинг ответа от бека, содержащего информацию о блоках
	 * @param document ответ
	 * @return список с информацией о блоках
	 * @throws Exception
	 */
	public static List<NodeInfo> parseNodeInfo(Document document) throws Exception
	{
		Element nodesElement = XmlHelper.selectSingleNode(document.getDocumentElement(), CSAResponseConstants.NODES_TAG);
		final List<NodeInfo> result = new ArrayList<NodeInfo>();

		XmlHelper.foreach(nodesElement, CSAResponseConstants.NODE_INFO_TAG, new ForeachElementAction()
			{
				public void execute(Element element) throws GateException, GateLogicException
				{
					try
					{
						result.add(fillNodeInfo(element));
					}
					catch (TransformerException e)
					{
						throw new RuntimeException(e);
					}
				}
			});
		return result;
	}

	/**
	 * Создать информацию о пользователе
	 * @param element элемент, содержащий информацию о пользователе
	 * @return информация о пользователе
	 * @throws Exception
	 */
	public static UserInfo createUserInfo(Element element) throws Exception
	{
		Element userInfo = XmlHelper.selectSingleNode(element, CSAResponseConstants.USER_INFO_TAG);
		return fillUserInfo(userInfo);
	}

	private static UserInfo fillUserInfo(Element userInfo) throws Exception
	{
		String firstName = XmlHelper.getSimpleElementValue(userInfo, CSAResponseConstants.FIRSTNAME_TAG);
		String surName = XmlHelper.getSimpleElementValue(userInfo, CSAResponseConstants.SURNAME_TAG);
		String patrName = XmlHelper.getSimpleElementValue(userInfo, CSAResponseConstants.PATRNAME_TAG);
		String passport = XmlHelper.getSimpleElementValue(userInfo, CSAResponseConstants.PASSPORT_TAG);
		String tb = XmlHelper.getSimpleElementValue(userInfo, CSAResponseConstants.TB_TAG);
		Calendar birthDate = XMLDatatypeHelper.parseDateTime(XmlHelper.getSimpleElementValue(userInfo, CSAResponseConstants.BIRTHDATE_TAG));

		return new UserInfo(tb, firstName, surName, patrName, passport, birthDate);
	}

	private static NodeInfo fillNodeInfo(Element nodeInfo) throws TransformerException
	{
		NodeInfo info = new NodeInfo();

		String nodeIdStr = XmlHelper.getSimpleElementValue(nodeInfo, CSAResponseConstants.NODE_ID_TAG);
		info.setId(nodeIdStr == null ? null : Long.parseLong(nodeIdStr));
		info.setName(XmlHelper.getSimpleElementValue(nodeInfo, CSAResponseConstants.NODE_NAME_TAG));
		info.setNewUsersAllowed(Boolean.parseBoolean(XmlHelper.getSimpleElementValue(nodeInfo, CSAResponseConstants.NODE_INFO_NEW_USERS_ALLOWED_NODE_NAME)));
		info.setExistingUsersAllowed(Boolean.parseBoolean(XmlHelper.getSimpleElementValue(nodeInfo, CSAResponseConstants.NODE_INFO_EXISTING_USERS_ALLOWED_NODE_NAME)));
		info.setTemporaryUsersAllowed(Boolean.parseBoolean(XmlHelper.getSimpleElementValue(nodeInfo, CSAResponseConstants.NODE_INFO_TEMPORARY_USERS_ALLOWED_NODE_NAME)));
		info.setUsersTransferAllowed(Boolean.parseBoolean(XmlHelper.getSimpleElementValue(nodeInfo, CSAResponseConstants.NODE_INFO_USERS_TRANSFER_ALLOWED_NODE_NAME)));
		info.setAdminAvailable(Boolean.parseBoolean(XmlHelper.getSimpleElementValue(nodeInfo, CSAResponseConstants.NODE_INFO_ADMIN_AVAILABLE_NODE_NAME)));
		info.setGuestAvailable(Boolean.parseBoolean(XmlHelper.getSimpleElementValue(nodeInfo, CSAResponseConstants.NODE_INFO_GUEST_AVAILABLE_NODE_NAME)));
		info.setHostname(XmlHelper.getSimpleElementValue(nodeInfo, CSAResponseConstants.HOST_TAG));
		info.setListenerHost(XmlHelper.getSimpleElementValue(nodeInfo, CSAResponseConstants.LISTENER_HOST_TAG));

		String smsQueueName = XmlHelper.getSimpleElementValue(nodeInfo, CSAResponseConstants.SMS_QUEUE_NAME_TAG);
		String smsFactoryName = XmlHelper.getSimpleElementValue(nodeInfo, CSAResponseConstants.SMS_FACTORY_NAME_TAG);
		String ermbQueueName = XmlHelper.getSimpleElementValue(nodeInfo, CSAResponseConstants.ERMB_QUEUE_NAME_TAG);
		String ermbFactoryName = XmlHelper.getSimpleElementValue(nodeInfo, CSAResponseConstants.ERMB_FACTORY_NAME_TAG);
		String dictionaryQueueName = XmlHelper.getSimpleElementValue(nodeInfo, CSAResponseConstants.DICTIONARY_QUEUE_NAME_TAG);
		String dictionaryFactoryName = XmlHelper.getSimpleElementValue(nodeInfo, CSAResponseConstants.DICTIONARY_FACTORY_NAME_TAG);
		String multiNodeDataQueueName = XmlHelper.getSimpleElementValue(nodeInfo, CSAResponseConstants.MULTI_NODE_DATA_QUEUE_NAME_TAG);
		String multiNodeDataFactoryName = XmlHelper.getSimpleElementValue(nodeInfo, CSAResponseConstants.MULTI_NODE_DATA_FACTORY_NAME_TAG);
		String mbkRegistrationQueueName = XmlHelper.getSimpleElementValue(nodeInfo, CSAResponseConstants.MBK_REGISTRATION_QUEUE_NAME_TAG);
		String mbkRegistrationFactoryName = XmlHelper.getSimpleElementValue(nodeInfo, CSAResponseConstants.MBK_REGISTRATION_FACTORY_NAME_TAG);

		info.setSmsMQ(new MQInfo(smsQueueName, smsFactoryName));
		info.setDictionaryMQ(new MQInfo(dictionaryQueueName, dictionaryFactoryName));
		info.setMultiNodeDataMQ(new MQInfo(multiNodeDataQueueName, multiNodeDataFactoryName));
		info.setMbkRegistrationMQ(new MQInfo(mbkRegistrationQueueName, mbkRegistrationFactoryName));
		info.setErmbQueueMQ(new MQInfo(ermbQueueName, ermbFactoryName));

		return info;
	}
}
