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
 * �������� ��� � ����������/������������� ���������� ����� �� ��������
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
	 * ��������� ��� � ���������� �� ��������
	 * @param profile ���� �������
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
			log.error("�� ������� ��������� ���������� � ���������� ���� �� ��������", e);
		}
	}

	/**
	 * ��������� ��� � ������������� �� ��������
	 * @param profile ���� �������
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
			log.error("�� ������� ��������� ���������� � ������������� ���� �� ��������", e);
		}
	}

	private void sendSms(ErmbProfileImpl profile, TextMessage message) throws IKFLMessagingException
	{
		String activePhone = profile.getMainPhoneNumber();
		messagingService.sendSms(activePhone, message.getText(), message.getTextToLog(), message.getPriority());
	}
}
