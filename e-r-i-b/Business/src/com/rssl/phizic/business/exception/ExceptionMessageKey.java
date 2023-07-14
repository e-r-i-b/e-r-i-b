package com.rssl.phizic.business.exception;

import com.rssl.phizic.utils.StringHelper;

/**
 * @author osminin
 * @ created 17.03.15
 * @ $Author$
 * @ $Revision$
 *
 * Ключ для поиска сообщения в кеше
 * !!! обязательно должен быть final, так как используется как ключ в мапе. !!!
 */
public final class ExceptionMessageKey
{
	private String hash;
	private String tb;
	private String application;

	/**
	 * ctor
	 * @param hash хэш
	 * @param tb тербанк
	 * @param application приложение
	 */
	public ExceptionMessageKey(String hash, String tb, String application)
	{
		this.hash = hash;
		this.tb = tb;
		this.application = application;
	}

	/**
	 * @return хэш
	 */
	public String getHash()
	{
		return hash;
	}

	/**
	 * @return тербанк
	 */
	public String getTb()
	{
		return tb;
	}

	/**
	 * @return приложение
	 */
	public String getApplication()
	{
		return application;
	}

	@Override
	public boolean equals(Object object)
	{
		if (this == object)
		{
			return true;
		}

		if (object == null || this.getClass() != object.getClass())
		{
			return false;
		}

		ExceptionMessageKey messageKey = (ExceptionMessageKey) object;
		boolean isEqualTb = StringHelper.isEmpty(getTb()) || getTb().equals(messageKey.getTb());
		boolean isEqualHash = getHash().equals(messageKey.getHash());
		boolean isEqualApplication = StringHelper.isEmpty(getApplication()) || getApplication().equals(messageKey.getApplication());

		return isEqualHash && isEqualTb && isEqualApplication;
	}

	@Override
	public int hashCode()
	{
		int hashCode = getHash().hashCode();

		if (StringHelper.isNotEmpty(getTb()))
		{
			hashCode = 31 * hashCode + getTb().hashCode();
		}
		if (StringHelper.isNotEmpty(getApplication()))
		{
			hashCode = 31 * hashCode + getApplication().hashCode();
		}

		return hashCode;
	}
}
