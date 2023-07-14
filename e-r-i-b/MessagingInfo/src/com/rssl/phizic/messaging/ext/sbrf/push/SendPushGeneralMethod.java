package com.rssl.phizic.messaging.ext.sbrf.push;

import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.common.types.transmiters.Triplet;
import com.rssl.phizic.messaging.MessagingSingleton;
import com.rssl.phizic.messaging.TranslitMode;
import com.rssl.phizic.messaging.ext.sbrf.sms.SendSmsGeneralMethod;
import com.rssl.phizic.messaging.push.PushHelper;
import com.rssl.phizic.messaging.push.PushMessage;
import com.rssl.phizic.messaging.push.PushTransportService;
import com.rssl.phizic.person.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * общий метод отправки push-уведомлений
 * @author basharin
 * @ created 02.10.13
 * @ $Author$
 * @ $Revision$
 */

public class SendPushGeneralMethod extends SendSmsGeneralMethod
{
	private final PushTransportService pushTransportService = MessagingSingleton.getInstance().getPushTransportService();
	private final Person person;
	private PushMessage pushMessage;

	protected SendPushGeneralMethod(Person person, String phone, TranslitMode translit, PushMessage message)
	{
		super(person.getLogin(), phone, translit, message);
		this.person = person;
		pushMessage = message;
	}

	@Override
	public void send(String text, String textToLog, Long priority) throws IKFLMessagingException
	{

		List<String> phones = new ArrayList<String>();
		if (!pushMessage.isSmsBackup())
		{
			//не заполняем поля smsMessage и phones. т.к. не требется sms резервироваине
			pushMessage.setSmsMessage(null);
		}
		else
		{
			phones = Arrays.asList(phone);
		}

		List<Triplet<String, String, String>> deviceInfo = PushHelper.getTripletDeviceIdGuidName(person);
		addRecipientInfo(deviceInfo);
		pushTransportService.sendPush(person.asClientData(), pushMessage, phones, deviceInfo);
	}

	private void addRecipientInfo(List<Triplet<String, String, String>> deviceInfo)
	{
		for (Triplet<String,String,String> triplet : deviceInfo)
			addRecipientInfo(triplet.getSecond());
	}

}
