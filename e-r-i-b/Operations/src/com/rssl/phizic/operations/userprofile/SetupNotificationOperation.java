package com.rssl.phizic.operations.userprofile;

import com.rssl.auth.csa.wsclient.responses.ConnectorInfo;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.csa.ConnectorsService;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.documents.strategies.monitoring.FraudMonitoringDocumentStrategy;
import com.rssl.phizic.business.documents.strategies.monitoring.FraudMonitoringPreConfirmDocumentStrategy;
import com.rssl.phizic.business.ermb.MobileBankManager;
import com.rssl.phizic.business.fraudMonitoring.FraudMonitoringSendersFactory;
import com.rssl.phizic.business.fraudMonitoring.exceptions.ProhibitionOperationFraudException;
import com.rssl.phizic.business.mail.MailConfig;
import com.rssl.phizic.business.messaging.info.NotificationChannel;
import com.rssl.phizic.business.messaging.info.SubscriptionImpl;
import com.rssl.phizic.business.messaging.info.SubscriptionService;
import com.rssl.phizic.business.messaging.info.UserNotificationType;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.profileSynchronization.PersonSettingsManager;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.logging.settings.NotificationInputType;
import com.rssl.phizic.logging.settings.UserNotificationLogHelper;
import com.rssl.phizic.messaging.IKFLMessage;
import com.rssl.phizic.messaging.MessageComposer;
import com.rssl.phizic.messaging.MessagingService;
import com.rssl.phizic.messaging.MessagingSingleton;
import com.rssl.phizic.messaging.ext.sbrf.UserLogonNotificationConfig;
import com.rssl.phizic.operations.ConfirmableOperationBase;
import com.rssl.phizic.rsa.InteractionType;
import com.rssl.phizic.rsa.PhaseType;
import com.rssl.phizic.rsa.exceptions.BlockClientOperationFraudException;
import com.rssl.phizic.rsa.exceptions.ProhibitionOperationFraudGateException;
import com.rssl.phizic.rsa.senders.FraudMonitoringSender;
import com.rssl.phizic.rsa.senders.initialization.PhaseInitializationData;
import com.rssl.phizic.rsa.senders.types.EventsType;
import com.rssl.phizic.security.ConfirmableObject;

import java.util.*;

/**
 * Операция настройки оповещений клиента в системе
 * @ author gorshkov
 * @ created 03.09.13
 * @ $Author$
 * @ $Revision$
 */
public class SetupNotificationOperation extends ConfirmableOperationBase
{
	public static final String NONE_CHANGE_MESSAGE = "Вы не внесли никаких изменений.";
	private static final String NOTIFICATION_CHANGE_CHANEL_NOTIFICATION_KEY = "changeChannelNotification.";
	private static final SubscriptionService SUBSCRIPTION_SERVICE = new SubscriptionService();
	private static final DepartmentService DEPARTMENT_SERVICE = new DepartmentService();

    private UserNotificationType[] editableTypes;
    private ActivePerson person;
	private List<ConnectorInfo> mapiConnectors;

	private List<UserNotificationType> types;
	private Map<UserNotificationType,SubscriptionImpl> subscriptions;

	public void initialize(UserNotificationType... types) throws BusinessException, BusinessLogicException
	{
		initialize(PersonContext.getPersonDataProvider().getPersonData().getPerson(), types);
		mapiConnectors = ConnectorsService.getClientMAPIConnectors(AuthenticationContext.getContext().getCSA_SID());
	}

	public void initialize(ActivePerson person, UserNotificationType... notificationTypes) throws BusinessException, BusinessLogicException
	{
		this.person = person;
		editableTypes = notificationTypes.clone();
		long loginId = person.getLogin().getId();
		List<UserNotificationType> typesTmp = new ArrayList<UserNotificationType>(Arrays.asList(notificationTypes));

		types = Collections.unmodifiableList(typesTmp);

		List<SubscriptionImpl> result = SUBSCRIPTION_SERVICE.findSubscriptions(loginId, types);
		subscriptions = new HashMap<UserNotificationType, SubscriptionImpl>(types.size());
		for (UserNotificationType type : notificationTypes)
		{
			SubscriptionImpl subscription = getSubscription(result, type);
			if (subscription == null)
			{
				subscription = new SubscriptionImpl();
				subscription.setLoginId(person.getLogin().getId());
				subscription.setNotificationType(type);
				subscription.setChannel(getDefaultCannel(type));
				subscription.setTB(DEPARTMENT_SERVICE.getNumberTB(person.getDepartmentId()));
			}
			subscriptions.put(type, subscription);
		}
	}

