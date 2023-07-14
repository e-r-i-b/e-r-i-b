package com.rssl.auth.csa.back.integration.ipas.store;

import com.rssl.auth.csa.back.CSAUserInfo;
import com.rssl.auth.csa.back.Config;
import com.rssl.auth.csa.back.exceptions.RetryIPasUnavailableException;
import com.rssl.auth.csa.back.integration.ipas.AdjacentServiceUnavailableException;
import com.rssl.auth.csa.back.integration.ipas.IPasService;
import com.rssl.auth.csa.back.integration.ipas.ServiceUnavailableException;
import com.rssl.auth.csa.back.integration.ipas.generated.IPASWSSoap;
import com.rssl.auth.csa.back.servises.connectors.TerminalConnector;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;

/**
 * @author krenev
 * @ created 12.09.2013
 * @ $Author$
 * @ $Revision$
 * Сервис IPas с прихраниванием паролей.
 */

public class PasswordStoreIPasService
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	//Список кодов отказов, требующих верификацию пароля по сохраненным данным.
	private static final List<String> ERROR_CODES_FOR_STORED_PASSWORD_VERIFICATION = Arrays.asList("ERR_PRMFMT","ERROR");
	private static final PasswordStoreIPasService INSTANCE = new PasswordStoreIPasService();

	private PasswordStoreIPasService()
	{
	}

	/**
	 * @return инстанс сервиса
	 */
	public static PasswordStoreIPasService getInstance()
	{
		return INSTANCE;
	}

	/**
	 * Верификация паролей в iPas с прихраниванием
	 * @param connector коннектор
	 * @param password пароль
	 * @return инфа о пользователе в случае успешной аутентификации, null в случае неудачной аутентификации
	 * @throws Exception
	 */
	public CSAUserInfo verifyPassword(TerminalConnector connector, String password) throws Exception
	{
		if (connector == null)
		{
			throw new IllegalArgumentException("Коннектор не может быть null");
		}
		String login = connector.getUserId();

		if (!ConfigFactory.getConfig(Config.class).isIPasAuthenticationAllowed())
		{
			return verifyByStoredPassword(connector, password);
		}

		CSAUserInfo userInfo = null;
		try
		{
			userInfo = IPasService.getInstance().verifyPassword(login, password);
		}
		catch (AdjacentServiceUnavailableException e)
		{
			if (ERROR_CODES_FOR_STORED_PASSWORD_VERIFICATION.contains(e.getErrorCode()))
			{
				log.error("Ошибка при верификации пароля в iPas", e);
				return verifyByStoredPassword(connector, password);
			}
			throw e;
		}
		catch (ServiceUnavailableException e)
		{
			if (e.getCause() instanceof RemoteException)
			{
				log.error("Ошибка при верификации пароля в iPas", e);
				return verifyByStoredPassword(connector, password);
			}
			throw e;
		}

		if (userInfo != null)
		{
			storeAuthData(login, password);
		}
		return userInfo;
	}

	private CSAUserInfo verifyByStoredPassword(TerminalConnector connector, String password) throws Exception
	{
		if (connector == null)
		{
			throw new IllegalArgumentException("Коннектор не может быть null");
		}
		String login = connector.getUserId();

		log.trace("Производим попытку верификации пароля по сохраненным данным для логина " + login);
		StoredPassword storedPassword = StoredPassword.findByLogin(login);
		if (storedPassword == null)
		{
			log.trace("Нет сохраненного пароля. Аутентифкация не пройдена для логина " + login);
			throw new RetryIPasUnavailableException("Вход в Сбербанк Онлайн временно недоступен. Пожалуйста, повторите попытку через 30 минут.");
		}
		if (!storedPassword.check(password))
		{
			log.debug("Не пройдена верификация пароля по сохраненным данным для логина " + login);
			return null;
		}
		log.debug("Верификация пароля по сохраненным данным для логина " + login + " пройдена успешно.");
		return connector.asUserInfo();
	}

	private void storeAuthData(String login, String password)
	{
		if (!ConfigFactory.getConfig(Config.class).isIPasPasswordStoreAllowed())
		{
			log.trace("Запрещено 'прихранивание' паролей. Сохранение пропущено для логина " + login);
			return;
		}

		try
		{
			StoredPassword storedPassword = StoredPassword.findByLogin(login);
			if (storedPassword == null)
			{
				log.trace("Нет сохраненного пароля для логина " + login + " сохраняем пароль. Производится сохрание аутентификациионных данных.");
				StoredPassword.create(login, password);
				return;
			}
			if (storedPassword.check(password))
			{
				log.trace("Для логина " + login + " пропускаем сохранение пароля, тк пароль не изменился");
				return;
			}
			log.debug("Для логина " + login + " изменен сохраненный пароль");
			storedPassword.changePassword(password);
		}
		catch (Exception e)
		{
			log.error("Ошибка при 'прихранивании' пароля для логина " + login, e);
		}
	}
}
