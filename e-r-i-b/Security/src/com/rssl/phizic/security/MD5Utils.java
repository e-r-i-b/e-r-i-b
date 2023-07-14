package com.rssl.phizic.security;

import javax.crypto.SecretKey;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Evgrafov
 * @ created 14.02.2007
 * @ $Author: Evgrafov $
 * @ $Revision: 3487 $
 */

public class MD5Utils
{
	/**
	 * @param string строка для расчета хеша (unicode символ => два байта)
	 * @return хеш
	 */
	public static byte[] hash(String string)
	{
		try
		{
			byte[] bytes = string.getBytes("Unicode");
			return hash(bytes, 2 , bytes.length - 2);
		}
		catch (UnsupportedEncodingException e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param bytes массив для расчета пароля
	 * @return хеш
	 */
	public static byte[] hash(byte[] bytes)
	{
		return hash(bytes, 0, bytes.length);
	}

	private static byte[] hash(byte[] bytes, int offset, int len)
	{
		try
		{
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(bytes, offset, len);
			return md.digest();
		}
		catch (NoSuchAlgorithmException e)
		{
			throw new RuntimeException(e);
		}
	}


	/**
	 * <a href="http://www.faqs.org/rfcs/rfc2104.html">RFC 2104</a>
	 * @param keyBytes ключ
	 * @param dataBytes данные
	 * @return hmac
	 */
	public static byte[] hmac(byte[] keyBytes, byte[] dataBytes)
	{
		try
		{
			SecretKey key = new SecretKeySpec(keyBytes, "HmacMD5");

			Mac mac = Mac.getInstance(key.getAlgorithm());
			mac.init(key);

			return mac.doFinal(dataBytes);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
}