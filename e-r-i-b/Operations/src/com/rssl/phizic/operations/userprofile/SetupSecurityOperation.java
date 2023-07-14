package com.rssl.phizic.operations.userprofile;

import com.rssl.phizic.auth.modes.*;
import com.rssl.phizic.auth.modes.generated.ChoiceDescriptor;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.MobileBankManager;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonBase;
import com.rssl.phizic.business.profile.UserConfirmStrategySettings;
import com.rssl.phizic.business.profileSynchronization.PersonSettingsManager;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.logging.settings.NotificationInputType;
import com.rssl.phizic.logging.settings.UserNotificationLogHelper;
import com.rssl.phizic.messaging.IKFLMessage;
import com.rssl.phizic.messaging.MessageComposer;
import com.rssl.phizic.messaging.MessagingService;
import com.rssl.phizic.messaging.MessagingSingleton;
import com.rssl.phizic.operations.ConfirmableOperationBase;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.SecurityLogicException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author potehin
 * @ created 23.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class SetupSecurityOperation extends ConfirmableOperationBase
{
	private static final String NOTIFICATION_CHANGE_CONFIRM_OPERATION_KEY = "changeChannelConfirmOperation.";

	private static final AccessPolicyService accessService = new AccessPolicyService();

	private static final Map<String,Boolean> confirmAuthOptions = new HashMap<String,Boolean>();

	private static final Map<String, ChoiceDescriptor.OptionType> userOptionsType = new HashMap<String, ChoiceDescriptor.OptionType>();

	static
	{
		// Ставим в соответствие ключам значение доступности подтверждения по одноразовому паролю
		confirmAuthOptions.put("lp", false);
		confirmAuthOptions.put("smsp", true);
	}

	private PersonBase person;
	private String authConfirmType;
	private Properties userProperties;
	private AuthenticationContext authContext;
	private String userConfirmType;
	private Boolean oneTimePassword;
	private Map<String, String> beforeChangesValues;

	public void initialize(AuthenticationContext authContext) throws BusinessException
	{
		this.authContext = authContext;

		PersonDataProvider dataProvider = PersonContext.getPersonDataProvider();
		PersonData personData = dataProvider.getPersonData();
		person = personData.getPerson();

		// Пользовательские настройки безопасности из политики
		AccessPolicy policy = authContext.getPolicy();
		try
		{
			AccessType accessType = policy.getAccessType();
			userProperties = accessService.getProperties(person.getLogin(), accessType);
		}
		catch (SecurityDbException e)
		{
			throw new BusinessException(e);
		}

		if (userProperties == null)
			throw new BusinessException("Не найдено пользовательских настроек подтверждения для текущей политики доступа");
		beforeChangesValues = new HashMap<String, String>();

		// Тип подтверждения
		String userConfirmValue = userProperties.getProperty("userOptionType");

		// Если не указана - берем из client-authantication-modes.xml, указанную как дефолтная
		if (userConfirmValue == null)
		{
			ConfirmStrategy result = getConfirmStrategyProvider().getStrategy();
			if (result instanceof CompositeConfirmStrategy)
			{
				CompositeConfirmStrategy strategy = (CompositeConfirmStrategy) result;
				userConfirmValue = strategy.getDefaultStrategy().toString();
			}
		}

		setUserConfirmType(userConfirmValue);
		beforeChangesValues.put("userConfirmType", userConfirmValue);
		// Способ входа
		String authValue = userProperties.getProperty(policy.getAuthenticationChoice().getProperty());
		setOneTimePassword(confirmAuthOptions.get(authValue));
		setAuthConfirmType(authValue);
		beforeChangesValues.put("authConfirmType", authValue);
	}

	/**
	 * Запись настроек типа подтверждения
	 * @throws BusinessException
	 */
	public void updateConfirmationSettings() throws BusinessException
	{
		AccessPolicy policy = authContext.getPolicy();
		AccessType accessType = policy.getAccessType();

		UserConfirmStrategySettings userConfirmStrategySettings = new UserConfirmStrategySettings(getAuthConfirmType(), getUserConfirmType());
		Choice authChoice = policy.getAuthenticationChoice();
		userProperties.setProperty(authChoice.getProperty(), getAuthConfirmType());

		// Установка типа подтверждения операции
		userProperties.setProperty("userOptionType", getUserConfirmType());

		// Сохраняем обновлённые свойства безопасности
		try
		{
			accessService.enableAccess(person.getLogin(), accessType, userProperties);
			UserNotificationLogHelper.saveNotificationSettings(person.getLogin().getId(), NotificationInputType.operationConfirm, getUserConfirmType());
			PersonSettingsManager.savePersonData(PersonSettingsManager.CONFIRM_STRATEGY_SETTINGS_DATA_KEY, userConfirmStrategySettings);
		}
		catch (SecurityDbException e)
		{
			throw new BusinessException(e);
		}
	}

	public PersonBase getPerson()
	{
		return person;
	}

	public String getAuthConfirmType()
	{
		return authConfirmType;
	}

	public void setAuthConfirmType(String confirmType)
	{
		this.authConfirmType = confirmType;
	}

	public String getUserConfirmType()
	{
		return userConfirmType;
	}

	public void setUserConfirmType(String userConfirmType)
	{
		this.userConfirmType = userConfirmType;
	}

	public Boolean isOneTimePassword()
	{
		return oneTimePassword;
	}

	public void setOneTimePassword(Boolean oneTimePassword)
	{
		this.oneTimePassword = oneTimePassword;

		for (String key: confirmAuthOptions.keySet())
		{
			if (oneTimePassword.equals(confirmAuthOptions.get(key)))
				setAuthConfirmType(key);
		}
	}

	protected void saveConfirm() throws BusinessException, BusinessLogicException, SecurityLogicException
	{
		updateConfirmationSettings();
	}

	public ConfirmableObject getConfirmableObject()
	{
		Map<String, String> changes = new HashMap<String, String>();
		if(!getUserConfirmType().equals(beforeChangesValues.get("userConfirmType")))
			changes.put("userConfirmType", getUserConfirmType());

		if(!getAuthConfirmType().equals(beforeChangesValues.get("authConfirmType")))
			changes.put("authConfirmType", getAuthConfirmType());

		return new SecuritySettings(changes);
	}
	
	/**
	 * @return номера телефонов
	 */
	public Collection<String> getMobilePhones() throws BusinessException
	{
		return MobileBankManager.getInfoPhones((ActivePerson) person, null);
	}
	/**
	 * Отправить уведомление о переключении канала подтверждений если нужно
	 */
	public void sendSmsNotification()
	{
		try
		{
			if (!ConfirmStrategyType.push.equals(userConfirmType) && !ConfirmStrategyType.sms.equals(userConfirmType))
				return;
			MessagingService messagingService = MessagingSingleton.getInstance().getMessagingService();
			IKFLMessage message = MessageComposer.buildInformingSmsMessage(person.getLogin().getId(), NOTIFICATION_CHANGE_CONFIRM_OPERATION_KEY + userConfirmType);
			message.setAdditionalCheck(false);
			messagingService.sendSms(message);
		}
		catch (Exception ignore)
		{
			log.error("Ошибка при отправке уведомления о переключении канала подтверждений");
		}
	}
}
