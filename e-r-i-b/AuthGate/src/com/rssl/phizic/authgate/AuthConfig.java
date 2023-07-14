package com.rssl.phizic.authgate;

/**
 * @author Gainanov
 * @ created 25.08.2009
 * @ $Author$
 * @ $Revision$
 */
public interface AuthConfig
{
	public String getProperty(String key);

	/**
	 * @param key ��� ��������
	 * @param defaultValue �������� �� ���������.
	 * @return �������� ��������
	 */
	public String getProperty(String key, String defaultValue);
}
