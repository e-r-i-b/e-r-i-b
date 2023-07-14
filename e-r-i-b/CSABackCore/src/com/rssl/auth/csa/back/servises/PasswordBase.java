package com.rssl.auth.csa.back.servises;

import com.rssl.auth.csa.back.HashProvider;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Calendar;

/**
 * ������� ����� ��� ������� ��������������
 * @author niculichev
 * @ created 31.01.15
 * @ $Author$
 * @ $Revision$
 */
public class PasswordBase extends ActiveRecord
{
	public static final String ALGORITHM = "SHA-1";
	private byte[] hash;
	private byte[] salt;
	private Calendar creationDate;
	private Long id;
	private boolean active;

	protected PasswordBase()
	{
	}

	protected PasswordBase(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		if (password == null)
		{
			throw new IllegalArgumentException("������ �� ����� ���� null");
		}
		salt = HashProvider.generateSalt();
		hash = getHashProvider().hash(password.toUpperCase(), salt);
		creationDate = getCurrentDate();
		active = true;
	}

	/**
	 * @return ������������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * ���������� ������������
	 * @param id ������������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return ��� ������
	 */
	public String getHash()
	{
		return toString(hash);
	}

	/**
	 * ���������� ��� ������
	 * @param hash ��� ������
	 */
	public void setHash(String hash)
	{
		this.hash = toByteArray(hash);
	}

	/**
	 * @return ����
	 */
	public String getSalt()
	{
		return toString(salt);
	}

	/**
	 * ���������� ����
	 * @param salt ����
	 */
	public void setSalt(String salt)
	{
		this.salt = toByteArray(salt);
	}

	/**
	 * @return ���� �������� ������
	 */
	public Calendar getCreationDate()
	{
		return creationDate;
	}

	/**
	 * ���������� ���� �������� ������
	 * @param creationDate ���� �������� ������
	 */
	public void setCreationDate(Calendar creationDate)
	{
		this.creationDate = creationDate;
	}

	public boolean isActive()
	{
		return active;
	}

	public void setActive(boolean active)
	{
		this.active = active;
	}

	/**
	 * ��������� ������� ������ �� ����������� ������
	 * @param password ������ ��� ��������
	 * @return ��/���
	 */
	public boolean check(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		if (password == null)
		{
			throw new IllegalArgumentException("������ �� ����� ���� null");
		}
		return Arrays.equals(getHashProvider().hash(password.toUpperCase(), salt), hash);
	}

	private HashProvider getHashProvider()
	{
		return HashProvider.getInstance(ALGORITHM);
	}

	private static String toString(byte[] value)
	{
		return StringUtils.toHexString(value);
	}

	private static byte[] toByteArray(String value)
	{
		if (StringHelper.isEmpty(value))
		{
			return new byte[0];
		}
		return StringUtils.fromHexString(value);
	}

	public void delete() throws Exception
	{
		setActive(false);
		save();
	}
}
