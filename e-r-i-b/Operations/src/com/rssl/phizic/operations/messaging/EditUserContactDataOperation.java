package com.rssl.phizic.operations.messaging;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.ermb.ErmbHelper;
import com.rssl.phizic.business.mail.MailHelper;
import com.rssl.phizic.business.messaging.info.PersonalSubscriptionData;
import com.rssl.phizic.business.messaging.info.SubscriptionService;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.profile.UserSubscriptionInfo;
import com.rssl.phizic.business.profileSynchronization.PersonSettingsManager;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.logging.settings.UserMessageLogHelper;
import com.rssl.phizic.messaging.*;
import com.rssl.phizic.operations.ConfirmableOperationBase;
import com.rssl.phizic.operations.userprofile.PersonalSettings;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.security.SecurityLogicException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kosyakov
 * @ created 05.02.2007
 * @ $Author: basharin $
 * @ $Revision: 83994 $
 */
public class EditUserContactDataOperation extends ConfirmableOperationBase
{
	private static SubscriptionService subscriptionService = new SubscriptionService();
	private static DepartmentService departmentService = new DepartmentService();
	private static PersonService personService = new PersonService();

	private PersonalSubscriptionData subscriptionData;
	private boolean isEmailChanged = false;

	/**
	 * инициализация в случае, если в БД уже присутствует соответствующая запись
	 * @param login
	 * @throws BusinessException
	 */
	public void initialize(CommonLogin login) throws BusinessException
	{
		subscriptionData = subscriptionService.findPersonalData(login);
		if (subscriptionData == null)
		{
			subscriptionData = new PersonalSubscriptionData();
			subscriptionData.setLogin(login);
		}
		if (subscriptionData.getSmsTranslitMode() == null)
			subscriptionData.setSmsTranslitMode(TranslitMode.DEFAULT);
		if (subscriptionData.getMailFormat() == null)
			subscriptionData.setMailFormat(MailFormat.PLAIN_TEXT);
	}

	/**
	 * получение email адреса из контактных данных
	 * @return email
	 */
	public String getEmailAddress()
	{
		return this.subscriptionData.getEmailAddress();
	}

	/**
	 * выставление значение email адреса
	 * @param emailAddress email
	 */
	public void setEmailAddress(String emailAddress)
	{
		if (emailAddress == null)
			emailAddress = "";
		if (!emailAddress.equals(this.subscriptionData.getEmailAddress()))
		{
			this.subscriptionData.setEmailAddress(emailAddress);
			isEmailChanged = true;
		}
	}

	public boolean isEmailChanged()
	{
		return isEmailChanged;
	}

	/**
	 * получение номера мобильного телефона из контактных данных
	 * @return номер телефона
	 */
	public String getMobilePhone()
	{
		return this.subscriptionData.getMobilePhone();
	}

	/**
	 * выставление значения номера мобильного телефона
	 * @param mobilePhone номер телефона
	 */
	public void setMobilePhone(String mobilePhone)
	{
		if (mobilePhone == null)
			mobilePhone = "";
		if (!mobilePhone.equals(this.subscriptionData.getMobilePhone()))
		{
			this.subscriptionData.setMobilePhone(mobilePhone);
		}
	}

	/**
	 * выставление значения формата отправления смс
	 * @param translit формат
	 */
	public void setTranslit(TranslitMode translit)
	{
		this.subscriptionData.setSmsTranslitMode(translit);
	}

	/**
	 * получение формата отправления mail-оповещений из контактных данных
	 * @return формат
	 */
	public MailFormat getMailFormat()
	{
		return this.subscriptionData.getMailFormat();
	}

	/**
	 * выставление значения формата отправления mail-оповещений
	 * @param mailFormat
	 */
	public void setMailFormat(MailFormat mailFormat)
	{
		this.subscriptionData.setMailFormat(mailFormat);
	}

	/**
	 * Проверка на изменение мобильного телефона клиента
	 * @param mobilePhone полученный из формы телефон
	 * @throws BusinessException
	 */
	public void checkMobileChanges(String mobilePhone) throws BusinessException, BusinessLogicException
	{
		String oldPhone = subscriptionData.getMobilePhone();
		if (!mobilePhone.equals(oldPhone))
		{
			sendSms(oldPhone, mobilePhone);
		}
	}

	/**
	 * в случае изменения номера мобильного телефона - отправляем смс
	 * @param oldPhone - старый номер
	 * @param newPhone - новый номер
	 * @throws BusinessException
	 */
	private void sendSms(String oldPhone, String newPhone) throws BusinessException, BusinessLogicException
	{
		Login login = (Login)subscriptionData.getLogin();
		Department dep = departmentService.findById(EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee().getDepartmentId());
		String messageText = "Интернет-банк СБРФ: Ваш номер для паролей доступа изменен на "+  newPhone +
				" Если Вы не меняли номер телефона, срочно свяжитесь с банком для блокирования услуги по т. " + dep.getTelephone();
		try
		{
		// информацию об изменениях отправляем на прежний мобильный телефон
			IKFLMessage message = new IKFLMessage(login.getId(), messageText, ErmbHelper.hasErmbProfileByLogin(login.getId()));
			message.setRecipientMobilePhone(oldPhone);
			MessagingSingleton.getInstance().getMessagingService().sendSms(message);
		}
		catch(IKFLMessagingException ex)
		{
		throw new BusinessException(ex.getMessage(), ex);
		}
		catch (IKFLMessagingLogicException ex)
		{
			throw new BusinessLogicException(ex.getMessage(), ex);
		}
	}

	/**
	 * изменение записи БД о контактных данных клиента через сервис
	 * @throws BusinessException
	 */
	@Transactional
	public void save() throws BusinessException
	{
		subscriptionService.changePersonalData(this.subscriptionData);
		UserSubscriptionInfo subscriptionInfo = new UserSubscriptionInfo();
		subscriptionInfo.setEmailAddress(subscriptionData.getEmailAddress());
		subscriptionInfo.setMailFormat(subscriptionData.getMailFormat());
		PersonSettingsManager.savePersonData(PersonSettingsManager.USER_SUBSCRIPTION_KEY, subscriptionInfo);

		if (PersonContext.isAvailable())
		{
			ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
			person.refreshPersonSubscriptionData();
		}
		if (isEmailChanged)
		{
			sendMailNotification();
		}
	}

	private void sendMailNotification()
	{
		try
		{
			Person person = personService.findByLogin(subscriptionData.getLoginId());

			Map<String, String> keyWords = new HashMap<String, String>();
			keyWords.put("shortName", person.getFirstName() + " " + person.getPatrName());

			MailHelper.sendEMail(subscriptionData, "ChangePersonEmail", keyWords, false);
			UserMessageLogHelper.saveMailNotificationToLog(subscriptionData.getLoginId(), null);
		}
		catch (Exception e)
		{
			log.error("Ошибка отправки email сообщения.", e);
		}
	}

	@Override
	protected void saveConfirm() throws BusinessException, BusinessLogicException, SecurityLogicException
	{
		save();
	}

	public ConfirmableObject getConfirmableObject()
	{
		Map<String, Object> unsavedData = new HashMap<String, Object>();
		if (isEmailChanged)
			unsavedData.put("E-mail", getEmailAddress());
		return new PersonalSettings(unsavedData);
	}
}
