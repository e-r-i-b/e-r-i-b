package com.rssl.phizicgate.stoplist.configs;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

/**
 * @author usachev
 * @ created 03.07.15
 * @ $Author$
 * @ $Revision$
 * ������ ��� �������� ����-�����
 */
public class StopListConfig extends Config
{

	private static final String STOP_LIST_SERVER_URL = "stop.list.servers.url";
	private static final String TIMEOUT = "stop.list.timeout";
	private static final String STOP_LIST_SYSTEM_CODE = "stop.list.system.code";

	private String stopListServerURL;
	private long timeout;
	private String stopListSystemCode;

	/**
	 * ����������� �� ���������
	 * @param reader ��������, �� �������� ����� ������ ������
	 */
	public StopListConfig(PropertyReader reader)
	{
		super(reader);
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		stopListServerURL = getProperty(STOP_LIST_SERVER_URL);
		timeout = getLongProperty(TIMEOUT);
		stopListSystemCode = getProperty(STOP_LIST_SYSTEM_CODE);
	}

	/**
	 * ��������� ������ �� ������-����������, ������������ ��������� �� ����-�����.
	 * @return ������ �� ������-����������
	 */
	public String getStopListServerURL()
	{
		return stopListServerURL;
	}

	/**
	 * ��������� ������� �������� ������� � ������� �������
	 * @return ����� ��������
	 */
	public long getTimeout()
	{
		return timeout;
	}

	/**
	 * ��������� ���� ������� ������� "����-����"
	 * @return ��� ������� ������� "����-����"
	 */
	public String getStopListSystemCode()
	{
		return stopListSystemCode;
	}
}
