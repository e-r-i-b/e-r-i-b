package com.rssl.phizic.operations.registration;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.phizic.auth.modes.AccessPolicyService;
import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.mail.MailHelper;
import com.rssl.phizic.business.messaging.info.SubscriptionService;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.clients.ClientImpl;
import com.rssl.phizic.business.persons.clients.PersonImportService;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.common.types.client.DefaultSchemeType;
import com.rssl.phizic.common.types.security.SecurityType;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.logging.settings.UserMessageLogHelper;
import com.rssl.phizic.messaging.*;
import com.rssl.phizic.operations.ConfirmableOperationBase;
import com.rssl.phizic.operations.userprofile.RegistrationSettings;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.store.StoreManager;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @author bogdanov
 * @ created 06.05.2013
 * @ $Author$
 * @ $Revision$
 */

public class SelfRegistrationOperation extends ConfirmableOperationBase
{
	private String login;
	private String password;
	private String email;
	private static final String OPERATON_UID_KEY = "register-ouid";
	private static final AccessPolicyService accessService = new AccessPolicyService();
	private static final SubscriptionService subscriptionService = new SubscriptionService();

	/**
	 * @return personData.
	 */
	public PersonData getPersonData() throws BusinessException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		if (personData == null)
			throw new BusinessException("Не задана personData");

		return personData;
	}

	/**
	 * @return необходимо ли отображать поле ввода e-mail.
	 * @throws BusinessException
	 */
	public boolean needEmailAddress() throws BusinessException
	{
		return StringHelper.isEmpty(getPersonData().getPerson().getEmail());
	}

	/**
	 * Начинает регистрации нового пользователя.
	 * @param login логин пользователя.
	 * @param password пароль.
	 * @param email адрес электронной почты.
	 */
	public void startRegistration(String login, String password, String email) throws BusinessException, BackLogicException
	{
		setValues(login, password, email);

		String cardNumber = getPersonData().getPerson().getLogin().getLastLogonCardNumber();
		try
		{
			Document doc = CSABackRequestHelper.sendStartUserSelfRegistrationRq(cardNumber, ConfirmStrategyType.none);
			StoreManager.getCurrentStore().save(OPERATON_UID_KEY, XmlHelper.getSimpleElementValue(doc.getDocumentElement(), "OUID"));
		}
		catch (BackException ex)
		{
			throw new BusinessException(ex);
		}
	}

	/**
	 * Заканчивает регистрацию пользователя.
	 */
	private void finishRegistration() throws BusinessException, BusinessLogicException
	{
		try
		{
			String ouid = (String) StoreManager.getCurrentStore().restore(OPERATON_UID_KEY);
			CSABackRequestHelper.sendConfirmOperationRq(ouid, "");
			CSABackRequestHelper.sendFinishUserSelfRegistrationRq(ouid, login, password, false);

			updatePerson();
			sendSms();
			sendEmail();
			StoreManager.getCurrentStore().remove(OPERATON_UID_KEY);
		}
		catch (BackException ex)
		{
			throw new BusinessException(ex);
		}
		catch (BackLogicException ex)
		{
			throw new BusinessLogicException(ex);
		}
	}

	/**
	 * обновляем персону
	 */
	private void updatePerson() throws BusinessException, BusinessLogicException
	{
		PersonData personData = getPersonData();
		ActivePerson person = personData.getPerson();
		person.setSecurityType(SecurityType.LOW);

		Client client = person.asClient();
		if (StringHelper.isNotEmpty(email))
			((ClientImpl)client).setEmail(email);

		PersonImportService personImportService = new PersonImportService();
		personImportService.addOrUpdatePerson(person, client, person.getCreationType(), DefaultSchemeType.getDefaultSchemeType(person.getCreationType()));
	}

	/**
	 * Отправляет СМС на все номера клиента с оповещением об успешной регистрации.
	 */
	private void sendSms() throws BusinessException
	{
		try
		{
			IKFLMessage message = MessageComposer.buildInformingSmsMessage(getPersonData().getLogin().getId(), "com.rssl.iccs.user.registration.text.sms");
			message.setOperationType(OperationType.REGISTRATION_OPERATION);
			message.setAdditionalCheck(false);
			formatMessage(message);
			message.setUseAlternativeRegistrations(null);

			MessagingService messagingService = MessagingSingleton.getInstance().getMessagingService();
			messagingService.sendSms(message);
		}
		catch (Exception ex)
		{
			log.error("Ошибка во время отправки СМС", ex);
		}
	}

	/**
	 * Отправляет письмо на адрес, указаный в анкете клиента об успешной регистрации.
	 * @throws BusinessException
	 */
	private void sendEmail() throws BusinessException
	{
		PersonData personData = getPersonData();
 		try
		{
			HashMap<String, String> keyWords = new HashMap<String, String>();
			ActivePerson person = personData.getPerson();
			keyWords.put("fullName", person.getFirstPatrName());
			MailHelper.sendEMail(subscriptionService.findPersonalData(personData.getLogin()), "UserRegistration", keyWords, false);
			UserMessageLogHelper.saveMailNotificationToLog(personData.getLogin().getId(), null);
		}
		catch (Exception ex)
		{
			log.error("Ошибка во время отправки e-mail", ex);
		}
	}

	private void formatMessage(IKFLMessage message)
	{
		String time = String.format("%1$tH:%1$tM %1$td.%1$tm.%1$ty", Calendar.getInstance());
		String text = String.format(message.getText(), time);

		message.setText(text);
	}

	protected void saveConfirm() throws BusinessException, BusinessLogicException, SecurityLogicException
	{
		finishRegistration();
	}

	public ConfirmableObject getConfirmableObject()
	{
		Map<String, String> changes = new HashMap<String, String>();
		changes.put("login", login);
		changes.put("password", password);
		changes.put("email", email);
		return new RegistrationSettings(changes);
	}

	/**
	 * устанваливает значение полей в операцию.
	 *
	 * @param login логин клиента.
	 * @param password пароль клиента.
	 * @param email e-mail клиента.
	 */
	public void setValues(String login, String password, String email)
	{
		this.login = login;
		this.password = password;
		this.email = email;
	}

	/**
	 * @return тип подтверждения по умолчанию для пользователя.
	 * @throws BusinessException
	 */
	public String getUserOptionType() throws BusinessException
	{
		try
		{
			PersonData personData = getPersonData();
			ConfirmStrategyType confirmStrategyType = accessService.getUserOptionType(personData.getLogin(), AccessType.simple);
			if (confirmStrategyType != null)
				return confirmStrategyType.name();

			return ConfirmStrategyType.sms.name();
		}
		catch (SecurityDbException ex)
		{
			throw new BusinessException(ex);
		}
	}
}
