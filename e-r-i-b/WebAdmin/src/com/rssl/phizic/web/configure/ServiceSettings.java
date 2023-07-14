package com.rssl.phizic.web.configure;

/**
 * @author akrenev
 * @ created 22.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * ��������� �������
 */

public class ServiceSettings
{
	private final String name;
	private final Mode mode;

	/**
	 * �����������
	 * @param name ���
	 * @param mode �����
	 */
	public ServiceSettings(String name, Mode mode)
	{
		this.name = name;
		this.mode = mode;
	}

	/**
	 * @return ��� �������
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return ����� �������
	 */
	public Mode getMode()
	{
		return mode;
	}

	public static enum Mode
	{
		modeAndTimeout,
		serviceProvider,
		;
	}
}
