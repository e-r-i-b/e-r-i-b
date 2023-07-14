package com.rssl.auth.csa.back.servises;

import com.rssl.auth.csa.back.HashProvider;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Calendar;

/**
 * Базовый класс для паролей аутентификации
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
			throw new IllegalArgumentException("Пароль не может быть null");
		}
		salt = HashProvider.generateSalt();
		hash = getHashProvider().hash(password.toUpperCase(), salt);
		creationDate = getCurrentDate();
		active = true;
	}

	/**
	 * @return идентифкатор
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * Установить идентифкатор
	 * @param id идентифкатор
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return хеш пароля
	 */
	public String getHash()
	{
		return toString(hash);
	}

	/**
	 * Установить хеш пароля
	 * @param hash хеш пароля
	 */
	public void setHash(String hash)
	{
		this.hash = toByteArray(hash);
	}

	/**
	 * @return соль
	 */
	public String getSalt()
	{
		return toString(salt);
	}

	/**
	 * Установить соль
	 * @param salt соль
	 */
	public void setSalt(String salt)
	{
		this.salt = toByteArray(salt);
	}

	/**
	 * @return дата создания пароля
	 */
	public Calendar getCreationDate()
	{
		return creationDate;
	}

	/**
	 * Установить дату создания пароля
	 * @param creationDate дата создания пароля
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
	 * проверить входную строку на соотвествие пароля
	 * @param password пароль для проверки
	 * @return да/нет
	 */
	public boolean check(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		if (password == null)
		{
			throw new IllegalArgumentException("Пароль не может быть null");
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
