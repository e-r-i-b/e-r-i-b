package com.rssl.phizic.auth;

import com.rssl.phizic.security.SecurityDbException;

/**
 * @author Kosyakov
 * @ created 12.01.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 2121 $
 */
public interface LoginGenerator<L extends CommonLogin>
{
	/**
	 * Создать новый логин
	 *
	 * @return созданный логин
	 * @throws SecurityDbException      ошибка записи в БД
	 * @throws DublicateUserIdException такой юзер id уже есть
	 */
	L generate() throws SecurityDbException, DublicateUserIdException;

}