	/**
	 * Получаем активного клиента
	 * @return активный клиент
	 */
	public ActivePerson getPerson()
	{
		return person;
	}

	/**
	 * Получаем канал отправки оповещения (sms, emal, push)
	 * @return канал отправки оповещения
	 */
	public String getChannel(UserNotificationType type) throws BusinessException
	{
		SubscriptionImpl subscription = subscriptions.get(type);
		if (subscription != null)
		{
			NotificationChannel channel = subscription.getChannel();
			if (channel != null)
				return channel.name();
		}

		throw new BusinessException("Не указан канал отправки для оповещения типа " + type.name());
	}

	/**
	 * Устанавливаем тип оповещения
	 */
	public void setChannel(UserNotificationType type, String channel) throws BusinessException, BusinessLogicException
	{
		if (subscriptions.containsKey(type))
		{
			subscriptions.get(type).setChannel(NotificationChannel.valueOf(channel));
			return;
		}
		throw new BusinessException("Ошибка при установке канала отправки для оповещения типа " + type.name());
	}

	/**
	 * Устанавливаем тип оповещения
	 */
	public void setChannel(String channel) throws BusinessException, BusinessLogicException
	{
		if (editableTypes.length > 1)
			throw new UnsupportedOperationException();

		setChannel(editableTypes[0], channel);
	}

	public String getChannel() throws BusinessException, BusinessLogicException
	{
		if (editableTypes.length > 1)
			throw new UnsupportedOperationException();

		return getChannel(editableTypes[0]);
	}

	public void doPreFraudControl() throws BusinessLogicException, BusinessException
	{
		try
		{
			FraudMonitoringSender sender = FraudMonitoringSendersFactory.getInstance().getSender(EventsType.CHANGE_ALERT_SETTINGS);
			//noinspection unchecked
			sender.initialize(new PhaseInitializationData(InteractionType.ASYNC, PhaseType.SENDING_REQUEST));
			sender.send();
		}
		catch (Exception e)
		{
			log.error(e);
		}
	}

	protected void doFraudControl() throws BusinessLogicException, BusinessException
	{
		try
		{
			FraudMonitoringSender sender = FraudMonitoringSendersFactory.getInstance().getSender(EventsType.CHANGE_ALERT_SETTINGS);
			//noinspection unchecked
			sender.initialize(new PhaseInitializationData(InteractionType.ASYNC, PhaseType.WAITING_FOR_RESPONSE));
			sender.send();
		}
		catch (ProhibitionOperationFraudGateException e)
		{
			throw new ProhibitionOperationFraudException(e.getMessage(), e);
		}
		catch (BlockClientOperationFraudException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			log.error(e);
		}
	}

	/**
	 * Сохраняем оповещение
	 */
	@Transactional
	public void saveNotification() throws BusinessException, BusinessLogicException
	{
		for (UserNotificationType t : types)
		{
			SubscriptionImpl subscription = subscriptions.get(t);

			if (subscription.getChannel() == NotificationChannel.none && subscription.getNotificationType() == UserNotificationType.newsNotification)
			{
				if (subscription.getId() != null)
				{
					SUBSCRIPTION_SERVICE.remove(subscription);
					PersonSettingsManager.savePersonData(getSettingKey(t), subscription.getChannel());
				}
			}
			else
			{
				SUBSCRIPTION_SERVICE.addOrUpdate(subscription);
				PersonSettingsManager.savePersonData(getSettingKey(t), subscription.getChannel());
			}

			UserNotificationLogHelper.saveNotificationSettings(person.getLogin().getId(), NotificationInputType.valueOf(t.name()), subscription.getChannel().name());
		}
	}

