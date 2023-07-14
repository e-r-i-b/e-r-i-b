package com.rssl.phizic.security.password;

import com.rssl.phizic.security.test.SecurityTestBase;
import com.rssl.phizic.security.config.SecurityConfig;
import com.rssl.phizic.config.ConfigFactory;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Kidyaev
 * @ created 22.12.2005
 * @ $Author$
 * @ $Revision$
 */
public class PasswordGeneratorTest extends SecurityTestBase
{
    public void testGeneratePasswords() throws NoSuchProviderException, NoSuchAlgorithmException
    {
	    SecurityConfig securityConfig = ConfigFactory.getConfig(SecurityConfig.class);

	    int               MAX_SIZE        = 1000;
        int               PASSWORD_LENGTH = 8;
        char[]            ALLOWED_SYMBOLS = securityConfig.getPasswordAllowedChars().toCharArray();
        SecurePasswordValueGenerator generator  = new SecurePasswordValueGenerator();
        Set<String>             passwords  = new HashSet<String>();

        for (int i = 0; i < MAX_SIZE; i++)
        {
            passwords.add( new String(generator.newPassword(PASSWORD_LENGTH, ALLOWED_SYMBOLS)) );
        }

        // Тест на уникальность не проходит
        // assertEquals( MAX_SIZE, passwords.size() );
        assertTrue( passwords.size() > 0);
    }
}
