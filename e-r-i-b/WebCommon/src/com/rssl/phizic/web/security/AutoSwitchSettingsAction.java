package com.rssl.phizic.web.security;

import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.messaging.info.NotificationChannel;
import com.rssl.phizic.business.messaging.info.UserNotificationType;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.mobile.MobileApiConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.messaging.IKFLMessage;
import com.rssl.phizic.messaging.MessageComposer;
import com.rssl.phizic.messaging.MessagingService;
import com.rssl.phizic.messaging.MessagingSingleton;
import com.rssl.phizic.operations.userprofile.SetupNotificationOperation;
import com.rssl.phizic.operations.userprofile.SetupSecurityOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;

import java.security.AccessControlException;

/**
 * экшен для автоматического переключения настройки предпочтительного способа подтверждений и всех оповещений на push
 * @author basharin
 * @ created 03.10.14
 * @ $Author$
 * @ $Revision$
 */

public abstract class AutoSwitchSettingsAction extends OperationalActionBase
{
	private static final String NOTIFICATION_CHANGE_CONFIRM_OPERATION_AND_NOTIFICATION_TO_PUSH_KEY = "changeConfirmOperationAndNotification";

	/**
	 * переключить настройки предпочтительного способа подтверждений и всех оповещений на push
	 */
	public void changeConfirmOperationAndNotificationToPush(AuthenticationContext authContext) throws BusinessException, BusinessLogicException
	{
		MobileApiConfig mobileApiConfig = ConfigFactory.getConfig(MobileApiConfig.class);
		if (!mobileApiConfig.isAutoSwitchPush())
			return;

		try
		{
			if (!PersonContext.isAvailable())
				return;

			ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();

			SetupNotificationOperation setupNotificationOperation = createOperation(SetupNotificationOperation.class);
			SetupSecurityOperation setupSecurityOperation = createOperation(SetupSecurityOperation.class);

			setupNotificationOperation.initialize(person, UserNotificationType.loginNotification, UserNotificationType.mailNotification, UserNotificationType.operationNotification);
			setupNotificationOperation.setChannel(UserNotificationType.loginNotification, NotificationChannel.push.toString());
			setupNotificationOperation.setChannel(UserNotificationType.mailNotification, NotificationChannel.push.toString());
			setupNotificationOperation.setChannel(UserNotificationType.operationNotification, NotificationChannel.push.toString());
			setupNotificationOperation.saveNotification();

			setupSecurityOperation.initialize(authContext);
			setupSecurityOperation.setUserConfirmType(ConfirmStrategyType.push.toString());
			setupSecurityOperation.updateConfirmationSettings();

			try
			{
				MessagingService messagingService = MessagingSingleton.getInstance().getMessagingService();
				IKFLMessage message = MessageComposer.buildInformingSmsMessage(person.getLogin().getId(), NOTIFICATION_CHANGE_CONFIRM_OPERATION_AND_NOTIFICATION_TO_PUSH_KEY);
				message.setAdditionalCheck(false);
				messagingService.sendSms(message);
			}
			catch (Exception ignore)
			{
				log.error("Ошибка при отправке уведомления о переключении канала подтверждений и оповещений");
			}
		}
		catch (AccessControlException ignore)
		{
			log.error("Не удалось автоматически переключить настройки предпочтительного способа подтверждений и всех оповещений на push. Не прав на операцию смены настроек");
		}
	}
}
