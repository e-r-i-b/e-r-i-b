package com.rssl.phizic.business.ermb.migration.list.task.migration;

import com.rssl.phizic.business.ermb.migration.list.entity.migrator.Client;
import com.rssl.phizic.business.ermb.migration.list.entity.migrator.Phone;
import com.rssl.phizic.common.types.TextMessage;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.messaging.MessageComposeHelper;
import com.rssl.phizic.messaging.MessagingSingleton;
import com.rssl.phizic.messaging.mail.SmsTransportService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Делает рассылку смс по телефонам клиента по результатам миграции
 * @author Puzikov
 * @ created 05.03.14
 * @ $Author$
 * @ $Revision$
 */

class MigrationSmsSender
{
	private Client client;
	private Phone activePhone;
	private Set<String> nonActivePhoneNumbers = new HashSet<String>();

	/**
	 * ctor
	 * @param client сегментированный клиент, по телефонам которого нужно сделать рассылку
	 * @param activePhone активный телефон (null - не создан профиль)
	 * @param nonActivePhoneNumbers набор неактивных номеров телефона
	 */
	MigrationSmsSender(Client client, Phone activePhone, Set<String> nonActivePhoneNumbers)
	{
		this.client = client;
		this.activePhone = activePhone;
		this.nonActivePhoneNumbers.addAll(nonActivePhoneNumbers);
	}

	/**
	 * Сделать рассылку смс в зависимости от сегмента клиента
	 */
	void send() throws IKFLMessagingException
	{
		SmsTransportService messagingService = MessagingSingleton.getInstance().getErmbSmsTransportService();
		List<TextMessage> messages = new ArrayList<TextMessage>();

		boolean migrated = activePhone != null;
		if (client.getSegment_1() || client.getSegment_1_1() || client.getSegment_1_2() || (client.getSegment_4() && client.getUDBO()))
		{
			messages.add(MessageComposeHelper.buildErmbMessageWithoutParameters("com.rssl.iccs.ermb.sms.migration.segment1.connect"));
		}
		if (client.getSegment_2_1() || (client.getSegment_4() && !client.getUDBO()))
		{
			messages.add(MessageComposeHelper.buildErmbMessageWithoutParameters("com.rssl.iccs.ermb.sms.migration.segment2_1.connect"));
		}
		if (client.getSegment_2_2())
		{
			messages.add(MessageComposeHelper.buildErmbMessageWithoutParameters("com.rssl.iccs.ermb.sms.migration.segment2_2.connect"));
		}
		if ((client.getSegment_3_1() || client.getSegment_3_2_3()) && migrated)
		{
			messages.add(MessageComposeHelper.buildErmbMessageWithoutParameters("com.rssl.iccs.ermb.sms.migration.segment3.connect"));
		}
		if (client.getSegment_3_2_1() && client.getUDBO() && migrated)
		{
			messages.add(MessageComposeHelper.buildErmbMessageWithoutParameters("com.rssl.iccs.ermb.sms.migration.segment3_2_1.connect"));
		}

		for (TextMessage message : messages)
		{
			messagingService.sendSms(activePhone.getPhoneNumber(), message.getText(), message.getTextToLog(), message.getPriority());
		}

		if (client.getSegment_4())
		{
			for (String phoneNumber : nonActivePhoneNumbers)
			{
				TextMessage message = MessageComposeHelper.buildErmbMessageWithoutParameters("com.rssl.iccs.ermb.sms.migration.segment4.notActive");
				messagingService.sendSms(phoneNumber, message.getText(), message.getTextToLog(), message.getPriority());
			}
		}
	}
}
