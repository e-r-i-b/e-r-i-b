package com.rssl.phizic.operations.userprofile;

import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.authgate.AuthGateException;
import com.rssl.phizic.authgate.AuthGateLogicException;
import com.rssl.phizic.authgate.AuthGateSingleton;
import com.rssl.phizic.authgate.AuthParamsContainer;
import com.rssl.phizic.authgate.passwords.PasswordGateService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.ErmbHelper;
import com.rssl.phizic.business.persons.PersonBase;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.authService.AuthServiceConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.messaging.*;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.config.SecurityConfig;
import com.rssl.phizic.security.config.SecurityFactory;
import com.rssl.phizic.security.crypto.CryptoService;
import com.rssl.phizic.security.password.SecurePasswordValueGenerator;
import com.rssl.phizic.security.password.UserPasswordChanger;

/**
 * @author potehin
 * @ created 26.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class GeneratePasswordOperation extends OperationBase
{
	private static final CryptoService cryptoService = SecurityFactory.cryptoService();
	private static final SecurityService securityService = new SecurityService();
	private final MessagingService messagingService = MessagingSingleton.getInstance().getMessagingService();

	private final MessageComposer messageComposer = new MessageComposer();

	private static final String IPAS_ERMB_ERROR = "�� ������� ��������� ��� ����� ������. �������� ����� ������ �� ������ � ����� ���������� ���������������� �����";

	protected PersonBase person;
	protected String password;

	public void initialize() throws BusinessException
	{
		PersonDataProvider dataProvider = PersonContext.getPersonDataProvider();
		PersonData personData = dataProvider.getPersonData();
		person = personData.getPerson();
	}

	/**
	 * ���������� ����� ������ �������, � ����������� �� �������� �������������� �������� �����
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void generate() throws BusinessException, BusinessLogicException
	{
		if(ConfigFactory.getConfig(AuthServiceConfig.class).isUseOwnAuth())
			generateOurPassword();
		else
			iPasGeneratePassword();
	}

	/**
	 * ��������� (���������) � ������� ���� SMS ������ ������ �������
	 * @throws com.rssl.phizic.business.BusinessException ������ ������ � ���������
	 * @throws  com.rssl.phizic.business.BusinessLogicException �������� ������� ������ �������
	 */
	public void generateOurPassword() throws BusinessException, BusinessLogicException
	{
		// �������� ������
		String enteredPasswordHash = cryptoService.hash(password);

		String databasePasswordHash;
		try
		{
			databasePasswordHash = securityService.getPasswordHash(person.getLogin());
		}
		catch (SecurityDbException e)
		{
			throw new BusinessException(e);
		}

		if (!enteredPasswordHash.equals(databasePasswordHash))
			throw new BusinessLogicException("�� ����������� ������� �������� � ���� \"������� ������\". ����������, ������� ��� ������� ������");

		// ��������� ��������� ������
		SecurityConfig securityConfig = ConfigFactory.getConfig(SecurityConfig.class);
		char[] allowedChars = securityConfig.getPasswordAllowedChars().toCharArray();
		int length = securityConfig.getPasswordLength();

		// ��������� ������ ������
		SecurePasswordValueGenerator generator = new SecurePasswordValueGenerator();
		char[] newPassword = generator.newPassword(length, allowedChars);

		// ����� ������
		UserPasswordChanger passwordChanger = new UserPasswordChanger();
		try
		{
			passwordChanger.changePassword(person.getLogin(), newPassword);
		}
		catch (SecurityDbException e)
		{
			throw new BusinessException(e);
		}

		// �������� ������
		try
		{
			IKFLMessage message = messageComposer.buildUserPasswordChangedMessage(person.getLogin(), new String(newPassword));
			messagingService.sendSms(message);
		}
		catch (IKFLMessagingException ex)
		{
			try
			{
				passwordChanger.changePassword(person.getLogin(), password.toCharArray());
			}
			catch (SecurityDbException ignored)
			{}

			throw new BusinessLogicException("������ �� ��� ������, ��� ��� �� ������� �������� ��������� SMS � �������.", ex);
		}
		catch (IKFLMessagingLogicException ex)
		{
			try
			{
				passwordChanger.changePassword(person.getLogin(), password.toCharArray());
			}
			catch (SecurityDbException ignored)
			{}

			throw new BusinessLogicException("������ �� ��� ������, ��� ��� �� ������� �������� ��������� SMS � �������.", ex);
		}
	}

   /*
	* ��������� � ������� �� sms ������ ������ ����� �����c iPas
	* @throws com.rssl.phizic.business.BusinessException ������ ������ � ���������
    */
	public void iPasGeneratePassword() throws BusinessException, BusinessLogicException
   {
	   //��� �������������� ������ ��� ����� � ����, ���� ������ ������� �������� � iPAS � ������ ��������� � ����, ���������� ���������� ������� ������� ��������� �� ������
	   if (ErmbHelper.isERMBConnectedPerson())
	   {
		   throw new BusinessLogicException(IPAS_ERMB_ERROR);
	   }

	   PasswordGateService passwordGateService = AuthGateSingleton.getPasswordService();
	   AuthParamsContainer resp = new AuthParamsContainer();
	   AuthParamsContainer container = new AuthParamsContainer();

       container.addParameter("UserId",person.getLogin().getCsaUserId());
 	   container.addParameter("password",password);
		try
		{
			//�������� �� iPas ����� ������
			resp = passwordGateService.generateStaticPassword(container);
		}
		catch (AuthGateException ex)
		{
		   throw new BusinessException(ex);
		}
		catch (AuthGateLogicException ex)
		{
		   throw new BusinessLogicException(ex.getMessage(), ex);
		}

		try
		{
			//���������� ����� ������ �� SMS
			IKFLMessage message = messageComposer.buildUserPasswordChangedMessage(person.getLogin(), resp.getParameter("Password"));
			messagingService.sendSms(message);
		}

		catch (IKFLMessagingException ex)
		{
		   throw new BusinessException("������ �� ��� ������, ��� ��� �� ������� �������� ��������� SMS � �������.", ex);
		}
		catch (IKFLMessagingLogicException ex)
		{
			throw new BusinessLogicException("������ �� ��� ������, ��� ��� �� ������� �������� ��������� SMS � �������.", ex);
		}
   }

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}
}
