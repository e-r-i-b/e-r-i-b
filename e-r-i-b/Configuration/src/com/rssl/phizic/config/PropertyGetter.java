package com.rssl.phizic.config;

/**
 * ������� ������� ���������� �������� ��������.
 *
 * @author bogdanov
 * @ created 17.07.2013
 * @ $Author$
 * @ $Revision$
 */

public interface PropertyGetter
{
	/**
	 * @param key ��� ��������
	 * @return �������� ��������
	 */
	public String getProperty(String key);
}
