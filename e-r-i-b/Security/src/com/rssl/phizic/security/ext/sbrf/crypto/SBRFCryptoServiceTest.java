package com.rssl.phizic.security.ext.sbrf.crypto;

import com.rssl.phizic.security.crypto.CryptoService;
import junit.framework.TestCase;

/**
 * @author Erkin
 * @ created 28.09.2011
 * @ $Author$
 * @ $Revision$
 */
public class SBRFCryptoServiceTest extends TestCase
{
	private final CryptoService cryptoService = new SBRFCryptoService();

	public void testHashPassword()
	{
		String password1 = "18735";
		String password2 = "16726";

		String hash1 = cryptoService.hash(password1);
		String hash2 = cryptoService.hash(password2);

		System.out.println("Хэш от пароля " + password1 + " равен " + hash1);
		System.out.println("Хэш от пароля " + password2 + " равен " + hash2);
		
		assertFalse(hash1.equals(hash2));
	}
}
