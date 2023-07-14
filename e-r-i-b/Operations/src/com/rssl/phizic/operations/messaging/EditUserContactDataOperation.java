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
	 * ������������� � ������, ���� � �� ��� ������������ ��������������� ������
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
	 * ��������� email ������ �� ���������� ������
	 * @return email
	 */
	public String getEmailAddress()
	{
		return this.subscriptionData.getEmailAddress();
	}

	/**
	 * ����������� �������� email ������
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
	 * ��������� ������ ���������� �������� �� ���������� ������
	 * @return ����� ��������
	 */
	public String getMobilePhone()
	{
		return this.subscriptionData.getMobilePhone();
	}

	/**
	 * ����������� �������� ������ ���������� ��������
	 * @param mobilePhone ����� ��������
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
	 * ����������� �������� ������� ����������� ���
	 * @param translit ������
	 */
	public void setTranslit(TranslitMode translit)
	{
		this.subscriptionData.setSmsTranslitMode(translit);
	}

	/**
	 * ��������� ������� ����������� mail-���������� �� ���������� ������
	 * @return ������
	 */
	public MailFormat getMailFormat()
	{
		return this.subscriptionData.getMailFormat();
	}

	/**
	 * ����������� �������� ������� ����������� mail-����������
	 * @param mailFormat
	 */
	public void setMailFormat(MailFormat mailFormat)
	{
		this.subscriptionData.setMailFormat(mailFormat);
	}

	/**
	 * �������� �� ��������� ���������� �������� �������
	 * @param mobilePhone ���������� �� ����� �������
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
	 * � ������ ��������� ������ ���������� �������� - ���������� ���
	 * @param oldPhone - ������ �����
	 * @param newPhone - ����� �����
	 * @throws BusinessException
	 */
	private void sendSms(String oldPhone, String newPhone) throws BusinessException, BusinessLogicException
	{
		Login login = (Login)subscriptionData.getLogin();
		Department dep = departmentService.findById(EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee().getDepartmentId());
		String messageText = "��������-���� ����: ��� ����� ��� ������� ������� ������� �� "+  newPhone +
				" ���� �� �� ������ ����� ��������, ������ ��������� � ������ ��� ������������ ������ �� �. " + dep.getTelephone();
		try
		{
		// ���������� �� ���������� ���������� �� ������� ��������� �������
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
	 * ��������� ������ �� � ���������� ������ ������� ����� ������
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
			log.error("������ �������� email ���������.", e);
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
