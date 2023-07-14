package com.rssl.phizic.config.cache;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

/**
 * @author osminin
 * @ created 13.08.15
 * @ $Author$
 * @ $Revision$
 *
 * ������ ��� ������ � �����
 */
public class CacheConfig extends Config
{
	private static final String WAIT_MESSAGE_PROPERTY = "com.rssl.iccs.cache.wait.message";

	private String waitMessage;

	/**
	 * ����� ������ ������ ����������� ������ �����������.
	 * @param reader �����.
	 */
	public CacheConfig(PropertyReader reader)
	{
		super(reader);
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		waitMessage = getProperty(WAIT_MESSAGE_PROPERTY);
	}

	/**
	 * �������� ���������, ������� ���������� ���������� �������, ���� ������ ���������� �����,
	 * �� ���������� ����� ������� ������ ������ ������� ��� �� ���������� � ��� ��� �� ��������
	 *
	 * @return ���������, ������������ �������
	 */
	public String getWaitMessage()
	{
		return waitMessage;
	}
}
