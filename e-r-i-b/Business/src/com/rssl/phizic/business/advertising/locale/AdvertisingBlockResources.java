package com.rssl.phizic.business.advertising.locale;

import com.rssl.phizic.business.locale.dynamic.resources.multi.block.MultiBlockLanguageResources;

/**
 * @author komarov
 * @ created 25.09.2014
 * @ $Author$
 * @ $Revision$
 */
public class AdvertisingBlockResources extends MultiBlockLanguageResources
{

	private String title; //Заголовок баннера.
	private String text;  //Текст на баннере.

	/**
	 * @return Заголовок баннера
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * @param title Заголовок баннера
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * @return Текст на баннере
	 */
	public String getText()
	{
		return text;
	}

	/**
	 * @param text Текст на баннере
	 */
	public void setText(String text)
	{
		this.text = text;
	}
}
