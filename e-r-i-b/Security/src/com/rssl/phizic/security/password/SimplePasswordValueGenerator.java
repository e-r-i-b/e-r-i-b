package com.rssl.phizic.security.password;

import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: Kidyaev
 * Date: 12.09.2005
 * Time: 14:30:26
 * Примитивный алгоритм генерации одного пароля заданной длины
 */
public class SimplePasswordValueGenerator implements PasswordValueGenerator
{
    public char [] newPassword(int length, char [] allowedChars)
    {
        char [] password = new char[length];
        Random  rand     = new Random();

        for ( int i = 0; i < length; i++ )
        {
            password[i] = allowedChars[ rand.nextInt(allowedChars.length) ];
        }

        return password;
    }
}
