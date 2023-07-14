package com.rssl.phizic.csa.ejb;

import com.rssl.auth.csa.utils.CSAResponseUtils;
import com.rssl.auth.csa.utils.UpdateProfileInfo;
import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.CSAResponseConstants;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.phizic.TBSynonymsDictionary;
import com.rssl.phizic.common.type.ConfirmProfilesRequest;
import com.rssl.phizic.common.type.ErmbProfileIdentity;
import com.rssl.phizic.common.type.ErmbUpdateProfileInfo;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.config.CSAFrontConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.csa.exceptions.CSAGetNodeInfoException;
import com.rssl.phizic.csa.exceptions.CSAGetNodeInfoLogicException;
import com.rssl.phizic.gate.csa.MQInfo;
import com.rssl.phizic.gate.csa.NodeInfo;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.synchronization.SOSMessageHelper;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.collections.CollectionUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.text.ParseException;
import java.util.*;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBException;

/**
 * @author osminin
 * @ created 04.11.13
 * @ $Author$
 * @ $Revision$
 *
 * Слушатель очереди сообщений служебного канала, подтверждающих обновление профилей
 */
public class CSAServiceProfileEjbListener extends CSAEjbListenerBase
{
	public void onMessage(Message message)
	{
		ApplicationInfo.setCurrentApplication(Application.CsaErmbListener);
		try
		{
			TextMessage textMessage = (TextMessage) message;
			//Возможно, что обновленные профили из разных блоков, поэтому строим мапу: блок - список профилей
			Map<MQInfo, List<ErmbUpdateProfileInfo>> nodeRequestMap = getNodeRequestMap(textMessage.getText());
			//строим по мапе запросы в блоки и отправляем
			for (MQInfo mqInfo : nodeRequestMap.keySet())
			{
				String text = SOSMessageHelper.getConfirmProfileRqXml(nodeRequestMap.get(mqInfo));
				jmsService.sendMessageToQueue(text, mqInfo.getQueueName(), mqInfo.getFactoryName(), null, null);
			}
		}
		catch (CSAGetNodeInfoLogicException e)
		{
			log.error(e.getMessage(), e);
		}
		catch (CSAGetNodeInfoException e)
		{
			log.error("Ошибка получения информации о блоке пользователя.", e);
		}
		catch (JAXBException e)
		{
			log.error("Ошибка распаковки бина по входящего сообщения.", e);
		}
		catch (ParseException e)
		{
			log.error("Ошибка при разборе входящего сообщения.", e);
		}
		catch (JMSException e)
		{
			log.error("Ошибка при работе с JMS.", e);
		}
		catch (Exception e)
		{
			log.error("Ошибка отправки сообщения в очередь.", e);
		}
		finally
		{
			ApplicationInfo.setDefaultApplication();
		}
	}

	@Override
	protected boolean writeAvailable()
	{
		return ConfigFactory.getConfig(CSAFrontConfig.class).isConfirmProfileMessageLogAvailable();
	}

	@Override
	protected String getMessageType()
	{
		return "sos-ConfirmProfile";
	}

	private Map<MQInfo, List<ErmbUpdateProfileInfo>> getNodeRequestMap(String text) throws Exception
	{
		ConfirmProfilesRequest confirmProfilesRequest = sosMessageHelper.getConfirmProfilesRequest(text);
		//Пишем сообщение в лог
		writeMessageToLog(text, confirmProfilesRequest.getRqUID());

		List<ErmbUpdateProfileInfo> profiles = confirmProfilesRequest.getConfirmProfilesRqInfo();
		//строим мап: информация о пользователе, приведенная к строке (getKey(userInfo)) - ErmbUpdateProfileInfo
		//мап нужен дя того, что бы при ответе из ЦСА с информацией о блоках распределить ErmbUpdateProfileInfo по блокам
		final Map<String, ErmbUpdateProfileInfo> profilesMap = new HashMap<String, ErmbUpdateProfileInfo>(profiles.size());

		List<UpdateProfileInfo> updateProfileInfoList = new ArrayList<UpdateProfileInfo>(profiles.size());
		//Заполняем список информацией об обновленных профилях для запроса в ЦСА
		for (ErmbUpdateProfileInfo profile : profiles)
		{
			UserInfo newUserInfo = fillUserInfo(profile.getClientIdentity());
			profilesMap.put(getKey(newUserInfo), profile);
			updateProfileInfoList.add(new UpdateProfileInfo(newUserInfo, getOldUserInfo(profile.getClientOldIdentity())));
		}
		//выполняем запрос в ЦСА, по ответу строим мап для маршрутизации сообщения
		return createNodeRequestMap(getResponse(updateProfileInfoList), profilesMap);
	}

