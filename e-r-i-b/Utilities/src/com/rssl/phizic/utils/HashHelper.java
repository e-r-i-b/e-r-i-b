package com.rssl.phizic.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Omeliyanchuk
 * @ created 09.03.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * ���������� md5 hash. ��� ������ ����������, ��� ��� ����� ��������������� ���������� ������������ CryptoService
 */
public class HashHelper
{
	/**
	 * md5 hash
	 * @param src ������, ������������� ��������� �� ���������
	 * @return hex ������������� �� md5 hash
	 */
	public static String hash(String src)
	{
		return hash(ArraysHelper.getBytes(src));
	}

	/**
	 * md5 hash ��� ���� �������.
	 * @param bytes ������ �� ���� ��� ���������.
	 * @return hex ������������� �� md5 hash.
	 */
	public static String hash(byte[] bytes)
	{
		if (bytes == null || bytes.length == 0)
			return null;

		try
		{
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(bytes);
			return StringUtils.toHexString(md5.digest());
		}
		catch (NoSuchAlgorithmException e)
		{
			throw new RuntimeException(e);
		}
	}
}
