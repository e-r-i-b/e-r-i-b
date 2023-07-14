package com.rssl.phizic.authgate.passwords;

import com.rssl.phizic.authgate.AuthParamsContainer;
import com.rssl.phizic.authgate.AuthGateException;
import com.rssl.phizic.authgate.AuthGateLogicException;

/**
 * @author Gainanov
 * @ created 13.04.2010
 * @ $Author$
 * @ $Revision$
 */
public interface PasswordGateService
{
	/**
	 * Подготовка ввода одноразового пароля с чека.
	 * @param container содержит в себе параметры для подготовки (userId, карта, etc.)
	 * @return Контейнер. В случае Way4 iPAS, в нем содержится номер чека и номер пароля, количество отавшихся  паролей
	 * @throws AuthGateException
	 * @throws AuthGateLogicException
	 */
	public AuthParamsContainer prepareOTP(AuthParamsContainer container) throws AuthGateException, AuthGateLogicException;

	/**
	 * Проверка правильности ввода одноразового пароля с чека.
	 * @param container содержит в себе параметры для проверки (пароль, etc.)
	 * @return результат проверки пароля и доп. аттрибуты результата (количество оставшихся попыток и т.п.)  
	 * @throws AuthGateException
	 * @throws AuthGateLogicException
	 */
	public AuthParamsContainer verifyOTP(AuthParamsContainer container) throws AuthGateException, AuthGateLogicException;

	/**
	 * Запрос на генерацию нового статического пароля
	 * @param container параметры для генерации пароля
	 * @return результат генерации (результат, новый пароль, etc.)
	 * @throws AuthGateException
	 * @throws AuthGateLogicException
	 */
	public AuthParamsContainer generateStaticPassword(AuthParamsContainer container) throws AuthGateException, AuthGateLogicException;

	/**
	 * метод отсылает клиенту одноразовый смс-пароль
	 * @param container контейнер входных данных (например номер карты, пароль, etc.)
	 * @return результат отсылки пароля
	 * @throws AuthGateException
	 * @throws AuthGateLogicException
	 */
	public AuthParamsContainer sendSmsPassword(AuthParamsContainer container) throws AuthGateException, AuthGateLogicException;


	/**
	 * Аутентификация по статическому паролю
	 * @param container контейнер входных данных (логин, пароль, etc.)
	 * @return результат проверки пароля и доп. аттрибуты результата
	 * @throws AuthGateException
	 * @throws AuthGateLogicException
	 */
	public AuthParamsContainer verifyPassword(AuthParamsContainer container) throws AuthGateException, AuthGateLogicException;

	/**
	 * Аутентификация с помощью CAP
	 * @param cardNumber Номер карты
	 * @param capToken   CAP пароль
	 * @throws AuthGateException
	 * @throws AuthGateLogicException
	 */
	public void verifyCAP(String cardNumber, String capToken) throws AuthGateException, AuthGateLogicException;
}
