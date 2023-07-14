package com.rssl.auth.csa.back.integration.mobilebank;

import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.Profile;
import com.rssl.auth.csa.back.servises.connectors.ERMBConnector;
import com.rssl.phizic.common.types.TextMessage;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.gate.mobilebank.MessageInfo;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.messaging.ermb.jms.JmsTransportService;
import com.rssl.phizic.messaging.mail.SmsTransportService;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author osminin
 * @ created 30.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Класс-маршрутизатор смс-сообщений.
 * Если у профиля есть ЕРМБ коннекторы, сообщения отправляются в очередь транспортного канала.
 * Если ЕРМБ коннекторов нет, сообщения отправляются через МБК
 */
public class SendMessageRouter
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);
	private static final SendMessageRouter INSTANCE = new SendMessageRouter();

	private SmsTransportService smsTransportService;

	private SendMessageRouter()
	{
		smsTransportService = new JmsTransportService();
	}

	/**
	 * @return инстанс маршрутизатора
	 */
	public static SendMessageRouter getInstance()
	{
		return INSTANCE;
	}

	/**
	 * Отправить сообщение
	 * @param info информация о сообщении
	 */
	public void sendMessage(SendMessageInfo info) throws Exception
	{
		if (info == null)
		{
			throw new IllegalArgumentException("Информация о сообщении не должна быть null");
		}

		// если заданы номера телефонов, то отправляем конкретно на них
		Set<String> phones = info.getPhones();
		if(CollectionUtils.isNotEmpty(phones))
		{
			sendByPhones(info, phones);
			return;
		}

		List<ERMBConnector> connectors = getERMBConnectors(info);
		//Если нет ЕРМБ коннекторов, сообщение отправляется через МБК
		Set<String> excludedPhones = info.getExcludedPhones();
		if (CollectionUtils.isEmpty(connectors))
		{
			sendToMBK(info, excludedPhones);
		}
		else //иначе складываем в очередь транспортного канала
		{
			sendToTransportQueue(info, connectors, excludedPhones);
		}
	}

	private void sendByPhones(SendMessageInfo info, Set<String> phones) throws Exception
	{
		for(String phone : phones)
		{
			// наверно лучше пачками
			if(Connector.getProfileIdByPhoneNumber(phone) != null)
			{
				sendToTransportQueue(info.getMessageInfo(), phone);
			}
			else
			{
				MobileBankService.getInstance().sendSmsByPhones(Collections.singleton(phone), info.getMessageInfo(), info.isCheckIMSI());
			}
		}
	}

	private void sendToTransportQueue(SendMessageInfo sendMessageInfo, List<ERMBConnector> connectors, Set<String> excludedPhones) throws Exception
	{
		try
		{
			MessageInfo info = sendMessageInfo.getMessageInfo();
			for (ERMBConnector connector : connectors)
			{
				if (excludedPhones == null || !excludedPhones.contains(connector.getPhoneNumber()))
				smsTransportService.sendSms(connector.getPhoneNumber(), info.getText(), info.getTextToLog(), TextMessage.DEFAULT_PRIORITY);
			}
		}
		catch (IKFLMessagingException e)
		{
			log.error(e.getMessage(), e);
			throw new Exception(e);
		}
	}

	private void sendToTransportQueue(MessageInfo info, String phone) throws Exception
	{
		try
		{
			smsTransportService.sendSms(phone, info.getText(), info.getTextToLog(), TextMessage.DEFAULT_PRIORITY);
		}
		catch (IKFLMessagingException e)
		{
			log.error(e.getMessage(), e);
			throw new Exception(e);
		}
	}

	private void sendToMBK(SendMessageInfo sendMessageInfo, Set<String> excludedPhones) throws Exception
	{
		if(StringHelper.isNotEmpty(sendMessageInfo.getCardNumber()))
		{
			MobileBankService.getInstance().sendSMSByCardNumber(sendMessageInfo, excludedPhones);
		}
		else if(CollectionUtils.isNotEmpty(sendMessageInfo.getPhones()))
		{
			MobileBankService.getInstance().sendSmsByPhones(sendMessageInfo.getPhones(), sendMessageInfo.getMessageInfo(), sendMessageInfo.isCheckIMSI());
		}
		else
			throw new IllegalStateException("Неправильное состояние объекта sendMessageInfo");

	}

	private List<ERMBConnector> getERMBConnectors(SendMessageInfo info) throws Exception
	{
		//Если идентификатор профиля не задан, то используем профль из любого найденного коннектора по карте
		List<ERMBConnector> connectors = (info.getProfileId() == null) ?
				ERMBConnector.getByCardNumber(info.getCardNumber()) :
				ERMBConnector.getByProfileId(info.getProfileId());
		return getResultConnectors(connectors);
	}

	private List<ERMBConnector> getResultConnectors(List<ERMBConnector> connectors) throws Exception
	{
		if (CollectionUtils.isEmpty(connectors))
			return Collections.emptyList();
		Profile profile = connectors.get(0).getProfile();
		return ERMBConnector.findNotClosedByClientInAnyTB(profile.getSurname(), profile.getFirstname(),
				profile.getPatrname(), profile.getBirthdate(), Collections.singletonList(profile.getPassport()));
	}

	/**
	 * получить номера телефонов для отправки sms сообщения в случае неудачи с push сообщением
	 * @param info информация о сообщении
	 * @return
	 * @throws Exception
	 */
	public List<String> getPhones(SendMessageInfo info) throws Exception
	{
		if (info == null)
		{
			throw new IllegalArgumentException("Информация о сообщении не должна быть null");
		}

		List<ERMBConnector> connectors = getERMBConnectors(info);
		//Если нет ЕРМБ коннекторов, сообщение отправляется через МБК
		if (CollectionUtils.isEmpty(connectors))
		{
			return MobileBankService.getInstance().getPhones(info);
		}
		else //иначе складываем в очередь транспортного канала
		{
			List<String> phones = new ArrayList<String>();
			for (ERMBConnector connector : connectors)
				phones.add(connector.getPhoneNumber());
			return phones;
		}
	}
}
