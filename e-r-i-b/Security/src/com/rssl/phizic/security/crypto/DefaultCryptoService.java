package com.rssl.phizic.security.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Krenev
 * @ created 17.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class DefaultCryptoService extends AbstractCryptoService
{

	public byte[] hash(byte[] m)
	{
		return hash(m, "MD5");
	}

	public byte[] hash(byte[] m, String algorinm)
	{
		try
		{
			MessageDigest md5 = MessageDigest.getInstance(algorinm);
			md5.update(m);
			return md5.digest();
		}
		catch (NoSuchAlgorithmException e)
		{
			throw new RuntimeException(e);
		}
	}
}
