package com.rssl.phizic.business.dictionaries.pfp.configure;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

/**
 * @author akrenev
 * @ created 04.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * ������ ��� ������� � ����������� ���
 */

public class PFPConfigHelper extends Config
{
	public static final String PROPERTY_PREFIX = "com.rssl.pfp.settings.";

	public PFPConfigHelper(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * �������� �������� ���������
	 * @param key ���� ���������
	 * @return ��������
	 */
	public String getValue(String key)
	{
		return getProperty(PROPERTY_PREFIX.concat(key));
	}

	/**
	 * �������� �������� ��������� (���� �� ���������� �������� �������� �� ����, ����� �� properties.)
	 * @param key ���� ���������
	 * @return ��������
	 */
	public String getValueSafe(String key)
	{
		return getValue(key);
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
	}
}
