package com.rssl.phizic.business.news.locale;

import com.rssl.phizic.business.locale.dynamic.resources.multi.block.MultiBlockLanguageResources;

/**
 * @author koptyaev
 * @ created 30.09.2014
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings("JavaDoc")
public class NewsResources extends MultiBlockLanguageResources
{
	private String title;
	private String text;
	private String shortText;

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public String getShortText()
	{
		return shortText;
	}

	public void setShortText(String shortText)
	{
		this.shortText = shortText;
	}
}