	/**
	 * Получаем объект подтверждения
	 * @return объект подтверждения
	 */
	public ConfirmableObject getConfirmableObject()
	{
		Map<String, String> changes = new HashMap<String, String>();
		return new NotificationSettings(changes);
	}

	/**
	 * @return номера телефонов для оповещений о письмах
	 */
	public Collection<String> getMobilePhonesForMail() throws BusinessException
	{
		return MobileBankManager.getInfoPhones(person, null);
	}

	/**
	 * @return список всех устройств с установленным и подключенным мобильным приложением
	 */
	public List<ConnectorInfo> getAllMobileDevices() throws BusinessException, BusinessLogicException
	{
		return mapiConnectors;
	}

	/**
	 * @return список устройств для оповещений
	 */
	public List<ConnectorInfo> getMobileDevices() throws BusinessException, BusinessLogicException
	{
		List<ConnectorInfo> result = new ArrayList<ConnectorInfo>();
		for (ConnectorInfo clientMAPIConnector : mapiConnectors)
		{
			if (clientMAPIConnector.isPushSupported())
			{
				result.add(clientMAPIConnector);
			}
		}
		return result;
	}

	/**
	 * @return номера телефонов для оповещений о входе
	 */
	public Collection<String> getMobilePhonesForNotification() throws BusinessException
	{
		return MobileBankManager.getInfoPhones(person, true);
	}

	protected void saveConfirm() throws BusinessException, BusinessLogicException
	{
		doFraudControl();
		saveNotification();
	}

	/**
	 * Отправить уведомление о переключении канала оповещений если нужно
	 */
	public void sendSmsNotification()
	{
		try
		{
			String channel = getChannel();
			if (!NotificationChannel.push.equals(channel) && !NotificationChannel.sms.equals(channel))
				return;
			MessagingService messagingService = MessagingSingleton.getInstance().getMessagingService();
			IKFLMessage message = MessageComposer.buildInformingSmsMessage(person.getLogin().getId(), NOTIFICATION_CHANGE_CHANEL_NOTIFICATION_KEY + channel);
			message.setAdditionalCheck(false);
			messagingService.sendSms(message);
		}
		catch (Exception ignore)
		{
			log.error("Ошибка при отправке уведомления о переключении канала оповещений");
		}
	}

	private String getSettingKey(UserNotificationType type)
	{
		switch (type)
		{
			case loginNotification: return PersonSettingsManager.LOGIN_NOTIFICATION_KEY;
			case mailNotification: return PersonSettingsManager.MAIL_NOTIFICATION_KEY;
			case newsNotification: return PersonSettingsManager.NEWS_NOTIFICATION_KEY;
			case operationNotification: return PersonSettingsManager.OPERATION_NOTIFICATION_KEY;
		}
		return null;
	}

	/**
	 * @return Типы редактируемого оповещения
	 */
	public UserNotificationType[] getTypes()
	{
		return editableTypes;
	}

	private SubscriptionImpl getSubscription(List<SubscriptionImpl> subscriptions, UserNotificationType type)
	{
		for (SubscriptionImpl subscription : subscriptions)
			if (subscription.getNotificationType() == type)
				return subscription;

		return null;
	}

	private NotificationChannel getDefaultCannel(UserNotificationType type)
	{
		switch (type)
		{
			case loginNotification:
				return toNotificationChannel(ConfigFactory.getConfig(UserLogonNotificationConfig.class).getDefaultMessageType());
			case operationNotification:
				return NotificationChannel.none;
			case mailNotification:
				return toNotificationChannel(ConfigFactory.getConfig(MailConfig.class).getDefaultMessageType());
			case newsNotification:
				return NotificationChannel.none;
			default:
				throw new IllegalStateException("Недопустимый тип оповещения" + type);
		}
	}

	private NotificationChannel toNotificationChannel(String value)
	{
		try
		{
			return NotificationChannel.valueOf(value);
		}
		catch (Exception e)
		{
			log.error("Некорректное значение канала оповещения: [ " +  value + "]", e);
			return NotificationChannel.none;
		}
	}
}
