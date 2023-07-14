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

	private String title; //��������� �������.
	private String text;  //����� �� �������.

	/**
	 * @return ��������� �������
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * @param title ��������� �������
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * @return ����� �� �������
	 */
	public String getText()
	{
		return text;
	}

	/**
	 * @param text ����� �� �������
	 */
	public void setText(String text)
	{
		this.text = text;
	}
}
