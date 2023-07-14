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
	 * @return название платфорты
	 */
	public String getPlatformName()
	{
		return platformName;
	}

	/**
	 * Установить название платформы
	 * @param platformName название платформы
	 */
	public void setPlatformName(String platformName)
	{
		this.platformName = platformName;
	}

	/**
	 * @return текст ошибки
	 */
	public String getErrorText()
	{
		return errorText;
	}

	/**
	 * Установить текст ошибки
	 * @param errorText Текст ошибки, отображаемый при несовместимости с поддерживаемой версией
	 */
	public void setErrorText(String errorText)
	{
		this.errorText = errorText;
	}
}
