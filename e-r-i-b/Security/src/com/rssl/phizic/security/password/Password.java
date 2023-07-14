package com.rssl.phizic.security.password;

/**
 * Created by IntelliJ IDEA.
 * User: Evgrafov
 * Date: 01.09.2005
 * Time: 16:45:35
 *
 * Описание пароля
 */
public interface Password
{
    /**
     * @return возвращает true если пароль действующий иначе false
     */
    public boolean isValid();

    /**
     * @return Содержимое пароля (там пароль или его хэш)
     */
    public char[]  getValue();
}
