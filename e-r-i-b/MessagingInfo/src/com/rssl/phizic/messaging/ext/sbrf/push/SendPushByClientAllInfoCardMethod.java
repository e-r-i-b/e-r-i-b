package com.rssl.phizic.messaging.ext.sbrf.push;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.common.types.transmiters.Triplet;
import com.rssl.phizic.gate.mobilebank.SendMessageError;
import com.rssl.phizic.messaging.MessagingSingleton;
import com.rssl.phizic.messaging.TranslitMode;
import com.rssl.phizic.messaging.ext.sbrf.sms.SendSmsByClientAllInfoCardMethod;
import com.rssl.phizic.messaging.push.*;
import com.rssl.phizic.person.Person;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * метод для отправки push-сообщений на устройства зарегистрированные на клиента
 * @author basharin
 * @ created 03.10.13
 * @ $Author$
 * @ $Revision$
 */

public class SendPushByClientAllInfoCardMethod extends SendSmsByClientAllInfoCardMethod
{
	private static final PushTransportService pushTransportService = MessagingSingleton.getInstance().getPushTransportService();
	private final Person person;
	private PushMessage pushMessage;

	public SendPushByClientAllInfoCardMethod(Person person, String phone, TranslitMode translit, boolean onlyMobileBank, PushMessage message)
	{
		super(person.getLogin(), phone, translit, onlyMobileBank, message);
		pushMessage = message;
		this.person = person;
	}

	@Override
	public void send(String text, String textToLog, Long priority) throws IKFLMessagingException
	{
		Collection<String> phones = new ArrayList<String>();
		if (pushMessage.isSmsBackup())
		{
			try
			{
				phones = getPhones(this.login, null);
			}
			catch (BusinessException ignore){}
		}
		else
		{
			//не заполняем поля smsMessage и phones. т.к. не требуется резервирование смс
			pushMessage.setSmsMessage(null);
		}
		List<Triplet<String, String, String>> deviceInfo = PushHelper.getTripletDeviceIdGuidName(person);
		try
		{
			pushTransportService.sendPush(person.asClientData(), pushMessage, phones, deviceInfo);
			for (Triplet<String, String, String> triplet : deviceInfo)
				addRecipientInfo(triplet.getSecond());
		}
		catch (IKFLMessagingException e)
		{
			log.error(e.getMessage(), e);
			for (Triplet<String, String, String> triplet : deviceInfo)
				addErrorInfo(triplet.getThird(), SendMessageError.error);

			throw new IKFLMessagingException("Hе удалось отправить Push-сообщение.");
		}
	}
}
