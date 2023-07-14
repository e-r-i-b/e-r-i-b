package com.rssl.phizic.security.password;

/**
 * Created by IntelliJ IDEA.
 * User: Kidyaev
 * Date: 12.09.2005
 * Time: 15:12:58
 * Создает один пароль.
 * Реализующие классы инкапсулируют алгоритм создания одного пароля.
 */
public interface PasswordValueGenerator
{
    /**
     * Метод создает один пароль
     * @param length       - длина пароля
     * @param allowedChars - допустимые символы, которые могут быть использованы в пароле
     * @return пароль в виде массива символов
     */
    public char [] newPassword(int length, char [] allowedChars);
}
