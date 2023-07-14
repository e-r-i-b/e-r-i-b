package com.rssl.phizic.security;

import com.rssl.phizic.security.config.SecurityFactory;
import com.rssl.phizic.security.crypto.CryptoProvider;
import com.rssl.phizic.security.crypto.CryptoProviderHelper;
import com.rssl.phizic.security.crypto.CryptoService;
import com.rssl.phizic.security.crypto.HashAlgorithms;

import java.io.UnsupportedEncodingException;

/**
 * @author Roshka
 * @ created 04.04.2007
 * @ $Author$
 * @ $Revision$
 */
//TODO может избавиться от него?
public class GOSTUtils
{
	/**
	 * @param string строка для расчета хеша (unicode символ => два байта)
	 * @return хеш
	 */
	public static byte[] hash(String string)
	{
		try
		{
			byte[] src = string.getBytes("Unicode");
			byte[] dest = new byte[src.length - 2];
			System.arraycopy(src, 2, dest, 0, src.length - 2);
			return hash(dest);
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
		CryptoService cryptoService = SecurityFactory.cryptoService();
		try
		{
			return cryptoService.hash(bytes);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * <a href="http://www.faqs.org/rfcs/rfc2104.html">RFC 2104</a>
	 *
	 * @param keyBytes  ключ
	 * @param dataBytes данные
	 * @return hmac
	 */
	public static byte[] hmac(byte[] keyBytes, byte[] dataBytes)
	{
		try
		{
			CryptoService cryptoService = SecurityFactory.cryptoService();
			return cryptoService.hmac(keyBytes, dataBytes, HashAlgorithms.GOST_SBERBANK);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
}