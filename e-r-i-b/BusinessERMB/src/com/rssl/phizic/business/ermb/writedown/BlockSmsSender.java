package com.rssl.phizic.business.ermb.writedown;

import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.business.sms.ErmbBusinessTextMessageBuilder;
import com.rssl.phizic.common.types.TextMessage;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.messaging.MessagingSingleton;
import com.rssl.phizic.messaging.mail.SmsTransportService;

/**
 * Отправка СМС о блокировке/разблокировке мобильного банка по неоплате
 * @author Puzikov
 * @ created 06.06.14
 * @ $Author$
 * @ $Revision$
 */

class BlockSmsSender
{
	private final ErmbBusinessTextMessageBuilder messageBuilder = new ErmbBusinessTextMessageBuilder();
	private final SmsTransportService messagingService = MessagingSingleton.getInstance().getErmbSmsTransportService();

	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	/**
	 * Отправить СМС о блокировке по неоплате
	 * @param profile ермб профиль
	 */
	void sendBlockSms(ErmbProfileImpl profile)
	{
		try
		{
			TextMessage message = messageBuilder.buildNonPayedBlockMessage();
			sendSms(profile, message);
		}
		catch (Exception e)
		{
			log.error("Не удалось отправить оповещение о блокировке ЕРМБ по неоплате", e);
		}
	}

	/**
	 * Отправить СМС о разблокировке по неоплате
	 * @param profile ермб профиль
	 */
	void sendUnblockSms(ErmbProfileImpl profile)
	{
		try
		{
			TextMessage message = messageBuilder.buildNonPayedUnblockMessage();
			sendSms(profile, message);
		}
		catch (IKFLMessagingException e)
		{
			log.error("Не удалось отправить оповещение о разблокировке ЕРМБ по неоплате", e);
		}
	}

	private void sendSms(ErmbProfileImpl profile, TextMessage message) throws IKFLMessagingException
	{
		String activePhone = profile.getMainPhoneNumber();
		messagingService.sendSms(activePhone, message.getText(), message.getTextToLog(), message.getPriority());
	}
}
