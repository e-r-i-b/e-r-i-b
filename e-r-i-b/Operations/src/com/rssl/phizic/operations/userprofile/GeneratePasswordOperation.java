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

	private static final String IPAS_ERMB_ERROR = "Не удалось отправить Вам новый пароль. Получить новый пароль Вы можете в любом устройстве самообслуживания банка";

	protected PersonBase person;
	protected String password;

	public void initialize() throws BusinessException
	{
		PersonDataProvider dataProvider = PersonContext.getPersonDataProvider();
		PersonData personData = dataProvider.getPersonData();
		person = personData.getPerson();
	}

	/**
	 * Генерируем новый пароль клиента, в зависимости от настроек аутентификации выбираем метод
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
	 * Установка (генерация) и отсылка путём SMS нового пароля клиента
	 * @throws com.rssl.phizic.business.BusinessException Ошибка работы с сервисами
	 * @throws  com.rssl.phizic.business.BusinessLogicException Неверный текущий пароль клиента
	 */
	public void generateOurPassword() throws BusinessException, BusinessLogicException
	{
		// Проверка пароля
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
			throw new BusinessLogicException("Вы неправильно указали значение в поле \"Текущий пароль\". Пожалуйста, введите Ваш текущий пароль");

		// Настройки генерации пароля
		SecurityConfig securityConfig = ConfigFactory.getConfig(SecurityConfig.class);
		char[] allowedChars = securityConfig.getPasswordAllowedChars().toCharArray();
		int length = securityConfig.getPasswordLength();

		// Генерация нового пароля
		SecurePasswordValueGenerator generator = new SecurePasswordValueGenerator();
		char[] newPassword = generator.newPassword(length, allowedChars);

		// Смена пароля
		UserPasswordChanger passwordChanger = new UserPasswordChanger();
		try
		{
			passwordChanger.changePassword(person.getLogin(), newPassword);
		}
		catch (SecurityDbException e)
		{
			throw new BusinessException(e);
		}

		// Отправка пароля
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

			throw new BusinessLogicException("Пароль не был изменён, так как не удалось отослать сообщение SMS с паролем.", ex);
		}
		catch (IKFLMessagingLogicException ex)
		{
			try
			{
				passwordChanger.changePassword(person.getLogin(), password.toCharArray());
			}
			catch (SecurityDbException ignored)
			{}

			throw new BusinessLogicException("Пароль не был изменён, так как не удалось отослать сообщение SMS с паролем.", ex);
		}
	}

   /*
	* Генерация и отсылка по sms нового пароля через сервиc iPas
	* @throws com.rssl.phizic.business.BusinessException Ошибка работы с сервисами
    */
	public void iPasGeneratePassword() throws BusinessException, BusinessLogicException
   {
	   //При восстановлении пароля для входа в ЕРИБ, если пароль клиента хранится в iPAS и клиент подключен к ЕРМБ, необходимо отображать данному клиенту сообщение об ошибке
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
			//получаем от iPas новый пароль
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
			//отпровляем новый пароль по SMS
			IKFLMessage message = messageComposer.buildUserPasswordChangedMessage(person.getLogin(), resp.getParameter("Password"));
			messagingService.sendSms(message);
		}

		catch (IKFLMessagingException ex)
		{
		   throw new BusinessException("Пароль не был изменён, так как не удалось отослать сообщение SMS с паролем.", ex);
		}
		catch (IKFLMessagingLogicException ex)
		{
			throw new BusinessLogicException("Пароль не был изменён, так как не удалось отослать сообщение SMS с паролем.", ex);
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
