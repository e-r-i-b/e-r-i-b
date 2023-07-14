package com.rssl.phizic.messaging.ext.sbrf;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.mail.MailConfig;
import com.rssl.phizic.business.mail.MailHelper;
import com.rssl.phizic.business.messaging.info.NotificationChannel;
import com.rssl.phizic.business.messaging.info.SubscriptionImpl;
import com.rssl.phizic.business.messaging.info.SubscriptionService;
import com.rssl.phizic.business.messaging.info.UserNotificationType;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.settings.UserMessageLogHelper;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.messaging.*;
import com.rssl.phizic.messaging.push.PushMessage;
import com.rssl.phizic.person.Person;

import java.util.Calendar;

/**
 * @author komarov
 * @ created 28.06.2011
 * @ $Author$
 * @ $Revision$
 */

public class NotificationSender
{
	private static final String TEMPLATE_PROPERTY_KEY_PUSH_SMS = "com.rssl.iccs.user.mail.text";
	private static final String TEMPLATE_PROPERTY_KEY_EMAIL = "MailHelp";

	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final SecurityService securrityService = new SecurityService();
	private static final PersonService personService = new PersonService();
	private static final SubscriptionService subscriptionService = new SubscriptionService();

	/**
	 * Высылает оповещение о получении письма из службы помощи
	 * @param id - идентификатор лгина пользователя
	 * @throws SecurityException
	 */
	public void sendNotifocation(Long id)
	{
		try
		{
			CommonLogin login = securrityService.findById(id);
			SubscriptionImpl subscription = subscriptionService.findSubscription(id, UserNotificationType.mailNotification, null);
			NotificationChannel channel = null;

			if (subscription != null)
				channel = subscription.getChannel();
			else
				channel = NotificationChannel.valueOf(ConfigFactory.getConfig(MailConfig.class).getDefaultMessageType());

			if (channel == NotificationChannel.sms)
			{
				sendSms(login);
				return;
			}
			else if (channel == NotificationChannel.email)
			{
				Person person = personService.findByLoginId(id);
				MailHelper.sendEMail(subscriptionService.findPersonalData(person.getLogin()), TEMPLATE_PROPERTY_KEY_EMAIL, MailHelper.getCurrentDateFields(), false);
				UserMessageLogHelper.saveMailNotificationToLog(person.getLogin().getId(), null);
				return;
			}
			else if (channel == NotificationChannel.push)
			{
				sendPush(login);
				return;
			}
			else if(channel == NotificationChannel.none)
			{
				return;
			}

			throw new SecurityException("Неизвестный тип рассылки оповещения");
		}
		catch (Exception e)
		{

			log.error("Ошибка при отправке сообщения о получении письма из службы помощи: loginId " + id, e);
		}
	}

	private void sendPush(CommonLogin login) throws IKFLMessagingException, IKFLMessagingLogicException
	{
		PushMessage pushMessage = MessageComposer.buildInformingPushMessage(login, TEMPLATE_PROPERTY_KEY_PUSH_SMS);
		formatPushMessage(pushMessage);

		MessagingService messagingService = MessagingSingleton.getInstance().getMessagingService();
		messagingService.sendPush(pushMessage);
	}

	private void sendSms(CommonLogin login) throws IKFLMessagingException, IKFLMessagingLogicException, BusinessException
	{
		IKFLMessage ikflMessage = MessageComposer.buildInformingSmsMessage(login.getId(), TEMPLATE_PROPERTY_KEY_PUSH_SMS);
		formatMessage(ikflMessage);

		MessagingService messagingService = MessagingSingleton.getInstance().getMessagingService();
		messagingService.sendSms(ikflMessage);
	}

	private void formatMessage(IKFLMessage message)
	{
		String time = String.format("%1$tH:%1$tM %1$td.%1$tm.%1$ty", Calendar.getInstance());
		String text = String.format(message.getText(), time);

		message.setText(text);
	}

	private void formatPushMessage(PushMessage message)
	{
		String time = String.format("%1$tH:%1$tM %1$td.%1$tm.%1$ty", Calendar.getInstance());
		String text = String.format(message.getText(), time);
		String shortMessage = String.format(message.getShortMessage(), time);
		String smsMessage = String.format(message.getSmsMessage(), time);

		message.setText(text);
		message.setShortMessage(shortMessage);
		message.setSmsMessage(smsMessage);
	}
}
