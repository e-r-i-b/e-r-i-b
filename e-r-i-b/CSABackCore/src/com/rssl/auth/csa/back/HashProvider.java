package com.rssl.auth.csa.back;

import com.rssl.phizic.utils.RandomGUID;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author krenev
 * @ created 12.09.2013
 * @ $Author$
 * @ $Revision$
 * ������� ���������� ��� ����������� ������� �� ��������� hash(hash(Password)+salt) � ������� ����������� �����������
 */

public class HashProvider
{
	private String algorithm;

	/**
	 * �����������
	 * @param algorithm �������� �����������
	 */
	private HashProvider(String algorithm)
	{
		this.algorithm = algorithm;
	}

	/**
	 * ������������ ������ �� ��������� hash(hash(Password)+salt)
	 * @param password ������
	 * @param salt ����
	 * @return �������������� ������
	 */
	public byte[] hash(String password, byte[] salt) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		byte[] passwordHash = hash(password.getBytes("UTF-16LE"));
		byte[] passwordHashAndSalt = new byte[passwordHash.length + salt.length];
		System.arraycopy(passwordHash, 0, passwordHashAndSalt, 0, passwordHash.length);
		System.arraycopy(salt, 0, passwordHashAndSalt, passwordHash.length, salt.length);
		return hash(passwordHashAndSalt);
	}

	/**
	 * ������������� ����
	 * @return ����
	 */
	public static byte[] generateSalt()
	{
		return new RandomGUID().getValue();
	}

	private byte[] hash(byte[] value) throws NoSuchAlgorithmException
	{
		MessageDigest digest = MessageDigest.getInstance(algorithm);
		digest.update(value);
		return digest.digest();
	}

	/**
	 * �������� ������� � ������������ ����������
	 * @param algorithm ��������
	 * @return �������
	 */
	public static HashProvider getInstance(String algorithm)
	{
		return new HashProvider(algorithm);
	}
}
