package com.rssl.phizic.logging.ermb;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

/**
 * ������ ��� �������� ����������� ���������� ������ �������� � ����/���  �� ������ ��������
 * @author lukina
 * @ created 07.11.2014
 * @ $Author$
 * @ $Revision$
 */
public class RequestCardByPhoneLogConfig  extends Config
{
	public static final String LOGGING_ON_KEY = "com.rssl.phizic.logging.ermb.RequestCardByPhoneLog.on";

	private boolean loggingOn;

	/**
	 * ����� ������ ������ ����������� ������ �����������.
	 *
	 * @param reader �����.
	 */
	public RequestCardByPhoneLogConfig(PropertyReader reader)
	{
		super(reader);
	}


	/**
	 * @return true - ����������� ��������
	 */
	public boolean isLoggingOn()
	{
		return loggingOn;
	}

	protected void doRefresh() throws ConfigurationException
	{
		loggingOn = getBoolProperty(LOGGING_ON_KEY);
	}
}
