package com.rssl.phizic.messaging.ext.sbrf;

import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.modes.AthenticationCompleteAction;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.mail.MailHelper;
import com.rssl.phizic.business.messaging.info.NotificationChannel;
import com.rssl.phizic.business.messaging.info.SubscriptionImpl;
import com.rssl.phizic.business.messaging.info.SubscriptionService;
import com.rssl.phizic.business.messaging.info.UserNotificationType;
import com.rssl.phizic.business.mobileDevices.MobilePlatformService;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.common.types.csa.AuthorizedZoneType;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.monitoring.MonitoringOperationConfig;
import com.rssl.phizic.logging.monitoring.MonitoringUserLoginEntry;
import com.rssl.phizic.logging.settings.UserMessageLogHelper;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.messaging.*;
import com.rssl.phizic.messaging.push.PushMessage;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.security.util.MobileApiUtil;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.mobile.MobileAPIVersions;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Экшн для оповещения клиента о входе в систему
 * и мониторинга.
 * @author gladishev
 * @ created 07.06.2011
 * @ $Author$
 * @ $Revision$
 */
public class UserLogonNotificationAction implements AthenticationCompleteAction
{
	private static final String EMAIL_MESSAGE_SUBJECT_PROPERTY_KEY = "Logon";

	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final PersonService personService = new PersonService();
	private static final SubscriptionService subscriptionService = new SubscriptionService();
	private static final MobilePlatformService mobilePlatformService = new MobilePlatformService();

	public void execute(AuthenticationContext context) throws SecurityException
	{
		ApplicationConfig applicationConfig = ApplicationConfig.getIt();
		monitoring(context);
		if (applicationConfig.getApplicationInfo().isATM() && !ConfigFactory.getConfig(UserLogonNotificationConfig.class).isAtmSmsTurnOn())
		{
			return;
		}
		if ((applicationConfig.getApplicationInfo().isSocialApi() || MobileApiUtil.isMobileApiGE(MobileAPIVersions.V8_00)) && AuthorizedZoneType.POST_REGISTRATION == context.getAuthorizedZoneType())
		{
			return;
		}

		CommonLogin userLogin = context.getLogin();
		try
		{
			SubscriptionImpl subscription = subscriptionService.findSubscription(userLogin.getId(), UserNotificationType.loginNotification, null);
			NotificationChannel channel = null;

			if (subscription != null)
				channel = subscription.getChannel();
			else
				channel = NotificationChannel.valueOf(ConfigFactory.getConfig(UserLogonNotificationConfig.class).getDefaultMessageType());

			if (channel == NotificationChannel.sms)
			{
				sendSms(userLogin);
				return;
			}
			else if (channel == NotificationChannel.email)
			{
				Person person = personService.findByLoginId(userLogin.getId());
				HashMap<String, String> keyWords = new HashMap<String, String>();
				keyWords.put("shortName", context.getFirstName() + " " + context.getMiddleName());
				keyWords.putAll(MailHelper.getCurrentDateFields());
				MailHelper.sendEMail(subscriptionService.findPersonalData(person.getLogin()), EMAIL_MESSAGE_SUBJECT_PROPERTY_KEY, keyWords, true);
				UserMessageLogHelper.saveMailNotificationToLog(userLogin.getId(), null);
				return;
			}
			else if (channel == NotificationChannel.push)
			{
				sendPush(userLogin);
				return;
			}

			throw new SecurityException("Неизвестный тип рассылки оповещения");
		}
		catch (Exception e)
		{
			log.error("Ошибка при отправке сообщения о входе в систему: loginId " + userLogin.getId(), e);
		}
	}

	private void monitoring(AuthenticationContext context)
	{
		if (!ConfigFactory.getConfig(MonitoringOperationConfig.class).isActiveLoginMonitoring())
		{
			return;
		}

		MonitoringUserLoginEntry entry = new MonitoringUserLoginEntry();
		entry.setApplication(ApplicationInfo.getCurrentApplication().name());
		entry.setNodeId(ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getNodeNumber());
		entry.setPlatform(context.getDeviceInfo());
		entry.setStartDate(Calendar.getInstance());
		entry.setTb(context.getTB());
		try
		{
			ConfigFactory.getConfig(MonitoringOperationConfig.class).writeLog(entry);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(LogModule.Core).error(e);
		}
	}

	private void sendPush(CommonLogin userLogin) throws IKFLMessagingException, IKFLMessagingLogicException
	{
		PushMessage message = null;
		if (ApplicationUtil.isMobileApi() || ApplicationUtil.isSocialApi())
		{
			try
			{
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("applicationName", mobilePlatformService.findByPlatformId(AuthenticationContext.getContext().getDeviceInfo()).getPlatformName());
				message = MessageComposer.buildInformingPushMessage(userLogin, UserLogonNotificationConfig.TEMPLATE_PROPERTY_KEY_PUSH_SMS, params, params);
			}
			catch (BusinessException e)
			{
				throw new IKFLMessagingException(e);
			}
		}
		else
		{
			message = MessageComposer.buildInformingPushMessage(userLogin, UserLogonNotificationConfig.TEMPLATE_PROPERTY_KEY_PUSH_SMS);
		}
		message.setOperationType(OperationType.LOGIN);
		message.setAdditionalCheck(false);
		formatPushMessage(message);

		MessagingService messagingService = MessagingSingleton.getInstance().getMessagingService();
		messagingService.sendPush(message);
	}

	private void sendSms(CommonLogin userLogin) throws IKFLMessagingException, IKFLMessagingLogicException, BusinessException
	{
		IKFLMessage message = null;
		if (ApplicationUtil.isMobileApi() || ApplicationUtil.isSocialApi())
		{
			try
			{
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("applicationName", mobilePlatformService.findByPlatformId(AuthenticationContext.getContext().getDeviceInfo()).getPlatformName());
				message = MessageComposer.buildInformingSmsMessage(userLogin.getId(), UserLogonNotificationConfig.TEMPLATE_PROPERTY_KEY_PUSH_SMS, params, params);
			}
			catch (BusinessException e)
			{
				throw new IKFLMessagingException(e);
			}
		}
		else
		{
			message = MessageComposer.buildInformingSmsMessage(userLogin.getId(), UserLogonNotificationConfig.TEMPLATE_PROPERTY_KEY_PUSH_SMS);
		}
		message.setOperationType(OperationType.LOGIN);
		message.setAdditionalCheck(false);
		formatMessage(message);

		MessagingService messagingService = MessagingSingleton.getInstance().getMessagingService();
		messagingService.sendSms(message);
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

	private void formatMessage(IKFLMessage message)
	{
		String time = String.format("%1$tH:%1$tM %1$td.%1$tm.%1$ty", Calendar.getInstance());
		String text = String.format(message.getText(), time);

		message.setText(text);
		message.setTextToLog(text);
	}
}