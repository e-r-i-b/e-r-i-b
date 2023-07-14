package com.rssl.phizic.security.password;

import com.rssl.phizic.security.config.SecurityFactory;
import com.rssl.phizic.security.crypto.CryptoProvider;
import com.rssl.phizic.security.crypto.CryptoService;

/**
 * @author Evgrafov
 * @ created 11.09.2006
 * @ $Author: krenev $
 * @ $Revision: 7605 $
 */
public class ManualPasswordGenerator implements PasswordValueGenerator
{
	private static final CryptoService cryptoService = SecurityFactory.cryptoService();
	private String password;

	public ManualPasswordGenerator(String password)
	{
		this.password = password;
	}

	public ManualPasswordGenerator(char[] passwordChars )
	{
		this.password = new String(passwordChars);
	}

	public char [] newPassword(int length, char [] allowedChars)
	{
		return password == null ? null : cryptoService.hash(password).toCharArray();
	}
}