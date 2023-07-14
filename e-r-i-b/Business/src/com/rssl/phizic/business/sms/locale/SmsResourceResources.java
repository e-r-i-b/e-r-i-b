package com.rssl.phizic.business.sms.locale;

import com.rssl.phizic.locale.dynamic.resources.LanguageResource;

/**
 * @author koptyaev
 * @ created 08.06.2015
 * @ $Author$
 * @ $Revision$
 */
public class SmsResourceResources  extends LanguageResource
{
	private String text; //текст смс-сообщения

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}
}
