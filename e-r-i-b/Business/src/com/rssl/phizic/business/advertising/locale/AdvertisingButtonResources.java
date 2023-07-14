package com.rssl.phizic.business.advertising.locale;

import com.rssl.phizic.business.locale.dynamic.resources.multi.block.MultiBlockLanguageResources;

/**
 * @author komarov
 * @ created 25.09.2014
 * @ $Author$
 * @ $Revision$
 */
public class AdvertisingButtonResources extends MultiBlockLanguageResources
{
	private String title; //Заголовок кнопки.

	/**
	 * @return заголовок кнопки
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * @param title заголовок кнопки
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}
}
