package com.rssl.phizic.business.cache;

import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.cache.CacheConfig;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author osminin
 * @ created 13.08.15
 * @ $Author$
 * @ $Revision$
 *
 * ����������, ��������������� � ���, ��� ����� ��� �� �������� ���� ����������� � ��� ��� ������ ��� �� ��������.
 */
public class BusinessWaitCreateCacheException extends BusinessLogicException
{
	@Override
	public String getMessage()
	{
		String message = ConfigFactory.getConfig(CacheConfig.class).getWaitMessage();

		return StringHelper.isEmpty(message) ? super.getMessage() : message;
	}

	/**
	 * ctor
	 * @param message ��������� �� ������
	 * @param throwable ����������
	 */
	public BusinessWaitCreateCacheException(String message, Throwable throwable)
	{
		super(message, throwable);
	}
}
