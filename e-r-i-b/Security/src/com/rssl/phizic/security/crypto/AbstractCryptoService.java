package com.rssl.phizic.security.crypto;

import com.rssl.phizic.utils.StringUtils;

import java.io.UnsupportedEncodingException;

/**
 * @author Krenev
 * @ created 17.07.2008
 * @ $Author$
 * @ $Revision$
 */
public abstract class AbstractCryptoService implements CryptoService
{
	public String hash(String m)
	{
		try
		{
			return StringUtils.toHexString(hash(m.getBytes("UTF-16LE")));
		}
		catch (UnsupportedEncodingException e)
		{
			throw new RuntimeException(e);
		}
	}

	public byte[] hmac(byte[] key, byte[] data, String algorithm)
	{
		if (key.length > 64)
		{
			key = hash(key, algorithm);
		}

		return hash(hmacMerge(hmacXor(key, 0x5c), hash(hmacMerge(hmacXor(key, 0x36), data), algorithm)), algorithm);
	}

	private static byte[] hmacMerge(byte[] data1, byte[] data2)
	{
		byte[] buff = new byte[data1.length + data2.length];

		int i = 0;
		int j = 0;
		int n = data1.length;

		while (j < n)
		{
			buff[i++] = data1[j++];
		}

		j = 0;
		n = data2.length;

		while (j < n)
		{
			buff[i++] = data2[j++];
		}

		return buff;
	}

	private static byte[] hmacXor(byte[] data, int v)
	{
		byte[] buff = new byte[64];

		for (int i = 0, n = data.length; i < 64; ++i)
		{
			buff[i] = (byte) ((i < n) ? (data[i] ^ v) : v);
		}

		return buff;
	}
}