	private Map<MQInfo, List<ErmbUpdateProfileInfo>> createNodeRequestMap(Document response, final Map<String, ErmbUpdateProfileInfo> profilesMap) throws Exception
	{
		final Map<MQInfo, List<ErmbUpdateProfileInfo>> nodeRequestMap = new HashMap<MQInfo, List<ErmbUpdateProfileInfo>>();
		//обрабатываем ответ ЦСА
		XmlHelper.foreach(response.getDocumentElement(), CSAResponseConstants.UPDATE_PROFILE_INFO_TAG, new ForeachElementAction()
		{
			public void execute(Element element) throws Exception
			{
				UserInfo userInfo = CSAResponseUtils.createUserInfo(element);
				NodeInfo nodeInfo = CSAResponseUtils.createNodeInfo(element);

				List<ErmbUpdateProfileInfo> nodeRequestInfo = nodeRequestMap.get(nodeInfo.getErmbQueueMQ());
				if (nodeRequestInfo == null)
				{
					nodeRequestInfo = new ArrayList<ErmbUpdateProfileInfo>();
				}
				nodeRequestInfo.add(profilesMap.get(getKey(userInfo)));

				nodeRequestMap.put(nodeInfo.getErmbQueueMQ(), nodeRequestInfo);
			}
		});

		return nodeRequestMap;
	}

	private List<UserInfo> getOldUserInfo(List<ErmbProfileIdentity> clientOldIdentity)
	{
		if (CollectionUtils.isNotEmpty(clientOldIdentity))
		{
			List<UserInfo> oldUserInfo = new ArrayList<UserInfo>(clientOldIdentity.size());
			for (ErmbProfileIdentity identity : clientOldIdentity)
			{
				oldUserInfo.add(fillUserInfo(identity));
			}
			return oldUserInfo;
		}
		return Collections.emptyList();
	}

	@Override
	protected MQInfo getMQInfo(String message) throws Exception
	{
		//Не используется, тк переопределен метод, вызывающий getMQInfo
		throw new UnsupportedOperationException();
	}

	private Document getResponse(List<UpdateProfileInfo> updateProfileInfoList) throws Exception
	{
		try
		{
			//запрос в ЦСА на получение информации о блоках
			return CSABackRequestHelper.sendFindNodesByUpdatedProfilesRq(updateProfileInfoList);
		}
		catch (BackLogicException e)
		{
			throw new CSAGetNodeInfoLogicException(e.getMessage(), e);
		}
		catch (BackException e)
		{
			throw new CSAGetNodeInfoException(e);
		}
	}

	private String getKey(UserInfo userInfo)
	{
		//строим строковый ключ по информации о пользователе
		StringBuilder builder = new StringBuilder();

		//Могут придти синонимы
		String tb = ConfigFactory.getConfig(TBSynonymsDictionary.class).getMainTBBySynonymAndIdentical(userInfo.getCbCode());

		builder.append(userInfo.getSurname().trim()).append(" ")
			    .append(userInfo.getFirstname().trim()).append(" ")
				.append(StringHelper.getEmptyIfNull(userInfo.getPatrname()).trim()).append(" ")
				.append(",")
				.append(userInfo.getPassport().replace(" ", ""))
				.append(",")
				.append(DateHelper.formatDateToString2(userInfo.getBirthdate()))
				.append(",")
				.append(tb);
		return builder.toString();
	}
}
