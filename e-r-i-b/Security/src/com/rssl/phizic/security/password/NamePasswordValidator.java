package com.rssl.phizic.security.password;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.security.SecurityLogicException;

/**
 * Created by IntelliJ IDEA.
 * User: Evgrafov
 * Date: 01.09.2005
 * Time: 15:08:07
 */
public interface NamePasswordValidator
{
    /**
     * Проверяет переданные имя пользователя и пароль
     * @param userId userId пользователя
     * @param password пароль
     * @return login найденный по юзер id
     * @exception SecurityLogicException неверные login\password,
     * состояние учетной записи (заблокирована) etc
     * @exception SecurityException прочие ошибки при валидации
     */
    CommonLogin validateLoginInfo(String userId, char[] password) throws SecurityLogicException, SecurityException;
}
