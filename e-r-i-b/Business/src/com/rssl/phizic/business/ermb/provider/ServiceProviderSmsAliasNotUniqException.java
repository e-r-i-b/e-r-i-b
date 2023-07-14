package com.rssl.phizic.business.ermb.provider;

import com.rssl.phizic.business.BusinessException;

/**
 * User: Moshenko
 * Date: 21.05.2013
 * Time: 16:53:01
 * Ошибка говорящая о том что псевдоним не уникален
 */
public class ServiceProviderSmsAliasNotUniqException extends BusinessException
{
	public ServiceProviderSmsAliasNotUniqException(String message)
	{
		super(message);
	}

	public ServiceProviderSmsAliasNotUniqException(Throwable cause)
	{
		super(cause);
	}

	public ServiceProviderSmsAliasNotUniqException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
