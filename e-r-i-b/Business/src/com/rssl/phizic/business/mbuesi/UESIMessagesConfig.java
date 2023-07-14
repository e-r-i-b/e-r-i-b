package com.rssl.phizic.business.mbuesi;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

/**
 * ��������� ��� ��������� UESI
 * @author Pankin
 * @ created 30.01.15
 * @ $Author$
 * @ $Revision$
 */
public class UESIMessagesConfig extends Config
{
	private static final String MESSAGES_STORAGE_TIME_KEY = "uesi.messages.storage.time";
	private int storageTime;

	/**
	 * ����� ������ ������ ����������� ������ �����������.
	 *
	 * @param reader �����.
	 */
	public UESIMessagesConfig(PropertyReader reader)
	{
		super(reader);
	}

	protected void doRefresh() throws ConfigurationException
	{
		storageTime = getIntProperty(MESSAGES_STORAGE_TIME_KEY);
	}

	/**
	 * @return ������������ ����� � ����, � ������� �������� �������������� ���������
	 */
	public int getStorageTime()
	{
		return storageTime;
	}
}
