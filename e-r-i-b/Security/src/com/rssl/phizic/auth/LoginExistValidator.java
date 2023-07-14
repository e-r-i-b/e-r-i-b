package com.rssl.phizic.auth;

import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.security.password.BlockedException;
import com.rssl.phizic.security.password.NamePasswordValidator;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * проверка на существование логина пользовател€
 * @author eMakarov
 * @ created 04.12.2008
 * @ $Author$
 * @ $Revision$
 */
public class LoginExistValidator implements NamePasswordValidator
{
	private static final SecurityService securityService = new SecurityService();

	/**
	 * ѕровер€ет переданное им€ пользовател€ (в случае когда не нужно провер€ть пароль)
	 * @param userId userId пользовател€
	 * @param password пароль
	 * @return login найденный по юзер id
	 * @exception com.rssl.phizic.security.SecurityLogicException неверные login\password,
	 * состо€ние учетной записи (заблокирована) etc
	 * @exception SecurityException прочие ошибки при валидации
	 */
	public CommonLogin validateLoginInfo(String userId, char[] password) throws SecurityLogicException, SecurityException
	{
		Login login;
		try
		{
			login = securityService.getClientLogin(userId);
		}
		catch (SecurityDbException e)
		{
			throw new SecurityException(e);
		}
		if (login == null)
		{
			// если нет логина, то это очень плохо
			throw new SecurityLogicException("Ќеверно введен логин.");
		}

		return validateLoginInfo(login);
	}

	/**
	 * ѕровер€ет блокировки логина
	 * @param login логин
	 * @return логин
	 */
	public CommonLogin validateLoginInfo(Login login) throws BlockedException
	{
		Calendar blockUntil = new GregorianCalendar();
		List<LoginBlock> blocks = securityService.getBlocksForLogin(login, blockUntil.getTime(), null);
		if (blocks.size() != 0)
		{
			throw new BlockedException("ќшибка регистрации. ƒоступ в систему запрещен.");
		}
		return login;
	}
}
