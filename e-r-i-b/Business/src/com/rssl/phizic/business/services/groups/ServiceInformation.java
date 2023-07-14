package com.rssl.phizic.business.services.groups;

/**
 * @author akrenev
 * @ created 22.08.2014
 * @ $Author$
 * @ $Revision$
 *
 * ���������� � �������
 */

public class ServiceInformation
{
	private String key;
	private ServiceMode mode;

	/**
	 * @return ���� �������
	 */
	public String getKey()
	{
		return key;
	}

	/**
	 * ������ ���� �������
	 * @param key ����
	 */
	public void setKey(String key)
	{
		this.key = key;
	}

	/**
	 * @return ����� �������
	 */
	public ServiceMode getMode()
	{
		return mode;
	}

	/**
	 * ������ ����� �������
	 * @param mode �����
	 */
	public void setMode(ServiceMode mode)
	{
		this.mode = mode;
	}
}
