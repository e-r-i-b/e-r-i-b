package com.rssl.phizic.business.mobileDevices.locale;

import com.rssl.phizic.locale.dynamic.resources.LanguageResource;

/**
 * @author koptyaev
 * @ created 10.10.2014
 * @ $Author$
 * @ $Revision$
 */
public class MobilePlatformResources extends LanguageResource
{
	private String platformName;
	private String errorText;

	/**
	 * @return �������� ���������
	 */
	public String getPlatformName()
	{
		return platformName;
	}

	/**
	 * ���������� �������� ���������
	 * @param platformName �������� ���������
	 */
	public void setPlatformName(String platformName)
	{
		this.platformName = platformName;
	}

	/**
	 * @return ����� ������
	 */
	public String getErrorText()
	{
		return errorText;
	}

	/**
	 * ���������� ����� ������
	 * @param errorText ����� ������, ������������ ��� ��������������� � �������������� �������
	 */
	public void setErrorText(String errorText)
	{
		this.errorText = errorText;
	}
}
