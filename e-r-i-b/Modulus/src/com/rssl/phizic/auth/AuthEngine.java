package com.rssl.phizic.auth;

import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.common.types.exceptions.UserErrorException;
import com.rssl.phizic.engine.Engine;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.session.PersonSession;
import com.rssl.phizic.utils.PhoneNumber;

/**
 * @author Erkin
 * @ created 18.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Движок аутентификации
 */
public interface AuthEngine extends Engine
{
	/**
	 * Аутентифицировать клиента по номеру телефона
	 * @param phone - номер телефона
	 * @return клиентская сессия (never null)
	 * @throws UserErrorException - ошибка аутентификации, готовая для показа пользователю
	 * @throws InternalErrorException - системный сбой
	 */
	PersonSession authenticateByPhone(PhoneNumber phone);

	/**
	 * Аутентифицировать клиента
	 * @param person - клиент
	 * @return клиентская сессия (never null)
	 * @throws UserErrorException - ошибка аутентификации, готовая для показа пользователю
	 * @throws InternalErrorException - системный сбой
	 */
	PersonSession authenticateByPerson(Person person);
}
